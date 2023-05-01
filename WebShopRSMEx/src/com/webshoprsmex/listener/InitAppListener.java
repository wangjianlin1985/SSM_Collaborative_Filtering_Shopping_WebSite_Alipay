package com.webshoprsmex.listener;

import java.io.File;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

import com.webshoprsmex.util.Constant;

/**
 * applicaiton监听器，当应用程序启动的时候，会执行该监听器
 * 初始化applicaiton数据，用于页面获取一些静态值
 */
public class InitAppListener implements ServletContextAware{

	@Override
	public void setServletContext(ServletContext arg0) {
		//项目根路径
		Constant.applicationRealPath = arg0.getRealPath("");
		String applicationRealPath = Constant.applicationRealPath;
		//设置文件上传路径
		Constant.applicationUploadRealPath = applicationRealPath.substring(0,applicationRealPath.lastIndexOf("\\")+1) 
				+ Constant.uploadFileDir;
		//创建文件上传路径
		new File(Constant.applicationUploadRealPath).mkdirs();
		arg0.setAttribute("uploadFileDir",Constant.uploadFileDir);
		//订单状态
		arg0.setAttribute("orderStateMapGlobal",Constant.orderStateMapGlobal);
	}
	
}
