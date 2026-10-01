package com.phamchien.payment.service;

import com.phamchien.payment.config.Config;
import com.phamchien.payment.util.VNPayUtil;
import java.util.LinkedHashMap;
import java.util.Map;

public class VNPayService {

    public String createPaymentUrl(
            String orderCode,
            long amount,
            String orderInfo,
            String ipAddress) {

        Map<String, String> params =
                new LinkedHashMap<>();

        params.put(
                "vnp_Version",
                "2.1.0"
        );

        params.put(
                "vnp_Command",
                "pay"
        );

        params.put(
                "vnp_TmnCode",
                Config.VNP_TMN_CODE
        );

        // VNPAY yêu cầu số tiền x100
        params.put(
                "vnp_Amount",
                String.valueOf(amount * 100)
        );

        params.put(
                "vnp_CurrCode",
                "VND"
        );

        params.put(
                "vnp_TxnRef",
                orderCode
        );

        params.put(
                "vnp_OrderInfo",
                orderInfo
        );

        params.put(
                "vnp_OrderType",
                "other"
        );

        params.put(
                "vnp_Locale",
                "vn"
        );

        params.put(
                "vnp_ReturnUrl",
                Config.VNP_RETURN_URL
        );

        params.put(
                "vnp_IpAddr",
                ipAddress
        );

        params.put(
                "vnp_CreateDate",
                VNPayUtil.now()
        );

        params.put(
                "vnp_ExpireDate",
                VNPayUtil.expireDate(15)
        );

        String hashData =
                VNPayUtil.buildHashData(params);

        String secureHash =
                VNPayUtil.hmacSHA512(
                        Config.VNP_HASH_SECRET,
                        hashData
                );

        return Config.VNP_PAY_URL
                + "?"
                + VNPayUtil.buildQueryString(params)
                + "&vnp_SecureHash="
                + secureHash;
    }

    public boolean verifyReturnHash(
            Map<String, String> params) {

        String receivedHash =
                params.get("vnp_SecureHash");

        if (receivedHash == null) {
            return false;
        }

        Map<String, String> data =
                new LinkedHashMap<>(params);

        data.remove("vnp_SecureHash");
        data.remove("vnp_SecureHashType");

        String hashData =
                VNPayUtil.buildHashData(data);

        String calculatedHash =
                VNPayUtil.hmacSHA512(
                        Config.VNP_HASH_SECRET,
                        hashData
                );

        return VNPayUtil.constantTimeEquals(
                calculatedHash,
                receivedHash
        );
    }
}
