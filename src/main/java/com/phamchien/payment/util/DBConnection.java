package com.phamchien.payment.util;

import com.phamchien.payment.config.Config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {

    private DBConnection() {
    }

    public static Connection getConnection()
            throws SQLException {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException(
                    "Khong tim thay MySQL JDBC Driver.",
                    e
            );
        }

        return DriverManager.getConnection(
                Config.DB_URL,
                Config.DB_USER,
                Config.DB_PASSWORD
        );
    }
}