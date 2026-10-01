package com.phamchien.payment.dao;

import com.phamchien.payment.util.DBConnection;
import java.sql.*;

public class PaymentDAO {

    public void createPayment(
            String orderCode,
            long amount,
            String email)
            throws SQLException {

        String sql =
                "INSERT INTO Payments "
                + "(order_code, amount, email, "
                + "payment_status, email_status) "
                + "VALUES (?, ?, ?, 'PENDING', 'NOT_SENT')";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, orderCode);
            ps.setLong(2, amount);
            ps.setString(3, email);

            ps.executeUpdate();
        }
    }

    public Payment findByOrderCode(
            String orderCode)
            throws SQLException {

        String sql =
                "SELECT * FROM Payments "
                + "WHERE order_code = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, orderCode);

            try (ResultSet rs =
                         ps.executeQuery()) {

                if (!rs.next()) {
                    return null;
                }

                Payment payment =
                        new Payment();

                payment.id =
                        rs.getInt("id");

                payment.orderCode =
                        rs.getString("order_code");

                payment.amount =
                        rs.getLong("amount");

                payment.email =
                        rs.getString("email");

                payment.transactionNo =
                        rs.getString("transaction_no");

                payment.responseCode =
                        rs.getString("response_code");

                payment.transactionStatus =
                        rs.getString("transaction_status");

                payment.paymentStatus =
                        rs.getString("payment_status");

                payment.emailStatus =
                        rs.getString("email_status");

                return payment;
            }
        }
    }

    public void updateResult(
            String orderCode,
            String transactionNo,
            String responseCode,
            String transactionStatus,
            String paymentStatus)
            throws SQLException {

        String sql =
                "UPDATE Payments SET "
                + "transaction_no = ?, "
                + "response_code = ?, "
                + "transaction_status = ?, "
                + "payment_status = ?, "
                + "updated_at = CURRENT_TIMESTAMP "
                + "WHERE order_code = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, transactionNo);
            ps.setString(2, responseCode);
            ps.setString(3, transactionStatus);
            ps.setString(4, paymentStatus);
            ps.setString(5, orderCode);

            ps.executeUpdate();
        }
    }

    public void updateEmailStatus(
            String orderCode,
            String emailStatus)
            throws SQLException {

        String sql =
                "UPDATE Payments SET "
                + "email_status = ?, "
                + "updated_at = CURRENT_TIMESTAMP "
                + "WHERE order_code = ?";

        try (Connection connection =
                     DBConnection.getConnection();
             PreparedStatement ps =
                     connection.prepareStatement(sql)) {

            ps.setString(1, emailStatus);
            ps.setString(2, orderCode);

            ps.executeUpdate();
        }
    }

    public static class Payment {

        public int id;
        public String orderCode;
        public long amount;
        public String email;
        public String transactionNo;
        public String responseCode;
        public String transactionStatus;
        public String paymentStatus;
        public String emailStatus;
    }
}
