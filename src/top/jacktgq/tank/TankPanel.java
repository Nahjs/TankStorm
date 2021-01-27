package top.jacktgq.tank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @Author CandyWall
 * @Date 2021/1/23--19:19
 * @Description 游戏主界面
 */
public class TankPanel extends JPanel {
    SelfTank selfTank;
    ArrayList<Tank> tanks = new ArrayList<>();
    ArrayList<Bullet> bullets = new ArrayList<>();
    ArrayList<Explode> explodes = new ArrayList<>();

    public TankPanel() {
        super(true);
        // 键盘监听事件的前提：获取焦点
        setFocusable(true);
        this.addKeyListener(new KeyAdapter() {
            private boolean isU;
            private boolean isD;
            private boolean isL;
            private boolean isR;

            @Override
            public void keyTyped(KeyEvent e) {
                super.keyTyped(e);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                //System.out.println("按下了键盘");
                switch (keyCode) {
                    case KeyEvent.VK_UP: {
                        isU = true;
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        isD = true;
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        isL = true;
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        isR = true;
                        break;
                    }
                    case KeyEvent.VK_SPACE: {
                        selfTank.fire();
                        break;
                    }
                }
                setMainTankDir();
                repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                //System.out.println("抬起了键盘");
                int keyCode = e.getKeyCode();
                switch (keyCode) {
                    case KeyEvent.VK_UP: {
                        isU = false;
                        break;
                    }
                    case KeyEvent.VK_DOWN: {
                        isD = false;
                        break;
                    }
                    case KeyEvent.VK_LEFT: {
                        isL = false;
                        break;
                    }
                    case KeyEvent.VK_RIGHT: {
                        isR = false;
                        break;
                    }
                }
                setMainTankDir();
            }

            /**
             * 设置坦克方向
             */
            public void setMainTankDir() {
                // 如果四个方向键都没有被按下
                if (!isL && !isU && !isR && !isD) {
                    //System.out.println("四个方向键都没有被按下！");
                    selfTank.setMoving(false);
                } else {
                    selfTank.setMoving(true);
                    if (isL) {
                        //System.out.println("按下方向左");
                        selfTank.setDir(Dir.LEFT);
                    }
                    if (isU) {
                        //System.out.println("按下方向上");
                        selfTank.setDir(Dir.UP);
                    }
                    if (isR) {
                        //System.out.println("按下方向右");
                        selfTank.setDir(Dir.RIGHT);
                    }
                    if (isD) {
                        //System.out.println("按下方向下");
                        selfTank.setDir(Dir.DOWN);
                    }
                }
            }
        });
        // 初始化我方坦克
        selfTank = new SelfTank(350, 500, Dir.DOWN, 5, this);
        tanks.add(selfTank);
        // 随机产生enemy_tank_count辆敌方坦克
        initEnemyTanks();
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                repaint();
            }
        }).start();
    }

    private void initEnemyTanks() {
        int count = PropertyMgr.getEnemy_tank_count();
        // 将游戏区域网格化
        // 敌方坦克的高度和宽度

        // int rows = this.getWidth();
        for (int i = 0; i < count; i++) {
            tanks.add(new EnemyTank(100 + 100 * i, 100, Dir.DOWN, 5, this));
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // 重新绘制
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();
        g.setColor(Color.BLACK);

        g.fillRect(0, 0, width, height);
        g.setColor(Color.WHITE);
        g.drawString("子弹数量：" + bullets.size(), 20, 30);
        g.drawString("敌方坦克数量：" + (tanks.size() - 1), 20, 60);
        // 绘制所有坦克
        Iterator<Tank> tankIterator = tanks.iterator();
        while (tankIterator.hasNext()) {
            Tank enemyTank = tankIterator.next();
            enemyTank.paint(g);
        }
        // 绘制所有子弹
        Iterator<Bullet> bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            bullet.paint(g);
            // 判断子弹是否还活着
            if (bullet.islive()) {
                bulletIterator.remove();
            }
        }

        // 进行子弹和坦克的碰撞检测
        bulletIterator = bullets.iterator();
        while (bulletIterator.hasNext()) {
            Bullet bullet = bulletIterator.next();
            tankIterator = tanks.iterator();
            while (tankIterator.hasNext()) {
                // 如果我方子弹和敌方坦克有交集
                Tank tank = tankIterator.next();
                if (bullet.collideWith(tank)) {
                    // 从子弹集合中移除子弹
                    bulletIterator.remove();
                    // 从敌方坦克集合中移除坦克
                    tankIterator.remove();
                    Rectangle tankRect = tank.getTankRect();
                    // 添加一个爆炸动画
                    explodes.add(new Explode(tankRect,2));
                    break;
                }
            }
        }
        Iterator<Explode> explodeIterator = explodes.iterator();
        // 绘制坦克爆炸动画
        while (explodeIterator.hasNext()) {
            Explode explode = explodeIterator.next();
            explode.paint(g);
            // 如果爆炸动画已经画到了最后一张，就从集合中移除该爆炸动画
            if (explode.getStep() == ResourceMgr.explodes.length) {
                explodeIterator.remove();
            }
        }

    }
}
