package top.jacktgq.tank.entity;

import top.jacktgq.tank.mgr.PropertyMgr;
import top.jacktgq.tank.mgr.ResourceMgr;
import top.jacktgq.tank.strategy.DefaultFireStrategy;
import top.jacktgq.tank.strategy.FireStrategy;
import top.jacktgq.tank.view.TankPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 坦克父类
 */
public class Tank {
    public TankPanel tankPanel;
    public Group group;
    public int x;
    public int y;
    //private static final int WIDTH = 60;
    //private static final int HEIGHT = 60;
    protected int speed = 10;
    public Dir dir = Dir.DOWN;
    public BufferedImage curTankImage;  // 当前坦克加载的图片
    protected boolean moving = true;
    private Rectangle rect;
    private FireStrategy fireStrategy = new DefaultFireStrategy();

    public Tank(int x, int y, Dir dir, int speed, TankPanel tankPanel, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
        this.tankPanel = tankPanel;
        this.group = group;
        this.curTankImage = (group == Group.SELF ? ResourceMgr.selfTankU : ResourceMgr.enemyTankD);
        this.rect = new Rectangle();
        updateRect(x, y);
        if (group == Group.SELF) {
            fireStrategy = PropertyMgr.getSelf_tank_fs();
        } else {
            fireStrategy = PropertyMgr.getEnemy_tank_fs();
        }
    }

    private void updateRect(int x, int y) {
        rect.x = x;
        rect.y = y;
        rect.width = curTankImage.getWidth();
        rect.height = curTankImage.getHeight();
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
    protected void move() {
        if(!moving)
            return;

        switch (dir) {
            case LEFT: {
                x -= speed;
                curTankImage = (group == Group.SELF ? ResourceMgr.selfTankL : ResourceMgr.enemyTankL);
                break;
            }
            case UP: {
                y -= speed;
                curTankImage = (group == Group.SELF ? ResourceMgr.selfTankU : ResourceMgr.enemyTankU);
                break;
            }
            case RIGHT: {
                x += speed;
                curTankImage = (group == Group.SELF ? ResourceMgr.selfTankR : ResourceMgr.enemyTankR);
                break;
            }
            case DOWN: {
                y += speed;
                curTankImage = (group == Group.SELF ? ResourceMgr.selfTankD : ResourceMgr.enemyTankD);
                break;
            }
        }
        // 添加边界检测：坦克不能走出游戏区域
        boundsCheck();

        updateRect(x, y);
    }

    /**
     * 边界检测：坦克不能走出游戏区域
     */
    private void boundsCheck() {
        if (x < 0) {
            x = 0;
        }
        if (y < 0) {
            y = 0;
        }
        if (x + curTankImage.getWidth() > tankPanel.getWidth()) {
            x = tankPanel.getWidth() - curTankImage.getWidth();
        }
        if (y + curTankImage.getHeight() > tankPanel.getHeight()) {
            y = tankPanel.getHeight() - curTankImage.getHeight();
        }
    }

    /**
     * 打出一颗子弹
     */
    public void fire() {
        fireStrategy.fire(this);
    }

    // 获取坦克图片坐标和宽高
    public Rectangle getTankRect() {
        int tankWidth = curTankImage.getWidth();
        int tankHeight = curTankImage.getHeight();
        return new Rectangle(x, y, tankWidth, tankHeight);
    }

    public Group getGroup() {
        return group;
    }
}
