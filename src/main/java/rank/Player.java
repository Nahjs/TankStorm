package rank;

public class Player {
    private String uuid; // 坦克的UUID作为玩家的名字
    private int score;

    public Player(String uuid) {
        this.uuid = uuid;
        this.score = 0;
    }
    // 更新玩家得分的方法
    public void updateScore() {
        this.score++;
    }


    public String getUsername() {
        return uuid;
    }

    public int getScore() {
        return score;
    }
}