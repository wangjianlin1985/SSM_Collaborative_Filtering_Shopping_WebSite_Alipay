package com.webshoprsmex.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webshoprsmex.model.User;
import com.webshoprsmex.service.UserService;
import com.webshoprsmex.util.CommonUtil;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.ValcodeModel;

/**
 * 前台用户控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/user")
public class FrontUserController extends BaseController{

	@Autowired
	private UserService userService;//注入用户业务类
	
	/**
	 * 跳转到用户详情页面
	 * @return
	 */
	@RequestMapping("/view")
	public String view(){
		User cUser = getCurrentUser();
		request.setAttribute("user", userService.selectByPrimaryKey(cUser.getId()));
		return "front/user/view";
	}
	
	/**
	 * 跳转到用户编辑页面
	 * @return
	 */
	@RequestMapping("/edit")
	public String edit(){
		User cUser = getCurrentUser();
		request.setAttribute("user", userService.selectByPrimaryKey(cUser.getId()));
		return "front/user/edit";
	}
	
	/**
	 * 更新
	 * @return
	 */
	@RequestMapping("/update")
	@ResponseBody
	public Object update(User user){
		User cUser = userService.selectByPrimaryKey(getCurrentUser().getId());
		cUser.setEmail(user.getEmail());
		cUser.setHeader(user.getHeader());
		int success = userService.updateByPrimaryKey(cUser);
		if(success>=1){
			request.getSession().setAttribute(Constant.session_user, cUser);
		}
		resultMap.put("success",success);
		resultMap.put("url","reload");
		return resultMap;
	}
	
	/**
	 * 跳转到修改密码页面
	 * @return
	 */
	@RequestMapping("/editPassword")
	public String editPassword(){
		User cUser = getCurrentUser();
		request.setAttribute("user", userService.selectByPrimaryKey(cUser.getId()));
		return "front/user/password";
	}
	
	/**
	 * 修改密码
	 * @return
	 */
	@RequestMapping("/updatePassword")
	@ResponseBody
	public Object updatePassword(User user){
		String oldPassword = request.getParameter("oldPassword");
		User cUser = userService.selectByPrimaryKey(getCurrentUser().getId());
		int success = 1;
		String message = "";
		if(cUser.getPassword().equals(oldPassword)){
			cUser.setPassword(user.getPassword());
			userService.updateByPrimaryKey(cUser);
			message = "修改成功！请重新登陆！";
		}else{
			success = -1;//原密码不正确
			message = "原密码不正确！";
		}
		resultMap.put("success",success);
		resultMap.put("message",message);
		if(success>0){
			request.getSession().setAttribute(Constant.session_user, null);
			resultMap.put("url","login");
		}
		return resultMap;
	}
	
	/**
	 * 跳转到忘记密码页面
	 * @return
	 */
	@RequestMapping("/forgetPassword")
	public String forgetPassword(){
		return "front/user/forgetPassword";
	}
	
	/**
	 * 修改忘记密码
	 * @return
	 */
	@RequestMapping("/doForgetPassword")
	@ResponseBody
	public Object doForgetPassword(String username,String valcode){
		int success = 0;
		String message = "";
		//查询参数
		params.add(new Object[]{"username","=","'"+username+"'"});
		User user = userService.findFirst(params);
		if(user==null){//用户名不存在
			message = "用户名不存在！";
		}else{
			Object object = request.getSession().getAttribute(Constant.session_valcode);
			if(object==null){
				message = "验证码已失效！请重新发送！";
			}else{
				ValcodeModel valcodeModel = (ValcodeModel) object;
				if(valcodeModel.getUser().getId()==user.getId()){
					if(valcode.equals(valcodeModel.getValcode())){
						String newPassword = CommonUtil.getRandom().toString();
						user.setPassword(newPassword);
						userService.updateByPrimaryKey(user);
						success = 1;
						message = "操作成功！新密码："+newPassword;
						request.getSession().setAttribute(Constant.session_valcode, null);
					}else{
						message = "验证码错误！";
					}
				}
			}
		}
		resultMap.put("success",success);
		resultMap.put("message",message);
		return resultMap;
	}
	
}