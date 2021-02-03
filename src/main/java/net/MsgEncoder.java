package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.msg.Msg;

/**
 * @Author CandyWall
 * @Date 2021/2/1--9:58
 * @Description 用户发送坦克信息给服务器时的编码器
 */
public class MsgEncoder extends MessageToByteEncoder<Msg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Msg msg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeInt(msg.getMsgType().ordinal()); // 写入消息类型
        byte[] bytes = msg.toBytes();                 // 消息转字节数组
        byteBuf.writeInt(bytes.length);               // 写入消息长度
        byteBuf.writeBytes(bytes);                    // 写入消息内容
    }
}
