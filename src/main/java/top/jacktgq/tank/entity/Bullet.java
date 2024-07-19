package top.jacktgq.tank.entity;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.BaseObject.BaseBullet;
import top.jacktgq.tank.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/23--22:13
 * @Description 默认风格的炮弹类
 */
public class Bullet extends BaseBullet {
    public BufferedImage curBulletImage;

    public Bullet(UUID id, UUID tankId, int x, int y, Dir dir, Group group) {
        this.id = id;
        this.tankId = tankId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.rect = new Rectangle();
        switch (dir) {
            case LEFT: curBulletImage = ResourceLoader.bulletL; break;
            case UP: curBulletImage = ResourceLoader.bulletU; break;
            case RIGHT: curBulletImage = ResourceLoader.bulletR; break;
            case DOWN: curBulletImage = ResourceLoader.bulletD; break;
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
            GameModel.INSTANCE.gameObjects.remove(this);
        }
        g.drawImage(curBulletImage, x, y, null);
        move();
    }

    private void move() {
        switch (dir) {
            case LEFT: {
                x -= speed;
                curBulletImage = ResourceLoader.bulletL;
                break;
            }
            case UP: {
                y -= speed;
                curBulletImage = ResourceLoader.bulletU;
                break;
            }
            case RIGHT: {
                x += speed;
                curBulletImage = ResourceLoader.bulletR;
                break;
            }
            case DOWN: {
                y += speed;
                curBulletImage = ResourceLoader.bulletD;
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
        int tankWidth = GameModel.INSTANCE.gameWidth;
        int tankHeight = GameModel.INSTANCE.gameHeight;
        int bulletWidth = curBulletImage.getWidth();
        int bulletHeight = curBulletImage.getHeight();
        return x + bulletWidth < 0 || y + bulletHeight < 0 || x > tankWidth || y > tankHeight;
    }

    @Override
    public UUID getTankId() {
        return tankId;
    }

    @Override
    public String toString() {
        return "DefaultBullet{" +
                "dir=" + dir +
                ", group=" + group +
                ", id=" + id +
                ", tankId=" + tankId +
                '}';
    }
}
