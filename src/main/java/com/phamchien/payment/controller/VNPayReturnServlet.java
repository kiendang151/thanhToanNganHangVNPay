package com.phamchien.payment.controller;

import com.phamchien.payment.dao.PaymentDAO;
import com.phamchien.payment.dao.PaymentDAO.Payment;
import com.phamchien.payment.service.EmailService;
import com.phamchien.payment.service.VNPayService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/vnpay-return")
public class VNPayReturnServlet
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
            throws ServletException, IOException {

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

        boolean validHash =
                vnPayService.verifyReturnHash(
                        params
                );

        String orderCode =
                params.get("vnp_TxnRef");

        String responseCode =
                params.get("vnp_ResponseCode");

        String transactionStatus =
                params.get(
                        "vnp_TransactionStatus"
                );

        String transactionNo =
                params.get(
                        "vnp_TransactionNo"
                );

        boolean success =
                validHash
                && "00".equals(responseCode)
                && "00".equals(transactionStatus);

        String emailStatus =
                "NOT_SENT";

        String error =
                null;

        try {

            Payment payment =
                    paymentDAO.findByOrderCode(
                            orderCode
                    );

            if (payment == null) {

                success = false;

                error =
                        "Khong tim thay don hang.";
            }

            if (success && payment != null) {

                String vnpAmountText =
                        params.get("vnp_Amount");

                if (vnpAmountText == null) {

                    success = false;

                    error =
                            "VNPAY khong tra ve so tien.";
                } else {

                    long vnpAmount =
                            Long.parseLong(
                                    vnpAmountText
                            ) / 100;

                    if (vnpAmount
                            != payment.amount) {

                        success = false;

                        error =
                                "So tien giao dich "
                                + "khong khop.";
                    }
                }
            }

            if (payment != null) {

                paymentDAO.updateResult(
                        orderCode,
                        transactionNo,
                        responseCode,
                        transactionStatus,
                        success
                                ? "PAID"
                                : "FAILED"
                );

                if (success) {

                    Payment updatedPayment =
                            paymentDAO.findByOrderCode(
                                    orderCode
                            );

                    if ("SENT".equalsIgnoreCase(
                            updatedPayment.emailStatus)) {

                        emailStatus =
                                "SENT";

                    } else {

                        try {

                            emailService
                                    .sendPaymentSuccessEmail(
                                            updatedPayment
                                    );

                            paymentDAO
                                    .updateEmailStatus(
                                            orderCode,
                                            "SENT"
                                    );

                            emailStatus =
                                    "SENT";

                        } catch (Exception mailError) {

                            paymentDAO
                                    .updateEmailStatus(
                                            orderCode,
                                            "FAILED"
                                    );

                            emailStatus =
                                    "FAILED";

                            error =
                                    "Thanh toan thanh cong "
                                    + "nhung gui email loi: "
                                    + mailError.getMessage();
                        }
                    }
                }
            }

        } catch (Exception e) {

            error =
                    e.getMessage();
        }

        request.setAttribute(
                "validHash",
                validHash
        );

        request.setAttribute(
                "success",
                success
        );

        request.setAttribute(
                "orderCode",
                orderCode
        );

        request.setAttribute(
                "responseCode",
                responseCode
        );

        request.setAttribute(
                "transactionStatus",
                transactionStatus
        );

        request.setAttribute(
                "transactionNo",
                transactionNo
        );

        request.setAttribute(
                "emailStatus",
                emailStatus
        );

        request.setAttribute(
                "error",
                error
        );

        request.getRequestDispatcher(
                "/result.jsp"
        ).forward(
                request,
                response
        );
    }
}
