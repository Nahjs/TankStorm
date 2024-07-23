package loader;

import game.collider.Collider;
import game.factory.abstractfactory.GameFactory;
import game.strategy.FireStrategy;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Properties;

/**
 * 加载和管理游戏的配置属性，如开火策略、工厂类和碰撞检测规则等
 */
public class ConfigLoader {
    // 定义五种难度模式"简单：真人对战", "：我要打十个", "困难：易如反掌，我要三十个", "没挑战，五十个", "疯狂：一点活不了"
    public static final String EASY = "简单：真人对战";
    public static final String NORMAL = "普通：我要打十个";
    public static final String HARD = "进阶：易如反掌，三十个";
    public static final String EXPERT = "困难：拿下！五十个";
    public static final String INSANE = "疯狂：一点活不了.一百个";

//    public static String getDifficultyDisplayName(String difficultyKey) {
//        return props.getProperty("difficulty." + difficultyKey);
//    }
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
    public static int getEnemyTankCount(String difficulty) {
        String countStr = props.getProperty(difficulty);
        if (countStr == null || countStr.trim().isEmpty()) {
            throw new IllegalArgumentException("难度级别 '" + difficulty + "' 没有找到对应的敌人坦克数量。");
        }
        return Integer.parseInt(countStr);
    }

    // 获取游戏区域宽度
    public static int getGameWidth() {
        return Integer.parseInt(get("gameWidth"));
    }

    // 获取游戏区域高度
    public static int getGameHeight() {
        return Integer.parseInt(get("gameHeight"));
    }

//    // 获取敌方坦克数量
//    public static void setEnemyTankCount(String difficulty, int count) {
//        props.setProperty(difficulty, String.valueOf(count));
//        try {
//            props.store(new FileOutputStream("config.properties"), "Game Configuration");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    // 获取敌方坦克开火策略
    public static FireStrategy getEnemy_tank_fs() {
        try {
            Class<?> clazz = Class.forName("game.strategy." + get("enemyTankFs"));
            return (FireStrategy) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("开火策略读取失败！");
        }
    }

    // 获取己方坦克开火策略
    public static FireStrategy getSelf_tank_fs() {
        try {
            Class<?> clazz = Class.forName("game.strategy." + get("myTankFs"));
            return (FireStrategy) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("开火策略读取失败！");
        }
    }

    // 获取生产坦克、炮弹和爆炸效果的工厂类
    public static GameFactory getFactory() {
        try {
            Class<?> clazz = Class.forName("game.factory." + get("factory"));
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
                Class<?> clazz = Class.forName("game.collider." + colliderName);
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
