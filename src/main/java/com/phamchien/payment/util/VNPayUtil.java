package com.phamchien.payment.util;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.TreeMap;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public final class VNPayUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    private VNPayUtil() {
    }

    public static String hmacSHA512(
            String secretKey,
            String data) {

        try {
            Mac hmac =
                    Mac.getInstance("HmacSHA512");

            SecretKeySpec secretKeySpec =
                    new SecretKeySpec(
                            secretKey.getBytes(StandardCharsets.UTF_8),
                            "HmacSHA512"
                    );

            hmac.init(secretKeySpec);

            byte[] result =
                    hmac.doFinal(
                            data.getBytes(StandardCharsets.UTF_8)
                    );

            StringBuilder hash =
                    new StringBuilder();

            for (byte b : result) {
                hash.append(
                        String.format("%02x", b)
                );
            }

            return hash.toString();

        } catch (Exception e) {
            throw new IllegalStateException(
                    "Khong tao duoc HMAC SHA512",
                    e
            );
        }
    }

    public static String buildHashData(
            Map<String, String> params) {

        StringBuilder data =
                new StringBuilder();

        boolean first = true;

        for (Map.Entry<String, String> entry
                : new TreeMap<>(params).entrySet()) {

            String value =
                    entry.getValue();

            if (value == null || value.isEmpty()) {
                continue;
            }

            if (!first) {
                data.append("&");
            }

            data.append(
                    URLEncoder.encode(
                            entry.getKey(),
                            StandardCharsets.UTF_8
                    )
            );

            data.append("=");

            data.append(
                    URLEncoder.encode(
                            value,
                            StandardCharsets.UTF_8
                    )
            );

            first = false;
        }

        return data.toString();
    }

    public static String buildQueryString(
            Map<String, String> params) {

        StringBuilder query =
                new StringBuilder();

        boolean first = true;

        for (Map.Entry<String, String> entry
                : new TreeMap<>(params).entrySet()) {

            String value =
                    entry.getValue();

            if (value == null || value.isEmpty()) {
                continue;
            }

            if (!first) {
                query.append("&");
            }

            query.append(
                    URLEncoder.encode(
                            entry.getKey(),
                            StandardCharsets.UTF_8
                    )
            );

            query.append("=");

            query.append(
                    URLEncoder.encode(
                            value,
                            StandardCharsets.UTF_8
                    )
            );

            first = false;
        }

        return query.toString();
    }

    public static String now() {
        return LocalDateTime.now(
                ZoneId.of("Asia/Ho_Chi_Minh")
        ).format(FORMATTER);
    }

    public static String expireDate(
            int minutes) {

        return LocalDateTime.now(
                ZoneId.of("Asia/Ho_Chi_Minh")
        ).plusMinutes(minutes)
         .format(FORMATTER);
    }

    public static String newOrderCode() {

        return "DH"
                + System.currentTimeMillis();
    }

    public static boolean constantTimeEquals(
            String a,
            String b) {

        if (a == null || b == null) {
            return false;
        }

        return MessageDigest.isEqual(
                a.toLowerCase()
                        .getBytes(StandardCharsets.UTF_8),
                b.toLowerCase()
                        .getBytes(StandardCharsets.UTF_8)
        );
    }
}
