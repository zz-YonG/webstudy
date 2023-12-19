package com.gzy.bank.utils;

import java.sql.*;
import java.util.ResourceBundle;

public class DBUtils {

    private DBUtils(){}
    private static ResourceBundle bundle = ResourceBundle.getBundle("com/gzy/bank/resources/jdbc");
    private static String driver = bundle.getString("driver");
    private static String url = bundle.getString("url");
    private static String user = bundle.getString("user");
    private static String password = bundle.getString("password");
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.err.println("Failed to load database driver: " + e.getMessage()); // 添加额外的打印语句
            throw new RuntimeException("Failed to initialize database driver", e);
        }
    }


    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(url,user,password);
        return conn;
    }

    public static void close(Connection conn, Statement ps, ResultSet rs){
        if(rs!=null){
            try {
                rs.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(ps!=null){
            try {
                ps.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
