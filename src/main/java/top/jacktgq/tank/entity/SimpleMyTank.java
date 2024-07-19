package top.jacktgq.tank.entity;

import java.awt.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 方块风格的我方坦克
 */
public class SimpleMyTank extends Tank {
    private int width = 60, height = 60;
    public SimpleMyTank(UUID id, int x, int y, Dir dir, int speed) {
        super(id, x, y, dir, speed, Group.SELF);
        moving = false;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
        move();
    }
}
