package game;

import loader.AudioLoader;
import gui.progress.TankFrame;

import javax.swing.*;



public class RunGame {
	public static void createGUI() {
		TankFrame tankFrame = new TankFrame();
	}
	public static void closeRunGame() {
		System.exit(0);
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
