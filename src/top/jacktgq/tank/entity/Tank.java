package top.jacktgq.tank.entity;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;
import top.jacktgq.tank.mgr.PropertyMgr;
import top.jacktgq.tank.mgr.ResourceMgr;
import top.jacktgq.tank.strategy.DefaultFireStrategy;
import top.jacktgq.tank.strategy.FireStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 坦克父类
 */
public abstract class Tank extends BaseTank {
    public Group group;
    //private static final int WIDTH = 60;
    //private static final int HEIGHT = 60;
    protected int speed = 10;
    public Dir dir = Dir.DOWN;
    public BufferedImage curTankImage;  // 当前坦克加载的图片
    protected boolean moving = true;
    private Rectangle rect;
    private FireStrategy fireStrategy = new DefaultFireStrategy();

    public Tank(int x, int y, Dir dir, int speed, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
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

    @Override
    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    @Override
    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public abstract void paint(Graphics g);

    /**
     * 坦克移动
     */
    protected void move() {
        oldX = x;
        oldY = y;
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
        if (x + curTankImage.getWidth() > GameModel.getINSTANCE().gameWidth) {
            x = GameModel.getINSTANCE().gameWidth - curTankImage.getWidth();
        }
        if (y + curTankImage.getHeight() > GameModel.getINSTANCE().gameHeight) {
            y = GameModel.getINSTANCE().gameHeight - curTankImage.getHeight();
        }
    }

    /**
     * 打出一颗子弹
     */
    @Override
    public void fire() {
        fireStrategy.fire(this);
    }

    // 获取坦克图片坐标和宽高
    @Override
    public Rectangle getRect() {
        int tankWidth = curTankImage.getWidth();
        int tankHeight = curTankImage.getHeight();
        return new Rectangle(x, y, tankWidth, tankHeight);
    }

    @Override
    public Group getGroup() {
        return group;
    }
}
