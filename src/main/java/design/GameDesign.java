package design;

import Log.LogPrint;
import decorator.BorderDecorator;
import decorator.IdDecorator;
import game.RunGame;
import game.collider.BulletTankCollider;
import game.collider.Collider;
import game.collider.ColliderChain;
import game.factory.abstractfactory.GameFactory;
import game.object.*;
import gui.over.FailGUI;
import gui.start.StartGame;
import gui.start.login.JdbcUtils;
import loader.ConfigLoader;
import loader.ResourceLoader;
import net.Client;
import rank.Player;
import rank.RankManager;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.*;

/**
 * 使用门面模式，将TankPanel和游戏对象（Tank、Bullet）分离。GameDesign作为Facade，负责与TankPanel打交道
 */
public class GameDesign {
    public static final GameDesign INSTANCE = new GameDesign(ConfigLoader.getGameWidth(), ConfigLoader.getGameHeight());

    public int gameWidth, gameHeight;   // 游戏区域宽高

    GameObject selfTank;//玩家坦克
    public List<GameObject> gameObjects = new ArrayList<>();//游戏中的所有物体
    public HashMap<UUID, GameObject> tankMap = new HashMap<>();//存储坦克UUID和坦克对象的映射
    private Map<UUID, Player> tankToPlayerMap= new HashMap<>();; // 存储坦克UUID和Player对象的映射

    public HashMap<UUID, GameObject> bulletMap = new HashMap<>();//// 存储坦克UUID和子弹对象的映射
    public ColliderChain colliderChain;//责任链模式的应用
    public GameFactory factory;

    public Random random = new Random();
    public boolean gameFail = false;//判断游戏结果


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
        gameObjects.add(factory.createWall(270, 300, 50, 50));
        gameObjects.add(factory.createWall(830, 300, 50, 50));
        gameObjects.add(factory.createWall(250, 120, 50, 50));
        gameObjects.add(factory.createWall(480, 120, 50, 50));
        gameObjects.add(factory.createWall(750, 530, 50, 50));
        gameObjects.add(factory.createWall(520, 530, 50, 50));
        gameObjects.add(factory.createWall(460, 360, 50, 50));

        // 初始化我方坦克，位置随机（位置必须合法，不能和墙壁相交），方向随机
        initSelfTank();
        addTank(selfTank);

        // 随机产生enemy_tank_count辆敌方坦克
        initEnemyTanks(StartGame.getEnemyCount());


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
        UUID tankId = UUID.randomUUID(); // 为坦克生成ID
        selfTank = new IdDecorator(factory.createSelfTank(tankId, tankRect.x, tankRect.y,
                Dir.values()[random.nextInt(4)], 5));
        Player player = new Player(tankId.toString());
        registerTank( tankId,player);//调用方法向存储坦克UUID和Player对象的映射中插入信息...（未实现排行功能）
    }

   // public int getEnemyTankCountByDifficulty(String difficulty) {
//        return ConfigLoader.getEnemyTankCount(difficulty);
//    }

