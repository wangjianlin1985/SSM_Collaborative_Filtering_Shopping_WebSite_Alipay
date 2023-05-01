package com.webshoprsmex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.webshoprsmex.model.Admin;
import com.webshoprsmex.service.AdminService;
import com.webshoprsmex.service.CollectService;
import com.webshoprsmex.service.CommentService;
import com.webshoprsmex.service.ItemService;
import com.webshoprsmex.service.OrderService;
import com.webshoprsmex.service.OrderitemService;
import com.webshoprsmex.service.ScorerecordService;
import com.webshoprsmex.service.TypeService;
import com.webshoprsmex.service.UserService;
import com.webshoprsmex.util.Constant;

/**
 * 后台管理员首页控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"admin"})
public class AdminIndexController extends BaseController{

	@Autowired
	private AdminService adminService;//注入管理员业务类
	@Autowired
	private UserService userService;//注入用户业务类
	@Autowired
	private TypeService typeService;//注入商品类型业务类
	@Autowired
	private ItemService itemService;//注入商品业务类
	@Autowired
	private ScorerecordService scorerecordService;//注入评分业务类
	@Autowired
	private CollectService collectService;//注入收藏业务类
	@Autowired
	private CommentService commentService;//注入评论业务类
	@Autowired
	private OrderService orderService;//注入订单业务类
	@Autowired
	private OrderitemService orderitemService;//注入订单详情业务类
	
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping({"","/"})
	public String index(){
		return "admin/common/index";
	}
	
	/**
	 * 首页内容
	 * @return
	 */
	@RequestMapping("/content")
	public String content(ModelMap map){
		Long userCount = userService.findCount(null);//用户数量
		Long itemCount = itemService.findCount(null);//商品数量
		Long typeCount = typeService.findCount(null);//商品类型数量
		Long scorerecordCount = scorerecordService.findCount(null);//评分记录数量
		Long collectCount = collectService.findCount(null);//收藏记录数量
		Long commentCount = commentService.findCount(null);//评论记录数量
		Long orderCount = orderService.findCount(null);//订单数量
		map.put("userCount",userCount);
		map.put("itemCount",itemCount);
		map.put("typeCount",typeCount);
		map.put("scorerecordCount",scorerecordCount);
		map.put("collectCount",collectCount);
		map.put("commentCount",commentCount);
		map.put("orderCount",orderCount);
		//统计每种菜品类型下的销量，饼状图
		List<Map<String, Object>> typeidCountMap = orderitemService.findTypeidCount();
		List<Object[]> typeOrderList = new ArrayList<Object[]>();
		if(typeidCountMap!=null && typeidCountMap.size()>0){
			for(int i=0;i<typeidCountMap.size();i++){
				Map<String, Object> typeidCount = typeidCountMap.get(i);
				Object[] object = new Object[2];
				object[0] = typeidCount.get("count");
				object[1] = typeidCount.get("typename");
				typeOrderList.add(object);
			}
		}
		map.put("typeOrderList",JSONArray.toJSON(typeOrderList));
		//统计近七天营业额，柱状图
		List<Map<String, Object>> orderPriceMap = orderitemService.findOrderPrice();
		List<Object[]> orderPriceList = new ArrayList<Object[]>();
		if(orderPriceMap!=null && orderPriceMap.size()>0){
			for(int i=0;i<orderPriceMap.size();i++){
				Map<String, Object> orderPrice = orderPriceMap.get(i);
				Object[] object = new Object[2];
				object[0] = orderPrice.get("price");
				object[1] = orderPrice.get("ordertime").toString();
				orderPriceList.add(object);
			}
		}
		map.put("orderPriceList",JSONArray.toJSON(orderPriceList));
		return "admin/common/index_content";
	}
	
	/**
	 * 跳转到后台登录页面
	 * @return
	 */
	@RequestMapping("/login")
	public String login(){
		return "admin/common/login";
	}
	
	/**
	 * 登录
	 * @return
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public Object doLogin(Admin admin){
		int success = 0;
		params.add(new Object[]{"username","=","'"+admin.getUsername()+"'"});
		params.add(new Object[]{"password","=","'"+admin.getPassword()+"'"});
		//查找
		List<Admin> adminList = adminService.find(params);
		String url = "";
		if(adminList==null || adminList.size()==0){//用户名或密码错误
			success = 0;
		}else{
			success = 1;
			url = "admin";
			//将管理员信息保存在session中
			request.getSession().setAttribute(Constant.session_admin,adminList.get(0));
		}
		resultMap.put("success",success);//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url",url);
		return resultMap;
	}
	
	/**
	 * 注销
	 * @return
	 */
	@RequestMapping("/quit")
	public String quit(){
		request.getSession().setAttribute(Constant.session_admin,null);
		return "redirect:/admin/login";
	}
	
}