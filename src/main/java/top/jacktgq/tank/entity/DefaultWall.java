package top.jacktgq.tank.entity;

import top.jacktgq.tank.entity.abstractEntity.BaseWall;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/29--8:13
 * @Description 默认风格的墙
 */
public class DefaultWall extends BaseWall {
    public DefaultWall(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(new Color(0xdba989));
        g.fillRect(x, y, width, height);
    }
}
