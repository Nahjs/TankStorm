package top.jacktgq.tank.collider;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.entity.abstractEntity.BaseWall;

/**
 * @Author CandyWall
 * @Date 2021/1/28--20:54
 * @Description 子弹和墙的碰撞器
 */
public class BulletWallCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof BaseBullet && o2 instanceof BaseWall) {
            BaseBullet bullet = (BaseBullet) o1;
            BaseWall wall = (BaseWall) o2;

            // 如果子弹和墙碰撞到一起了
            if (bullet.getBulletRect().intersects(wall.getWallRect())) {
                // 移除子弹
                GameModel.getINSTANCE().gameObjects.remove(bullet);
                return true;
            }
        } else if (o1 instanceof BaseWall && o2 instanceof BaseBullet) {
            return collide(o2, o1);
        }
        return false;
    }
}
