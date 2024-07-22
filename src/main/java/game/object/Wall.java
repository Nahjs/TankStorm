package game.object;

import game.object.BaseObject.BaseWall;

import javax.swing.*;
import java.awt.*;

/**
 * 墙
 */
public class Wall extends BaseWall {
    Image image =  new ImageIcon("images/wall/steel.gif").getImage();
    public Wall(Image image, int x, int y, int width, int height) {
        super(x, y, width, height);
        this.image = image; // 确保传入的 image 参数不是 null
    }
  //  public Wall(int x, int y, int width, int height) {
     //   super(x, y, width, height);
 //   }

    @Override
    public void paint(Graphics g) {
       // g.setColor(new Color(209, 214, 215));

        if (image != null) {
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null); // 使用 null 作为 ImageObserver
            g.fillRect(x, y, width, height);
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
