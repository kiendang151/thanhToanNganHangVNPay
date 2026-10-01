package com.phamchien.payment.controller;

import com.phamchien.payment.dao.PaymentDAO;
import com.phamchien.payment.service.VNPayService;
import com.phamchien.payment.util.VNPayUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/payment")
public class PaymentServlet extends HttpServlet {

    private final PaymentDAO paymentDAO =
            new PaymentDAO();

    private final VNPayService vnPayService =
            new VNPayService();

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            String email =
                    request.getParameter("email");

            String amountText =
                    request.getParameter("amount");

            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException(
                        "Email khong duoc de trong."
                );
            }

            long amount =
                    Long.parseLong(amountText);

            if (amount < 1000) {
                throw new IllegalArgumentException(
                        "So tien phai >= 1.000 VND."
                );
            }

            String orderCode =
                    VNPayUtil.newOrderCode();

            paymentDAO.createPayment(
                    orderCode,
                    amount,
                    email
            );

            String ipAddress =
                    request.getRemoteAddr();

            if ("0:0:0:0:0:0:0:1"
                    .equals(ipAddress)) {

                ipAddress = "127.0.0.1";
            }

            String paymentUrl =
                    vnPayService.createPaymentUrl(
                            orderCode,
                            amount,
                            "Thanh toan don hang "
                            + orderCode,
                            ipAddress
                    );

            response.sendRedirect(
                    paymentUrl
            );

        } catch (Exception e) {

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "/payment.jsp"
            ).forward(
                    request,
                    response
            );
        }
    }
}
