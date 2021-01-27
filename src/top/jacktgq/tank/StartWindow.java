package top.jacktgq.tank;

import top.jacktgq.tank.util.Audio;
import top.jacktgq.tank.view.TankFrame;

import javax.swing.*;

/**
 * 
* @Title: StartWindow.java 
* @Package top.jacktgq 
* @Description: 界面启动的入口程序
* @author CandyWall   
* @date 2019年9月2日 上午9:17:05 
* @version V1.0
 */
public class StartWindow {
	private static void createGUI() {
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
