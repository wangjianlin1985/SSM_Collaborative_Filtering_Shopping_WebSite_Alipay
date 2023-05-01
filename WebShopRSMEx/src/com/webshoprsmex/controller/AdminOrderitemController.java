package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webshoprsmex.model.Order;
import com.webshoprsmex.model.Orderitem;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.OrderService;
import com.webshoprsmex.service.OrderitemService;
import com.webshoprsmex.service.UserService;

/**
 * 后台订单详情控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/orderitem")
public class AdminOrderitemController extends BaseController{

	@Autowired
	private OrderService orderService;//注入订单业务类
	@Autowired
	private OrderitemService orderitemService ;//注入订单详情业务类
	@Autowired
	private UserService userService ;//注入用户业务类
	
	/**
	 * 查询
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,Integer orderid){
		params.add(new Object[]{"oi.orderid","=",orderid});
		List<Orderitem> orderitemList = orderitemService.findJoin(params);
		Order order = orderService.selectByPrimaryKey(orderid);
		User user = userService.selectByPrimaryKey(order.getUserid());
		map.put("orderitemList", orderitemList);
		map.put("order", order);
		map.put("user", user);
		return "admin/orderitem/list";
	}
	
}