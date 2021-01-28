package top.jacktgq.tank.entity;

import top.jacktgq.tank.view.TankPanel;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 默认风格的我方坦克
 */
public class DefaultSelfTank extends Tank {

    public DefaultSelfTank(int x, int y, Dir dir, int speed, TankPanel tankPanel) {
        super(x, y, dir, speed, tankPanel, Group.SELF);
        moving = false;
    }

    @Override
    public void paint(Graphics g) {
        /*g.setColor(Color.MAGENTA);
        g.fillRect(x, y, WIDTH, HEIGHT);*/
        g.drawImage(curTankImage, x, y, null);
        move();
    }
}
