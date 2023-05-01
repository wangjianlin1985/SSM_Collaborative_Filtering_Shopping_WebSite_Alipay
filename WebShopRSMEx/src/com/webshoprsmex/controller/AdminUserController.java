package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.UserService;
import com.webshoprsmex.util.Constant;

/**
 * 后台管理员用户控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/user")
public class AdminUserController extends BaseController{

	@Autowired
	private UserService userService;//注入用户业务类
	
	/**
	 * 带参数分页查询
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			String username){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.add(new Object[]{"username","like",username});
		List<User> list = userService.find(params);
		PageInfo<User> pageInfo = new PageInfo<User>(list);
		map.put("pageBean", pageInfo);
		map.put("username", username);
		return "admin/user/list";
	}
	
	/**
	 * 跳转到用户详情页面
	 * @return
	 */
	@RequestMapping("/view")
	public String view(Integer userid){
		request.setAttribute("user", userService.selectByPrimaryKey(userid));
		return "admin/user/view";
	}
	
	/**
	 * 删除
	 * @param user
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(User user){
		resultMap.put("success",userService.deleteByPrimaryKey(user.getId()));//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url","reload");
		return resultMap;
	}
	
}