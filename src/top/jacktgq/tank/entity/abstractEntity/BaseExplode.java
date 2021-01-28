package top.jacktgq.tank.entity.abstractEntity;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.util.Audio;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:22
 * @Description
 */
public abstract class BaseExplode extends GameObject {
    protected Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    protected GameModel gameModel;
    protected int speed = 2;
    protected int step = 0;

    @Override
    public abstract void paint(Graphics g);

    public BaseExplode(Rectangle tankRect, GameModel gameModel) {
        this.tankRect = tankRect;
        this.gameModel = gameModel;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    /**
     * 获取爆炸动画到了第几张图片
     * @return
     */
    public int getStep() {
        return step;
    }
}
