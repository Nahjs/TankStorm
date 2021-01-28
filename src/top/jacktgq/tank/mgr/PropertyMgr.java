package top.jacktgq.tank.mgr;

import top.jacktgq.tank.factory.abstractfactory.GameFactory;
import top.jacktgq.tank.strategy.FireStrategy;

import java.io.IOException;
import java.util.Properties;

/**
 * @Author CandyWall
 * @Date 2021/1/24--17:04
 * @Description 读取配置文件
 */
public class PropertyMgr {
    private static Properties props = null;
    static {
        try {
            props = new Properties();
            props.load(PropertyMgr.class.getResourceAsStream("/config.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return props.getProperty(key);
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
}
