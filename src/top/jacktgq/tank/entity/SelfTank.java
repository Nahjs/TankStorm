package top.jacktgq.tank.entity;

import top.jacktgq.tank.view.TankPanel;

/**
 * @Author CandyWall
 * @Date 2021/1/23--21:16
 * @Description 我方坦克
 */
public class SelfTank extends Tank {

    public SelfTank(int x, int y, Dir dir, int speed, TankPanel tankPanel) {
        super(x, y, dir, speed, tankPanel, Group.SELF);
        moving = false;
    }
}
