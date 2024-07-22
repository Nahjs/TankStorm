package game;

import gui.progress.TankFrame;
import loader.AudioLoader;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RunGame {
    private static JFrame runGameFrame;

    public static void createGUI() {
        runGameFrame = new TankFrame(); // 假设 TankFrame 是游戏窗口

        runGameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 禁用默认的关闭操作
        runGameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(runGameFrame, "确定退出游戏？", "提示！",
                        JOptionPane.YES_NO_OPTION);
                if (closeCode == JOptionPane.YES_OPTION) {
                    closeRunGame();
                }
            }
        });

        runGameFrame.setVisible(true);
    }

    public static void closeRunGame() {
        if (runGameFrame != null) {
            runGameFrame.dispose(); // 关闭窗口，不退出应用程序
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createGUI();
            }
        });
        new Thread(() -> new AudioLoader("audio/war1.wav").loop()).start();
    }
}