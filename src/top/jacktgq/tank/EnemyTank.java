package top.jacktgq.tank;

import java.util.Random;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 敌方坦克
 */
public class EnemyTank extends Tank {
    private Random random = new Random();

    public EnemyTank(int x, int y, Dir dir, int speed, TankPanel tankPanel) {
        super(x, y, dir, speed, tankPanel, Group.ENEMY);
    }

    /**
     * 坦克移动
     */
    @Override
    protected void move() {
        super.move();
        // %5的机会改变方向
        if (random.nextInt(100) > 95) {
            dir = Dir.values()[random.nextInt(4)];
        }
        // %5的机会开火
        if (random.nextInt(100) > 95) {
            fire();
        }
    }
}
