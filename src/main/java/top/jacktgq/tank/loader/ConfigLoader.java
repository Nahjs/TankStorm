package top.jacktgq.tank.loader;

import top.jacktgq.tank.collider.Collider;
import top.jacktgq.tank.factory.abstractfactory.GameFactory;
import top.jacktgq.tank.strategy.FireStrategy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * 加载和管理游戏的配置属性，如开火策略、工厂类和碰撞检测规则等
 */
public class ConfigLoader {
    private static Properties props = null;
    static {
        try {
            props = new Properties();
            props.load(ConfigLoader.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    // 获取游戏区域宽度
    public static int getGameWidth() {
        return Integer.parseInt(get("gameWidth"));
    }

    // 获取游戏区域高度
    public static int getGameHeight() {
        return Integer.parseInt(get("gameHeight"));
    }

    // 获取敌方坦克数量
    public static int getEnemy_tank_count() {
        return Integer.parseInt(get("enemy_tank_count"));
    }

    // 获取敌方坦克开火策略
    public static FireStrategy getEnemy_tank_fs() {
        try {
            Class<?> clazz = Class.forName("top.jacktgq.tank.strategy." + get("enemy_tank_fs"));
            return (FireStrategy) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("开火策略读取失败！");
        }
    }

    // 获取己方坦克开火策略
    public static FireStrategy getSelf_tank_fs() {
        try {
            Class<?> clazz = Class.forName("top.jacktgq.tank.strategy." + get("self_tank_fs"));
            return (FireStrategy) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("开火策略读取失败！");
        }
    }

    // 获取生产坦克、炮弹和爆炸效果的工厂类
    public static GameFactory getFactory() {
        try {
            Class<?> clazz = Class.forName("top.jacktgq.tank.factory." + get("factory_style"));
            return (GameFactory) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("开火策略读取失败！");
        }
    }

    // 获取碰撞检测规则
    public static LinkedList<Collider> getColliders() {
        try {
            String[] colliderNames = get("colliders").split(",");
            LinkedList<Collider> colliders = new LinkedList<>();
            for (String colliderName : colliderNames) {
                Class<?> clazz = Class.forName("top.jacktgq.tank.collider." + colliderName);
                Collider collider = (Collider) clazz.newInstance();
                colliders.add(collider);
            }
            return colliders;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("开火策略读取失败！");
        }
    }
}
