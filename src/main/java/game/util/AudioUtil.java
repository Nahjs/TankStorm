
package game.util;

import javax.sound.sampled.*;
import java.io.IOException;
/**
 * 用于处理音频播放的工具类
 */
public class AudioUtil {

	byte[] b = new byte[1024 * 1024 * 15];//存储音频数据的字节数组。



	private AudioFormat audioFormat = null;//音频格式
	private SourceDataLine sourceDataLine = null;//音频数据行，用于播放音频
	private DataLine.Info dataLine_info = null;//数据行信息

	private AudioInputStream audioInputStream = null;//音频输入流，用于读取音频数据。


	//加载音频文件
	public AudioUtil(String fileName) {
		try {
			//加载指定文件名的音频资源
			audioInputStream = AudioSystem.getAudioInputStream(AudioUtil.class.getClassLoader().getResource(fileName));
			//音频输入流中获取音频格式
			audioFormat = audioInputStream.getFormat();
			//使用该格式创建数据行信息。
			dataLine_info = new DataLine.Info(SourceDataLine.class, audioFormat);
			//利用数据行信息获取SourceDataLine实例，用于播放音频
			sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLine_info);
			//音量控制
			// FloatControl volctrl=(FloatControl)sourceDataLine.getControl(FloatControl.Type.MASTER_GAIN);
			//volctrl.setValue(-40);//

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//播放音频
	public void play() {
		try {
			byte[] b = new byte[1024*5];//临时存储音频数据
			int len = 0;
			sourceDataLine.open(audioFormat, 1024*5);//打开SourceDataLine并启动，准备播放音频。
			sourceDataLine.start();
			//System.out.println(audioInputStream.markSupported());
			audioInputStream.mark(12358946);
			while ((len = audioInputStream.read(b)) > 0) {
				sourceDataLine.write(b, 0, len);//读取数据并写入到SourceDataLine中，直到所有数据被读取完毕。
			}
			// audioInputStream.reset();

			//调用drain方法以确保所有数据都被播放，然后关闭数据行。
			sourceDataLine.drain();
			sourceDataLine.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//循环播放音频。使用mark和reset方法来实现循环
	public void loop() {
		try {

			while (true) {
				int len = 0;
				sourceDataLine.open(audioFormat, 1024 * 1024 * 15);
				sourceDataLine.start();
				//System.out.println(audioInputStream.markSupported());
				audioInputStream.mark(12358946);//标记当前位置，以便在读取完数据后使用reset方法回到标记的位置，实现循环播放。
				while ((len = audioInputStream.read(b)) > 0) {
					sourceDataLine.write(b, 0, len);//读取音频数据并写入到SourceDataLine中
				}
				audioInputStream.reset();//调用reset方法回到标记的位置，然后继续循环

				sourceDataLine.drain();//循环结束后，调用drain方法并关闭数据行。
				sourceDataLine.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			audioInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// Audio a = new Audio("audio/explode.wav");
		AudioUtil a = new AudioUtil("audio/war1.wav");
		a.loop();

	}

}
