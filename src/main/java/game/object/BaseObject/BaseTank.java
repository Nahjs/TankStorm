package game.object.BaseObject;

import game.object.GameObject;
import game.object.Dir;
import game.object.GameObjectType;
import game.object.Group;

import java.awt.*;
import java.util.UUID;

/**
 * 坦克基类
 */
public abstract class BaseTank extends GameObject {
    public boolean moving = true;

    public String name;
    public UUID id;

    protected GameObjectType gameObjectType = GameObjectType.TANK;

    public abstract void setDir(Dir dir);

    @Override
    public Dir getDir() {
        return dir;
    }

    public abstract void setMoving(boolean moving);

    @Override
    public boolean isMoving() {
        return moving;
    }

    @Override
    public abstract void paint(Graphics g);

    @Override
    public abstract void fire();

    // 获取坦克图片坐标和宽高
    @Override
    public abstract Rectangle getRect();

    @Override
    public abstract Group getGroup();

    // 坦克在碰撞后回到上一次的位置
    @Override
    public void back() {
        x = oldX;
        y = oldY;
    }

    @Override
    public GameObjectType getGameObjectType() {
        return gameObjectType;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return null;
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
