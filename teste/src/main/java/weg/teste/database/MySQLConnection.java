package weg.teste.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {

    private static final String URL = "jdbc:mysql://hopper.proxy.rlwy.net:13611/railway";
    private static final String USER = "root";
    private static final String PASSWORD = "bpjqiXKuLvbksWeQbwlPrieLGCvlLTFh";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
