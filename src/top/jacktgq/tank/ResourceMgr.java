package top.jacktgq.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @Author CandyWall
 * @Date 2021/1/24--8:43
 * @Description 读取游戏图片和音频资源
 */
public class ResourceMgr {
    public static BufferedImage tankL, tankU, tankR, tankD;
    public static BufferedImage bulletL, bulletU, bulletR, bulletD;
    public static BufferedImage[] explodes = new BufferedImage[16];
    static {
        // 加载四个方向的我方坦克图片
        tankL = readImage("tankL.gif");
        tankU = readImage("tankU.gif");
        tankR = readImage("tankR.gif");
        tankD = readImage("tankD.gif");
        // 加载四个方向的子弹图片
        bulletL = readImage("bulletL.gif");
        bulletU = readImage("bulletU.gif");
        bulletR = readImage("bulletR.gif");
        bulletD = readImage("bulletD.gif");
        // 加载坦克爆炸的图片
        for (int i = 0; i < explodes.length; i++) {
            explodes[i] = readImage("e" + (i + 1) + ".gif");
        }
    }

    private static BufferedImage readImage(String filename) {
        try {
            return ImageIO.read(ResourceMgr.class.getResource("/images/" + filename));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("读取图片资源失败！");
        }
    }
}
