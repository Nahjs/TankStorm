package game.factory.abstractfactory;

import game.entity.BaseObject.BaseBullet;
import game.entity.BaseObject.BaseTank;
import game.entity.GameObject;
import game.entity.Group;
import game.entity.Dir;
import game.entity.BaseObject.BaseBoom;

import java.awt.*;
import java.util.UUID;

/**
 * 生产坦克、子弹和爆炸的抽象工厂类
 */
public abstract class GameFactory {
    /**
     * 创建己方坦克
     * @return
     */
    public abstract BaseTank createSelfTank(UUID id, int x, int y, Dir dir, int speed);

    /**
     * 创建敌方坦克
     * @return
     */
    public abstract BaseTank createEnemyTank(UUID id, int x, int y, Dir dir, int speed);

    /**
     * 创建子弹
     * @return
     */
    public abstract BaseBullet createBullet(UUID id, UUID tankId, int x, int y, Dir dir, Group group);

    /**
     * 创建爆炸
     * @return
     */
    public abstract BaseBoom createExplode(Rectangle tankRect);

    public abstract GameObject createWall(int x, int y, int width, int height);



}