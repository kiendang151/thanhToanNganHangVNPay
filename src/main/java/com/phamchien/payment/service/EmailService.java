package com.phamchien.payment.service;

import com.phamchien.payment.config.Config;
import com.phamchien.payment.dao.PaymentDAO.Payment;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class EmailService {

    private final HttpClient httpClient =
            HttpClient.newHttpClient();

    public void sendPaymentSuccessEmail(
            Payment payment) throws Exception {

        String subject =
                "Xac nhan thanh toan - "
                + payment.orderCode;

        String body =
                "Xin chao,\n\n"
                + "Thanh toan cua ban da thanh cong.\n\n"
                + "Ma don hang: "
                + payment.orderCode + "\n"
                + "So tien: "
                + payment.amount + " VND\n"
                + "Ma giao dich VNPAY: "
                + (payment.transactionNo == null
                    ? ""
                    : payment.transactionNo) + "\n"
                + "Trang thai: DA THANH TOAN\n\n"
                + "Cam on ban.";

        String json =
                "{"
                + "\"sender\":{"
                + "\"name\":\""
                + escapeJson(Config.BREVO_SENDER_NAME)
                + "\","
                + "\"email\":\""
                + escapeJson(Config.BREVO_SENDER_EMAIL)
                + "\""
                + "},"
                + "\"to\":[{"
                + "\"email\":\""
                + escapeJson(payment.email)
                + "\""
                + "}],"
                + "\"subject\":\""
                + escapeJson(subject)
                + "\","
                + "\"textContent\":\""
                + escapeJson(body)
                + "\""
                + "}";

        HttpRequest request =
                HttpRequest.newBuilder()
                        .uri(URI.create(
                                Config.BREVO_API_URL))
                        .header(
                                "accept",
                                "application/json")
                        .header(
                                "api-key",
                                Config.BREVO_API_KEY)
                        .header(
                                "content-type",
                                "application/json")
                        .POST(
                                HttpRequest.BodyPublishers
                                        .ofString(
                                                json,
                                                StandardCharsets.UTF_8))
                        .build();

        HttpResponse<String> response =
                httpClient.send(
                        request,
                        HttpResponse.BodyHandlers
                                .ofString(StandardCharsets.UTF_8));

        int status =
                response.statusCode();

        if (status < 200 || status >= 300) {
            throw new IllegalStateException(
                    "Brevo API loi. HTTP "
                    + status
                    + ": "
                    + response.body()
            );
        }
    }

    private String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\r", "")
                .replace("\n", "\\n");
    }
}