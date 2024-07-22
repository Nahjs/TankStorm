package net;

import Log.LogPrint;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import net.msg.GameResultMsg;
import net.msg.Msg;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 客户端消息的处理器，它是服务器端的核心组件，负责处理客户端的连接和断开事件，以及接收和转发消息。
 */

public class ServerHandler extends SimpleChannelInboundHandler<Msg> {
    //定义一个channel组，管理所有的Channel
    //GlobalEventExecutor.INSTANCE：是一个全局的事件执行器，是一个单例
    private static final ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static final ExecutorService playerJoinExecutor = Executors.newFixedThreadPool(4); // 线程池用于处理玩家加入
    //表示连接一旦建立，第一个被执行
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //记录客户端加入游戏的信息
        String joinMsg = LogPrint.getCurrentTime() + " [客户端 " + channel.remoteAddress().toString().substring(1) + "] 加入游戏\n";

        ServerFrame.INSTANCE.addMsg(joinMsg);
        //将该客户单加入聊天的信息推送给其他在线的客户端
        //该方法会将channelGroup中所有的channel遍历，并发送消息
        //这里是先群发了再加入组，所以不会发给自己
        // channelGroup.writeAndFlush(forwardMsg);
        //将当前channel加入到channelGroup中，以便后续可以向所有连接的客户端广播消息。
        channelGroup.add(channel);
    }

    //表示断开连接，将xx客户离开的信息推送给当前在线的客户
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();

        String leaveMsg = "[客户端 " + channel.remoteAddress().toString().substring(1) + "] 离开游戏\n";
        ServerFrame.INSTANCE.addMsg(leaveMsg);
        channelGroup.writeAndFlush(leaveMsg);
        //这里不需要自己去把当前的channel从channelGroup中移除，netty内部已经实现
        System.out.println("channelGroup.size() = " + channelGroup.size());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
//        if (msg instanceof JoinMsg) {
//            playerJoinExecutor.submit(() -> handlePlayerJoin((JoinMsg) msg, ctx));
//        } else if (msg instanceof GameResultMsg) {
//            GameResultMsg gameResultMsg = (GameResultMsg) msg;
//            channelGroup.writeAndFlush(gameResultMsg);
//        }
            // 将其他消息广播给所有客户端
            String forwardMsg = LogPrint.getCurrentTime() +
                    " 来自[客户端 " + ctx.channel().remoteAddress().toString().substring(1) +
                    "] 的消息类型：" + msg.getMsgType() + "\n";
            ServerFrame.INSTANCE.addMsg(forwardMsg);
            channelGroup.writeAndFlush(msg);

    }

//    private void handlePlayerJoin(JoinMsg joinMsg, ChannelHandlerContext ctx) {
//        // 处理玩家加入房间的逻辑
//        boolean joined = Room.joinRoom(joinMsg.id);
//        if (joined) {
//            // 玩家成功加入房间
//            ctx.writeAndFlush("Joined room successfully!");
//        } else {
//            // 房间已满
//            ctx.writeAndFlush("Room is full!");
//        }
//    }

    public static void handleGameResultMsg(boolean isVictory) {
        GameResultMsg resultMsg = new GameResultMsg(isVictory);
        channelGroup.writeAndFlush(resultMsg);
        }

        //在服务器关闭时，确保关闭线程池以释放资源
    public static void shutdown() {
        playerJoinExecutor.shutdown();
    }

}
