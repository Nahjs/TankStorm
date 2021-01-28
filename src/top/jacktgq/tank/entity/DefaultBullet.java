package top.jacktgq.tank.entity;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.mgr.ResourceMgr;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/23--22:13
 * @Description 默认风格的炮弹类
 */
public class DefaultBullet extends BaseBullet {
    private static int SPEED = 6;
    private Dir dir;

    public BufferedImage curBulletImage;

    public DefaultBullet(int x, int y, Dir dir, GameModel gameModel, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.gameModel = gameModel;
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

    @Override
    public void paint(Graphics g) {
        // 子弹越界或者打在坦克上，移除掉
        if (!islive()) {
            gameModel.gameObjects.remove(this);
        }
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
        int tankWidth = gameModel.gameWidth;
        int tankHeight = gameModel.gameHeight;
        int bulletWidth = curBulletImage.getWidth();
        int bulletHeight = curBulletImage.getHeight();
        return x + bulletWidth < 0 || y + bulletHeight < 0 || x > tankWidth || y > tankHeight;
    }

}
