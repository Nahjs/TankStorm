package top.jacktgq.tank.collider;

import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;
import top.jacktgq.tank.entity.abstractEntity.BaseWall;

/**
 * @Author CandyWall
 * @Date 2021/1/28--20:54
 * @Description 坦克和墙的碰撞器
 */
public class TankWallCollider implements Collider {

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof BaseTank && o2 instanceof BaseWall) {
            BaseTank tank = (BaseTank) o1;
            BaseWall wall = (BaseWall) o2;

            // 如果坦克和墙碰撞到一起了
            if (tank.getTankRect().intersects(wall.getWallRect())) {
                tank.back();
                return true;
            }
        } else if (o1 instanceof BaseWall && o2 instanceof BaseTank) {
            return collide(o2, o1);
        }
        return false;
    }
}
