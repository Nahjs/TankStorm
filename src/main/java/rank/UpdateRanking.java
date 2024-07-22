package rank;

import gui.start.login.JdbcUtils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.List;

public class UpdateRanking {

    private RankManager rankManager;
    private ExcelManager excelManager;

    // 构造函数注入依赖

    public UpdateRanking(Connection connection, String excelFilePath) {
        this.rankManager = new RankManager(connection);
        this.excelManager = new ExcelManager(excelFilePath);
    }

    // 更新排行的方法
    public void update() {
        try {
            // 查询当前得分
            List<Player> players = RankManager.queryScores();

            // 按得分降序排序玩家列表
            Collections.sort(players, (p1, p2) -> Integer.compare(p2.getScore(), (Integer) p1.getScore()));

            // 清空现有得分记录（根据实际业务逻辑可能需要调整）
           // rankManager.clearScoreboard();

            // 更新数据库中的得分记录
         //   updateScoresInDatabase(players);

            // 导出到Excel
            excelManager.exportToExcel(players);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            // 处理异常，例如可以记录日志或通知管理员
        }
    }

    // 在数据库中更新得分的方法
    private void updateScoresInDatabase(List<Player> players) throws SQLException {
        // 遍历玩家列表并更新数据库
        for (Player player : players) {
            rankManager.insertScore(player);
        }
    }

    // 清空得分记录的方法（可选）
    private void clearScoreboard() throws SQLException {
        String clearSql = "DELETE FROM scoreboard";
        Connection connection= JdbcUtils.getConnection();;
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(clearSql);
        }
    }
}
