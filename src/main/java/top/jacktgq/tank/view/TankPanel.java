package top.jacktgq.tank.view;

import net.Client;
import net.msg.DirChangeMsg;
import net.msg.MoveMsg;
import net.msg.StopMsg;
import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;

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
                        GameModel.INSTANCE.getSelfTank().fire();
                        return;
                    }
                }
                setMainTankDir();
                // 坦克在每次通过键盘事件移动后不需要重绘
                // repaint();
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
                    case KeyEvent.VK_SPACE: {
                        return;
                    }
                }
                setMainTankDir();
            }

            /**
             * 设置坦克方向
             */
            public void setMainTankDir() {
                GameObject selfTank = GameModel.INSTANCE.getSelfTank();
                // 如果四个方向键都没有被按下
                if (!isL && !isU && !isR && !isD) {
                    //System.out.println("四个方向键都没有被按下！");
                    selfTank.setMoving(false);
                    // 坦克停下，向服务器发送停下的消息
                    Client.INSTANCE.send(new StopMsg(selfTank));
                } else {
                    // 存储改变前的方向
                    Dir oldDir = selfTank.getDir();
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
                    if (!selfTank.isMoving()) {
                        // 坦克开始移动，向服务器发送坦克移动的消息
                        Client.INSTANCE.send(new MoveMsg(selfTank));
                    }
                    selfTank.setMoving(true);
                    // 如果方向发生改变，就发送消息给服务器
                    if (oldDir != selfTank.getDir()) {
                        Client.INSTANCE.send(new DirChangeMsg(selfTank));
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
        GameModel.INSTANCE.paint(g, width, height);
    }
}
