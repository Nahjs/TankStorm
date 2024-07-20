package game.strategy;

import game.GameModel;
import game.entity.BaseObject.BaseBullet;
import game.entity.Dir;
import game.entity.Group;
import game.entity.Tank;
import game.loader.ResourceLoader;
import game.util.AudioUtil;
import net.Client;
import net.msg.BulletMsg;

import java.util.UUID;

/**
 * 坦克的开火策略：朝一个方向打出子弹
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
                bulletWidth = ResourceLoader.bulletL.getWidth();
                bulletX = t.x - bulletWidth - 5;
                bulletHeight = ResourceLoader.bulletL.getHeight();
                bulletY = t.y + (tankHeight - bulletHeight) / 2;
                break;
            }
            case UP: {
                bulletWidth = ResourceLoader.bulletU.getWidth();
                bulletX = t.x + (tankWidth - bulletWidth) / 2;
                bulletHeight = ResourceLoader.bulletU.getHeight();
                bulletY = t.y - bulletHeight - 5;
                break;
            }
            case RIGHT: {
                bulletHeight = ResourceLoader.bulletR.getHeight();
                bulletX = t.x + tankWidth + 5;
                bulletY = t.y + (tankHeight - bulletHeight) / 2;
                break;
            }
            case DOWN: {
                bulletWidth = ResourceLoader.bulletD.getWidth();
                bulletX = t.x + (tankWidth - bulletWidth) / 2;
                bulletY = t.y + tankHeight + 5;
                break;
            }
        }

        //创建一个 BaseBullet 对象，表示新发射的子弹，并将其添加到游戏模型中。
        BaseBullet bullet = GameModel.INSTANCE.factory.createBullet(UUID.randomUUID(), t.getId(), bulletX, bulletY, t.dir, t.group == Group.SELF ? Group.SELF : Group.ENEMY);
        GameModel.INSTANCE.addBullet(bullet);


        // 将该坦克打出了新子弹的消息发送给服务器
        Client.INSTANCE.send(new BulletMsg(bullet));

        //如果坦克属于玩家（Group.SELF），则播放开火音效
        if (t.group == Group.SELF) {
            new Thread(() -> new AudioUtil("audio/tank_fire.wav").play()).start();
        }
    }
}