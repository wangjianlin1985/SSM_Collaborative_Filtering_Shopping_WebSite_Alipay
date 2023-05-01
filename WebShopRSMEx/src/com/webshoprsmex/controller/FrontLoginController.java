package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webshoprsmex.model.User;
import com.webshoprsmex.service.UserService;
import com.webshoprsmex.util.Constant;

/**
 * 前台登录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"/"})
public class FrontLoginController extends BaseController{

	@Autowired
	private UserService userService;//注入用户业务类
	
	/**
	 * 跳转到用户登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "front/common/login";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public Object doLogin(User user){
		int success = 0;
		String message = "";
		params.add(new Object[]{"username","=","'"+user.getUsername()+"'"});
		params.add(new Object[]{"password","=","'"+user.getPassword()+"'"});
		List<User> userList = userService.find(params);
		if(userList==null || userList.size()==0){//用户名或密码错误
			success = 0;
			message = "登录失败！用户名或密码错误！";
		}else{
			success = 1;
			resultMap.put("url","/");
			//将用户信息保存在session中
			request.getSession().setAttribute(
					Constant.session_user,userList.get(0));
		}
		resultMap.put("success",success);
		resultMap.put("message",message);
		return resultMap;
	}
	
	/**
	 * 用户注销
	 * @return
	 */
	@RequestMapping("/quit")
	public String quit(){
		//销毁session
		request.getSession().setAttribute(Constant.session_user, null);
		return "redirect:/";
	}
	
}