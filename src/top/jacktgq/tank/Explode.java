package top.jacktgq.tank;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/24--11:52
 * @Description 爆炸动画
 */
public class Explode {
    private int x, y;
    private int speed = 2;
    private int step = 0;

    public Explode(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    // 绘制爆炸效果
    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step], x, y, null);
        step++;
    }

    /**
     * 获取爆炸动画到了第几张图片
     * @return
     */
    public int getStep() {
        return step;
    }
}
