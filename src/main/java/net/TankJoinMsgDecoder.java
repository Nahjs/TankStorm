package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import top.jacktgq.tank.entity.Dir;
import top.jacktgq.tank.entity.Group;

import java.util.List;
import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2021/2/1--9:27
 * @Description 服务器接收新用户加入游戏的消息时的解码器
 */
public class TankJoinMsgDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 如果不到33字节，就不进行消息的解码（TCP拆包、粘包的问题）
        if (byteBuf.readableBytes() < 33) {
            return;
        }
        TankJoinMsg msg = new TankJoinMsg();
        msg.x = byteBuf.readInt();
        msg.y = byteBuf.readInt();
        msg.dir = Dir.values()[byteBuf.readInt()];
        msg.isMoving = byteBuf.readBoolean();
        msg.group = Group.values()[byteBuf.readInt()];
        msg.id = new UUID(byteBuf.readLong(), byteBuf.readLong());
        list.add(msg);
    }
}
