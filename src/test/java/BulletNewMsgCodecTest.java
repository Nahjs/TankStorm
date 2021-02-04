import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.MsgDecoder;
import net.MsgEncoder;
import net.msg.BulletNewMsg;
import net.msg.MsgType;
import org.junit.jupiter.api.Test;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author CandyWall
 * @Date 2021/2/1--11:01
 * @Description 测试坦克打出新的子弹时发送给服务器的消息的编解码器
 */
public class BulletNewMsgCodecTest {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        UUID tankId = UUID.randomUUID();
        BulletNewMsg msg = new BulletNewMsg(4, 10, Dir.DOWN, Group.SELF, id, tankId);
        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(msg);

        ByteBuf byteBuf = channel.readOutbound();
        // 读取消息类型
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        // 读取消息长度
        int msgLength = byteBuf.readInt();
        assertEquals(48, msgLength);

        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        Dir dir = Dir.values()[byteBuf.readInt()];
        Group group = Group.values()[byteBuf.readInt()];
        UUID id2 = new UUID(byteBuf.readLong(), byteBuf.readLong());
        UUID tankId2 = new UUID(byteBuf.readLong(), byteBuf.readLong());
        assertEquals(4, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(Group.SELF, group);
        assertEquals(id, id2);
        assertEquals(tankId, tankId2);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        UUID tankId = UUID.randomUUID();
        BulletNewMsg msg = new BulletNewMsg(4, 10, Dir.DOWN, Group.SELF, id, tankId);

        channel.pipeline().addLast(new MsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        // 写入消息类型
        byteBuf.writeInt(msg.getMsgType().ordinal());
        // 消息内容转字节码
        byte[] bytes = msg.toBytes();
        // 写入字节码长度
        byteBuf.writeInt(bytes.length);
        // 写入字节码
        byteBuf.writeBytes(bytes);
        channel.writeInbound(byteBuf.duplicate());

        BulletNewMsg msg2 = channel.readInbound();
        assertEquals(4, msg2.x);
        assertEquals(10, msg2.y);
        assertEquals(Dir.DOWN, msg2.dir);
        assertEquals(Group.SELF, msg2.group);
        assertEquals(id, msg2.id);
        assertEquals(tankId, msg2.tankId);
    }
}
