package top.jacktgq.tank.collider;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.GameObjectType;
import top.jacktgq.tank.entity.Group;

/**
 * @Author CandyWall
 * @Date 2021/1/28--20:54
 * @Description 子弹和坦克的碰撞器
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
                GameModel.INSTANCE.gameObjects.remove(o1);
                // 移除坦克
                GameModel.INSTANCE.gameObjects.remove(o2);
                // 添加一个爆炸动画
                GameModel.INSTANCE.gameObjects.add(GameModel.INSTANCE.factory.createExplode(o2.getRect()));
                return true;
            }
        } else if (o1.getGameObjectType() == GameObjectType.TANK && o2.getGameObjectType() == GameObjectType.BULLET) {
            return collide(o2, o1);
        }
        return false;
    }
}
