package net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.jacktgq.tank.GameModel;

public class ClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
        // 显示消息
        // ta.append(msg);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(GameModel.INSTANCE.getSelfTank()));
    }
}
