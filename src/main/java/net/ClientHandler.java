package net;

import design.GameDesign;
import gui.over.FailGUI;
import gui.over.VictoryGUI;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.msg.GameResultMsg;
import net.msg.JoinMsg;
import net.msg.Msg;

import javax.swing.*;

/**
 * 客户端处理器
 */

public class ClientHandler extends SimpleChannelInboundHandler<Msg> {
    //消息接收处理
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Msg msg) throws Exception {
        if (msg instanceof GameResultMsg) {
            GameResultMsg gameResultMsg = (GameResultMsg) msg;
            SwingUtilities.invokeLater(() -> {
                if (gameResultMsg.isVictory()) {
                    new VictoryGUI(true, null).setVisible(true);
                } else {
                    new FailGUI().setVisible(true);
                }
            });
        } else {
            // 处理其他类型的消息
            System.out.println(msg);
            msg.handle();
        }
    }

    //通道激活处理
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new JoinMsg(GameDesign.INSTANCE.getSelfTank()));
    }
}
