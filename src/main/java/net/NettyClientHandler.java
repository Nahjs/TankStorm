package net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Author CandyWall
 * @Date 2020/11/13--16:37
 * @Description netty服务器端处理器：演示粘包拆包现象
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //客户端发送10条数据
        for (int i = 0; i < 10; i++) {
            ByteBuf byteBuf = Unpooled.copiedBuffer("hello, server" + i + " ", CharsetUtil.UTF_8);
            ctx.writeAndFlush(byteBuf);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        //客户端接收服务器发来的消息
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String content = new String(buffer, CharsetUtil.UTF_8);
        System.out.println("服务器接收到的消息：" + content);
        System.out.println("服务器接收消息进行到第 " + (++count) + " 次");
    }

    //处理异常情况
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
