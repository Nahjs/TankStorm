package top.jacktgq.game.entity.BaseObject;


import top.jacktgq.game.entity.Dir;
import top.jacktgq.game.entity.GameObject;
import top.jacktgq.game.entity.GameObjectType;
import top.jacktgq.game.entity.Group;
import top.jacktgq.game.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * 炮弹基类，包含炮弹的通用属性和方法。
 */
public abstract class BaseBullet extends GameObject {
    protected Group group;//炮弹所属的组
    protected Rectangle rect;//炮弹的矩形区域
    protected Rectangle tankRect; // 被击中坦克的矩形区域，用于爆炸效果的定位。
    protected int speed = 6;//炮弹的移动速度
    protected int step = 0;//爆炸动画的当前步骤
    protected GameObjectType gameObjectType = GameObjectType.BULLET;//游戏对象的类型，这里为 BULLET。
    protected UUID id;//炮弹的唯一标识符
    protected UUID tankId;//发射炮弹的坦克的唯一标识符

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
     * 获取爆炸动画
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
