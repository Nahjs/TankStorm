package game.strategy;

import design.GameDesign;
import game.object.BaseObject.BaseBullet;
import game.object.Dir;
import game.object.Group;
import game.object.Tank;
import loader.ResourceLoader;
import loader.AudioLoader;
import net.Client;
import net.msg.BulletMsg;

import java.util.UUID;

/**
 * 新的坦克开火攻略：可以朝4个方向同时开火
 */
public class NewFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank t) {
        // 根据坦克的方向确定打出子弹的位置
        int bulletX = 0, bulletY = 0;
        int tankWidth = t.curTankImage.getWidth();
        int tankHeight = t.curTankImage.getHeight();
        int bulletWidth, bulletHeight;
        // 左
        bulletWidth = ResourceLoader.bulletL.getWidth();
        bulletX = t.x - bulletWidth - 5;
        bulletHeight = ResourceLoader.bulletL.getHeight();
        bulletY = t.y + (tankHeight - bulletHeight) / 2;
        BaseBullet bullet = GameDesign.INSTANCE.factory.createBullet(UUID.randomUUID(), t.getId(), bulletX, bulletY, Dir.LEFT, t.group == Group.SELF ? Group.SELF : Group.ENEMY);
        GameDesign.INSTANCE.addBullet(bullet);
        // System.out.println("发送子弹信息Left");
        // 将该坦克打出了新子弹的消息发送给服务器
        //System.out.println(bullet);
        //System.out.println(new BulletNewMsg(bullet));
        Client.INSTANCE.send(new BulletMsg(bullet));

        // 上
        bulletWidth = ResourceLoader.bulletU.getWidth();
        bulletX = t.x + (tankWidth - bulletWidth) / 2;
        bulletHeight = ResourceLoader.bulletU.getHeight();
        bulletY = t.y - bulletHeight - 5;
        bullet = GameDesign.INSTANCE.factory.createBullet(UUID.randomUUID(), t.getId(), bulletX, bulletY, Dir.UP, t.group == Group.SELF ? Group.SELF : Group.ENEMY);
        GameDesign.INSTANCE.addBullet(bullet);
        // 将该坦克打出了新子弹的消息发送给服务器
        Client.INSTANCE.send(new BulletMsg(bullet));

        // 右
        bulletHeight = ResourceLoader.bulletR.getHeight();
        bulletX = t.x + tankWidth + 5;
        bulletY = t.y + (tankHeight - bulletHeight) / 2;
        bullet = GameDesign.INSTANCE.factory.createBullet(UUID.randomUUID(), t.getId(), bulletX, bulletY, Dir.RIGHT, t.group == Group.SELF ? Group.SELF : Group.ENEMY);
        GameDesign.INSTANCE.addBullet(bullet);
        // 将该坦克打出了新子弹的消息发送给服务器
        Client.INSTANCE.send(new BulletMsg(bullet));

        // 下
        bulletWidth = ResourceLoader.bulletD.getWidth();
        bulletX = t.x + (tankWidth - bulletWidth) / 2;
        bulletY = t.y + tankHeight + 5;
        bullet = GameDesign.INSTANCE.factory.createBullet(UUID.randomUUID(), t.getId(), bulletX, bulletY, Dir.DOWN, t.group == Group.SELF ? Group.SELF : Group.ENEMY);
        GameDesign.INSTANCE.addBullet(bullet);
        // 将该坦克打出了新子弹的消息发送给服务器
        Client.INSTANCE.send(new BulletMsg(bullet));

        if (t.group == Group.SELF) {
            new Thread(() -> new AudioLoader("audio/tank_fire.wav").play()).start();
        }
    }
}
