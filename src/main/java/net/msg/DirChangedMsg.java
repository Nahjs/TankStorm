package net.msg;

import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.GameObject;

import java.io.*;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/2/3--13:47
 * @Description 坦克方向改变的时候，给服务器发送的消息
 */
public class DirChangedMsg extends Msg {
    public int x, y;
    public Dir dir;
    public UUID id;

    public DirChangedMsg() {

    }

    public DirChangedMsg(GameObject tank) {
        this.x = tank.getX();
        this.y = tank.getY();
        this.dir = tank.getDir();
        this.id = tank.getId();
    }

    public DirChangedMsg(int x, int y, Dir dir, UUID id) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.id = id;
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
        return MsgType.TankDirChanged;
    }

    @Override
    public String toString() {
        return "TankDirChangedMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        // 客户端接收到TankJoinMsg的逻辑处理：是不是自己？
        // 如果传过来的连接信息的ID和本身的ID相等或者本地的列表中有这个ID，不做处理
        if (this.id.equals(GameModel.INSTANCE.getSelfTank().getId())) {
            return;
        }
        // 根据id找到对应的坦克
        GameObject tank = GameModel.INSTANCE.findTankByUUID(id);
        if (tank != null) {
            tank.setX(x);
            tank.setY(y);
            tank.setDir(dir);
        }
    }
}