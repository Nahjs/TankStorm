package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 
 * @Title: ServerFrame.java 
 * @Package top.jacktgq.view.groupchat 
 * @Description: 服务器端
 * @author CandyWall   
 * @date 2021年1月30日 下午4:47:03 
 * @version V1.0
 */
public class Server {
    private void initServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        
        try {
            ChannelFuture future = bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new TankJoinMsgEncoder());
                        pipeline.addLast(new TankJoinMsgDecoder());
                        pipeline.addLast(new ServerHandler());
                    }
                })
                .bind(8888);
            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        ServerFrame.INSTANCE.addMsg("服务器启动成功！\n");
                    } else {
                        ServerFrame.INSTANCE.addMsg("服务器启动失败！\n");
                    }
                }
            }).sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
