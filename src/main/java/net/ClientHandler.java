package net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.msg.Msg;
import net.msg.JoinMsg;
import game.GameModel;
/**
 * 客户端的消息处理器 ，用于处理从服务器接收的消息以及处理客户端通道的活动状态.
 */

public class ClientHandler extends SimpleChannelInboundHandler<Msg> {
    //消息接收处理
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        System.out.println(msg);
        msg.handle();
    }
    //通道激活处理
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new JoinMsg(GameModel.INSTANCE.getSelfTank()));
    }
}
