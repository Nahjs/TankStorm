package top.jacktgq.tank.entity;

import java.awt.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/28--17:04
 * @Description 游戏物体
 */
public abstract class GameObject {
    public int x, y;
    protected int oldX, oldY;

    public Dir dir = Dir.DOWN;

    public abstract void paint(Graphics g);

    public abstract Rectangle getRect();

    public abstract GameObjectType getGameObjectType();

    public abstract void back();

    public abstract Group getGroup();

    public abstract UUID getId();

    public abstract boolean isMoving();

    public abstract String getName();

    public abstract void fire();

    public abstract void setMoving(boolean b);

    public abstract void setDir(Dir dir);

    public abstract Dir getDir();

    public abstract int getX();

    public abstract int getY();
}
