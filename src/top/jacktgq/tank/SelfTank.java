package top.jacktgq.tank;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 我方坦克
 */
public class SelfTank extends Tank {
    private boolean moving = false;

    public SelfTank(int x, int y, Dir dir, int speed, TankPanel tankPanel) {
        super(x, y, dir, speed, tankPanel, Group.SELF);
    }
}
