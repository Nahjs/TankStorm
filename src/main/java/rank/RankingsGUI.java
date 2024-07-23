package rank;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class RankingsGUI extends JFrame {
    private List<Player> players;

    public RankingsGUI(List<Player> players) {
        this.players = (List<Player>) players;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("游戏排行榜");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 创建排行榜表格
        String[] columnNames = {"排名", "玩家UUID", "得分"};
        Object[][] data = new Object[players.size()][3];
        int rank = 1;
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            data[i] = new Object[]{rank++, player.getUsername(), player.getScore()};
        }
        JTable table = new JTable(data, columnNames);
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // 添加操作按钮
        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("返回游戏");
        backButton.addActionListener(e -> {
            // 处理返回游戏的逻辑
        });
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        // 在游戏结束并更新排行榜后

        List<Player> updatedPlayers = RankManager.queryScores();
        RankingsGUI rankingsGUI = new RankingsGUI(updatedPlayers);
        rankingsGUI.setVisible(true);
//        // 假设这是从数据库或Excel文件中获取的玩家列表
//        List<Player> players = ...;
//        new RankingsGUI(players);
    }
}