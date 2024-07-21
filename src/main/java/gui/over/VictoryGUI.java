package gui.over;

import gui.start.StartGame;
import loader.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class VictoryGUI extends JFrame {
    private boolean playerWon; // 是否玩家胜利

    public VictoryGUI(boolean playerWon, List<Boolean> playerStatuses) {
        this.playerWon = playerWon;
        initGUI(playerStatuses);
    }

    private void initGUI(List<Boolean> playerStatuses) {
        setTitle("胜利窗口"); // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口默认关闭操作
        setSize(1100, 700); // 设置窗口大小
        setLocationRelativeTo(null); // 窗口居中显示

        setLayout(null);
        
        // 添加背景图片

        ImageIcon originalIcon = new ImageIcon(ResourceLoader.class.getResource("/images/win.png"));
        Image originalImage = originalIcon.getImage();
        Image scaledImage = originalImage.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        JLabel background = new JLabel(scaledIcon);
        background.setBounds(0, 0, getWidth(), getHeight());
        this.add(background);

//        JLabel background = new JLabel(new ImageIcon(ResourceLoader.class.getResource("/images/win.png")));
//
//        background.setBounds(0, 0, 1100, 700);
//        this.add(background);

        // 创建并添加回到游戏按钮
        JButton backToMainButton = new JButton("回到游戏");
        backToMainButton.addActionListener(e -> {
            dispose(); // 关闭当前窗口
            new StartGame(); // 显示主菜单
        });
        backToMainButton.setFont(new Font("SansSerif", Font.PLAIN, 15)); // 设置字体大小
        backToMainButton.setBounds(530, 550, 100, 40);

        this.add(backToMainButton);
        
        // 显示窗口
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) { new VictoryGUI(true, null); } // 测试用例
}