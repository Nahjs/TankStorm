package gui.over;

import gui.start.StartGame;
import loader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FailGUI {

    private JFrame frame;
    private boolean visible;

    public FailGUI() {
        visible = false;
        initUI();
    }

    private void initUI() {
        frame = new JFrame("你个菜B");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 836);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/GameOver.png"));
                g.drawImage(background.getImage(), 0, 0, null);
//                ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/GameOver.png"));
//                Image backgroundImage = background.getImage();
//
//                // 缩放图片以适应窗口大小
//                g.drawImage(backgroundImage, 0, 0, gameWidth, gameHeight, null);
            }
        };
        backgroundPanel.setLayout(null);
        frame.add(backgroundPanel);

        JButton endGameButton = new JButton("打不过，滚！");
        backgroundPanel.add(endGameButton);

//        endGameButton.addActionListener(e -> {
//
//                System.exit(0);
//
//        });
        endGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 弹出确认对话框
                int closeCode = JOptionPane.showConfirmDialog(
                        frame,  // 假定 frame 是您应用程序中的 JFrame 实例
                        "你是怂逼？",  // 消息内容
                        "你滚吧",  // 标题
                        JOptionPane.YES_NO_OPTION  // 对话框按钮类型
                );
                // 检查用户是否点击了“是”
                if (closeCode == JOptionPane.YES_OPTION) {
                    System.exit(0);  // 如果是，退出程序
                }
            }
        });

        JButton backToMainButton = new JButton("不服，再战！");
        backgroundPanel.add(backToMainButton);

        backToMainButton.addActionListener(e -> {
            frame.dispose();
            new StartGame();
        });

        endGameButton.setBounds(600, 750, 150, 40);
        backToMainButton.setBounds(50, 750, 150, 40);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(frame, "你是怂逼？", "你滚吧", JOptionPane.YES_NO_OPTION);
                if (closeCode == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    public void setVisible(boolean isVisible) {
        if (isVisible && !visible) {
            visible = true;
            frame.setVisible(true);
        } else if (!isVisible && visible) {
            visible = false;
            frame.dispose();
        }
    }

    public static void main(String[] args) {
        new FailGUI().setVisible(true);
    }
}