package loader;

import game.util.ImageUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * 加载和管理游戏中的资源，如图片和音频文件
 */
public class ResourceLoader {
    public static BufferedImage selfTankL, selfTankU, selfTankR, selfTankD;//存储我方坦克在不同方向的图片
    public static BufferedImage enemyTankL, enemyTankU, enemyTankR, enemyTankD;//存储敌方坦克在不同方向的图片
    public static BufferedImage bulletL, bulletU, bulletR, bulletD;//存储子弹在不同方向的图片
    public static BufferedImage[] explodes = new BufferedImage[16];//存储坦克爆炸的动画图片数组
    public static BufferedImage gameOver;//存储游戏结束的图片
    static {
        // 加载四个方向的我方坦克图片
        selfTankU = readImage("GoodTank1.png");
        selfTankL = ImageUtil.rotateImage(selfTankU, -90);
        selfTankR = ImageUtil.rotateImage(selfTankU, 90);
        selfTankD = ImageUtil.rotateImage(selfTankU, 180);
        // 加载四个方向的敌方坦克图片
        enemyTankU = readImage("BadTank1.png");
        enemyTankL = ImageUtil.rotateImage(enemyTankU, -90);
        enemyTankR = ImageUtil.rotateImage(enemyTankU, 90);
        enemyTankD = ImageUtil.rotateImage(enemyTankU, 180);
        // 加载四个方向的子弹图片
        bulletU = readImage("bulletU.png");
        bulletL = ImageUtil.rotateImage(bulletU, -90);
        bulletR = ImageUtil.rotateImage(bulletU, 90);
        bulletD = ImageUtil.rotateImage(bulletU, 180);
        // 加载坦克爆炸的图片
        for (int i = 0; i < explodes.length; i++) {
            explodes[i] = readImage("e" + (i + 1) + ".gif");
        }
        gameOver = readImage("GameOver.png");
    }

    private static BufferedImage readImage(String filename) {
        try {
            return ImageIO.read(ResourceLoader.class.getResource("/images/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取图片资源失败！");
        }
    }
}
