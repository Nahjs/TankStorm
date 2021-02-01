package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.swing.*;
import java.awt.*;
/**
 * 
 * @Title: ServerFrame.java 
 * @Package top.jacktgq.view.groupchat 
 * @Description: 服务器端
 * @author CandyWall   
 * @date 2021年1月30日 下午4:47:03 
 * @version V1.0
 */
public class ServerFrame extends JFrame {
    public static final ServerFrame INSTANCE = new ServerFrame();
    private JTextArea ta;

    private ServerFrame() {
        setLayout(new BorderLayout());
        ta = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(ta);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        initFrame();
        // 初始化服务器端
        /*new Thread(() -> {
            initServer();
        }).start();*/
    }
    
    private void initFrame() {
        setTitle("Netty服务端");
        setVisible(true);
        setSize(1600, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        // 初始化服务器端
        ServerFrame.INSTANCE.initServer();
    }

    public void addMsg(String msg) {
        ta.append(msg);
    }
    
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
                        ta.append("服务器启动成功！\n");
                    } else {
                        ta.append("服务器启动失败！\n");
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
