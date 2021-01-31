package top.jacktgq.tank.entity;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--17:04
 * @Description 游戏物体
 */
public abstract class GameObject {
    protected int x, y;
    protected int oldX, oldY;
    protected Group group;

    public abstract void paint(Graphics g);

    public abstract Rectangle getRect();

    public abstract GameObjectType getGameObjectType();

    public abstract void back();

    public abstract Group getGroup();
}
