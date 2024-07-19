package net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.msg.Msg;
import net.msg.JoinMsg;
import top.jacktgq.tank.GameModel;

public class ClientHandler extends SimpleChannelInboundHandler<Msg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println(msg);
        msg.handle();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new JoinMsg(GameModel.INSTANCE.getSelfTank()));
    }
}
