package top.jacktgq.tank.entity;

import top.jacktgq.tank.mgr.ResourceMgr;
import top.jacktgq.tank.view.TankPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/23--22:13
 * @Description 炮弹类
 */
public class Bullet {
    private static int SPEED = 6;
    //public static final int WIDTH = 10;
    //public static final int HEIGHT = 10;
    private int x, y;
    private Dir dir;
    private TankPanel tankPanel;
    public BufferedImage curBulletImage;
    private Group group;
    private Rectangle rect;

    public Bullet(int x, int y, Dir dir, TankPanel tankPanel, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.tankPanel = tankPanel;
        this.group = group;
        this.rect = new Rectangle();
        switch (dir) {
            case LEFT: curBulletImage = ResourceMgr.bulletL; break;
            case UP: curBulletImage = ResourceMgr.bulletU; break;
            case RIGHT: curBulletImage = ResourceMgr.bulletR; break;
            case DOWN: curBulletImage = ResourceMgr.bulletD; break;
        }
        updateRect(x, y);
    }

    private void updateRect(int x, int y) {
        rect.x = x;
        rect.y = y;
        rect.width = curBulletImage.getWidth();
        rect.height = curBulletImage.getHeight();
    }

    public void paint(Graphics g) {
        /*g.setColor(Color.RED);
        g.fillOval(x, y, WIDTH, HEIGHT);*/
        g.drawImage(curBulletImage, x, y, null);
        move();
    }

    private void move() {
        switch (dir) {
            case LEFT: {
                x -= SPEED;
                curBulletImage = ResourceMgr.bulletL;
                break;
            }
            case UP: {
                y -= SPEED;
                curBulletImage = ResourceMgr.bulletU;
                break;
            }
            case RIGHT: {
                x += SPEED;
                curBulletImage = ResourceMgr.bulletR;
                break;
            }
            case DOWN: {
                y += SPEED;
                curBulletImage = ResourceMgr.bulletD;
                break;
            }
        }
        updateRect(x, y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    // 判断子弹的状态
    public boolean islive() {
        return !isOutOfScreen();
    }

    /**
     * 判断子弹有没有飞出游戏区域
     * @return
     */
    private boolean isOutOfScreen() {
        int width = tankPanel.getWidth();
        int height = tankPanel.getHeight();
        int bulletWidth = curBulletImage.getWidth();
        int bulletHeight = curBulletImage.getHeight();
        return x + bulletWidth < 0 || y + bulletHeight < 0 || x > width || y > height;
    }

    // 子弹和坦克进行碰撞检测，碰撞检测的方法调用的频率比较高
    // 每次都要产生新的Rectangle对象，会占用过多内存，
    // 所以将Rectangle对象放到全局，在坐标值发生变化的时候动态更新即可
    public boolean collideWith(Tank tank) {
        // 如果是己方坦克，无敌
        if (tank.getGroup() == Group.SELF) {
            return false;
        }
        // 一个阵营的不做碰撞检测
        if (getGroup() == tank.getGroup()) {
            return false;
        }
        return getBulletRect().intersects(tank.getTankRect());
    }

    // 获取子弹图片坐标和宽高
    public Rectangle getBulletRect() {
        return rect;
    }

    public Group getGroup() {
        return group;
    }
}
