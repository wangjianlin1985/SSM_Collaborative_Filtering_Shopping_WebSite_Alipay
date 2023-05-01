package com.webshoprsmex.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.webshoprsmex.model.User;
import com.webshoprsmex.service.UserService;
import com.webshoprsmex.util.CommonUtil;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.MailUtil;
import com.webshoprsmex.util.ValcodeModel;

/**
 * 公共控制器（包含文件上传等功能）
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"front","admin"})
public class CommonController extends BaseController{
	
	@Autowired
	private UserService userService;//注入用户业务类
	
	/**
	 * 文件上传
	 * @return
	 */
	@RequestMapping("/upload")
	@ResponseBody
	public Object upload(@RequestParam("file") CommonsMultipartFile file){
		int success = 0;//上传失败
        System.out.println("fileName："+file.getOriginalFilename());
        String fileName = file.getOriginalFilename();
        String newFileName = "";
        //获取要保存文件夹的物理路径(绝对路径)
        //String realPath = Constant.applicationUploadRealPath; 
        String realPath = "D:\\毕业系统调试区\\基于协同过滤个性化商城推荐系统\\WebShopRSMEx\\WebContent\\WebShopRSMExUpload\\upload";
        File cFile = new File(realPath);
        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，
        //包括所有必需但不存在的父目录。
        if(!cFile.exists())
        	cFile.mkdirs();
        try {
        	newFileName = System.currentTimeMillis()+"_"+CommonUtil.getRandom()+
        			fileName.substring(fileName.lastIndexOf( "." ));
            System.out.println("newFileName："+newFileName);
        	//保存文件
            file.transferTo(new File(cFile,newFileName));
            success = 1;//上传成功
        } catch (IOException e) {
        	System.out.println("文件上传异常！");
            e.printStackTrace();
        }
		resultMap.put("newFileName",newFileName);//返回给页面的响应数据，上传文件的新名称
		resultMap.put("success",success);//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		return resultMap;
	}
	
	/**
	 * 获取邮箱验证码
	 * @return
	 */
	@RequestMapping("/doValcode")
	@ResponseBody
	public Object doValcode(){
		int success = 0;
		String message = "";
		String username = request.getParameter("username");
		//查询参数
		params.add(new Object[]{"username","=","'"+username+"'"});
		User user = userService.findFirst(params);
		if(user==null){//用户名不存在
			message = "用户名不存在！";
		}else{
			String email = user.getEmail();
			if(email==null || email.equals("")){
				message = "邮箱为空！请联系系统管理员！";
			}else{
				//发送验证码
				String valcode = CommonUtil.getRandom().toString();
				MailUtil mailUtil = new MailUtil();
				//设置要发送的邮箱,输入要发送的邮箱的账号
				mailUtil.setReceiveMailAccount(email);
				mailUtil.setInfo(valcode);
				try {
					mailUtil.Send();
					success = 1;
					message = "验证码已发送！邮箱："+email;
					//将验证码保存在session中
					ValcodeModel valcodeModel = new ValcodeModel(user,valcode);
					request.getSession().setAttribute(Constant.session_valcode, valcodeModel);
				} catch (Exception e) {
					e.printStackTrace();
					message = "操作失败！";
				}
			}
		}
		resultMap.put("success",success);
		resultMap.put("message",message);
		return resultMap;
	}
	
}