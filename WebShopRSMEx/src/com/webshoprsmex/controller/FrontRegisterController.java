package com.webshoprsmex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webshoprsmex.model.User;
import com.webshoprsmex.service.UserService;
import com.webshoprsmex.util.DateUtil;

/**
 * 前台注册控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"/"})
public class FrontRegisterController extends BaseController{
	
	@Autowired
	private UserService userService;//注入用户业务类
	
	/**
	 * 跳转到用户注册页面
	 * @return
	 */
	@RequestMapping("/register")
	public String register(){
		return "front/common/register";
	}
	
	/**
	 * 注册
	 * @return
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public Object doRegister(User user){
		int success = 0;
		String message = "";
		params.add(new Object[]{"username","=","'"+user.getUsername()+"'"});
		User selectUser = userService.findFirst(params);
		if(selectUser!=null){
			success = -1;//用户名已存在
			message = "注册失败！用户名已存在！";
		}else{
			user.setHeader("header.jpg");
			user.setCreatetime(DateUtil.getCurrentDate());
			success = userService.insertSelective(user);
		}
		resultMap.put("success",success);
		resultMap.put("message",message);
		resultMap.put("url","login");
		return resultMap;
	}

}