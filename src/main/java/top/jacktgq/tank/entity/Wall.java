package top.jacktgq.tank.entity;

import top.jacktgq.tank.entity.BaseObject.BaseWall;

import java.awt.*;

/**
 * 墙
 */
public class Wall extends BaseWall {
    public Wall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0xdba989));
        g.fillRect(x, y, width, height);
    }
}
