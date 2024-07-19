package top.jacktgq.tank.entity;

import top.jacktgq.tank.entity.BaseObject.BaseBoom;

import java.awt.*;

/**
 * 爆炸动画
 */
public class SimpleExplode extends BaseBoom {
    public SimpleExplode(Rectangle tankRect) {
        super(tankRect);
    }

    // 绘制爆炸效果
    @Override
    public void paint(Graphics g) {
        step++;
        int width = 6 * step;
        int height = width;
        x = tankRect.x + (tankRect.width - width) / 2;
        y = tankRect.y - (tankRect.height - height) / 2;
        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }
}
