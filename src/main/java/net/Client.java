package net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author CandyWall
 * @version V1.0
 * @Title: Client.java
 * @Description: 客户端
 * @date 2021年1月30日 下午4:46:43
 */
public class Client {
    public static final Client INSTANCE = new Client();
    private Channel channel;

    private Client() {}

    private void sendMsg(String msg) {
        channel.writeAndFlush(msg);
    }

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {
            ChannelFuture future = bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            pipeline.addLast(new TankJoinMsgEncoder());
                            pipeline.addLast(new TankJoinMsgDecoder());
                            pipeline.addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 8888);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        //ta.append("登录成功！\n");
                        channel = future.channel();
                    } else {
                        //ta.append("登录失败！\n");
                    }
                }
            }).sync();
            System.out.println("...");
            //channel = future.channel();
            channel.closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    // 发送消息
    public void send(TankJoinMsg tankJoinMsg) {
        channel.writeAndFlush(tankJoinMsg);
    }
}
