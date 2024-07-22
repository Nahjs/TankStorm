package game.object.BaseObject;

import game.object.GameObject;
import game.object.Dir;
import game.object.GameObjectType;
import game.object.Group;

import java.awt.*;
import java.util.UUID;

/**
 *  墙
 */
public abstract class BaseWall extends GameObject {
    protected int width, height;
    protected Rectangle rect;
    protected GameObjectType gameObjectType = GameObjectType.WALL;

    public BaseWall(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        rect = new Rectangle(x, y, width, height);
    }

    //public abstract void paint(Graphics g);

    @Override
    public Rectangle getRect() {
        return rect;
    }

    @Override
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    // 碰撞后回到上一次的位置
    @Override
    public void back() {}

    @Override
    public Group getGroup() {
        return null;
    }

    @Override
    public UUID getId() {
        return null;
    }

    @Override
    public void fire() {}

    @Override
    public boolean isMoving() {
        return true;
    }

    @Override
    public String getName() {
        return null;
    }

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
