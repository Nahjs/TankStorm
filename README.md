# 坦克大战和设计模式
> <font color="red">以坦克大战为这个游戏为案例，融合设计模式，进行设计模式的训练。</font>

1. 使用抽象工厂模式：可以生产不同风格的坦克、炮弹、爆炸、墙共4中游戏物体。

2. 使用Facade模式：TankPanel中不再直接调用和绘制各种游戏物体，而是和GameModel打交道，GameModel充当facade，View和Model进行了分离；

3. 使用调停者模式，将坦克、子弹、爆炸动画、墙体等抽象出一个公共的父类GameObject，各种对游戏物体之间的碰撞检测逻辑不用写死在某个类中，后期也不用修改游戏对象中的代码；

4. 使用责任链模式，将碰撞处理的逻辑串起来，统一进行碰撞检测，方便后期扩展；

5. 在游戏区域中添加游戏物体——墙，只需再编写炮弹、坦克和墙的（Collider）碰撞检测器，然后将规则串到责任链上即可；

6. 使用单例模式，将GameModel做成单例，以供全局使用，在创建游戏物体的时候就不需要再将GameModel作为参数传入；

7. 使用装饰器模式，给坦克加上一个红色的边框，小bug：给坦克用装饰器包装了一下，添加到GameModel中的GameObject集合中，在碰撞检测的时候，碰撞检测失效，在GODecorator的对象不是BaseTank的实例，使用了装饰器以后该如何解决呢？！；

# 坦克大战网络版：使用Netty框架

1. 去掉关于敌方坦克的初始化，添加一些Netty基础代码，为网络版做准备。

2. 给坦克添加名称和随机ID属性

3. 初始化我方坦克的时候，先产生墙，然后产生一个随机的位置，不能和围墙相交，方向也随机。

4. 为了正确使用装饰器模式（后期可能会跟坦克添加一个血条、或者给坦克加一个名称以区别不同玩家的坦克）,给所有游戏物体添加上GameObjectType属性，分为坦克、炮弹、墙、爆炸四种类型，并修改碰撞器中的部分if代码，将Tank的getGroup()和back()方法抽到GameObject类中。

5. 使用装饰器模式在坦克上方绘制id，用以标识。

6. 在启动客户端后，需要把初始坦克信息发送给服务器端，可是收到的初始坐标都是(0, 0)，被装饰器装饰后的部分属性都没有被赋值。

   经过调试发现，如下图所示，这是由于装饰器模式的缺陷所致：<font color="red">装饰器不能继承被装饰对象的属性，需要手动将被装饰对象的属性赋值给装饰器中的对应属性</font>。

   ![装饰器模式的缺陷](https://img-blog.csdnimg.cn/20210201152341287.png?x-oss-process=image/watermark,type_ZmFuZ3poZW5naGVpdGk,shadow_10,text_aHR0cHM6Ly9ibG9nLmNzZG4ubmV0L3FxXzM4NTA1OTY5,size_16,color_FFFFFF,t_70)

   <font color="red">总结：使用了装饰器模式之后，不能使用被装饰后的对象的public 属性了，取而代之的是使用内部的方法，而顶级父类中的属性最好全部设置成private的，然后获取属性的值应该使用对应属性的get方法。</font>

7. 重构消息，让消息自己handle

8. 添加TankJoinMsg、TankDirChangeMsg、TankStopMsg、TankStartingMovingMsg、BulletNewMsg；基于这些不同的类型的消息类抽象出父类Msg；新增MsgType枚举类，不同子类消息类中添加MsgType加以区别；将TankJoinMsgEncoder改名为MsgEncoder，将TankJoinMsgDecoder改名为MsgDecoder，完成TankStartMoving、TankStop两种状态下的消息发送。

9. 修复坦克在每次通过键盘事件移动后重绘引起的bug。

10. 坦克方向改变后会发送消息给服务器。

11. 空格键按下不应该调用setMainTankDir()方法，将switch case KeyEvent.VK_SPACE中的break改成return，也就不会发送TankStopMsg消息了。

12. 坦克打出新的子弹发送BulletNewMsg给服务器。

13. 本地客户端的坦克和子弹产生后应该也加入到各自的集合中。

14. 增加一个网络版子弹和坦克的碰撞检测器，坦克和其他坦克的打出的子弹碰撞后发送TankDieMsg。