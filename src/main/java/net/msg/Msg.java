package net.msg;


/**
 * 消息类
 */
public abstract class Msg {
    public abstract void handle();
    public abstract void parse(byte[] bytes);
    public abstract byte[] toBytes();
    public abstract MsgType getMsgType();
}
