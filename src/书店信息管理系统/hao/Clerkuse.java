package 书店信息管理系统.hao;
//包含了用于构建图形用户界面（GUI）的类和接口，如各种组件（按钮、文本框、表格等）以及相关的事件处理机制。
import javax.swing.*;
//用于创建和管理 JTable（表格组件）的数据模型，它定义了表格的结构和数据操作方法。
import javax.swing.table.DefaultTableModel;
//用于创建基本的图形界面元素和处理图形相关的操作。
import java.awt.*;

import java.sql.Connection;
//用于预编译的 SQL 语句，可以设置参数并执行，提高了数据库操作的效率和安全性。
import java.sql.PreparedStatement;
//存储执行查询操作后返回的结果集，通过它可以遍历和获取查询结果的数据
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
//格式化数字，特别是十进制数字，可以按照指定的格式规则将数字转换为特定的字符串表示形式。
import java.text.DecimalFormat;

public class Clerkuse {
    private JFrame frame;
    private JTable bookTable; 
    private JTable salesTable;
    private DefaultTableModel bookTableModel;
    private DefaultTableModel salesTableModel;

    public Clerkuse() {
        frame = new JFrame("书店信息管理系统");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(1000, 800));
//        创建了一个 JPanel 对象 mainPanel，并使用 BorderLayout 布局管理器进行布局管理。
        JPanel mainPanel = new JPanel(new BorderLayout());
//        createBookPanel() 方法创建的面板添加到 mainPanel 的中心区域（CENTER）。
        mainPanel.add(createBookPanel(), BorderLayout.CENTER);
//        createBookPanel() 创建的面板会显示在 mainPanel 的中心位置，createSalesPanel() 创建的面板会显示在 mainPanel 的底部位置
        mainPanel.add(createSalesPanel(), BorderLayout.SOUTH);

        frame.add(mainPanel);
//        JFrame（即 frame）中的组件的首选大小，自动调整窗口的大小，使其刚好能够容纳所有组件。
        frame.pack();
        frame.setVisible(true);
    }
//   书籍面板
    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new BorderLayout());
