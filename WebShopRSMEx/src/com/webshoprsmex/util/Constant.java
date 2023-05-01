package com.webshoprsmex.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 常量工具类
 */
public class Constant {

	/**
	 * session用户名称
	 */
	public static final String session_user = "session_user";
	
	/**
	 * session管理员名称
	 */
	public static final String session_admin = "session_admin";
	
	/**
	 * session验证码名称
	 */
	public static final String session_valcode = "session_valcode";
	
	/**
	 * 上传文件保存的app目录
	 */
	private static final String uploadAppDir = "WebShopRSMExUpload";
	
	/**
	 * 上传文件保存的app下的文件夹目录
	 */
	private static final String uploadDir = "upload";
	
	/**
	 * 上传文件保存的文件夹目录
	 */
	public static final String uploadFileDir = uploadAppDir + "/" +uploadDir;
	
	/**
	 * 项目根路径
	 */
	public static String applicationRealPath = "";

	/**
	 * 项目文件上传根路径
	 */
	public static String applicationUploadRealPath = "";
	
	/**
	 * 分页大小
	 */
	public static final String pageSize = "10";
	
	/**
	 * 订单状态map集合
	 */
	public static Map<Integer,String> orderStateMapGlobal = new LinkedHashMap<Integer, String>();
	
	static{
		orderStateMapGlobal.put(1,"未付款");
		orderStateMapGlobal.put(2,"已付款，未发货");
		orderStateMapGlobal.put(3,"已发货，未收货");
		orderStateMapGlobal.put(4,"已收货");
		orderStateMapGlobal.put(5,"已退款");
	}
	
}