package designer;

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
 * 将TankPanel和游戏对象（Tank、Bullet）分离，即Model和view分离
 * 使用门面模式，GameDesign作为Facade，负责与TankPanel打交道
 */
public class GameDesign {
    public static final GameDesign INSTANCE = new GameDesign(ConfigLoader.getGameWidth(), ConfigLoader.getGameHeight());

    public int gameWidth, gameHeight;   // 游戏区域宽高

    GameObject selfTank;


    public List<GameObject> gameObjects = new ArrayList<>();
    public HashMap<UUID, GameObject> tankMap = new HashMap<>();
    private Map<UUID, Player> tankToPlayerMap= new HashMap<>();; // 存储坦克UUID和Player对象的映射

    public HashMap<UUID, GameObject> bulletMap = new HashMap<>();
    public ColliderChain colliderChain;
    public GameFactory factory;

    public Random random = new Random();
    public boolean gameFail = false;


    private GameDesign(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        factory = ConfigLoader.getFactory();
        colliderChain = new ColliderChain();

        // 初始化墙
        Image image =  new ImageIcon("images/wall/steel.gif").getImage();
        gameObjects.add(factory.createWall(image,100, 220, 50, 50));
        gameObjects.add(factory.createWall(image,620, 220, 50, 50));
        gameObjects.add(factory.createWall(image,100, 530, 50, 50));
        gameObjects.add(factory.createWall(image,820, 130, 50, 50));
        gameObjects.add(factory.createWall(image,270, 300, 50, 50));//
        gameObjects.add(factory.createWall(image,830, 300, 50, 50));
        gameObjects.add(factory.createWall(image,250, 120, 50, 50));
        gameObjects.add(factory.createWall(image,480, 120, 50, 50));
        gameObjects.add(factory.createWall(image,750, 530, 50, 50));
        gameObjects.add(factory.createWall(image,520, 530, 50, 50));
        gameObjects.add(factory.createWall(image,460, 360, 50, 50));

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
        // 假设这是创建玩家坦克的代码
//        UUID playerTankId = UUID.randomUUID();
//        BaseTank playerTank = factory.createSelfTank(UUID.randomUUID(), 100, 100, Dir.NORTH, 5);

        // 使用装饰器模式在坦克上方绘制id，用以标识
        UUID tankId = UUID.randomUUID(); // 为坦克生成ID
        selfTank = new IdDecorator(factory.createSelfTank(tankId, tankRect.x, tankRect.y,
                Dir.values()[random.nextInt(4)], 5));
       // Player player = new Player(tankId.toString());
    }

    public int getEnemyTankCountByDifficulty(String difficulty) {
        return ConfigLoader.getEnemyTankCount(difficulty);
    }

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
    // （做排行榜用）当创建一个新的坦克时，将其添加到映射中
    public void registerTank(UUID tankId, Player player) {
        tankToPlayerMap.put(tankId, player);
    }

    // findPlayerByTankId方法的实现
    public Player findPlayerByTankId(UUID tankId) {
        LogPrint.log(this.getClass().getSimpleName(), "Searching for player with tank ID: " + tankId);
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

            checkBulletHits();
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

        LogPrint.log(this.getClass().getSimpleName(), "Checking bullet hits...");

        for (GameObject possibleBullet : gameObjectsCopy) {
            if (possibleBullet.getGameObjectType() == GameObjectType.BULLET) {
                Bullet bullet = (Bullet) possibleBullet;
                LogPrint.log(this.getClass().getSimpleName(), "Checking bullet: " + bullet.getId());

                for (GameObject possibleTank : gameObjects) {
                    if (possibleTank.getGameObjectType() == GameObjectType.TANK) {
                        Tank tank = (Tank) possibleTank;
                        LogPrint.log(this.getClass().getSimpleName(), "Checking against tank: " + tank.getId());

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
