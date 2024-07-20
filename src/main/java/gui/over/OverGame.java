package gui.over;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OverGame {
    private JFrame frame;

    public OverGame() {
        // 创建窗口
        frame = new JFrame("游戏结束"); // 窗口标题
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 设置窗口默认关闭操作为不采取任何操作
        frame.setSize(300, 200); // 设置窗口大小
        frame.setLocationRelativeTo(null); // 窗口居中显示

        // 创建面板
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout()); // 使用流式布局

        // 创建结束游戏按钮
        JButton endGameButton = new JButton("结束游戏");
        panel.add(endGameButton);

        // 为结束游戏按钮添加监听器
        endGameButton.addActionListener(e -> {
            int closeCode = JOptionPane.showConfirmDialog(
                frame,
                "\u786e\u5b9a\u9000\u51fa\u6e38\u620f\uff1f", // "确定退出游戏？"
                "\u63d0\u793a\uff01", // "提示！"
                JOptionPane.YES_NO_OPTION
            );
            if (closeCode == JOptionPane.YES_OPTION) {
                System.exit(0); // 如果用户选择“是”，则退出程序
            }
        });

        // 添加面板到窗口
        frame.add(panel);

        // 添加窗口关闭逻辑
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(
                    frame,
                    "\u786e\u5b9a\u8981\u7ec8\u6b62\u6e38\u620f\u5417\uff1f", // "确定要结束游戏吗？"
                    "\u6e38\u620f\u7ec8\u6b62", // "游戏结束"
                    JOptionPane.YES_NO_OPTION
                );
                if (closeCode == JOptionPane.YES_OPTION) {
                    System.exit(0); // 如果用户选择“是”，则退出程序
                }
            }
        });

        // 显示窗口
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new OverGame();
    }
}