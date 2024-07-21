package game;

import loader.AudioLoader;
import gui.progress.TankFrame;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xdgf.usermodel.section.geometry.PolyLineTo;

import javax.swing.*;



public class RunGame {
	private static JFrame runGameFrame;

	public static void createGUI() {
		runGameFrame = new TankFrame(); // 假设 TankFrame 是游戏窗口
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
		new Thread(()->new AudioLoader("audio/war1.wav").loop()).start();
	}
}
