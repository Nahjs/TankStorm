package top.jacktgq.tank.strategy;

import top.jacktgq.tank.entity.Tank;

/**
 * @Author CandyWall
 * @Date 2021/1/27--17:03
 * @Description 坦克开火策略
 */
public interface FireStrategy {
    void fire(Tank t);
}
