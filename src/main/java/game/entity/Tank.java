package game.entity;

import game.GameModel;
import game.entity.BaseObject.BaseTank;
import game.loader.ConfigLoader;
import game.loader.ResourceLoader;
import game.strategy.DefaultFireStrategy;
import game.strategy.FireStrategy;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.UUID;

/**
 * 坦克父类
 */
public abstract class Tank extends BaseTank {
    public Group group;
    protected int speed = 10;

    public BufferedImage curTankImage;  // 当前坦克加载的图片

    private Rectangle rect;
    private FireStrategy fireStrategy = new DefaultFireStrategy();

    public Tank(UUID id, int x, int y, Dir dir, int speed, Group group) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.speed = speed;
        this.group = group;
        // this.curTankImage = (group == Group.SELF ? ResourceMgr.selfTankU : ResourceMgr.enemyTankD);
        initCurTankImage();
        this.rect = new Rectangle();
        updateRect();
        if (group == Group.SELF) {
            fireStrategy = ConfigLoader.getSelf_tank_fs();
        } else {
            fireStrategy = ConfigLoader.getEnemy_tank_fs();
        }
    }

    private void initCurTankImage() {
        if (group == Group.SELF) {
            switch (dir) {
                case LEFT:
                    curTankImage = ResourceLoader.selfTankL;
                    break;
                case UP:
                    curTankImage = ResourceLoader.selfTankU;
                    break;
                case RIGHT:
                    curTankImage = ResourceLoader.selfTankR;
                    break;
                case DOWN:
                    curTankImage = ResourceLoader.selfTankD;
                    break;
            }
        } else {
            switch (dir) {
                case LEFT:
                    curTankImage = ResourceLoader.enemyTankL;
                    break;
                case UP:
                    curTankImage = ResourceLoader.enemyTankU;
                    break;
                case RIGHT:
                    curTankImage = ResourceLoader.enemyTankR;
                    break;
                case DOWN:
                    curTankImage = ResourceLoader.enemyTankD;
                    break;
            }
        }
    }

    private void updateRect() {
        rect.x = x;
        rect.y = y;
        rect.width = curTankImage.getWidth();
        rect.height = curTankImage.getHeight();
    }

    @Override
    public void setDir(Dir dir) {
        this.dir = dir;
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
                curTankImage = (group == Group.SELF ? ResourceLoader.selfTankL : ResourceLoader.enemyTankL);
                break;
            }
            case UP: {
                y -= speed;
                curTankImage = (group == Group.SELF ? ResourceLoader.selfTankU : ResourceLoader.enemyTankU);
                break;
            }
            case RIGHT: {
                x += speed;
                curTankImage = (group == Group.SELF ? ResourceLoader.selfTankR : ResourceLoader.enemyTankR);
                break;
            }
            case DOWN: {
                y += speed;
                curTankImage = (group == Group.SELF ? ResourceLoader.selfTankD : ResourceLoader.enemyTankD);
                break;
            }
        }
        // 添加边界检测：坦克不能走出游戏区域
        boundsCheck();

        updateRect();
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
        if (x + curTankImage.getWidth() > GameModel.INSTANCE.gameWidth) {
            x = GameModel.INSTANCE.gameWidth - curTankImage.getWidth();
        }
        if (y + curTankImage.getHeight() > GameModel.INSTANCE.gameHeight) {
            y = GameModel.INSTANCE.gameHeight - curTankImage.getHeight();
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
        return rect;
    }

    @Override
    public Group getGroup() {
        return group;
    }
}
