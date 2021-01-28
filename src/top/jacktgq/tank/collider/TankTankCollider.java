package top.jacktgq.tank.collider;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.abstractEntity.BaseTank;

/**
 * @Author CandyWall
 * @Date 2021/1/28--20:54
 * @Description 坦克和坦克的碰撞器
 */
public class TankTankCollider implements Collider {
    private GameModel gameModel;
    public TankTankCollider(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof BaseTank && o2 instanceof BaseTank) {
            BaseTank tank1 = (BaseTank) o1;
            BaseTank tank2 = (BaseTank) o2;
            // 如果坦克和坦克碰撞到一起了，不能继续
            if (tank1.getTankRect().intersects(tank2.getTankRect())) {
                tank1.setMoving(false);
                tank2.setMoving(false);
                return true;
            }
        }
        return false;
    }
}
