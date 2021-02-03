package top.jacktgq.tank.decorator;

import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.GameObjectType;
import top.jacktgq.tank.entity.Group;

import java.awt.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/29--11:52
 * @Description 游戏物体装饰器
 */
public class GODecorator extends GameObject {
    protected GameObject gameObject;

    public GODecorator(GameObject gameObject) {
        this.gameObject = gameObject;
        this.x = gameObject.x;
        this.y = gameObject.y;
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

    @Override
    public UUID getId() {
        return gameObject.getId();
    }

    @Override
    public boolean isMoving() {
        return gameObject.isMoving();
    }

    @Override
    public String getName() {
        return gameObject.getName();
    }

    @Override
    public void setMoving(boolean b) {
        gameObject.setMoving(b);
    }

    @Override
    public void setDir(Dir dir) {
        gameObject.setDir(dir);
    }

    @Override
    public void fire() {
        gameObject.fire();
    }


    @Override
    public int getX() {
        return gameObject.getX();
    }

    @Override
    public int getY() {
        return gameObject.getY();
    }

    @Override
    public Dir getDir() {
        return gameObject.getDir();
    }

    @Override
    public void setX(int x) {
        gameObject.setX(x);
    }

    @Override
    public void setY(int y) {
        gameObject.setY(y);
    }
}
