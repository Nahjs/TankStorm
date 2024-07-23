package game.object.BaseObject;

import game.object.Dir;
import game.object.GameObject;
import game.object.GameObjectType;
import game.object.Group;
import loader.AudioLoader;

import java.awt.*;
import java.util.UUID;

/**
 * 爆炸效果基类
 */
public abstract class BaseBoom extends GameObject {
    protected Rectangle rect;
    protected Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    protected Group group;
    protected int speed = 2;
    protected int step = 0;
    protected GameObjectType gameObjectType = GameObjectType.EXPLODE;

    @Override
    public abstract void paint(Graphics g);

    public BaseBoom(Rectangle tankRect) {
        this.tankRect = tankRect;
        rect = new Rectangle();
        new Thread(()->new AudioLoader("audio/explode.wav").play()).start();
    }

    /**
     * 获取爆炸动画到了第几张图片
     * @return
     */
    public int getStep() {
        return step;
    }

    @Override
    public abstract Rectangle getRect();

    @Override
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    // 碰撞后回到上一次的位置
    @Override
    public void back() {}

    @Override
    public Group getGroup() {
        return group;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public boolean isMoving() {
        return true;
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
    public int getX() {
        return x;
    }

    @Override
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
    public UUID getTankId() {
        return null;
    }
}
