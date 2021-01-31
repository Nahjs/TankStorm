package top.jacktgq.tank.decorator;

import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.GameObjectType;
import top.jacktgq.tank.entity.Group;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/29--11:52
 * @Description 游戏物体装饰器
 */
public class GODecorator extends GameObject {
    protected GameObject gameObject;

    public GODecorator(GameObject gameObject) {
        this.gameObject = gameObject;
    }

    @Override
    public void paint(Graphics g) {
        gameObject.paint(g);
    }

    @Override
    public Rectangle getRect() {
        return gameObject.getRect();
    }

    @Override
    public GameObjectType getGameObjectType() {
        return gameObject.getGameObjectType();
    }

    @Override
    public Group getGroup() {
        return gameObject.getGroup();
    }

    // 碰撞后回到上一次的位置
    @Override
    public void back() {
        gameObject.back();
    }
}
