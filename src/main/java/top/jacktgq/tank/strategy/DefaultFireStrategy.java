package top.jacktgq.tank.strategy;

import net.Client;
import net.msg.BulletNewMsg;
import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.entity.Tank;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.mgr.ResourceMgr;
import top.jacktgq.tank.util.Audio;

import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/27--17:06
 * @Description 坦克默认的开火策略：只能朝一个方向打出子弹
 */
public class DefaultFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        // 根据坦克的方向确定打出子弹的位置
        int bulletX = 0, bulletY = 0;
        int tankWidth = t.curTankImage.getWidth();
        int tankHeight = t.curTankImage.getHeight();
        int bulletWidth, bulletHeight;
        switch (t.dir) {
            case LEFT: {
                bulletWidth = ResourceMgr.bulletL.getWidth();
                bulletX = t.x - bulletWidth - 5;
                bulletHeight = ResourceMgr.bulletL.getHeight();
                bulletY = t.y + (tankHeight - bulletHeight) / 2;
                break;
            }
            case UP: {
                bulletWidth = ResourceMgr.bulletU.getWidth();
                bulletX = t.x + (tankWidth - bulletWidth) / 2;
                bulletHeight = ResourceMgr.bulletU.getHeight();
                bulletY = t.y - bulletHeight - 5;
                break;
            }
            case RIGHT: {
                bulletHeight = ResourceMgr.bulletR.getHeight();
                bulletX = t.x + tankWidth + 5;
                bulletY = t.y + (tankHeight - bulletHeight) / 2;
                break;
            }
            case DOWN: {
                bulletWidth = ResourceMgr.bulletD.getWidth();
                bulletX = t.x + (tankWidth - bulletWidth) / 2;
                bulletY = t.y + tankHeight + 5;
                break;
            }
        }

        BaseBullet bullet = GameModel.INSTANCE.factory.createBullet(UUID.randomUUID(), t.getId(), bulletX, bulletY, t.dir, t.group == Group.SELF ? Group.SELF : Group.ENEMY);
        GameModel.INSTANCE.addBullet(bullet);
        // 将该坦克打出了新子弹的消息发送给服务器
        Client.INSTANCE.send(new BulletNewMsg(bullet));
        if (t.group == Group.SELF) {
            new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
        }
    }
}
