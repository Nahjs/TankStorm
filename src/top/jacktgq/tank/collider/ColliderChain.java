package top.jacktgq.tank.collider;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.mgr.PropertyMgr;

import java.util.LinkedList;

/**
 * @Author CandyWall
 * @Date 2021/1/28--21:55
 * @Description
 */
public class ColliderChain implements Collider {
    private LinkedList<Collider> colliders;

    public ColliderChain(GameModel gameModel) {
        colliders = PropertyMgr.getColliders(gameModel);
    }

    public void add(Collider collider) {
        colliders.add(collider);
    }

    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        for (Collider collider : colliders) {
            if(collider.collide(o1, o2)) {
                return true;
            }
        }
        return false;
    }
}
