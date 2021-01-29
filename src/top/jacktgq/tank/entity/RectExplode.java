package top.jacktgq.tank.entity;

import top.jacktgq.tank.entity.abstractEntity.BaseExplode;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/24--11:52
 * @Description 方块风格的爆炸动画
 */
public class RectExplode extends BaseExplode {
    public RectExplode(Rectangle tankRect) {
        super(tankRect);
    }

    // 绘制爆炸效果
    @Override
    public void paint(Graphics g) {
        step++;
        int width = 6 * step;
        int height = width;
        g.setColor(Color.RED);
        g.fillRect(tankRect.x + (tankRect.width - width) / 2, tankRect.y - (tankRect.height - height) / 2, width, height);
    }
}
