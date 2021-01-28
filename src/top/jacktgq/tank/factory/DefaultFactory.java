package top.jacktgq.tank.factory;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.*;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.entity.abstractEntity.BaseExplode;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:25
 * @Description 默认的生产坦克、子弹、爆炸的工厂
 */
public class DefaultFactory extends GameFactory {
    @Override
    public BaseTank createSelfTank(int x, int y, Dir dir, int speed, GameModel gameModel) {
        return new DefaultSelfTank(350, 500, Dir.DOWN, speed, gameModel);
    }

    @Override
    public BaseTank createEnemyTank(int x, int y, Dir dir, int speed, GameModel gameModel) {
        return new DefaultEnemyTank(x, y, Dir.DOWN, speed, gameModel);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, GameModel gameModel, Group group) {
        return new DefaultBullet(x, y, dir, gameModel, group);
    }

    @Override
    public BaseExplode createExplode(Rectangle tankRect, GameModel gameModel) {
        return new DefaultExplode(tankRect, gameModel);
    }
}
