package top.jacktgq.tank.mgr;

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
}
