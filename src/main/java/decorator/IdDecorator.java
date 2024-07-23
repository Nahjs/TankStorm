package decorator;

import game.object.GameObject;

import java.awt.*;

/**
 *在坦克上方绘制id的装饰器
 */
public class IdDecorator extends ObjectDecorator {
    public IdDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.GREEN);
        Rectangle rect = super.getRect();
        g.drawString(gameObject.getId().toString().substring(0, 16), rect.x - 15, rect.y - 20);
    }
}
