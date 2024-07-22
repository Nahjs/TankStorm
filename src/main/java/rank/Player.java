package rank;

public class Player {
    private String uuid; // 坦克的UUID作为玩家的名字
    private int score;

    public Player(String tankId) {
        this.uuid = tankId; // 直接使用坦克的ID作为玩家的UUID
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