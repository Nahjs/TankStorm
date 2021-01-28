package top.jacktgq.tank.entity.abstractEntity;

import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:19
 * @Description 坦克基类
 */
public abstract class BaseTank {
    public abstract void setDir(Dir dir);

    public abstract void setMoving(boolean moving);

    public abstract void paint(Graphics g);

    public abstract void fire();

    // 获取坦克图片坐标和宽高
    public abstract Rectangle getTankRect();

    public abstract Group getGroup();
}
