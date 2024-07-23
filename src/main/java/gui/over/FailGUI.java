package gui.over;

import gui.start.StartGame;
import loader.ConfigLoader;
import loader.ResourceLoader;
import rank.RankManager;
import rank.RankingsGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.List;

public class FailGUI {

    private JFrame frame;
    private boolean visible;

    private static final int gameWidth= ConfigLoader.getGameWidth(),
            gameHeight = ConfigLoader.getGameHeight();

    public FailGUI() {
        visible = false;
        initUI();
    }

    private void initUI() {
        frame = new JFrame("你个菜B");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setSize(800, 837);
        frame.setLocationRelativeTo(null);

        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/GameOver.png"));
                g.drawImage(background.getImage(), 0, 0, gameWidth, gameHeight, null);
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

        JButton backToMainButton = new JButton("不服，观战！");
        backgroundPanel.add(backToMainButton);

        backToMainButton.addActionListener(e -> {
            frame.dispose();
            new StartGame();
        });

        endGameButton.setBounds(600, 750, 150, 40);
        backToMainButton.setBounds(50, 750, 150, 40);

        // 添加查看排行榜的按钮
        JButton rankingsButton = new JButton("查看排行榜");
        backgroundPanel.add(rankingsButton);

// 为排行榜按钮添加事件监听器
        rankingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // 获取更新后的玩家排名信息
                    List updatedPlayers = RankManager.queryScores();
                    // 创建并显示排行榜界面
                    RankingsGUI rankingsGUI = new RankingsGUI(updatedPlayers);
                    //rankingsGUI.pack(); // 根据内容调整窗口大小
                    rankingsGUI.setVisible(true);
                    // 可以选择隐藏失败界面
                     frame.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 处理数据库查询异常，例如显示错误消息
                    JOptionPane.showMessageDialog(frame, "无法加载排行榜: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// 为按钮设置位置（示例位置，根据实际布局调整）
        rankingsButton.setBounds(300, 700, 120, 30);


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