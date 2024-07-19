package top.jacktgq.tank.collider;

import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.loader.ConfigLoader;

import java.util.LinkedList;

/**
 * 碰撞器链
 */
public class ColliderChain implements Collider {
    private LinkedList<Collider> colliders;

    public ColliderChain() {
        colliders = ConfigLoader.getColliders();
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
