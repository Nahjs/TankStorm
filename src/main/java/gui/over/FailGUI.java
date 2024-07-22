package gui.over;

import gui.start.StartGame;
import loader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
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
        frame.setSize(1200, 700);
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

        JButton endGameButton = new JButton("结束游戏");
        backgroundPanel.add(endGameButton);

        endGameButton.addActionListener(e -> {

                System.exit(0);

        });

        JButton backToMainButton = new JButton("重新开始");
        backgroundPanel.add(backToMainButton);

        backToMainButton.addActionListener(e -> {
            frame.dispose();
            new StartGame();
        });

        endGameButton.setBounds(350, 550, 100, 40);
        backToMainButton.setBounds(700, 550, 100, 40);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(frame, "确定要结束游戏吗？", "游戏结束", JOptionPane.YES_NO_OPTION);
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