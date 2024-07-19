package top.jacktgq.tank.entity;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * 敌方坦克
 */
public class SimpleEnemyTank extends Tank {
    private int width = 60, height = 60;
    private Random random = new Random();

    public SimpleEnemyTank(UUID id, int x, int y, Dir dir, int speed) {
        super(id, x, y, dir, speed, Group.ENEMY);
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
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
        move();
    }
}
