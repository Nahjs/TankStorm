package net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import javax.swing.*;
import java.awt.*;
/**
 * 服务器端窗口，用于显示服务器端的日志信息
 */
public class ServerFrame extends JFrame {

    //创建用于全局访问 ServerFrame的单例实例。
    public static final ServerFrame INSTANCE = new ServerFrame();
    // 创建用于显示服务器日志的文本区域。
    private static JTextArea jTextArea;

    private ServerFrame() {
        setLayout(new BorderLayout());
        jTextArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(jTextArea);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
        initFrame();
        // 初始化服务器端
        /*new Thread(() -> {
            initServer();
        }).start();*/
    }

    public void initFrame() {
        setTitle("Netty服务端");
        setVisible(true);
        setSize(800, 400);

        //将窗口的显示位置相对于其父窗口或屏幕的中心进行调整。
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    //将消息追加到文本区域 jTextArea，用于显示服务器的日志信息。
    public void addMsg(String msg) {
        jTextArea.append(msg);
    }

    //初始化和启动 Netty 服务器端
    public static void initServer() {
        EventLoopGroup connectionAcceptorGroup = new NioEventLoopGroup();// 用于接受连接
        EventLoopGroup dataProcessingGroup = new NioEventLoopGroup();// 用于处理数据读写
        ServerBootstrap bootstrap = new ServerBootstrap();

        //配置服务器端使用的事件循环组、通道实现和子处理器。
        try {
            ChannelFuture future = bootstrap.group(connectionAcceptorGroup, dataProcessingGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();

                            //为每个新连接的客户端通道配置 ChannelPipeline添加必要的处理器
                            pipeline.addLast(new MsgEncoder());
                            pipeline.addLast(new MsgDecoder());
                            pipeline.addLast(new ServerHandler());
                        }
                    })
                    .bind(8888);
            //监听服务器启动的结果，并在文本区域中显示相应的消息。

            future.addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()) {
                        jTextArea.append("服务器启动成功！\n");
                    } else {
                        jTextArea.append("服务器启动失败！\n");
                    }
                }
            }).sync();
            future.channel().closeFuture().sync();//同步等待服务器启动结果
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //关闭事件循环组，释放资源。
            connectionAcceptorGroup.shutdownGracefully();
            dataProcessingGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        initServer();
    }
}
