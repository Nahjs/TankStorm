package net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import top.jacktgq.tank.GameModel;
import top.jacktgq.tank.entity.GameObject;

public class ClientHandler extends SimpleChannelInboundHandler<TankJoinMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TankJoinMsg msg) throws Exception {
        // System.out.println(msg);
        // 客户端接收到TankJoinMsg的逻辑处理：是不是自己？列表是否已经有了
        // 如果传过来的连接信息的ID和本身的ID相等或者本地的列表中有这个ID，不做处理
        if (msg.id.equals(GameModel.INSTANCE.getSelfTank().getId()) ||
                GameModel.INSTANCE.findByUUID(msg.id) != null) {
            return;
        }
        GameObject tank = GameModel.INSTANCE.factory.createSelfTank(msg.id, msg.x, msg.y, msg.dir, 5);
        GameModel.INSTANCE.addTank(tank);

        // 再发送一次消息给新连上的坦克
        ctx.writeAndFlush(new TankJoinMsg(GameModel.INSTANCE.getSelfTank()));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(GameModel.INSTANCE.getSelfTank()));
    }
}
