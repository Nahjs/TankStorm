package top.jacktgq.tank.factory;

import top.jacktgq.tank.entity.*;
import top.jacktgq.tank.entity.BaseObject.BaseBullet;
import top.jacktgq.tank.entity.BaseObject.BaseExplode;
import top.jacktgq.tank.entity.BaseObject.BaseTank;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;

import java.awt.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/28--9:25
 * @Description 生产方形风格的坦克、子弹和爆炸效果的工厂类
 */
public class RectFactory extends GameFactory {
    @Override
    public BaseTank createSelfTank(UUID id, int x, int y, Dir dir, int speed) {
        return new SimpleMyTank(id, x, y, dir, speed);
    }

    @Override
    public BaseTank createEnemyTank(UUID id, int x, int y, Dir dir, int speed) {
        return new SimpleEnemyTank(id, x, y, Dir.DOWN, speed);
    }

    @Override
    public BaseBullet createBullet(UUID id, UUID tankId, int x, int y, Dir dir, Group group) {
        return new SimpleBullet(id, tankId, x, y, dir, group);
    }

    @Override
    public BaseExplode createExplode(Rectangle tankRect) {
        return new SimpleExplode(tankRect);
    }

    @Override
    public GameObject createWall(int x, int y, int width, int height) {
        return new Wall(x, y, width, height);
    }
}
