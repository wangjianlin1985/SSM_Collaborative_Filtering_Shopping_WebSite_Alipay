package com.webshoprsmex.util;

/**
 * 常用的工具类
 */
public class CommonUtil {

	/**
	 * 随机产生一个六位随机数
	 * @return
	 */
	public static Integer getRandom(){
		return (int)((Math.random()*9+1)*100000);
	}
	
}
