package net.msg;

/**
 * @Author CandyWall
 * @Date 2021/1/31--21:23
 * @Description 玩家坦克消息类型
 */
public enum MsgType {
    TankJoin,           // 加入游戏
    TankDirChanged,     // 坦克方向改变
    TankStop,           // 坦克停下来
    TankStartMoving,    // 坦克开始移动
    BulletNew,          // 新增子弹
    TankDie             // 坦克死亡
}
