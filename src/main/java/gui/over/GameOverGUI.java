package gui.over;

import gui.start.StartGame;
import loader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOverGUI extends JFrame {
    private boolean isVictory;

    public GameOverGUI(boolean isVictory) {
        this.isVictory = isVictory;
        initUI();
    }

    private void initUI() {
        setTitle("游戏结束");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);

        setLayout(null);

        // 添加背景图片
        ImageIcon originalIcon = new ImageIcon(ResourceLoader.class.getResource("/images/win.png"));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, getWidth(), getHeight());
        this.add(background);

        // 创建并添加回到游戏按钮
        JButton backToMainButton = new JButton("回到游戏");
        backToMainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // 关闭当前窗口
                new StartGame(); // 显示主菜单
            }
        });
        backToMainButton.setFont(new Font("SansSerif", Font.PLAIN, 15)); // 设置字体大小
        backToMainButton.setBounds(530, 550, 100, 40);

        this.add(backToMainButton);

        // 显示窗口
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) {
        new GameOverGUI(true).setVisible(true); // 测试用例
    }
}