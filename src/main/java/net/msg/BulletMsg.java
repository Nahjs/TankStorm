package net.msg;

import top.jacktgq.game.GameModel;
import top.jacktgq.game.entity.Dir;
import top.jacktgq.game.entity.GameObject;
import top.jacktgq.game.entity.Group;

import java.io.*;
import java.util.UUID;

/**
 *  玩家加入游戏的时候，给服务器发送的消息
 */
public class BulletMsg extends Msg {
    public int x, y;
    public Dir dir;
    public Group group;
    public UUID id;
    public UUID tankId;

    public BulletMsg() {

    }

    public BulletMsg(GameObject bullet) {
        this.x = bullet.getX();
        this.y = bullet.getY();
        this.dir = bullet.getDir();
        this.group = bullet.getGroup();
        this.id = bullet.getId();
        this.tankId = bullet.getTankId();
    }

    public BulletMsg(int x, int y, Dir dir, Group group, UUID id, UUID tankId) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.id = id;
        this.tankId = tankId;
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
            this.group = Group.values()[dis.readInt()];
            this.id = new UUID(dis.readLong(), dis.readLong());
            this.tankId = new UUID(dis.readLong(), dis.readLong());
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
            dos.writeInt(group.ordinal()); // 4字节
            dos.writeLong(id.getMostSignificantBits());  // 8字节
            dos.writeLong(id.getLeastSignificantBits()); // 8字节
            dos.writeLong(tankId.getMostSignificantBits());  // 8字节
            dos.writeLong(tankId.getLeastSignificantBits()); // 8字节
            dos.flush();
            bytes = baos.toByteArray(); // 一共48字节
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
        return MsgType.BulletNew;
    }

    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                ", id=" + id +
                ", tankId=" + tankId +
                '}';
    }

    @Override
    public void handle() {
        // 客户端接收到BulletNewMsg的逻辑处理：是不是本机坦克打出？子弹列表是否已经有了？是的话就不做处理
        if (this.tankId.equals(GameModel.INSTANCE.getSelfTank().getId()) ||
                GameModel.INSTANCE.findBulletByUUID(this.id) != null) {
            return;
        }
        GameModel.INSTANCE.addBullet(GameModel.INSTANCE.factory.createBullet(this.id, this.tankId, this.x, this.y, this.dir, group));
    }
}