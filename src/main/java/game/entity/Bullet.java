package game.entity;

import game.GameModel;
import game.entity.BaseObject.BaseBullet;
import game.loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * 炮弹
 */
public class Bullet extends BaseBullet {
    public BufferedImage curBulletImage;//用于存储当前炮弹的图像

    public Bullet(UUID id, UUID tankId, int x, int y, Dir dir, Group group) {
        this.id = id;
        this.tankId = tankId;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.rect = new Rectangle();

        //根据炮弹的方向 (dir) 来设置 curBulletImage（炮弹的图像）。
        switch (dir) {
            case LEFT: curBulletImage = ResourceLoader.bulletL; break;
            case UP: curBulletImage = ResourceLoader.bulletU; break;
            case RIGHT: curBulletImage = ResourceLoader.bulletR; break;
            case DOWN: curBulletImage = ResourceLoader.bulletD; break;
        }
        updateRect(x, y);
    }

    //更新炮弹的矩形区域 rect，设置其位置和尺寸以匹配 curBulletImage 的宽度和高度。
    private void updateRect(int x, int y) {
        rect.x = x;
        rect.y = y;
        rect.width = curBulletImage.getWidth();
        rect.height = curBulletImage.getHeight();
    }

    //绘制炮弹的图像，并调用 move() 方法来移动炮弹。
    @Override
    public void paint(Graphics g) {
        // 子弹越界或者打在坦克上，移除掉
        if (!islive()) {
            GameModel.INSTANCE.gameObjects.remove(this);
        }
        g.drawImage(curBulletImage, x, y, null);
        move();
    }


    //根据炮弹的方向移动炮弹，每次移动后更新炮弹的矩形区域 rect。
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

    //返回发射炮弹的坦克的 ID。
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
