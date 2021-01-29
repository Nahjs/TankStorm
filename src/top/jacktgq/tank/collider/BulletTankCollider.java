package top.jacktgq.tank.collider;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.Group;
import top.jacktgq.tank.entity.abstractEntity.BaseBullet;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;

/**
 * @Author CandyWall
 * @Date 2021/1/28--20:54
 * @Description 子弹和坦克的碰撞器
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof BaseBullet && o2 instanceof BaseTank) {
            BaseBullet bullet = (BaseBullet) o1;
            BaseTank tank = (BaseTank) o2;

            // 如果是己方坦克，无敌
            if (tank.getGroup() == Group.SELF) {
                return false;
            }
            // 一个阵营的不做碰撞检测
            if (bullet.getGroup() == tank.getGroup()) {
                return false;
            }
            // 如果子弹和坦克碰撞到一起了
            if (bullet.getBulletRect().intersects(tank.getTankRect())) {
                // 移除子弹
                GameModel.getINSTANCE().gameObjects.remove(bullet);
                // 移除坦克
                GameModel.getINSTANCE().gameObjects.remove(tank);
                // 添加一个爆炸动画
                GameModel.getINSTANCE().gameObjects.add(GameModel.getINSTANCE().factory.createExplode(tank.getTankRect()));
                return true;
            }
        } else if (o1 instanceof BaseTank && o2 instanceof BaseBullet) {
            return collide(o2, o1);
        }
        return false;
    }
}
