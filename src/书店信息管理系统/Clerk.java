package 书店信息管理系统;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Clerk extends JFrame {
    private JButton bookQueryButton;
    private JButton salesProcessingButton;
    private JButton inventoryManagementButton;

    public Clerk() {
        JPanel panel = new JPanel(new GridLayout(3, 1));
        bookQueryButton = new JButton("书籍查询");
        bookQueryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 打开书籍查询界面
                openQuery();
            }
        });
        panel.add(bookQueryButton);

        salesProcessingButton = new JButton("销售处理");
        salesProcessingButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 打开销售处理界面
                openSalesProcessing();
            }
        });
        panel.add(salesProcessingButton);

        inventoryManagementButton = new JButton("库存管理");
        inventoryManagementButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 打开库存管理界面
                openInventoryManagement();
            }
        });
        panel.add(inventoryManagementButton);

        add(panel);
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void openQuery() {
        // 实现书籍查询界面的逻辑
        JOptionPane.showMessageDialog(null, "打开书籍查询界面");
    }

    private void openSalesProcessing() {
        // 实现销售处理界面的逻辑
        JOptionPane.showMessageDialog(null, "打开销售处理界面");
    }

    private void openInventoryManagement() {
        // 实现库存管理界面的逻辑
        JOptionPane.showMessageDialog(null, "打开库存管理界面");
    }


    }
