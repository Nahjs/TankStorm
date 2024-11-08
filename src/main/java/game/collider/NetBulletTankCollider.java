package game.collider;

import design.GameDesign;
import game.object.GameObject;
import game.object.GameObjectType;
import net.Client;
import net.msg.DieMsg;

/**
 * 网络版子弹和坦克的碰撞器
 */
public class NetBulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1.getGameObjectType() == GameObjectType.BULLET && o2.getGameObjectType() == GameObjectType.TANK) {
            // 自己打出的子弹不和自己做碰撞检测
            if (o1.getTankId() == o2.getId()) {
                return false;
            }

            // 如果自己打出的子弹和击中了别的坦克
            if (o1.getRect().intersects(o2.getRect())) {
                // 移除子弹
                GameDesign.INSTANCE.gameObjects.remove(o1);
                // 移除坦克
                GameDesign.INSTANCE.gameObjects.remove(o2);
                // 添加一个爆炸动画
                GameDesign.INSTANCE.gameObjects.add(GameDesign.INSTANCE.factory.createExplode(o2.getRect()));
                // 有坦克被别人的子弹击中，就将消息发送到服务器
                Client.INSTANCE.send(new DieMsg(o2.getId(), o1.getId()));
                return true;
            }
        } else if (o1.getGameObjectType() == GameObjectType.TANK && o2.getGameObjectType() == GameObjectType.BULLET) {
            return collide(o2, o1);
        }
        return false;
    }
}
