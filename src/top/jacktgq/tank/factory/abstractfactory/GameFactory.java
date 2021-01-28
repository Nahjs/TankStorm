package top.jacktgq.tank.factory.abstractfactory;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.entity.abstractEntity.BaseExplode;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--8:56
 * @Description 生产坦克、子弹和爆炸的抽象工厂类
 */
public abstract class GameFactory {
    /**
     * 创建己方坦克
     * @return
     */
    public abstract BaseTank createSelfTank(int x, int y, Dir dir, int speed, GameModel gameModel);

    /**
     * 创建敌方坦克
     * @return
     */
    public abstract BaseTank createEnemyTank(int x, int y, Dir dir, int speed, GameModel gameModel);

    /**
     * 创建子弹
     * @return
     */
    public abstract BaseBullet createBullet(int x, int y, Dir dir, GameModel gameModel, Group group);

    /**
     * 创建爆炸
     * @return
     */
    public abstract BaseExplode createExplode(Rectangle tankRect);
}
