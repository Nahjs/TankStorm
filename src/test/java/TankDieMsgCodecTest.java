import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.MsgDecoder;
import net.MsgEncoder;
import net.msg.MsgType;
import net.msg.TankDieMsg;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author CandyWall
 * @Date 2021/2/1--11:01
 * @Description 测试坦克停止时发送给服务器的消息的编解码器
 */
public class TankDieMsgCodecTest {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        UUID tankId = UUID.randomUUID();
        UUID bulletId = UUID.randomUUID();
        TankDieMsg msg = new TankDieMsg(tankId, bulletId);
        channel.pipeline().addLast(new MsgEncoder());

        channel.writeOutbound(msg);

        ByteBuf byteBuf = channel.readOutbound();
        // 读取消息类型
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        // 读取消息长度
        int msgLength = byteBuf.readInt();
        assertEquals(32, msgLength);

        UUID tankId2 = new UUID(byteBuf.readLong(), byteBuf.readLong());
        UUID bulletId2 = new UUID(byteBuf.readLong(), byteBuf.readLong());
        assertEquals(tankId, tankId2);
        assertEquals(bulletId, bulletId2);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID tankId = UUID.randomUUID();
        UUID bulletId = UUID.randomUUID();
        TankDieMsg msg = new TankDieMsg(tankId, bulletId);

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

        TankDieMsg msg2 = channel.readInbound();
        assertEquals(tankId, msg2.tankId);
        assertEquals(bulletId, msg2.bulletId);
    }
}
