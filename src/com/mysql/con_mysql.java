package com.mysql;
import java.sql.*;

public class con_mysql {
    public static void main(String[] args) {
        Connection conn = null;
        try {
            // 数据库连接字符串
            String url = "jdbc:myUser user = validateCredentials(username, password);sql://localhost:3306/书店管理信息系统?characterEncoding=utf8";
            // 数据库用户名和密码
            String username = "root";
            String password = "YES";

            // 注册JDBC驱动
            Class.forName("com.mysql.jdbc.Driver");

            // 打开连接
            System.out.println("连接数据库...");
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("连接成功");

            // 可以在这里执行数据库操作

            // 关闭连接
            conn.close();
            System.out.println("数据库连接已关闭");

        } catch (SQLException se) {
            se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接
            try {
                if (conn != null) {
                    conn.close();
                    System.out.println("连接已关闭");
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
}