package top.jacktgq.tank.entity;

import top.jacktgq.tank.view.TankPanel;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 方块风格的我方坦克
 */
public class RectSelfTank extends Tank {
    private int width = 60, height = 60;
    public RectSelfTank(int x, int y, Dir dir, int speed, TankPanel tankPanel) {
        super(x, y, dir, speed, tankPanel, Group.SELF);
        moving = false;
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
        move();
    }
}
