package game.collider;

import design.GameDesign;
import game.object.GameObject;
import game.object.GameObjectType;
import game.object.Group;

/**
 * 子弹和坦克的碰撞器
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1.getGameObjectType() == GameObjectType.BULLET && o2.getGameObjectType() == GameObjectType.TANK) {
            // 如果是己方坦克，无敌
            if (o2.getGroup() == Group.SELF) {
                return false;
            }
            // 一个阵营的不做碰撞检测
            if (o1.getGroup() == o2.getGroup()) {
                return false;
            }
            // 如果子弹和坦克碰撞到一起了
            if (o1.getRect().intersects(o2.getRect())) {
                // 移除子弹
                GameDesign.INSTANCE.gameObjects.remove(o1);
                // 移除坦克
                GameDesign.INSTANCE.gameObjects.remove(o2);
                // 添加一个爆炸动画
                GameDesign.INSTANCE.gameObjects.add(GameDesign.INSTANCE.factory.createExplode(o2.getRect()));
                return true;
            }
        } else if (o1.getGameObjectType() == GameObjectType.TANK && o2.getGameObjectType() == GameObjectType.BULLET) {
            return collide(o2, o1);
        }
        return false;
    }
}
