package designer;

import game.RunGame;
import game.collider.ColliderChain;
import decorator.BorderDecorator;
import decorator.IdDecorator;
import game.object.*;
import game.factory.abstractfactory.GameFactory;
import gui.over.FailGUI;
import gui.over.base.OverGUI;
import gui.start.StartGame;
import loader.ConfigLoader;
import loader.ResourceLoader;
import net.Client;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * 将TankPanel和游戏对象（Tank、Bullet）分离，即Model和view分离
 * 使用门面模式，GameModel作为Facade，负责与TankPanel打交道
 */
public class GameDesign {
    public static final GameDesign INSTANCE = new GameDesign(ConfigLoader.getGameWidth(), ConfigLoader.getGameHeight());
    public int gameWidth, gameHeight;   // 游戏区域宽高
    GameObject selfTank;
    public List<GameObject> gameObjects = new ArrayList<>();
    public HashMap<UUID, GameObject> tankMap = new HashMap<>();
    public HashMap<UUID, GameObject> bulletMap = new HashMap<>();
    public ColliderChain colliderChain;
    public GameFactory factory;

    public Random random = new Random();
    public boolean gameOver = false;
    public boolean gameSuccess = false;

    private GameDesign(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        factory = ConfigLoader.getFactory();
        colliderChain = new ColliderChain();

        // 初始化墙
        gameObjects.add(factory.createWall(100, 220, 50, 50));
        gameObjects.add(factory.createWall(620, 220, 50, 50));
        gameObjects.add(factory.createWall(100, 530, 50, 50));
        gameObjects.add(factory.createWall(820, 130, 50, 50));
        gameObjects.add(factory.createWall(270, 300, 60, 100));
        gameObjects.add(factory.createWall(830, 300, 60, 100));
        gameObjects.add(factory.createWall(250, 120, 50, 50));
        gameObjects.add(factory.createWall(480, 120, 50, 50));
        gameObjects.add(factory.createWall(750, 530, 50, 50));
        gameObjects.add(factory.createWall(520, 530, 50, 50));
        gameObjects.add(factory.createWall(460, 360, 100, 50));

        // 初始化我方坦克，位置随机（位置必须合法，不能和墙壁相交），方向随机
        initSelfTank();
        addTank(selfTank);
        // 随机产生enemy_tank_count辆敌方坦克
        initEnemyTanks();
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
            tankRect.width = ResourceLoader.selfTankU.getWidth();
            tankRect.height = ResourceLoader.selfTankU.getHeight();
            tankRect.x = random.nextInt(gameWidth - tankRect.width);
            tankRect.y = random.nextInt(gameHeight - tankRect.height);
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
        selfTank = new IdDecorator(factory.createSelfTank(UUID.randomUUID(), tankRect.x, tankRect.y, Dir.values()[random.nextInt(4)], 5));
        //selfTank = factory.createSelfTank(tankRect.x, tankRect.y, Dir.values()[ramdom.nextInt(4)], 5);
        return;


    }


private void initEnemyTanks() {
    int enemyTankCount = ConfigLoader.getEnemy_tank_count(); // 敌方坦克的数量
    for (int i = 0; i < enemyTankCount; i++) {
        Rectangle tankRect = new Rectangle();
        boolean valid = false;
        while (!valid) {
            tankRect.width = ResourceLoader.enemyTankU.getWidth(); // 假设这是敌方坦克的图像宽度
            tankRect.height = ResourceLoader.enemyTankU.getHeight(); // 假设这是敌方坦克的图像高度
            tankRect.x = random.nextInt(gameWidth - tankRect.width);
            tankRect.y = random.nextInt(gameHeight - tankRect.height);
            valid = true; // 假设位置有效
            for (GameObject gameObject : gameObjects) {
                if (gameObject.getGameObjectType() == GameObjectType.WALL) {
                    // 如果此次生成的随机坦克位置和围墙相交，就重新生成
                    if (gameObject.getRect().intersects(tankRect)) {
                        valid = false;
                        break;
                    }
                }
            }
        }
        // 随机生成方向
        Dir direction = Dir.values()[random.nextInt(Dir.values().length)];
        // 创建敌方坦克并添加到游戏对象列表中
        GameObject enemyTank = factory.createEnemyTank(UUID.randomUUID(), tankRect.x, tankRect.y, direction, 5);
        gameObjects.add(new BorderDecorator(enemyTank));
        tankMap.put(enemyTank.getId(), enemyTank);
    }
}
//游戏背景设置
    public void paint(Graphics g, int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
      //  g.setColor(Color.darkGray);//游戏背景色设置
        // 加载背景图片
        ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/background.png"));
        Image backgroundImage = background.getImage();

        // 缩放图片以适应窗口大小
        g.drawImage(backgroundImage, 0, 0, gameWidth, gameHeight, null);
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
        gameObjects.add(new IdDecorator(tank));
        tankMap.put(tank.getId(), tank);
    }

    public void addBullet(GameObject bullet) {
        gameObjects.add(bullet);
        bulletMap.put(bullet.getId(), bullet);
    }

    public GameObject findTankByUUID(UUID id) {
        return tankMap.get(id);
    }

    public GameObject findBulletByUUID(UUID id) {
        return bulletMap.get(id);
    }

    public void removeTankByUUID(UUID tankId) {
        GameObject tank = tankMap.get(tankId);
        // 检查坦克是否存在，并且不是玩家控制的坦克（即不是MyTank的实例）
        if (tank != null && !(tank instanceof MyTank)) {
            gameObjects.remove(tank);
            tankMap.remove(tankId);
        }
    }

    public void removePlayerTankByUUID(UUID tankId) {
        System.out.println("GameDesign: Removing player tank by UUID: " + tankId);

        // 从tankMap中获取坦克对象
        GameObject tank = tankMap.get(tankId);

        // 检查坦克是否存在
        System.out.println("GameDesign: Tank found? " + (tank != null));

        if (tank != null) {
            // 检查坦克是否是玩家控制的坦克（MyTank的实例）
            if (tank instanceof MyTank) {
                MyTank playerTank = (MyTank) tank;

                System.out.println("GameDesign: Tank is an instance of MyTank.");

                // 设置坦克死亡状态
                System.out.println("GameDesign: Setting player tank death state.");
                playerTank.die();

                // ... 其他代码 ...
                // 关闭 RunGame 窗口
                System.out.println("GameDesign: Closing RunGame window.");
                RunGame.closeRunGame();

                // 启动游戏结束窗口
                System.out.println("GameDesign: Creating and showing OverGUI.");
                OverGUI overGUI = new OverGUI();
                overGUI.setVisible(true);
            } else {
                System.out.println("GameDesign: Tank is not an instance of MyTank.");
            }
        } else {
            System.out.println("GameDesign: Tank not found in tankMap.");
        }
    }
    public void removeBulletByUUID(UUID bulletId) {
        GameObject bullet = bulletMap.get(bulletId);
        if (bullet != null) {
            gameObjects.remove(bullet);
            bulletMap.remove(bulletId);
        }
    }
}
