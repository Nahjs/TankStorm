package top.jacktgq.game.collider;

import top.jacktgq.game.entity.GameObject;

/**
 *  碰撞器：完成两个游戏物体间的碰撞检测
 */
public interface Collider {
    boolean collide(GameObject o1, GameObject o2);
}
