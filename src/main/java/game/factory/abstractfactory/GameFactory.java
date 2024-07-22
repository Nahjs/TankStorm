package game.factory.abstractfactory;

import designer.GameDesign;
import game.object.BaseObject.BaseBullet;
import game.object.BaseObject.BaseTank;
import game.object.GameObject;
import game.object.Group;
import game.object.Dir;
import game.object.BaseObject.BaseBoom;
import game.object.MyTank;
import rank.Player;

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
    public BaseTank createSelfTank(UUID id, int x, int y, Dir dir, int speed) {
        MyTank tank = new MyTank(id, x, y, dir, speed);
        GameDesign.INSTANCE.registerTank(id, new Player(id.toString())); // 注册坦克和玩家的映射
        return tank;
    }/**
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


    public abstract GameObject createWall(Image image, int x, int y, int width, int height);
}
