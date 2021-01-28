package top.jacktgq.tank.entity.abstractEntity;

import top.jacktgq.tank.util.Audio;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:22
 * @Description
 */
public abstract class BaseExplode {
    protected Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    protected int speed = 2;
    protected int step = 0;

    public abstract void paint(Graphics g);

    public BaseExplode(Rectangle tankRect) {
        this.tankRect = tankRect;
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
