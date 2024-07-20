package game.collider;

import game.entity.GameObject;
import game.entity.GameObjectType;

/**
 * 坦克和墙的碰撞器
 */
public class TankWallCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1.getGameObjectType() == GameObjectType.TANK && o2.getGameObjectType() == GameObjectType.WALL) {
            // 如果坦克和墙碰撞到一起了
            if (o1.getRect().intersects(o2.getRect())) {
                o1.back();
                return true;
            }
        } else if (o1.getGameObjectType() == GameObjectType.WALL && o2.getGameObjectType() == GameObjectType.TANK) {
            return collide(o2, o1);
        }
        return false;
    }
}
