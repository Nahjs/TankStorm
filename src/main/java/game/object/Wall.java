package game.object;

import game.object.BaseObject.BaseWall;
import loader.ResourceLoader;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * 墙
 */
public class Wall extends BaseWall {
    public static BufferedImage curwall ;
    public Wall( int x, int y, int width, int height) {
        super(x, y, width, height);
        this.curwall = initCurWallImage();; // 确保传入的 image 参数不是 null
    }

    private BufferedImage initCurWallImage() {
        return curwall=ResourceLoader.wall;

    }
    //  public Wall(int x, int y, int width, int height) {
     //   super(x, y, width, height);
 //   }

    @Override
    public void paint(Graphics g) {
       // g.setColor(new Color(209, 214, 215));
        //g.fillRect(x, y, width, height);

        if (g != null) {
            g.drawImage(curwall, x, y, getWidth(), getHeight(), null); // 使用 null 作为 ImageObserver
        } else {
            System.out.println("Image is not initialized.");
        }
        }


    private int getHeight() {
        return this.height;
    }

    private int getWidth() {
        return this.width;
    }


}
