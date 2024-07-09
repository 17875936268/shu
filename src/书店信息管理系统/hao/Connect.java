package 书店信息管理系统.hao;

import java.sql.*;

public class Connect {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/书店管理信息系统?characterEncoding=utf8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "YES";

    // 加载JDBC驱动（只需要加载一次）
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }

    // 查询表中的数据，使用Statement
    public static ResultSet query(String sql, Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            return st.executeQuery(sql);
        }
    }

    // 查询表中的数据，使用PreparedStatement
    public static ResultSet query(String sql, Connection conn, Object... params) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    setParameter(pstmt, i + 1, params[i]);
                }
            }
            return pstmt.executeQuery();
        }
    }

    // 添加数据，删除数据，修改数据，使用Statement
    public static void executeUpdate(String sql, Connection conn) throws SQLException {
        try (Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        }
    }

    // 添加数据，删除数据，修改数据，使用PreparedStatement
    public static void executeUpdate(String sql, Connection conn, Object... params) throws SQLException {
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    setParameter(pstmt, i + 1, params[i]);
                }
            }
            pstmt.executeUpdate();
        }
    }

    // 设置PreparedStatement的参数
    private static void setParameter(PreparedStatement pstmt, int index, Object value) throws SQLException {
        if (value == null) {
            pstmt.setNull(index, Types.NULL);
        } else if (value instanceof String) {
            pstmt.setString(index, (String) value);
        } else if (value instanceof Integer) {
            pstmt.setInt(index, (Integer) value);
            // ... 可以添加其他类型的设置方法 ...
        } else {
            throw new SQLException("Unsupported parameter type: " + value.getClass().getName());
        }
    }

    // 关闭连接
    public static void closeConnection(Connection conn) throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    // PreparedStatementSetter接口，用于设置PreparedStatement的参数
    public interface PreparedStatementSetter {
        void setValues(PreparedStatement pstmt) throws SQLException;
    }
}