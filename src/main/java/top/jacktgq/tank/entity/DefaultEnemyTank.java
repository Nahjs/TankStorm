package top.jacktgq.tank.entity;

import java.awt.*;
import java.util.Random;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 默认风格的敌方坦克
 */
public class DefaultEnemyTank extends Tank {
    private Random random = new Random();

    public DefaultEnemyTank(int x, int y, Dir dir, int speed) {
        super(x, y, dir, speed, Group.ENEMY);
    }

    /**
     * 敌方坦克移动的过程中会随机变向和开火
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

    @Override
    public void paint(Graphics g) {
        /*g.setColor(Color.MAGENTA);
        g.fillRect(x, y, WIDTH, HEIGHT);*/
        g.drawImage(curTankImage, x, y, null);
        move();
    }
}
