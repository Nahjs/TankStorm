package net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import top.jacktgq.tank.util.LogUtils;

public class ServerHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
    //定义一个channel组，管理所有的Channel
    //GlobalEventExecutor.INSTANCE：是一个全局的事件执行器，是一个单例
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    //表示连接一旦建立，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String forwardMsg = LogUtils.getCurrentTime() + " [客户端 "+ channel.remoteAddress().toString().substring(1) +"] 加入游戏\n";
        ServerFrame.INSTANCE.addMsg(forwardMsg);
        //将该客户单加入聊天的信息推送给其他在线的客户端
        //该方法会将channelGroup中所有的channel遍历，并发送消息
        //这里是先群发了再加入组，所以不会发给自己
        // channelGroup.writeAndFlush(forwardMsg);
        //将当前channel加入到channelGroup中
        channelGroup.add(channel);
    }
    
    //表示断开连接，将xx客户离开的信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        String forwardMsg = "[客户端 "+ channel.remoteAddress().toString().substring(1) +"] 离开游戏\n";
        ServerFrame.INSTANCE.addMsg(forwardMsg);
        channelGroup.writeAndFlush(forwardMsg);
        //这里不需要自己去把当前的channel从channelGroup中移除，netty内部已经实现
        System.out.println("channelGroup.size() = " + channelGroup.size());
    }
    
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
        //获取到当前Channel
        Channel channel = ctx.channel();
        String forwardMsg = LogUtils.getCurrentTime() + " [客户端 " + channel.remoteAddress().toString().substring(1) + "] 的连接信息：" + msg + "\n";
        ServerFrame.INSTANCE.addMsg(forwardMsg);
        channelGroup.writeAndFlush(msg);
        //这时我们遍历channelGroup，根据不同的情况，回送不同的消息
        /*channelGroup.forEach(ch -> {
            if (ch != channel) {    //不是当前的channel，转发消息
                ch.writeAndFlush(forwardMsg);
            } else {    //回显自己发送消息给自己
                ch.writeAndFlush(LogUtils.getCurrentTime() + " [我] 说：" + msg + "\n");
            }
        });*/
    }
}
