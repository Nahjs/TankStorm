package game;

import gui.over.VictoryGUI;
import gui.progress.TankFrame;
import loader.AudioLoader;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RunGame {
    private static JFrame runGameFrame;
   // private static AudioLoader audioLoader;

    public static void createGUI() {
        runGameFrame = new TankFrame(); // 假设 TankFrame 是游戏窗口

//        audioLoader = new AudioLoader("audio/war1.wav"); // 初始化音频加载器
//        audioLoader.loop(); // 开始播放音乐

        runGameFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // 禁用默认的关闭操作
        runGameFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int closeCode = JOptionPane.showConfirmDialog(runGameFrame, "确定就离开了吗？", "你有机会排第一！",
                        JOptionPane.YES_NO_OPTION);
                if (closeCode == JOptionPane.YES_OPTION) {
                    closeRunGame();
//                    audioLoader.stop(); // 停止音乐播放
                    new VictoryGUI(true, null);//启动胜利窗口
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

    //测试用
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