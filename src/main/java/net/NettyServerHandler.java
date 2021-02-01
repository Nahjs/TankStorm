package net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Author CandyWall
 * @Date 2020/11/13--16:42
 * @Description netty客户端处理器：演示粘包拆包现象
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //服务器接收客户端的数据
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String content = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器接收到的消息：" + content);
        System.out.println("服务器接收消息进行到第 " + (++count) + " 次");

        //服务器会送消息给客户端，回送一个随机ID
        ByteBuf responseByteBuf = Unpooled.copiedBuffer(UUID.randomUUID().toString(), CharsetUtil.UTF_8);
        ctx.writeAndFlush(responseByteBuf);
    }

    //处理异常情况
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
