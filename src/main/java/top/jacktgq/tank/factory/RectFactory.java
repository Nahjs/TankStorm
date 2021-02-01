package top.jacktgq.tank.factory;

import top.jacktgq.tank.entity.*;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.entity.abstractEntity.BaseExplode;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:25
 * @Description 生产方形风格的坦克、子弹和爆炸效果的工厂类
 */
public class RectFactory extends GameFactory {
    @Override
    public BaseTank createSelfTank(int x, int y, Dir dir, int speed) {
        return new RectSelfTank(x, y, dir, speed);
    }

    @Override
    public BaseTank createEnemyTank(int x, int y, Dir dir, int speed) {
        return new RectEnemyTank(x, y, Dir.DOWN, speed);
    }

    @Override
    public BaseBullet createBullet(int x, int y, Dir dir, Group group) {
        return new RectBullet(x, y, dir, group);
    }

    @Override
    public BaseExplode createExplode(Rectangle tankRect) {
        return new RectExplode(tankRect);
    }

    @Override
    public GameObject createWall(int x, int y, int width, int height) {
        return new DefaultWall(x, y, width, height);
    }
}
