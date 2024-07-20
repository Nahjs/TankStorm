package gui.over;

import javax.swing.*;
import java.awt.*;

public class LossWindow extends JFrame {
    public LossWindow() {
        setTitle("失败窗口"); // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口默认关闭操作
        setSize(800, 600); // 设置窗口大小
        setLocationRelativeTo(null); // 窗口居中显示
        setLayout(new BorderLayout()); // 设置布局管理器

        // 添加背景图片
        JLabel background = new JLabel(new ImageIcon("path/to/loss/background.png"));
        add(background, BorderLayout.CENTER);

        // 创建并添加失败信息标签
        JLabel lossMessage = new JLabel("很遗憾，你输了。", SwingConstants.CENTER);
        lossMessage.setFont(new Font("SansSerif", Font.BOLD, 36));
        lossMessage.setForeground(Color.RED);
        background.add(lossMessage);

        // 显示窗口
        setVisible(true);
    }

    public static void main(String[] args) {
        new LossWindow();
    }
}