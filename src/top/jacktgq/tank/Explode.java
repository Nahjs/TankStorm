package top.jacktgq.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/24--11:52
 * @Description 爆炸动画
 */
public class Explode {
    private Rectangle tankRect; // 爆炸坦克的尺寸和坐标
    private int speed = 2;
    private int step = 0;

    public Explode(Rectangle tankRect, int y) {
        this.tankRect = tankRect;
        new Thread(()->new Audio("audio/explode.wav").play()).start();
    }

    // 绘制爆炸效果
    public void paint(Graphics g) {
        BufferedImage explodeImage = ResourceMgr.explodes[step];
        int width = explodeImage.getWidth();
        int height = explodeImage.getHeight();
        g.drawImage(explodeImage, tankRect.x + (tankRect.width - width) / 2, tankRect.y - (tankRect.height - height) / 2, null);
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
