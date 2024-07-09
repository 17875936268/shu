package 书店信息管理系统.hao;

import 书店信息管理系统.user.Clerk;
import 书店信息管理系统.BookInfo.BookInfo;
//Swing是一个用于构建图形用户界面的工具包，它提供了许多用于创建窗口、按钮、文本框等GUI组件的类。
import javax.swing.*;
//DefaultTableModel 是Swing库中用于表示表格数据（如JTable中的数据）的一个类。
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
//PreparedStatement 是JDBC API中的一个接口，它表示一个预编译的SQL语句。
import java.sql.PreparedStatement;
//ResultSet 是JDBC API中的一个接口，它表示从数据库查询返回的结果集。
import java.sql.ResultSet;
//JDBC API中的一个类，它表示与数据库交互时发生的异常。当执行SQL语句或处理数据库连接时出现问题时，JDBC会抛出一个SQLException对象。
import java.sql.SQLException;
//ArrayList 是Java集合框架中的一个类，它实现了动态数组的功能
import java.util.ArrayList;

public class Adminuse {
//    私有的 JFrame 类型的变量 frame，用于表示主窗口
    private JFrame frame;
//    声明查询、添加、删除、更新和退出按钮。
    private JButton queryButton, addButton, deleteButton, updateButton, exitButton;

    private JTable bookTable;
    private DefaultTableModel bookTableModel;

//    DefaultTableModel 类型的变量 tableModel，用于用户信息相关的表格。
    private DefaultTableModel tableModel;
    private JTable userTable;

//    initUI 方法用于初始化用户界面
    public Adminuse() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("书店信息管理系统");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

//     用户管理和书籍管理面板
        JPanel userPanel = createUserPanel();
        JPanel bookPanel = createBookPanel();
//选项卡
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("用户管理", userPanel);
        tabbedPane.addTab("书籍管理", bookPanel);

        frame.add(tabbedPane, BorderLayout.CENTER);
        frame.setVisible(true);
    }
//      对用户面板进行操作
    private JPanel createUserPanel() {
//        userPanel，并为其设置了 BorderLayout 布局管理器 即下面的东西
        JPanel userPanel = new JPanel(new BorderLayout());

        queryButton = new JButton("查询用户信息");
        addButton = new JButton("添加用户");
        deleteButton = new JButton("删除用户");
        exitButton = new JButton("退出");
        updateButton = new JButton("更新用户信息");
//     创建了表格和表格模型，并为表格添加列名
        userTable = new JTable();
        tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("用户名");
        tableModel.addColumn("密码");
        userTable.setModel(tableModel);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(queryButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(exitButton);

        userPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        userPanel.add(buttonPanel, BorderLayout.SOUTH);

        queryButton.addActionListener(e -> queryUsers());
        addButton.addActionListener(e -> addUserGUI());
        deleteButton.addActionListener(e -> deleteUserGUI());
        updateButton.addActionListener(e -> updateUserGUI());

        return userPanel;
    }
//查询
    private void queryUsers() {
        String sql = "SELECT * FROM Clerk";
//        创建了这个 ArrayList 的实例，并将其赋值给 clerks 变量。后续可以通过 clerks 来对这个列表进行添加、删除、遍历等操作
        ArrayList<Clerk> clerks = new ArrayList<>();
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            tableModel.setRowCount(0); // 清空表格数据

            while (rs.next()) {
                Clerk clerk = new Clerk(rs.getInt("UserId"), rs.getString("Username"), rs.getString("Password"));
                clerks.add(clerk);
                tableModel.addRow(new Object[]{clerk.getId(), clerk.getUsername(), clerk.getPassword()}); // 添加密码字段
            }

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询用户信息时出错");
        }
    }
//    添加用户信息的图形界面交互操作
    private void addUserGUI() {
        String username = JOptionPane.showInputDialog(frame, "请输入用户名:");
        String password = JOptionPane.showInputDialog(frame, "请输入密码:");
        addUserData(username, password);
    }

    private void addUserData(String username, String password) {
        try {
            Connection conn = Connect.getConnection();
            if (conn!= null) {
                String sql = "INSERT INTO Clerk (username, password) VALUES (?,?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setString(1, username);
                    pstmt.setString(2, password);
                    pstmt.executeUpdate();
                    queryUsers(); // 更新表格以显示新用户
                    JOptionPane.showMessageDialog(frame, "用户添加成功");
                }
                conn.close();
            } else {
                JOptionPane.showMessageDialog(frame, "数据库连接失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "添加用户时出错");
        }

    }

    private void deleteUserGUI() {
        int row = userTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "请选择要删除的用户");
            return;
        }

        int userId = (int) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "确认删除该用户吗？", "提示", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = Connect.getConnection();
                if (conn!= null) {
                    String sql = "DELETE FROM Clerk WHERE userId =?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, userId);
                        pstmt.executeUpdate();
                        queryUsers(); // 更新表格以反映删除操作
                        JOptionPane.showMessageDialog(frame, "用户删除成功");
                    }
                    conn.close();
                } else {
                    JOptionPane.showMessageDialog(frame, "数据库连接失败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "删除用户时出错");
            }
        }
    }

    private void updateUserGUI() {
        int row = userTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "请选择要更改密码的用户");
            return;
        }

        int userId = (int) tableModel.getValueAt(row, 0);
