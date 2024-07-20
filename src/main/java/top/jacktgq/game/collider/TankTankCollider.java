package top.jacktgq.game.collider;

import top.jacktgq.game.entity.GameObject;
import top.jacktgq.game.entity.GameObjectType;

/**
 * 坦克和坦克的碰撞器
 */
public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1.getGameObjectType() == GameObjectType.TANK && o2.getGameObjectType() == GameObjectType.TANK) {
            // 如果坦克和坦克碰撞到一起了，不能继续
            if (o1.getRect().intersects(o2.getRect())) {
                o1.back();
                o2.back();
                return true;
            }
        }
        return false;
    }
}
