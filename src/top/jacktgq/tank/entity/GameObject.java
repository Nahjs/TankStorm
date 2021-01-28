package top.jacktgq.tank.entity;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--17:04
 * @Description 游戏物体
 */
public abstract class GameObject {
    int x, y;

    public abstract void paint(Graphics g);
}