//        弹出输入对话框，让用户输入新密码，并将输入的内容存储在 newPassword 变量中。
        String newPassword = JOptionPane.showInputDialog(frame, "请输入新密码");

        if (newPassword!= null &&!newPassword.isEmpty()) {
            try {
                Connection conn = Connect.getConnection();
                if (conn!= null) {
                    String sql = "UPDATE Clerk SET Password =? WHERE UserId =?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, newPassword);
                        pstmt.setInt(2, userId);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "密码修改成功");
                    }
                    conn.close();
                } else {
                    JOptionPane.showMessageDialog(frame, "数据库连接失败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "更改密码时出错");
            }
        }
    }

    private JPanel createBookPanel() {

        JPanel bookPanel = new JPanel(new BorderLayout());


        queryButton = new JButton("查询图书信息");
        addButton = new JButton("添加图书");
        deleteButton = new JButton("删除图书");
        exitButton = new JButton("退出");
        updateButton = new JButton("更新图书信息");
        bookTable = new JTable();
        bookTableModel = new DefaultTableModel();
        bookTableModel.addColumn("BookId");
        bookTableModel.addColumn("Category");
        bookTableModel.addColumn("BookName");
        bookTableModel.addColumn("Price");
        bookTableModel.addColumn("Description");
        bookTableModel.addColumn("Store");
        bookTable.setModel(bookTableModel);

//        updateButton.addActionListener(e -> updateBookGUI());
//        buttonPanel按钮面板
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(queryButton);
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(exitButton);

        queryButton.addActionListener(e -> queryBooks());
        addButton.addActionListener(e -> addBookGUI());
        deleteButton.addActionListener(e -> deleteBookGUI());
        updateButton.addActionListener(e -> updateBookGUI());
        bookPanel.add(new JScrollPane(bookTable), BorderLayout.CENTER);
        bookPanel.add(buttonPanel, BorderLayout.SOUTH);

        return bookPanel;
    }

    private void queryBooks() {
        String sql = "SELECT * FROM BookInfo";
        ArrayList<BookInfo> books = new ArrayList<>();
        try (Connection conn = Connect.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            bookTableModel.setRowCount(0);
            while (rs.next()) {
                BookInfo book = new BookInfo(rs.getLong("BookId"), rs.getString("Category"), rs.getString("BookName"),
                        rs.getDouble("Price"), rs.getString("Description"), rs.getInt("Store"));
                books.add(book);
                bookTableModel.addRow(new Object[]{book.getBookId(), book.getCategory(), book.getBookname(),
                        book.getPrice(), book.getDescription(), book.getStore()});
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "查询图书信息时出错");
        }

    }

    private void addBookGUI() {
        long bookId = Long.parseLong(JOptionPane.showInputDialog(frame, "请输入图书 ID:"));
        String category = JOptionPane.showInputDialog(frame, "请输入图书类别:");
        String bookName = JOptionPane.showInputDialog(frame, "请输入图书名称:");
        double price = Double.parseDouble(JOptionPane.showInputDialog(frame, "请输入图书价格:"));
        String description = JOptionPane.showInputDialog(frame, "请输入图书描述:");
        int store = Integer.parseInt(JOptionPane.showInputDialog(frame, "请输入图书库存数量:"));
        addBookData(bookId, category, bookName, price, description, store);
    }

    private void addBookData(long bookId, String category, String bookName, double price, String description, int store) {
        try {
            Connection conn = Connect.getConnection();
            if (conn!= null) {
                String sql = "INSERT INTO BookInfo (BookId, Category, BookName, Price, Description, Store) VALUES (?,?,?,?,?,?)";
                try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                    pstmt.setLong(1, bookId);
                    pstmt.setString(2, category);
                    pstmt.setString(3, bookName);
                    pstmt.setDouble(4, price);
                    pstmt.setString(5, description);
                    pstmt.setInt(6, store);
                    pstmt.executeUpdate();
                    queryBooks();
                    if (category == null || category.isEmpty()) {
                        // 用户取消了操作，不需要进一步处理
                        return;
                    }
                    JOptionPane.showMessageDialog(frame, "图书添加成功");
                }
                conn.close();
            } else {
                JOptionPane.showMessageDialog(frame, "数据库连接失败");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "添加图书时出错");
        }
    }

    private void deleteBookGUI() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "请选择要删除的图书");
            return;
        }

        int bookId = (int) bookTableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(frame, "确认删除该图书吗？", "提示", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                Connection conn = Connect.getConnection();
                if (conn!= null) {
                    String sql = "DELETE FROM BookInfo WHERE BookId =?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setInt(1, bookId);
                        pstmt.executeUpdate();
                        queryBooks();
                        JOptionPane.showMessageDialog(frame, "图书删除成功");
                    }
                    conn.close();
                } else {
                    JOptionPane.showMessageDialog(frame, "数据库连接失败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(frame, "删除图书时出错");
            }
        }
    }

    private void updateBookGUI() {
        int row = bookTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(frame, "请选择要修改的图书");
            return;
        }
        long bookId;
        try {
            bookId = Long.parseLong(bookTableModel.getValueAt(row, 0).toString());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "无效的图书 ID");
            return;
        }

        String newPrice = JOptionPane.showInputDialog(frame, "请输入新价格");

        if (newPrice!= null &&!newPrice.isEmpty()) {
            try (Connection conn = Connect.getConnection()) {
                if (conn!= null) {
                    String sql = "UPDATE BookInfo SET Price =? WHERE BookId =?";
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setDouble(1, Double.parseDouble(newPrice));
                        pstmt.setLong(2, bookId);
                        pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(frame, "价格修改成功");
                        // 可以选择调用 queryBooks() 来更新表格，但请注意性能影响
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Adminuse());
    }
}