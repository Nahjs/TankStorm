package net.msg;

import net.Client;
import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;
import top.jacktgq.tank.entity.Group;

import java.io.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/2/3--13:47
 * @Description 坦克死亡的时候，给服务器发送的消息
 */
public class TankDieMsg extends Msg {
    public int x, y;
    public Dir dir;
    public boolean moving;
    public Group group;
    public UUID id;
    public String name;

    public TankDieMsg() {

    }

    public TankDieMsg(GameObject tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.moving = tank.isMoving();
        this.group = tank.getGroup();
        this.id = tank.getId();
        this.name = tank.getName();
    }

    public TankDieMsg(int x, int y, Dir dir, boolean isMoving, Group group, UUID id, String name) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.moving = isMoving;
        this.group = group;
        this.id = id;
        this.name = name;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais = null;
        DataInputStream dis = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bais);
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.moving = dis.readBoolean();
            this.id = new UUID(dis.readLong(), dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dis != null) {
                    dis.close();
                }
                if (bais != null) {
                    bais.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
            dos.writeBoolean(moving);  // 1字节
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

    @Override
    public MsgType getMsgType() {
        return MsgType.TankDie;
    }

    @Override
    public String toString() {
        return "TankDieMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", isMoving=" + moving +
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
        Client.INSTANCE.send(new TankDieMsg(GameModel.INSTANCE.getSelfTank()));
    }
}