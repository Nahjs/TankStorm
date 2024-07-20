package top.jacktgq.game.entity;

import top.jacktgq.game.GameModel;
import top.jacktgq.game.entity.BaseObject.BaseBoom;
import top.jacktgq.game.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 爆炸动画
 */
public class Boom extends BaseBoom {
    public Boom(Rectangle tankRect) {
        super(tankRect);
    }

    // 绘制爆炸效果
    @Override
    public void paint(Graphics g) {

        //使用 ResourceMgr.explodes 数组中的图像来显示爆炸动画。
        // 每次调用时，都会更新 step 变量以显示下一帧动画。
        BufferedImage boomImage = ResourceLoader.explodes[step];
        int width = boomImage.getWidth();
        int height = boomImage.getHeight();
        x = tankRect.x + (tankRect.width - width) / 2;
        y = tankRect.y - (tankRect.height - height) / 2;
        rect.x = x;
        rect.y = y;
        rect.width = width;
        rect.height = height;
        g.drawImage(boomImage, x, y, null);
        step++;
        // 播放到爆炸动画的最后一帧，就移除这个爆炸动画
        if (step == ResourceLoader.explodes.length) {
            GameModel.INSTANCE.gameObjects.remove(this);
        }
    }

    //返回爆炸效果的矩形区域
    @Override
    public Rectangle getRect() {
        return rect;
    }
}
