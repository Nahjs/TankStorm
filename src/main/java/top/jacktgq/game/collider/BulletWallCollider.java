package top.jacktgq.game.collider;

import top.jacktgq.game.GameModel;
import top.jacktgq.game.entity.GameObject;
import top.jacktgq.game.entity.GameObjectType;

/**
 * 子弹和墙的碰撞器
 */
public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1.getGameObjectType() == GameObjectType.BULLET && o2.getGameObjectType() == GameObjectType.WALL) {

            // 如果子弹和墙碰撞到一起了
            if (o1.getRect().intersects(o2.getRect())) {
                // 移除子弹
                GameModel.INSTANCE.gameObjects.remove(o1);
                return true;
            }
        } else if (o1.getGameObjectType() == GameObjectType.WALL && o2.getGameObjectType() == GameObjectType.BULLET) {
            return collide(o2, o1);
        }
        return false;
    }
}
