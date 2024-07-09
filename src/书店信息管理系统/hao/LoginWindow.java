package 书店信息管理系统.hao;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//以JFrame为基础，它是屏幕上window的对象
public class LoginWindow extends JFrame {
    public LoginWindow() {
        setTitle("书店管理信息系统");
        setSize(500, 300);
//        默认关闭窗口操作
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        窗口居中
        setLocationRelativeTo(null);
//        向组件集合中添加一个组件。
        addComponent();

        setLayout(null);
//        将组件设置为可见状态，使其能够在界面上显示出来。
        setVisible(true);
    }

    private void addComponent() {
//        用户名标签
        JLabel lb1 = new JLabel("用户名：");
//        标签的大小和位置
        lb1.setSize(60, 30);
        lb1.setLocation(100, 50);
//        向当前的 LoginWindow 窗口中添加一个标签组件 lb1
        this.add(lb1);
//      添加用户名输入框：
        JTextField tf = new JTextField();
        tf.setSize(200, 30);
        tf.setLocation(160, 50);
        this.add(tf);
//
        JLabel lb2 = new JLabel("密码：");
        lb2.setSize(60, 30);
        lb2.setLocation(100, 100);
        this.add(lb2);

        JPasswordField pf = new JPasswordField();
        pf.setSize(200, 30);
        pf.setLocation(160, 100);
        this.add(pf);
//        创建管理员按钮
        JButton adminButton = new JButton("管理员登录");
        adminButton.setSize(120, 40);
        adminButton.setLocation(100, 160);
        this.add(adminButton);

        JButton staffButton = new JButton("员工登录");
        staffButton.setSize(120, 40);
        staffButton.setLocation(280, 160);
        this.add(staffButton);

//        匿名内部类 ActionListener 对象，用于监听管理员登录按钮 adminButton 的点击事件。
        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 管理员登录按钮的逻辑
                String username = tf.getText();
                String password = new String(pf.getPassword());

                if (username.equals("root") && password.equals("YES")) {
                    JOptionPane.showMessageDialog(null, "管理员登录成功，进入管理员界面");
                    new Adminuse();
                } else {
                    JOptionPane.showMessageDialog(null, "管理员登录失败，密码错误，程序退出");
                    System.exit(0);
                }
            }
        });

        staffButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 员工登录按钮的逻辑
                String username = tf.getText();
                String password = new String(pf.getPassword());

                try (Connection conn = Connect.getConnection()) {
                    String sql = "SELECT * FROM Clerk WHERE Username =? AND Password =?";
//                    pstmt.setString(1, username); 将第一个参数（索引从1开始）设置为username变量的值。1
                    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                        pstmt.setString(1, username);
                        pstmt.setString(2, password);
//                       返回结果集  结果集包含了符合条件的数据库记录
                        try (ResultSet rs = pstmt.executeQuery()) {
                            if (rs.next()) {
                                JOptionPane.showMessageDialog(null, "员工登录成功，进入员工界面");
                                new Clerkuse();
                            } else {
                                JOptionPane.showMessageDialog(null, "员工登录失败，密码错误，程序退出");
                                System.exit(0);
                            }
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "数据库操作异常，登录失败，程序退出");
                    System.exit(0);
                }
            }
        });
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}