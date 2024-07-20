package gui.over;

import gui.start.StartGame;
import loader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;

public class OverGame {
    private JFrame frame;

    public OverGame(JFrame mainFrame) {
        // 创建窗口
        frame = new JFrame("游戏结束"); // 窗口标题
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 设置窗口默认关闭操作为不采取任何操作
        frame.setSize(1100, 700); // 设置窗口大小
        frame.setLocationRelativeTo(null); // 窗口居中显示

        // 设置背景面板
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/GameOver.png"));
                g.drawImage(background.getImage(), 0, 0, null);
            }
        };
        backgroundPanel.setLayout(null);
        frame.add(backgroundPanel);

        // 创建结束游戏按钮
        JButton endGameButton = new JButton("结束游戏");
        backgroundPanel.add(endGameButton);

        // 为结束游戏按钮添加监听器
        endGameButton.addActionListener(e -> {
            int closeCode = JOptionPane.showConfirmDialog(
                frame,
                "确定退出游戏？",
                "提示！",
                JOptionPane.YES_NO_OPTION
            );
            if (closeCode == JOptionPane.YES_OPTION) {
                System.exit(0); // 如果用户选择“是”，则退出程序
            }
        });

        // 创建回到主界面按钮
        JButton backToMainButton = new JButton("回到游戏");
        backgroundPanel.add(backToMainButton);

        // 为回到主界面按钮添加监听器
        backToMainButton.addActionListener(e -> {

            frame.dispose(); // 关闭当前窗口
            mainFrame.setVisible(true); // 显示主界面窗口
        });

        endGameButton.setBounds(350, 550, 100, 40);
            backToMainButton.setBounds(700, 550, 100, 40);
        endGameButton.setVisible(true);
    backToMainButton.setVisible(true);
        // 添加窗口关闭逻辑
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(
                    frame,
                    "确定要结束游戏吗？",
                    "游戏结束",
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
        JFrame mainFrame = new StartGame().frame; // 获取主界面窗口的引用
        new OverGame(mainFrame); // 显示结束游戏窗口
    }
}