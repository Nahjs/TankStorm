package gui.start;

import game.RunGame;
import loader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class StartGame {
    private JFrame frame;

    public StartGame() {
        frame = new JFrame("坦克风云");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 625);
        frame.setLocationRelativeTo(null);

        // 设置背景面板
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/background/background.png"));
                g.drawImage(background.getImage(), 0, 0, null);
            }
        };
        backgroundPanel.setLayout(null);
        frame.add(backgroundPanel);

        // 创建登录按钮
        JButton loginButton = new JButton("开始游戏");
        loginButton.setBounds(350, 500, 150, 50); // 根据需要调整位置和大小
        backgroundPanel.add(loginButton);

        // 创建退出按钮
        JButton exitButton = new JButton("退出游戏");
        exitButton.setBounds(750, 500, 150, 50); // 根据需要调整位置和大小
        backgroundPanel.add(exitButton);

        // 为开始游戏按钮添加监听器
        loginButton.addActionListener(e -> {
            RunGame.createGUI();
            frame.dispose(); // 关闭当前窗口
        });

        // 为退出按钮添加监听器
        exitButton.addActionListener(e -> {
            // 弹出确认对话框
            int closeCode = JOptionPane.showConfirmDialog(
                    frame,
                    "确定退出游戏？",
                    "提示！！！",
                    JOptionPane.YES_NO_OPTION
            );
            // 检查用户是否点击了“是”
            if (closeCode == JOptionPane.YES_OPTION) {
                System.exit(0); // 如果是，退出程序
            }
        });
            // 添加窗口关闭逻辑
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    int closeCode = JOptionPane.showConfirmDialog(frame, "\u786e\u5b9a\u9000\u51fa\u6e38\u620f\uff1f", "\\u63d0\\u793a\\uff01",
                            JOptionPane.YES_NO_OPTION);
                    if (closeCode == JOptionPane.YES_OPTION) {
                        System.exit(0);
                    }
                }
            });


            frame.setVisible(true);
    }

    public static void main(String[] args) {

        new StartGame();
    }
}