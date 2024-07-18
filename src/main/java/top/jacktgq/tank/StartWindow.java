package top.jacktgq.tank;

import top.jacktgq.tank.util.Audio;
import top.jacktgq.tank.view.TankFrame;

import javax.swing.*;



public class StartWindow {
	public static void createGUI() {
		TankFrame tankFrame = new TankFrame();
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				createGUI();
			}
		});
		new Thread(()->new Audio("audio/war1.wav").loop()).start();
	}
}
