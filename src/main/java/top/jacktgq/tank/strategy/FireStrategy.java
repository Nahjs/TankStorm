package top.jacktgq.tank.strategy;

import top.jacktgq.tank.entity.Tank;

/**
 * 开火策略
 */
public interface FireStrategy {
    void fire(Tank t);
}
