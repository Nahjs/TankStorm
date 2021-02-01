package net;

import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;

/**
 * @Author CandyWall
 * @Date 2021/1/31--17:53
 * @Description 玩家加入游戏的时候，给服务器发送的消息
 */
public class PlayerJoinMsg {
    public int x, y;
    public Dir dir;
    public boolean isMoving;
    public Group group;
    public String id;
    public String name;

    public PlayerJoinMsg(int x, int y, Dir dir, boolean isMoving, Group group, String id, String name) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.isMoving = isMoving;
        this.group = group;
        this.id = id;
        this.name = name;
    }
}