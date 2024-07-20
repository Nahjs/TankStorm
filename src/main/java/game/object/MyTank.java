package game.object;

import java.awt.*;
import java.util.UUID;

/**
 *我方坦克
 */
public class MyTank extends Tank {

    public MyTank(UUID id, int x, int y, Dir dir, int speed) {
        super(id, x, y, dir, speed, Group.SELF);
        moving = false;
    }
    public MyTank( String id, int x, int y, Dir dir, int speed) {
        super(UUID.fromString(id), x, y, dir, speed, Group.SELF);
        moving = false;
    }

    @Override
    public void paint(Graphics g) {
        /*g.setColor(Color.MAGENTA);
        g.fillRect(x, y, WIDTH, HEIGHT);*/
        g.drawImage(curTankImage, x, y, null);
        move();
    }
}
