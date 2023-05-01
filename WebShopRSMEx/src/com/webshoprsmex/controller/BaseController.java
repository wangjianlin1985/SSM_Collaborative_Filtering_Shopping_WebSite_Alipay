package com.webshoprsmex.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.webshoprsmex.model.Admin;
import com.webshoprsmex.model.User;
import com.webshoprsmex.util.Constant;

/**
 * 基础控制器（包含获取当前请求、查询参数、ajax异步请求json格式数据结果封装、当前登录用户、当前登录管理员等信息）
 */
@Controller
@Scope("prototype")
public class BaseController{
	
	@Autowired
	protected HttpServletRequest request;//自动注入request
	//封装ajax异步请求的json格式结果数据
	protected Map<Object, Object> resultMap = new HashMap<Object, Object>();
	//查询参数集合
	protected List<Object[]> params = new ArrayList<Object[]>();

	/**
	 * 获取登录用户
	 * @return
	 */
	public User getCurrentUser(){
		return (User) request.getSession().getAttribute(Constant.session_user);
	}
	
	/**
	 * 获取登录管理员
	 * @return
	 */
	public Admin getCurrentAdmin(){
		return (Admin) request.getSession().getAttribute(Constant.session_admin);
	}
	
}