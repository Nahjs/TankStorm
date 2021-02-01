package top.jacktgq.tank.decorator;

import top.jacktgq.tank.entity.GameObject;

import java.awt.*;

/**
 * @Author CandyWall
 * @Date 2021/2/1--13:54
 * @Description 使用装饰器模式在坦克上方绘制id，用以标识。
 * 在启动客户端后，需要把初始坦克信息发送给服务器端，可是收到的初始坐标都是(0, 0)，被装饰器装饰后的部分属性都没有被赋值。
 */
public class TankIdDecorator extends GODecorator {
    public TankIdDecorator(GameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.YELLOW);
        Rectangle rect = super.getRect();
        g.drawString(gameObject.getId().toString().substring(0, 16), rect.x - 15, rect.y - 20);
    }
}
