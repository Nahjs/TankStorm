package top.jacktgq.tank.view;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * @Author CandyWall
 * @Date 2021/1/23--19:19
 * @Description 游戏主界面
 */
public class TankPanel extends JPanel {
    GameModel gameModel;

    public TankPanel() {
        super(true);
        // 键盘监听事件的前提：获取焦点
        setFocusable(true);
        gameModel = new GameModel(this.getWidth(), this.getHeight());
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
                        gameModel.getSelfTank().fire();
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
                BaseTank selfTank = gameModel.getSelfTank();
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

    @Override
    protected void paintComponent(Graphics g) {
        // 重新绘制
        super.paintComponent(g);
        int width = this.getWidth();
        int height = this.getHeight();
        gameModel.paint(g, width, height);
    }
}
