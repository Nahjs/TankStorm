package top.jacktgq.tank;

import top.jacktgq.tank.collider.ColliderChain;
import top.jacktgq.tank.decorator.RectDecorator;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;
import top.jacktgq.tank.mgr.PropertyMgr;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author CandyWall
 * @Date 2021/1/28--15:31
 * @Description 将TankPanel和游戏对象（Tank、Bullet）分离，即Model和view分离
 *              使用门面模式，GameModel作为Facade，负责与TankPanel打交道
 */
public class GameModel {
    private static final GameModel INSTANCE = new GameModel(PropertyMgr.getGameWidth(), PropertyMgr.getGameHeight());
    public int gameWidth, gameHeight;   // 游戏区域宽高
    BaseTank selfTank;
    public List<GameObject> gameObjects = new ArrayList<>();
    public ColliderChain colliderChain;
    public GameFactory factory;

    public static GameModel getINSTANCE() {
        return INSTANCE;
    }

    private GameModel(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        factory = PropertyMgr.getFactory();
        colliderChain = new ColliderChain();
        // 初始化墙
        gameObjects.add(factory.createWall(100, 400, 60, 200));
        gameObjects.add(factory.createWall(820, 400, 60, 200));
        gameObjects.add(factory.createWall(200, 250, 200, 50));
        gameObjects.add(factory.createWall(600, 250, 200, 50));
        // 初始化我方坦克
        selfTank = factory.createSelfTank(350, 500, Dir.DOWN, 5);
        gameObjects.add(selfTank);
        // 随机产生enemy_tank_count辆敌方坦克
        // initEnemyTanks();
    }

    private void initEnemyTanks() {
        int count = PropertyMgr.getEnemy_tank_count();
        // 将游戏区域网格化
        // 敌方坦克的高度和宽度

        // int rows = this.getWidth();
        for (int i = 0; i < count; i++) {
             gameObjects.add(new RectDecorator(factory.createEnemyTank(100 + 100 * i, 100, Dir.DOWN, 5)));
            // gameObjects.add(factory.createEnemyTank(100 + 100 * i, 100, Dir.DOWN, 5));
        }
    }

    public void paint(Graphics g, int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        g.setColor(Color.BLACK);

        g.fillRect(0, 0, gameWidth, gameHeight);
        g.setColor(Color.WHITE);
        // 绘制所有游戏物体：坦克、子弹、爆炸
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);
        }

        // 处理游戏物体间的碰撞
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                colliderChain.collide(gameObjects.get(i), gameObjects.get(j));
            }
        }
    }

    public BaseTank getSelfTank() {
        return selfTank;
    }
}
