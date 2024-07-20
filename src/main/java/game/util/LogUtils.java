package game.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *日志打印类
 */
public class LogUtils {

	/**
	 * 获取当前系统时间，并进行格式化
	 */
	public static String getCurrentTime() {
		LocalDateTime now = LocalDateTime.now();
		return now.format(DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm:ss:SSS"));
	}

	/**
	 * @param info ：要输出的内容
	 */
	public static void log(String info) {
		log("", info);
	}

	/**
	 *
	 * @param className	：类名
	 * @param info		：要输出的内容
	 */
	public static void log(String className, String info) {
		System.out.println(getCurrentTime() + " <" + className + "> [" + Thread.currentThread().getName() + "] : " + info);
	}
}
