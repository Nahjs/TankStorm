package net.msg;

import design.GameDesign;

import java.io.*;
import java.util.UUID;

/**
 * 坦克死亡的时候，给服务器发送的消息
 */
public class DieMsg extends Msg {
    public UUID tankId;
    public UUID bulletId;


    public DieMsg() {

    }

    public DieMsg(UUID tankId, UUID bulletId) {
        this.tankId = tankId;
        this.bulletId = bulletId;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais = null;
        DataInputStream dis = null;
        try {
            bais = new ByteArrayInputStream(bytes);
            dis = new DataInputStream(bais);
            this.tankId = new UUID(dis.readLong(), dis.readLong());
            this.bulletId = new UUID(dis.readLong(), dis.readLong());
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
            dos.writeLong(tankId.getMostSignificantBits());  // 8字节
            dos.writeLong(tankId.getLeastSignificantBits()); // 8字节
            dos.writeLong(bulletId.getMostSignificantBits());  // 8字节
            dos.writeLong(bulletId.getLeastSignificantBits()); // 8字节
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
        return "DieMsg{" +
                "tankId=" + tankId +
                ", bulletId=" + bulletId +
                '}';
    }

    @Override
    public void handle() {
        // 客户端接收到TankDieMsg的逻辑处理
        if (this.tankId.equals(GameDesign.INSTANCE.getSelfTank().getId())) {
            GameDesign.INSTANCE.gameFail = true;
        }
//        Player player = GameDesign.INSTANCE.findPlayerByTankId(this.tankId);
//        if (player != null) {
//            player.updateScore(); // 更新玩家得分
//        }

       GameDesign.INSTANCE.checkBulletHits();
        GameDesign.INSTANCE.removeTankByUUID(tankId);
        GameDesign.INSTANCE.removeBulletByUUID(bulletId);
    }
}