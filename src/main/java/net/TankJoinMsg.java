package net;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.Group;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/1/31--17:53
 * @Description 玩家加入游戏的时候，给服务器发送的消息
 */
public class TankJoinMsg extends Msg {
    public int x, y;
    public Dir dir;
    public boolean isMoving;
    public Group group;
    public UUID id;
    public String name;

    public TankJoinMsg() {

    }

    public TankJoinMsg(GameObject tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.isMoving = tank.isMoving();
        this.group = tank.getGroup();
        this.id = tank.getId();
        this.name = tank.getName();
    }

    public TankJoinMsg(int x, int y, Dir dir, boolean isMoving, Group group, UUID id, String name) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.isMoving = isMoving;
        this.group = group;
        this.id = id;
        this.name = name;
    }

    public void parse(byte[] bytes) {

    }

    // 将对象转成字节数组
    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);
            dos.writeInt(x);    // 4字节
            dos.writeInt(y);    // 4字节
            dos.writeInt(dir.ordinal()); // 4字节
            dos.writeBoolean(isMoving);  // 1字节
            dos.writeInt(group.ordinal()); // 4字节
            dos.writeLong(id.getMostSignificantBits());  // 8字节
            dos.writeLong(id.getLeastSignificantBits()); // 8字节
            dos.flush();
            bytes = baos.toByteArray(); // 一共33字节
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }
    /*public byte[] toBytes() {
        byte[] bytes = null;
        ByteArrayOutputStream baos = null;
        ObjectOutputStream oos = null;
        try {
            baos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(baos);
            oos.writeObject(this);
            bytes = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                }
                if (oos != null) {
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }*/

    @Override
    public String toString() {
        return "TankJoinMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", isMoving=" + isMoving +
                ", group=" + group +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public void handle() {
        // 客户端接收到TankJoinMsg的逻辑处理：是不是自己？列表是否已经有了
        // 如果传过来的连接信息的ID和本身的ID相等或者本地的列表中有这个ID，不做处理
        if (this.id.equals(GameModel.INSTANCE.getSelfTank().getId()) ||
                GameModel.INSTANCE.findByUUID(this.id) != null) {
            return;
        }
        GameObject tank = GameModel.INSTANCE.factory.createSelfTank(this.id, this.x, this.y, this.dir, 5);
        GameModel.INSTANCE.addTank(tank);

        // 再发送一次消息给新连上的坦克
        Client.INSTANCE.send(new TankJoinMsg(GameModel.INSTANCE.getSelfTank()));
    }
}