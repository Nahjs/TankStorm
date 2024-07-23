package gui.progress;

import loader.ConfigLoader;

import javax.swing.*;

/**
 * 游戏界面
 */
public class TankFrame extends JFrame {
    private static final int GAME_WIDTH = ConfigLoader.getGameWidth();
    private static final int GAME_HEIGHT =  ConfigLoader.getGameHeight();

    public TankFrame() {
        setTitle("\u5766\u514b\u98ce\u4e91");
        setSize(GAME_WIDTH, GAME_HEIGHT);
        getContentPane().add(new TankPanel());
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
