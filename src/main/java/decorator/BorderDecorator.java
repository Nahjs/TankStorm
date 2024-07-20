package decorator;

import game.entity.GameObject;

import java.awt.*;

/**
 * 在游戏物体外围加上矩形边框的装饰器
 */
public class BorderDecorator extends ObjectDecorator {
    public BorderDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.RED);
        Graphics2D g2d = (Graphics2D) g;
        g2d.draw(super.getRect());
    }
}
