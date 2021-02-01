package top.jacktgq.tank;

import net.Client;
import top.jacktgq.tank.collider.ColliderChain;
import top.jacktgq.tank.decorator.RectDecorator;
import top.jacktgq.tank.decorator.TankIdDecorator;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.GameObjectType;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;
import top.jacktgq.tank.mgr.PropertyMgr;
import top.jacktgq.tank.mgr.ResourceMgr;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * @Author CandyWall
 * @Date 2021/1/28--15:31
 * @Description 将TankPanel和游戏对象（Tank、Bullet）分离，即Model和view分离
 *              使用门面模式，GameModel作为Facade，负责与TankPanel打交道
 */
public class GameModel {
    public static final GameModel INSTANCE = new GameModel(PropertyMgr.getGameWidth(), PropertyMgr.getGameHeight());
    public int gameWidth, gameHeight;   // 游戏区域宽高
    GameObject selfTank;
    public List<GameObject> gameObjects = new ArrayList<>();
    public HashMap<UUID, GameObject> tankMap = new HashMap<>();
    public ColliderChain colliderChain;
    public GameFactory factory;
    public Random ramdom = new Random();

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
        // 初始化我方坦克，位置随机（位置必须合法，不能和墙壁相交），方向随机
        initSelfTank();
        gameObjects.add(selfTank);
        // 随机产生enemy_tank_count辆敌方坦克
        // initEnemyTanks();
        // 连接服务器
        new Thread(() -> {
            Client.INSTANCE.connect();
        }).start();
    }

    // 初始化我方坦克，位置随机（位置必须合法，不能和墙壁相交），方向随机
    private void initSelfTank() {
        Rectangle tankRect = new Rectangle();
        while (true) {
            boolean valid = true;
            tankRect.width = ResourceMgr.selfTankU.getWidth();
            tankRect.height = ResourceMgr.selfTankU.getHeight();
            tankRect.x = ramdom.nextInt(gameWidth - tankRect.width);
            tankRect.y = ramdom.nextInt(gameHeight - tankRect.height);
            for (GameObject gameObject : gameObjects) {
                if (gameObject.getGameObjectType() == GameObjectType.WALL) {
                    // 如果此次生成的随机坦克位置和围墙相交，就重新生成
                    if (gameObject.getRect().intersects(tankRect)) {
                        valid = false;
                        break;
                    }
                }
            }
            // 如果得到了合法的初始坦克位置，就退出循环
            if (valid) {
                break;
            }
        }
        // 使用装饰器模式在坦克上方绘制id，用以标识
        selfTank = new TankIdDecorator(factory.createSelfTank(UUID.randomUUID(), tankRect.x, tankRect.y, Dir.values()[ramdom.nextInt(4)], 5));
        //selfTank = factory.createSelfTank(tankRect.x, tankRect.y, Dir.values()[ramdom.nextInt(4)], 5);
        return;
    }

    private void initEnemyTanks() {
        int count = PropertyMgr.getEnemy_tank_count();
        // 将游戏区域网格化
        // 敌方坦克的高度和宽度

        // int rows = this.getWidth();
        for (int i = 0; i < count; i++) {
             gameObjects.add(new RectDecorator(factory.createEnemyTank(UUID.randomUUID(), 100 + 100 * i, 100, Dir.DOWN, 5)));
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

    public GameObject getSelfTank() {
        return selfTank;
    }


    public void addTank(GameObject tank) {
        gameObjects.add(new TankIdDecorator(tank));
        tankMap.put(tank.getId(), tank);
    }

    public GameObject findByUUID(UUID id) {
        return tankMap.get(id);
    }
}
