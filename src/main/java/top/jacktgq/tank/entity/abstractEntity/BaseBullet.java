package top.jacktgq.tank.entity.abstractEntity;

import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.mgr.ResourceMgr;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:22
 * @Description 炮弹类
 */
public abstract class BaseBullet extends GameObject {
    protected int x, y;
    protected Group group;
    protected Rectangle rect;
    protected Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    protected int speed = 2;
    protected int step = 0;
    // 绘制爆炸效果
    @Override
    public void paint(Graphics g) {
        BufferedImage explodeImage = ResourceMgr.explodes[step];
        int width = explodeImage.getWidth();
        int height = explodeImage.getHeight();
        g.drawImage(explodeImage, tankRect.x + (tankRect.width - width) / 2, tankRect.y - (tankRect.height - height) / 2, null);
        step++;
    }

    /**
     * 获取爆炸动画到了第几张图片
     * @return
     */
    public int getStep() {
        return step;
    }

    // 判断子弹的状态
    public abstract boolean islive();

    // 获取子弹图片坐标和宽高
    @Override
    public Rectangle getRect() {
        return rect;
    }

    public Group getGroup() {
        return group;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
