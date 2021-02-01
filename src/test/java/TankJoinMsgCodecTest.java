import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import net.TankJoinMsg;
import net.TankJoinMsgDecoder;
import net.TankJoinMsgEncoder;
import org.junit.jupiter.api.Test;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author CandyWall
 * @Date 2021/2/1--11:01
 * @Description 测试坦克加入游戏时发送给服务器的消息的编解码器
 */
public class TankJoinMsgCodecTest {
    @Test
    public void testEncoder() {
        EmbeddedChannel channel = new EmbeddedChannel();
        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(4, 10, Dir.DOWN, true, Group.ENEMY, id, "hello");
        channel.pipeline().addLast(new TankJoinMsgEncoder());

        channel.writeOutbound(msg);

        ByteBuf byteBuf = channel.readOutbound();
        int x = byteBuf.readInt();
        int y = byteBuf.readInt();
        Dir dir = Dir.values()[byteBuf.readInt()];
        boolean isMoving = byteBuf.readBoolean();
        Group group = Group.values()[byteBuf.readInt()];
        UUID uuid = new UUID(byteBuf.readLong(), byteBuf.readLong());
        assertEquals(4, x);
        assertEquals(10, y);
        assertEquals(Dir.DOWN, dir);
        assertEquals(Group.ENEMY, group);
        assertEquals(true, isMoving);
        assertEquals(id, uuid);
    }

    @Test
    public void testDecoder() {
        EmbeddedChannel channel = new EmbeddedChannel();

        UUID id = UUID.randomUUID();
        TankJoinMsg msg = new TankJoinMsg(4, 10, Dir.DOWN, true, Group.ENEMY, id, "hello");

        channel.pipeline().addLast(new TankJoinMsgDecoder());

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(msg.toBytes());
        channel.writeInbound(byteBuf.duplicate());

        TankJoinMsg msg2 = channel.readInbound();
        assertEquals(4, msg2.x);
        assertEquals(10, msg2.y);
        assertEquals(Dir.DOWN, msg2.dir);
        assertEquals(Group.ENEMY, msg2.group);
        assertEquals(true, msg2.isMoving);
        assertEquals(id, msg2.id);
    }
}
