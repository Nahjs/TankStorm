package top.jacktgq.tank.entity;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.BaseObject.BaseExplode;
import top.jacktgq.tank.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/24--11:52
 * @Description 默认风格的爆炸动画
 */
public class Boom extends BaseExplode {
    public Boom(Rectangle tankRect) {
        super(tankRect);
    }

    // 绘制爆炸效果
    @Override
    public void paint(Graphics g) {
        BufferedImage explodeImage = ResourceLoader.explodes[step];
        int width = explodeImage.getWidth();
        int height = explodeImage.getHeight();
        x = tankRect.x + (tankRect.width - width) / 2;
        y = tankRect.y - (tankRect.height - height) / 2;
        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;
        g.drawImage(explodeImage, x, y, null);
        step++;
        // 播放到爆炸动画的最后一帧，就移除这个爆炸动画
        if (step == ResourceLoader.explodes.length) {
            GameModel.INSTANCE.gameObjects.remove(this);
        }
    }

    @Override
    public Rectangle getRect() {
        return rect;
    }
}
