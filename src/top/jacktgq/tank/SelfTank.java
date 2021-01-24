package top.jacktgq.tank;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 我方坦克
 */
public class SelfTank {
    private TankPanel tankPanel;
    private Group group = Group.ENEMY;
    private int x;
    private int y;
    //private static final int WIDTH = 60;
    //private static final int HEIGHT = 60;
    private int speed = 10;
    private Dir dir = Dir.DOWN;
    private BufferedImage curTankImage = ResourceMgr.tankU;;  // 当前坦克加载的图片
    private boolean moving = false;

    public SelfTank(int x, int y, Dir dir, int speed, TankPanel tankPanel) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
        this.tankPanel = tankPanel;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public void paint(Graphics g) {
        /*g.setColor(Color.MAGENTA);
        g.fillRect(x, y, WIDTH, HEIGHT);*/
        g.drawImage(curTankImage, x, y, null);
        move();
    }

    /**
     * 坦克移动
     */
    private void move() {
        if(!moving)
            return;
        switch (dir) {
            case LEFT: {
                x -= speed;
                curTankImage = ResourceMgr.tankL;
                break;
            }
            case UP: {
                y -= speed;
                curTankImage = ResourceMgr.tankU;
                break;
            }
            case RIGHT: {
                curTankImage = ResourceMgr.tankR;
                x += speed;
                break;
            }
            case DOWN: {
                curTankImage = ResourceMgr.tankD;
                y += speed;
                break;
            }
        }
    }

    /**
     * 打出一颗子弹
     */
    public void fire() {
        // 根据坦克的方向确定打出子弹的位置
        int bulletX = 0, bulletY = 0;
        int tankWidth = curTankImage.getWidth();
        int tankHeight = curTankImage.getHeight();
        int bulletWidth, bulletHeight;
        switch (dir) {
            case LEFT: {
                bulletX = x - 5;
                bulletHeight = ResourceMgr.bulletL.getHeight();
                bulletY = y + (tankHeight - bulletHeight) / 2;
                break;
            }
            case UP: {
                bulletWidth = ResourceMgr.bulletU.getWidth();
                bulletX = x + (tankWidth - bulletWidth) / 2;
                bulletY = y - 5;
                break;
            }
            case RIGHT: {
                bulletHeight = ResourceMgr.bulletR.getHeight();
                bulletX = x + tankWidth + 5;
                bulletY = y + (tankHeight - bulletHeight) / 2;
                break;
            }
            case DOWN: {
                bulletWidth = ResourceMgr.bulletD.getWidth();
                bulletX = x + (tankWidth - bulletWidth) / 2;
                bulletY = y + tankHeight + 5;
                break;
            }
        }

        tankPanel.bullets.add(new Bullet(bulletX, bulletY, this.dir, tankPanel, Group.SELF));
    }

    public Group getGroup() {
        return group;
    }
}
