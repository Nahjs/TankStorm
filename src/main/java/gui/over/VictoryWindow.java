package gui.over;

import javax.swing.*;
import java.awt.*;

public class VictoryWindow extends JFrame {
    public VictoryWindow() {
        setTitle("胜利窗口"); // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口默认关闭操作
        setSize(800, 600); // 设置窗口大小
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new BorderLayout()); // 设置布局管理器

        // 添加背景图片
        JLabel background = new JLabel(new ImageIcon("path/to/victory/background.png"));
        add(background, BorderLayout.CENTER);

        // 创建并添加胜利信息标签
        JLabel victoryMessage = new JLabel("恭喜，你赢了！", SwingConstants.CENTER);
        victoryMessage.setFont(new Font("SansSerif", Font.BOLD, 36));
        victoryMessage.setForeground(Color.GREEN);
        background.add(victoryMessage);

        // 显示窗口
        setVisible(true);
    }

    public static void main(String[] args) {
        new VictoryWindow();
    }
}