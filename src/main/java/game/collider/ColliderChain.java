package game.collider;

import game.object.GameObject;
import loader.ConfigLoader;

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
