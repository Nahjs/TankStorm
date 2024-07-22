package game.factory;

import game.factory.abstractfactory.GameFactory;
import game.object.BaseObject.BaseBoom;
import game.object.BaseObject.BaseBullet;
import game.object.BaseObject.BaseTank;
import game.object.*;

import java.awt.*;
import java.util.UUID;

/**
 * 生产坦克、子弹、爆炸的工厂
 */
public class DefaultFactory extends GameFactory {
    @Override
    public BaseTank createSelfTank(UUID id, int x, int y, Dir dir, int speed) {
        MyTank tank = new MyTank(id, x, y, dir, speed);
        //GameDesign.INSTANCE.registerTank(id, new Player(id.toString())); // 注册坦克和玩家的映射
        return tank;
    }

    @Override
    public BaseTank createEnemyTank(UUID id, int x, int y, Dir dir, int speed) {
        return new EnemyTank(id, x, y, Dir.DOWN, speed);
    }

    @Override
    public BaseBullet createBullet(UUID id, UUID tankId, int x, int y, Dir dir, Group group) {
        return new Bullet(id, tankId, x, y, dir, group);
    }

    @Override
    public BaseBoom createExplode(Rectangle tankRect) {
        return new Boom(tankRect);
    }

    @Override
    public GameObject createWall(Image image, int x, int y, int width, int height) {
        return new Wall(image,x, y, width, height);
    }
}
