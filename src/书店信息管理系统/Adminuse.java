package 书店信息管理系统;

import javax.swing.*;

public class Adminuse extends JFrame {
    public Adminuse() {
        setTitle("书店信息管理系统 - 管理员界面");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        getContentPane().add(panel);

        // 在管理员界面添加相关功能按钮
        JButton userManagementButton = new JButton("用户管理");
        userManagementButton.addActionListener(e -> {
            // 打开用户管理界面，你需要为每个功能创建对应的界面类
            // 或者在 Admin 类中直接添加用户管理相关的组件
        });
        panel.add(userManagementButton);

        JButton bookManagementButton = new JButton("书籍管理");
        bookManagementButton.addActionListener(e -> {
            // 打开书籍管理界面
        });
        panel.add(bookManagementButton);

        JButton salesRecordButton = new JButton("销售记录管理");
        salesRecordButton.addActionListener(e -> {
            // 打开销售记录管理界面
        });
        panel.add(salesRecordButton);

        JButton statisticsButton = new JButton("统计报表");
        statisticsButton.addActionListener(e -> {
            // 打开统计报表界面
        });
        panel.add(statisticsButton);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Adminuse());
    }
}