package net.msg;

/**
 * 消息类型
 */
public enum MsgType {
    TankJoin,           // 加入游戏
    TankDirChanged,     // 坦克方向改变
    TankStop,           // 坦克停下来
    TankStartMoving,    // 坦克开始移动
    BulletNew,          // 新增子弹
    GameResult, //游戏结果
    TankDie             // 坦克死亡
}
