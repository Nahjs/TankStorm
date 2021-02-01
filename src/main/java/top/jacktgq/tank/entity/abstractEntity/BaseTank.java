package top.jacktgq.tank.entity.abstractEntity;

import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.GameObjectType;
import top.jacktgq.tank.entity.Group;

import java.awt.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:19
 * @Description 坦克基类
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
}
