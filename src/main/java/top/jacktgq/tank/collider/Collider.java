package top.jacktgq.tank.collider;

import top.jacktgq.tank.entity.GameObject;

/**
 * @Author CandyWall
 * @Date 2021/1/28--20:51
 * @Description 碰撞器：完成两个游戏物体间的碰撞检测
 */
public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
