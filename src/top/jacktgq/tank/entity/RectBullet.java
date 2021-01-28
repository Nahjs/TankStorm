package top.jacktgq.tank.entity;

import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.mgr.ResourceMgr;
import top.jacktgq.tank.view.TankPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/23--22:13
 * @Description 方块风格的炮弹类
 */
public class RectBullet extends BaseBullet {
    private static int SPEED = 6;
    private int bulletWidth = 20, bulletHeight = 20;
    private Dir dir;
    private TankPanel tankPanel;
    public BufferedImage curBulletImage;


    public RectBullet(int x, int y, Dir dir, TankPanel tankPanel, Group group) {
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
        rect.width = bulletWidth;
        rect.height = bulletHeight;
        updateRect(x, y);
    }

    private void updateRect(int x, int y) {
        rect.x = x;
        rect.y = y;
    }

    public void paint(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(x, y, bulletWidth, bulletHeight);
        move();
    }

    private void move() {
        switch (dir) {
            case LEFT: {
                x -= SPEED;
                break;
            }
            case UP: {
                y -= SPEED;
                break;
            }
            case RIGHT: {
                x += SPEED;
                break;
            }
            case DOWN: {
                y += SPEED;
                break;
            }
        }
        updateRect(x, y);
    }

    // 判断子弹的状态
    @Override
    public boolean islive() {
        return !isOutOfScreen();
    }

    /**
     * 判断子弹有没有飞出游戏区域
     * @return
     */
    private boolean isOutOfScreen() {
        int tankWidth = tankPanel.getWidth();
        int tankHeight = tankPanel.getHeight();
        return x + bulletWidth < 0 || y + bulletHeight < 0 || x > tankWidth || y > tankHeight;
    }
}
