package top.jacktgq.tank.entity;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.BaseObject.BaseBullet;
import top.jacktgq.tank.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 *炮弹类
 */
public class SimpleBullet extends BaseBullet {
    private int bulletWidth = 20, bulletHeight = 20;
    public BufferedImage curBulletImage;


    public SimpleBullet(UUID id, UUID tankId, int x, int y, Dir dir, Group group) {
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
                x -= speed;
                break;
            }
            case UP: {
                y -= speed;
                break;
            }
            case RIGHT: {
                x += speed;
                break;
            }
            case DOWN: {
                y += speed;
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

    @Override
    public UUID getTankId() {
        return tankId;
    }

    /**
     * 判断子弹有没有飞出游戏区域
     * @return
     */
    private boolean isOutOfScreen() {
        int tankWidth = GameModel.INSTANCE.gameWidth;
        int tankHeight = GameModel.INSTANCE.gameHeight;
        return x + bulletWidth < 0 || y + bulletHeight < 0 || x > tankWidth || y > tankHeight;
    }
}
