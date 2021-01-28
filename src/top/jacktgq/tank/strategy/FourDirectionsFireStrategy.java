package top.jacktgq.tank.strategy;

import top.jacktgq.tank.entity.Bullet;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.entity.Tank;
import top.jacktgq.tank.mgr.ResourceMgr;
import top.jacktgq.tank.util.Audio;

/**
 * @Author CandyWall
 * @Date 2021/1/27--17:46
 * @Description 坦克可以朝4个方向同时开火
 */
public class FourDirectionsFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        // 根据坦克的方向确定打出子弹的位置
        int bulletX = 0, bulletY = 0;
        int tankWidth = t.curTankImage.getWidth();
        int tankHeight = t.curTankImage.getHeight();
        int bulletWidth, bulletHeight;
        // 左
        bulletWidth = ResourceMgr.bulletL.getWidth();
        bulletX = t.x - bulletWidth - 5;
        bulletHeight = ResourceMgr.bulletL.getHeight();
        bulletY = t.y + (tankHeight - bulletHeight) / 2;
        t.tankPanel.bullets.add(new Bullet(bulletX, bulletY, Dir.LEFT, t.tankPanel, t.group == Group.SELF ? Group.SELF : Group.ENEMY));

        // 上
        bulletWidth = ResourceMgr.bulletU.getWidth();
        bulletX = t.x + (tankWidth - bulletWidth) / 2;
        bulletHeight = ResourceMgr.bulletU.getHeight();
        bulletY = t.y - bulletHeight - 5;
        t.tankPanel.bullets.add(new Bullet(bulletX, bulletY, Dir.UP, t.tankPanel, t.group == Group.SELF ? Group.SELF : Group.ENEMY));

        // 右
        bulletHeight = ResourceMgr.bulletR.getHeight();
        bulletX = t.x + tankWidth + 5;
        bulletY = t.y + (tankHeight - bulletHeight) / 2;
        t.tankPanel.bullets.add(new Bullet(bulletX, bulletY, Dir.RIGHT, t.tankPanel, t.group == Group.SELF ? Group.SELF : Group.ENEMY));

        // 下
        bulletWidth = ResourceMgr.bulletD.getWidth();
        bulletX = t.x + (tankWidth - bulletWidth) / 2;
        bulletY = t.y + tankHeight + 5;
        t.tankPanel.bullets.add(new Bullet(bulletX, bulletY, Dir.DOWN, t.tankPanel, t.group == Group.SELF ? Group.SELF : Group.ENEMY));

        if (t.group == Group.SELF) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
