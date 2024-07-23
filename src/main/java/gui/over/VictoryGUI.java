package gui.over;

import gui.start.StartGame;
import loader.ResourceLoader;
import rank.RankManager;
import rank.RankingsGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

import static gui.start.StartGame.frame;


public class VictoryGUI extends JFrame {
    public boolean gameSuccess; // 是否玩家胜利

    public VictoryGUI(boolean gamewin, List<Boolean> playerStatuses) {
        this.gameSuccess = gamewin;
        initGUI(playerStatuses);
    }

    private void initGUI(List<Boolean> playerStatuses) {
        setTitle("独孤求败！！！"); // 设置窗口标题
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗口默认关闭操作
        setSize(800, 800); // 设置窗口大小
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
        // 添加查看排行榜的按钮

        JButton rankingsButton = new JButton("查看排行榜");
        this.add(rankingsButton);

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
                  // this.dispose();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    // 处理数据库查询异常，例如显示错误消息
                    JOptionPane.showMessageDialog(frame, "无法加载排行榜: " + ex.getMessage(), "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

// 为按钮设置位置（示例位置，根据实际布局调整）
        rankingsButton.setBounds(300, 650, 100, 40);
        
        // 显示窗口
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    public static void main(String[] args) { new VictoryGUI(true, null); } // 测试用例
}