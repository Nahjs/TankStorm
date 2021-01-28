package top.jacktgq.tank.entity.abstractEntity;

import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.mgr.ResourceMgr;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:22
 * @Description 炮弹类
 */
public abstract class BaseBullet {
    protected int x, y;
    protected Group group;
    protected Rectangle rect;
    protected Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    protected int speed = 2;
    protected int step = 0;
    // 绘制爆炸效果
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

    // 子弹和坦克进行碰撞检测，碰撞检测的方法调用的频率比较高
    // 每次都要产生新的Rectangle对象，会占用过多内存，
    // 所以将Rectangle对象放到全局，在坐标值发生变化的时候动态更新即可
    public boolean collideWith(BaseTank tank) {
        // 如果是己方坦克，无敌
        if (tank.getGroup() == Group.SELF) {
            return false;
        }
        // 一个阵营的不做碰撞检测
        if (getGroup() == tank.getGroup()) {
            return false;
        }
        return getBulletRect().intersects(tank.getTankRect());
    }

    // 获取子弹图片坐标和宽高
    public Rectangle getBulletRect() {
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
