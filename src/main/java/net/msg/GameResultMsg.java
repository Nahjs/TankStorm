package net.msg;

import gui.over.FailGUI;
import gui.over.VictoryGUI;

import javax.swing.*;
import java.io.*;

public class GameResultMsg extends Msg {
    private boolean isVictory;

    public GameResultMsg(boolean isVictory) {
        this.isVictory = isVictory;
    }

    public boolean isVictory() {
        return isVictory;
    }

    @Override
    public void parse(byte[] bytes) {
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        DataInputStream dis = new DataInputStream(bais);
        try {
            this.isVictory = dis.readBoolean();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dis.close();
                bais.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public byte[] toBytes() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(baos);
        try {
            dos.writeBoolean(isVictory);
            dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
                baos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return baos.toByteArray();
    }

    @Override
    public MsgType getMsgType() {
        return MsgType.GameResult;
    }

    @Override
    public String toString() {
        return "GameResultMsg{" +
                "isVictory=" + isVictory +
                '}';
    }

    @Override
    public void handle() {
        // 这个方法在客户端调用
        SwingUtilities.invokeLater(() -> {
            if (isVictory) {
                new VictoryGUI(true, null).setVisible(true);
            } else {
                new FailGUI().setVisible(true);
            }
        });
    }
}