//        bookTableModel 则是数据的模型
        bookTableModel = new DefaultTableModel(new String[]{"BookId", "Category", "BookName", "Price", "Description", "Store"}, 0);
        bookTable = new JTable(bookTableModel);
        panel.add(new JScrollPane(bookTable), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
//        创建了一个名为"修改价格"的按钮 updateButton，并为其添加了点击事件监听器，当点击该按钮时会执行 updateBookGUI() 方法
        JButton updateButton = new JButton("修改价格");
        updateButton.addActionListener(e -> updateBookGUI());
        JButton recordSaleButton = new JButton("记录销售");
        recordSaleButton.addActionListener(e -> recordSaleGUI());
        JButton querySalesButton = new JButton("根据日期查询销售");
        querySalesButton.addActionListener(e -> querySalesByDate(new Date(System.currentTimeMillis())));

        JButton calculateProfitButton = new JButton("统计销售价格");
        calculateProfitButton.addActionListener(e -> calculateProfitOnDate(new Date(System.currentTimeMillis())));

        buttonPanel.add(updateButton);
        buttonPanel.add(recordSaleButton);
        buttonPanel.add(querySalesButton);

        buttonPanel.add(calculateProfitButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        queryBooks();
        return panel;
    }
// 销售面板
    private JPanel createSalesPanel() {
        JPanel panel = new JPanel();
        String[] columnNames = {"BookId", "Quantity", "TotalPrice", "SaleDate"};
        salesTableModel = new DefaultTableModel(columnNames, 0);
        salesTable = new JTable(salesTableModel);
        panel.add(new JScrollPane(salesTable));

        return panel;
    }

    // 销售记录GUI
    private void recordSaleGUI() {
        int row = bookTable.getSelectedRow();
//        没有选中任何行（row 为 -1）
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "请选择要售出的图书");
            return;
        }
        String bookId = bookTableModel.getValueAt(row, 0).toString();
        int quantity = Integer.parseInt(JOptionPane.showInputDialog(frame, "请输入售出数量"));

        if (quantity <= 0) {
            JOptionPane.showMessageDialog(frame, "无效的销售数量");
            return;
        }
        double totalPrice = calculateTotalPrice(Long.parseLong(bookId), quantity);
        sellBookAndUpdateStock(bookId, quantity, totalPrice);
    }

    // 更新销售信息并更新库存
    private void sellBookAndUpdateStock(String bookId, int quantity, double totalPrice) {
        try (Connection conn = Connect.getConnection()) {
            if (conn!= null) {
                // 获取图书价格
                double bookPrice = getBookPrice(conn, bookId);

                // 插入销售记录
                insertSalesRecord(conn, bookId, bookPrice, quantity, totalPrice);

                // 更新图书库存
                updateBookStock(conn, bookId, quantity);

                JOptionPane.showMessageDialog(frame, quantity + "本图书销售成功");
            } else {
                showErrorDialog("数据库连接失败");
            }
        } catch (SQLException e) {
            showErrorDialog("销售图书并更新库存时出错");
            e.printStackTrace();
        }
    }

    // 获取图书价格
    private double getBookPrice(Connection conn, String bookId) throws SQLException {
        String sql = "SELECT Price FROM BookInfo WHERE BookId =?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("Price");
                }
            }
        }
        return 0.0;
    }

    // 插入销售记录
    private void insertSalesRecord(Connection conn, String bookId, double bookPrice, int quantity, double totalPrice) throws SQLException {
        String sql = "INSERT INTO SalesInfo (BookId, Price, Quantity, TotalPrice, SaleDate) VALUES (?,?,?,?,?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, bookId);
            pstmt.setDouble(2, bookPrice);
            pstmt.setInt(3, quantity);
            pstmt.setDouble(4, totalPrice);
            pstmt.setDate(5, new Date(System.currentTimeMillis()));
            pstmt.executeUpdate();
        }
    }

    // 更新图书库存
    private void updateBookStock(Connection conn, String bookId, int quantity) throws SQLException {
        String sql = "UPDATE BookInfo SET Store = Store -? WHERE BookId =?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, quantity);
            pstmt.setString(2, bookId);
            pstmt.executeUpdate();
        }
    }
    private double calculateTotalPrice(long bookId, int quantity) {
        double price = getBookPriceFromDB(bookId);
        return price * quantity;
    }


    private double getBookPriceFromDB(long bookId) {
        double price = 0.0;
        try (Connection conn = Connect.getConnection()) {
            if (conn!= null) {
                String sql = "SELECT Price FROM BookInfo WHERE BookId =?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setLong(1, bookId);
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            price = rs.getDouble("Price");
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "获取图书价格时出错");
        }
        return price;
    }


    // 新增方法：根据购书日期查询销售情况
    private void querySalesByDate(Date saleDate) {
        salesTableModel.setRowCount(0);

        try (Connection conn = Connect.getConnection()) {
            if (conn!= null) {
                String sql = "SELECT BookId, Quantity, TotalPrice, SaleDate FROM SalesInfo WHERE SaleDate =?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setDate(1, new java.sql.Date(saleDate.getTime()));
                    try (ResultSet rs = pstmt.executeQuery()) {
                        while (rs.next()) {
                            Object[] rowData = {
                                    rs.getString("BookId"),
                                    rs.getString("Quantity"),
                                    rs.getString("TotalPrice"),
                                    rs.getDate("SaleDate")
                            };
                            salesTableModel.addRow(rowData);
                        }
                    }
                }
            } else {
                showErrorDialog("数据库连接失败");
            }
        } catch (SQLException e) {
            showErrorDialog("查询销售情况时出错");
            e.printStackTrace();
        }
    }

    // 统计特定日期销售价格
    private void calculateProfitOnDate(Date specificDate) {
        double profit = 0.0;

        try (Connection conn = Connect.getConnection()) {
            if (conn!= null) {
                String sql = "SELECT SUM(CAST(TotalPrice AS DECIMAL(10,2))) FROM SalesInfo WHERE SaleDate =?";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setDate(1, new java.sql.Date(specificDate.getTime()));
                    try (ResultSet rs = pstmt.executeQuery()) {
                        if (rs.next()) {
                            profit = rs.getDouble(1);
                        }
                    }
                }
            } else {
                showErrorDialog("数据库连接失败");
            }
        } catch (SQLException e) {
            showErrorDialog("统计特定日期销售利润时出错");
            e.printStackTrace();
        }
        JOptionPane.showMessageDialog(frame, "特定日期销售利润为: " + new DecimalFormat("#.##").format(profit));
    }

    // 显示错误对话框的辅助方法
    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(frame, message, "错误", JOptionPane.ERROR_MESSAGE);
    }

    private void updateBookGUI() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "请选择要修改的图书");
            return;
        }
        String bookId = bookTableModel.getValueAt(row, 0).toString();

        String newPrice = JOptionPane.showInputDialog(frame, "请输入新价格");

        if (newPrice!= null &&!newPrice.isEmpty()) {
            try (Connection conn = Connect.getConnection()) {
                if (conn!= null) {
                    String sql = "UPDATE BookInfo SET Price =? WHERE BookId =?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setDouble(1, Double.parseDouble(newPrice));
                        pstmt.setString(2, bookId);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "价格修改成功");
                        queryBooks(); // 添加这行，修改价格后重新查询图书信息
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "数据库连接失败");
                }
            } catch (SQLException | NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "修改价格时出错");
            }
        }
    }

    // 查询图书信息
    private void queryBooks() {
        bookTableModel.setRowCount(0);
        try (Connection conn = Connect.getConnection()) {
            if (conn!= null) {
                String sql = "SELECT * FROM BookInfo";
                try (PreparedStatement pstmt = conn.prepareStatement(sql);
                     ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        String bookId = rs.getString("BookId");
                        String category = rs.getString("Category");
                        String bookName = rs.getString("BookName");
                        double price = rs.getDouble("Price");
                        String description = rs.getString("Description");
                        int store = rs.getInt("Store");
                        Object[] rowData = {bookId, category, bookName, price, description, store};
                        bookTableModel.addRow(rowData);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(frame, "数据库连接失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询图书信息时出错");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Clerkuse());
    }
}