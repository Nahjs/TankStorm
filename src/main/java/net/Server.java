package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器端
 */
public class Server {

    private void initServer() {
        //处理服务器端的连接请求的NioEventLoopGroup 实例
        EventLoopGroup connectionAcceptorGroup = new NioEventLoopGroup();
        // 处理与客户端建立连接后的数据读写的NioEventLoopGroup 实例
        EventLoopGroup dataProcessingGroup = new NioEventLoopGroup();
        //创建ServerBootstrap
        ServerBootstrap bootstrap = new ServerBootstrap();



        //配置 ServerBootstrap
        try {
            ChannelFuture future = bootstrap.group(connectionAcceptorGroup, dataProcessingGroup)//将 connectionAcceptorGroup 和 dataProcessingGroup 分配给 bootstrap
                    .channel(NioServerSocketChannel.class)//指定服务器端使用的通道实现类型
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {//设置 ChannelInitializer，用于配置新连接的客户端通道
                            ChannelPipeline pipeline = ch.pipeline();
                            //为每个客户端通道的 ChannelPipeline 添加处理器
                            pipeline.addLast(new MsgEncoder());
                            pipeline.addLast(new MsgDecoder());
                            pipeline.addLast(new ServerHandler());
                        }
                    })
                    .bind(8888);//绑定服务器到指定端口（这里为 8888 端口）并启动服务器

            future.addListener(new ChannelFutureListener() {//为绑定操作添加监听器
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        ServerFrame.INSTANCE.addMsg("服务器启动成功！\n");
                    } else {
                        ServerFrame.INSTANCE.addMsg("服务器启动失败！\n");
                    }
                }
            }).sync();//阻塞当前线程，直到绑定操作完成。
            future.channel().closeFuture().sync();//阻塞当前线程，直到服务器通道关闭
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭 connectionAcceptorGroup 和 dataProcessingGroup，释放所有资源
            connectionAcceptorGroup.shutdownGracefully();
            dataProcessingGroup.shutdownGracefully();
        }
    }
}
