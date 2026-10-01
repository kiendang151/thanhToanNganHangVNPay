package com.phamchien.payment.controller;

import com.phamchien.payment.dao.PaymentDAO;
import com.phamchien.payment.dao.PaymentDAO.Payment;
import com.phamchien.payment.service.EmailService;
import com.phamchien.payment.service.VNPayService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/vnpay-ipn")
public class VNPayIPNServlet
        extends HttpServlet {

    private final VNPayService vnPayService =
            new VNPayService();

    private final PaymentDAO paymentDAO =
            new PaymentDAO();

    private final EmailService emailService =
            new EmailService();

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        process(request, response);
    }

    private void process(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.setContentType(
                "application/json;charset=UTF-8"
        );

        Map<String, String> params =
                new HashMap<>();

        Enumeration<String> names =
                request.getParameterNames();

        while (names.hasMoreElements()) {

            String name =
                    names.nextElement();

            if (name.startsWith("vnp_")) {

                params.put(
                        name,
                        request.getParameter(name)
                );
            }
        }

        try {

            if (!vnPayService
                    .verifyReturnHash(params)) {

                response.getWriter().print(
                        "{\"RspCode\":\"97\","
                        + "\"Message\":"
                        + "\"Invalid signature\"}"
                );

                return;
            }

            String orderCode =
                    params.get("vnp_TxnRef");

            Payment payment =
                    paymentDAO.findByOrderCode(
                            orderCode
                    );

            if (payment == null) {

                response.getWriter().print(
                        "{\"RspCode\":\"01\","
                        + "\"Message\":"
                        + "\"Order not found\"}"
                );

                return;
            }

            long vnpAmount =
                    Long.parseLong(
                            params.get("vnp_Amount")
                    ) / 100;

            if (vnpAmount != payment.amount) {

                response.getWriter().print(
                        "{\"RspCode\":\"04\","
                        + "\"Message\":"
                        + "\"Invalid amount\"}"
                );

                return;
            }

            String responseCode =
                    params.get("vnp_ResponseCode");

            String transactionStatus =
                    params.get(
                            "vnp_TransactionStatus"
                    );

            boolean success =
                    "00".equals(responseCode)
                    && "00".equals(
                            transactionStatus
                    );

            paymentDAO.updateResult(
                    orderCode,
                    params.get("vnp_TransactionNo"),
                    responseCode,
                    transactionStatus,
                    success
                            ? "PAID"
                            : "FAILED"
            );

            if (success
                    && !"SENT".equalsIgnoreCase(
                            payment.emailStatus)) {

                try {

                    Payment updated =
                            paymentDAO
                                    .findByOrderCode(
                                            orderCode
                                    );

                    emailService
                            .sendPaymentSuccessEmail(
                                    updated
                            );

                    paymentDAO
                            .updateEmailStatus(
                                    orderCode,
                                    "SENT"
                            );

                } catch (Exception e) {

                    paymentDAO
                            .updateEmailStatus(
                                    orderCode,
                                    "FAILED"
                            );
                }
            }

            response.getWriter().print(
                    "{\"RspCode\":\"00\","
                    + "\"Message\":"
                    + "\"Confirm Success\"}"
            );

        } catch (Exception e) {

            response.getWriter().print(
                    "{\"RspCode\":\"99\","
                    + "\"Message\":"
                    + "\"Server error\"}"
            );
        }
    }
}
