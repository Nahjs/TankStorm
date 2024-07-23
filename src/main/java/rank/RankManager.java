package rank;

import gui.start.login.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RankManager {
    static Connection connection;

    public RankManager(Connection connection) {
        this.connection = connection;
    }

    //检查是否已存在具有相同 username 的记录。如果存在，则更新该记录的得分而不是插入新记录
    public void insertScore(Player player) throws SQLException {
        // 检查该 username 是否已存在
        String checkSql = "SELECT COUNT(*) FROM scoreboard WHERE username = ?";
        try (PreparedStatement stmtCheck = connection.prepareStatement(checkSql)) {
            stmtCheck.setString(1, player.getUsername());
            try (ResultSet rs = stmtCheck.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    // 如果存在，更新得分
                    String updateSql = "UPDATE scoreboard SET score = ? WHERE username = ?";
                    try (PreparedStatement stmtUpdate = connection.prepareStatement(updateSql)) {
                        stmtUpdate.setInt(1, player.getScore());
                        stmtUpdate.setString(2, player.getUsername());
                        stmtUpdate.executeUpdate();
                    }
                } else {
                    // 如果不存在，插入新记录
                    String insertSql = "INSERT INTO scoreboard (username, score) VALUES (?, ?)";
                    try (PreparedStatement stmtInsert = connection.prepareStatement(insertSql)) {
                        stmtInsert.setString(1, player.getUsername());
                        stmtInsert.setInt(2, player.getScore());
                        stmtInsert.executeUpdate();
                    }
                }
            }
        }
    }

    public static java.util.List queryScores() throws SQLException {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT username, score FROM scoreboard";

        connection = JdbcUtils.getConnection(); // 获取数据库连接
        // 执行SQL查询语句
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                String username = rs.getString("username");
                int score = rs.getInt("score");
                Player player = new Player(username);

                players.add(player);
            }
        }
        return  players;
    }

    public void clearScoreboard() throws SQLException {
        // 执行SQL语句来清空scoreboard表
        String sql = "DELETE FROM scoreboard";
        try (Statement stmt = connection.createStatement()) {
            int rowsAffected = stmt.executeUpdate(sql);
            // 可以输出或记录影响的行数，对于清空操作，这通常应该是表中的行数
            System.out.println(rowsAffected + " rows deleted from scoreboard");
        }
    }


    }