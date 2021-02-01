package top.jacktgq.tank.factory;

import top.jacktgq.tank.entity.*;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.entity.abstractEntity.BaseExplode;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;

import java.awt.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:25
 * @Description 默认的生产坦克、子弹、爆炸的工厂
 */
public class DefaultFactory extends GameFactory {
    @Override
    public BaseTank createSelfTank(UUID id, int x, int y, Dir dir, int speed) {
        return new DefaultSelfTank(id, x, y, dir, speed);
    }

    @Override
    public BaseTank createEnemyTank(UUID id, int x, int y, Dir dir, int speed) {
        return new DefaultEnemyTank(id, x, y, Dir.DOWN, speed);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group) {
        return new DefaultBullet(x, y, dir, group);
    }

    @Override
    public BaseExplode createExplode(Rectangle tankRect) {
        return new DefaultExplode(tankRect);
    }

    @Override
    public GameObject createWall(int x, int y, int width, int height) {
        return new DefaultWall(x, y, width, height);
    }
}
