package top.jacktgq.tank.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @Title: TimeUtils.java 
 * @Package top.jacktgq 
 * @Description: 自定义日志打印类
 * @author CandyWall   
 * @date 2020年11月1日 下午7:35:23 
 * @version V1.0
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
