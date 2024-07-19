package net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.msg.*;

import java.util.List;

/**
 * @Author CandyWall
 * @Date 2021/2/1--9:27
 * @Description 服务器接收各个用户坦克信息时的编码器
 */
public class MsgDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        // 1~4字节表示消息类型，4~8字节表示消息长度，如果不到8字节，就不进行消息类型和长度的解码（TCP拆包、粘包的问题）
        if (byteBuf.readableBytes() < 8) {
            return;
        }

        // 在消息内容起始处做个标记
        byteBuf.markReaderIndex();
        MsgType msgType = MsgType.values()[byteBuf.readInt()];
        int msgLength = byteBuf.readInt();
        // 消息没有接受全，就不进行解码
        if (byteBuf.readableBytes() < msgLength) {
            byteBuf.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[msgLength];
        byteBuf.readBytes(bytes);
        Msg msg = null;
        switch (msgType) {
            case TankJoin:
                msg = new JoinMsg();
                break;
            case TankDirChanged:
                msg = new DirChangedMsg();
                break;
            case TankStartMoving:
                msg = new MoveMsg();
                break;
            case TankStop:
                msg = new StopMsg();
                break;
            case BulletNew:
                msg = new BulletMsg();
                break;
            case TankDie:
                msg = new DieMsg();
                break;
            default:
                break;
        }
        msg.parse(bytes);
        list.add(msg);
    }
}
