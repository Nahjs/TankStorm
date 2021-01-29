package top.jacktgq.tank.decorator;

import top.jacktgq.tank.entity.GameObject;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/1/29--11:54
 * @Description 在游戏物体外围加上矩形边框的装饰器
 */
public class RectDecorator extends GODecorator {
    public RectDecorator(GameObject gameObject) {
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
