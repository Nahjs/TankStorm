package top.jacktgq.tank;

import top.jacktgq.tank.collider.ColliderChain;
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
    public int gameWidth, gameHeight;   // 游戏区域宽高
    BaseTank selfTank;
    /*public ArrayList<BaseTank> tanks = new ArrayList<>();
    public ArrayList<BaseBullet> bullets = new ArrayList<>();
    public ArrayList<BaseExplode> explodes = new ArrayList<>();*/
    public List<GameObject> gameObjects = new ArrayList<>();
    public ColliderChain colliderChain;
    public GameFactory factory;

    public GameModel(int gameWidth, int gameHeight) {
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        factory = PropertyMgr.getFactory();
        colliderChain = new ColliderChain(this);
        // 初始化我方坦克
        selfTank = factory.createSelfTank(350, 500, Dir.DOWN, 5, this);
        gameObjects.add(selfTank);
        // 随机产生enemy_tank_count辆敌方坦克
        initEnemyTanks();
    }

    private void initEnemyTanks() {
        int count = PropertyMgr.getEnemy_tank_count();
        // 将游戏区域网格化
        // 敌方坦克的高度和宽度

        // int rows = this.getWidth();
        for (int i = 0; i < count; i++) {
            gameObjects.add(factory.createEnemyTank(100 + 100 * i, 100, Dir.DOWN, 5, this));
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
        /*g.drawString("子弹数量：" + bullets.size(), 20, 30);
        g.drawString("敌方坦克数量：" + (tanks.size() - 1), 20, 60);
        // 绘制所有坦克
        Iterator<BaseTank> tankIterator = tanks.iterator();
        while (tankIterator.hasNext()) {
            BaseTank enemyTank = tankIterator.next();
            enemyTank.paint(g);
        }
        // 绘制所有子弹
        Iterator<BaseBullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            BaseBullet bullet = bulletIterator.next();
            bullet.paint(g);
            // 判断子弹是否还活着
            if (!bullet.islive()) {
                bulletIterator.remove();
            }
        }

        // 进行子弹和坦克的碰撞检测
        bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            BaseBullet bullet = bulletIterator.next();
            tankIterator = tanks.iterator();
            while (tankIterator.hasNext()) {
                // 如果我方子弹和敌方坦克有交集
                BaseTank tank = tankIterator.next();
                if (bullet.collideWith(tank)) {
                    // 从子弹集合中移除子弹
                    bulletIterator.remove();
                    // 从敌方坦克集合中移除坦克
                    tankIterator.remove();
                    Rectangle tankRect = tank.getTankRect();
                    // 添加一个爆炸动画
                    explodes.add(factory.createExplode(tankRect));
                    break;
                }
            }
        }
        Iterator<BaseExplode> explodeIterator = explodes.iterator();
        // 绘制坦克爆炸动画
        while (explodeIterator.hasNext()) {
            BaseExplode explode = explodeIterator.next();
            explode.paint(g);
            // 如果爆炸动画已经画到了最后一张，就从集合中移除该爆炸动画
            if (explode.getStep() == ResourceMgr.explodes.length) {
                explodeIterator.remove();
            }
        }*/
    }

    public BaseTank getSelfTank() {
        return selfTank;
    }
}
