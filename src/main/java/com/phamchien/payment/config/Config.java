package com.phamchien.payment.config;

public final class Config {

    private Config() {
    }

    // ==============================
    // VNPAY SANDBOX
    // ==============================

    public static final String VNP_PAY_URL =
            "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static final String VNP_TMN_CODE =
                "SLQPZH3M";

    public static final String VNP_HASH_SECRET =
            "MEXFJDZBNYMXMMRBCZZREYVKJTWZAETU";

    public static final String BASE_URL =
            envOrDefault("BASE_URL", "http://localhost:8080");

    public static final String VNP_RETURN_URL =
            BASE_URL + "/vnpay-return";

    public static final String VNP_IPN_URL =
            BASE_URL + "/vnpay-ipn";


    // ==============================
    // MYSQL - AIVEN
    // ==============================

    public static final String DB_URL =
            envOrDefault(
                    "DB_URL",
                    "jdbc:mysql://mysql-emaillist-kiendang151-6380.e.aivencloud.com:11503/PaymentDemo"
                    + "?sslMode=REQUIRED"
                    + "&serverTimezone=Asia/Ho_Chi_Minh"
                    + "&characterEncoding=UTF-8"
            );

    public static final String DB_USER =
            envOrDefault("DB_USER", "avnadmin");

    public static final String DB_PASSWORD =
            envOrDefault("DB_PASSWORD", "AVNS_ZaZKBJ7Vml8nNC2OkeG");


    // ==============================
    // BREVO API
    // ==============================

    public static final String BREVO_API_URL =
            "https://api.brevo.com/v3/smtp/email";

    public static final String BREVO_API_KEY =
            envOrDefault("BREVO_API_KEY", "");

    public static final String BREVO_SENDER_NAME =
            "sendmailC14";

    public static final String BREVO_SENDER_EMAIL =
            "kiendang151@gmail.com";


    // ==============================
    // ENVIRONMENT VARIABLE
    // ==============================

    private static String envOrDefault(
            String key,
            String defaultValue) {

        String value = System.getenv(key);

        if (value == null || value.isBlank()) {
            return defaultValue;
        }

        return value;
    }
}