package top.jacktgq.tank.entity.BaseObject;

import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.GameObjectType;
import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:22
 * @Description 炮弹类
 */
public abstract class BaseBullet extends GameObject {
    protected Group group;
    protected Rectangle rect;
    protected Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    protected int speed = 6;
    protected int step = 0;
    protected GameObjectType gameObjectType = GameObjectType.BULLET;
    protected UUID id;
    protected UUID tankId;

    // 绘制爆炸效果
    @Override
    public void paint(Graphics g) {
        BufferedImage explodeImage = ResourceLoader.explodes[step];
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

    @Override
    public boolean isMoving() {
        return true;
    }

    // 获取子弹图片坐标和宽高
    @Override
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public Group getGroup() {
        return group;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }

    @Override
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    // 碰撞后回到上一次的位置
    @Override
    public void back() {}

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void fire() {}

    @Override
    public void setMoving(boolean b) {}

    @Override
    public void setDir(Dir dir) {}

    @Override
    public Dir getDir() {
        return dir;
    }

    @Override
    public abstract UUID getTankId();
}