private void initEnemyTanks(int enemyTankCount) {

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

    //游戏内容设置
    public void paint(Graphics g, int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
      //  g.setColor(Color.darkGray);//游戏背景色设置
        // 加载背景图片
        ImageIcon background = new ImageIcon(ResourceLoader.class.getResource("/images/background.png"));
        Image backgroundImage = background.getImage();

        // 缩放图片以适应窗口大小
        g.drawImage(backgroundImage, 0, 0, gameWidth, gameHeight, null);

        // 绘制所有游戏物体：坦克、子弹、爆炸，加上了墙
        for (int i = 0; i < gameObjects.size(); i++) {
            gameObjects.get(i).paint(g);

        }

        // 处理游戏物体间的碰撞
        for (int i = 0; i < gameObjects.size(); i++) {
            for (int j = i + 1; j < gameObjects.size(); j++) {
                colliderChain.collide(gameObjects.get(i), gameObjects.get(j));//碰撞检测链用在这里
            }
        }
    }

    public GameObject getSelfTank() {
        return selfTank;
    }

    public void addTank(GameObject tank) {
        gameObjects.add(new IdDecorator(tank));
        tankMap.put(tank.getId(), tank);//往映射中插入数据
    }

    public void registerTank(UUID tankId, Player player) {
        tankToPlayerMap.put(tankId, player);
    }// （排行榜）当创建一个新的坦克时，将其添加到映射中

    // findPlayerByTankId方法的实现
    public Player findPlayerByTankId(UUID tankId) {
        LogPrint.log(this.getClass().getSimpleName(), "Searching for player with tank ID: " + tankId);//no
        return tankToPlayerMap.get(tankId); // 直接从映射中获取Player对象
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
        if (tank != null) {
            gameObjects.remove(tank);
            tankMap.remove(tankId);
            handleGameResult();
        }
    }
    public void handleGameResult() {
        if (gameFail) {
            SwingUtilities.invokeLater(() -> {
                RunGame.closeRunGame();
                new FailGUI().setVisible(true);
            });
        } else {

            checkBulletHits();//用来使击毁敌人坦克的玩家得分加1的方法

            // 如果游戏成功完成，则更新排行榜
//            SwingUtilities.invokeLater(() -> {
//                try {
//                    // 获取数据库连接
//                    Connection databaseConnection = JdbcUtils.getConnection();
//                    if (databaseConnection != null) {
//                        // 指定Excel文件路径
//                        String excelFilePath = "Scoreboard.xlsx";
//                        // 创建UpdateRanking实例
//                        UpdateRanking updateRanking = new UpdateRanking(databaseConnection, excelFilePath);
//
//                        // 更新排行榜
//                        updateRanking.update();
//                    } else {
//                        // 如果无法获取数据库连接，打印错误信息
//                        System.err.println("Failed to connect to the database.");
//                    }
//                } catch (SQLException e) {
//                    // 打印异常信息
//                    e.printStackTrace();
//                }
//
////            UpdateRanking updateRanking = new UpdateRanking(databaseConnection, excelFilePath);
////            updateRanking.update();
////            SwingUtilities.invokeLater(() -> {
////                RunGame.closeRunGame();
////                new VictoryGUI(true, null).setVisible(true);
//            });
        }
    }
    public void removeBulletByUUID(UUID bulletId) {
        GameObject bullet = bulletMap.get(bulletId);
        if (bullet != null) {
            gameObjects.remove(bullet);
            bulletMap.remove(bulletId);
        }
    }

    // 检查并处理子弹击中目标的逻辑
    public void checkBulletHits() {
        List<GameObject> gameObjectsCopy = new ArrayList<>(gameObjects);

        LogPrint.log(this.getClass().getSimpleName(), "Checking bullet hits...");//yes

        for (GameObject possibleBullet : gameObjectsCopy) {
            if (possibleBullet.getGameObjectType() == GameObjectType.BULLET) {
                Bullet bullet = (Bullet) possibleBullet;
                LogPrint.log(this.getClass().getSimpleName(), "Checking bullet: " + bullet.getId());//yes

                for (GameObject possibleTank : gameObjects) {
                    if (possibleTank.getGameObjectType() == GameObjectType.TANK) {
                        Tank tank = (Tank) possibleTank;
                        LogPrint.log(this.getClass().getSimpleName(), "Checking against tank: " + tank.getId());//no...

                        Collider collider = new BulletTankCollider();
                        if (collider.collide(bullet, tank)) {
                            LogPrint.log(this.getClass().getSimpleName(), "Collision detected between bullet " + bullet.getId() + " and tank " + tank.getId() + ". Calling updateScoreOnHit.");
                            updateScoreOnHit(bullet, tank);
                            gameObjects.remove(bullet);
                            gameObjects.remove(tank);
                            break;
                        }
                    }
                }
            }
        }
    }

    private void updateScoreOnHit(Bullet bullet, Tank tank) {
        UUID tankId = tank.getId();
        Player player = findPlayerByTankId(tankId);

        if (player != null) {
            LogPrint.log(this.getClass().getSimpleName(), "Updating score for player: " + player.getUsername());
            player.updateScore();

            try {
                RankManager rankManager = new RankManager(JdbcUtils.getConnection());
                rankManager.insertScore(player);
                LogPrint.log(this.getClass().getSimpleName(), "Score updated in database for player: " + player.getUsername());
            } catch (SQLException e) {
                e.printStackTrace();
                LogPrint.log(this.getClass().getSimpleName(), "Error updating score in database: " + e.getMessage());
            }
        } else {
            LogPrint.log(this.getClass().getSimpleName(), "No player found for tank ID: " + tankId);
        }
    }

//    public void removeTankByUUID(UUID tankId) {
//        GameObject tank = tankMap.get(tankId);
//        if (tank != null) {
//            boolean isEnemyTank = tank instanceof EnemyTank;
//            boolean isPlayerTank = tank instanceof MyTank;
//
//            gameObjects.remove(tank);
//            tankMap.remove(tankId);
//
//            if (isEnemyTank) {
//                enemyTankCount--;
//                if (enemyTankCount == 0) {
//                    new GameResultMsg(true);
//                }
//            } else if (isPlayerTank) {
//                playerTanks.remove(tank);
//                if (playerTanks.isEmpty()) {
//                    gameFail = true;
//
//                }
//
//            }
//
//        }
//
//        }

}
