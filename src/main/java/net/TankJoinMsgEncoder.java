package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Author CandyWall
 * @Date 2021/2/1--9:58
 * @Description 新用户加入游戏时给服务器传的消息时的编码器
 */
public class TankJoinMsgEncoder extends MessageToByteEncoder<TankJoinMsg> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, TankJoinMsg tankJoinMsg, ByteBuf byteBuf) throws Exception {
        byteBuf.writeBytes(tankJoinMsg.toBytes());
    }
}
