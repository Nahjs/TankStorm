package net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import net.msg.Msg;

/**
 * 使用 Bootstrap 辅助类来初始化客户端，并连接到服务器。
 */
public class Client {
    public static final Client INSTANCE = new Client();//客户端的单例实例
    private Channel channel;//用于与服务器通信的 Channel

    private Client() {}

    public void connect() {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();

        try {//配置 Bootstrap
            //将之前创建的 EventLoopGroup 分配给 Bootstrap，这样客户端的所有 I/O 操作都将由这个 EventLoopGroup 处理
            ChannelFuture future = bootstrap.group(group)

                    .channel(NioSocketChannel.class)//指定客户端使用的 Channel 实现类型为 NioSocketChannel

                    //设置 ChannelInitializer，帮助用户配置一个新的 Channel
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //添加自定义的处理器到 ChannelPipeline 中
                            pipeline.addLast(new MsgEncoder());
                            pipeline.addLast(new MsgDecoder());
                            pipeline.addLast(new ClientHandler());
                        }
                    })
                    .connect("localhost", 8888);//异步连接
            future.addListener(new ChannelFutureListener() {
                //当连接操作完成时（无论成功还是失败），都会调用 operationComplete 方法。
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if (future.isSuccess()) {
                        //ta.append("登录成功！\n");
                        channel = future.channel();//如果成功，将 Channel 实例赋值给 channel 变量
                    } else {
                        //ta.append("登录失败！\n");
                    }
                }
            }).sync();//阻塞当前线程，直到连接操作完成（成功或失败）。
            System.out.println("...");
           // channel = future.channel();
            channel.closeFuture().sync();//阻塞当前线程，直到 channel 被关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            //关闭 EventLoopGroup，释放所有资源
        }
    }

    // 通过客户端通道发送消息到服务器。
    public void send(Msg msg) {
        if (channel != null && channel.isActive()) {
            channel.writeAndFlush(msg);
        } else {
            // 处理 channel 为 null 或非活跃状态的情况
            // 例如，记录日志、尝试重新连接等
        }
    }
}
