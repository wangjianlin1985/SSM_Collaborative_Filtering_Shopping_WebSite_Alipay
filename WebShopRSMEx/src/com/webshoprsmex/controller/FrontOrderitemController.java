package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.webshoprsmex.model.Order;
import com.webshoprsmex.model.Orderitem;
import com.webshoprsmex.service.OrderService;
import com.webshoprsmex.service.OrderitemService;

/**
 * 前台订单详情控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/orderitem")
public class FrontOrderitemController extends BaseController{

	@Autowired
	private OrderService orderService;//注入订单业务类	
	@Autowired
	private OrderitemService orderitemService;//注入订单详情业务类
	
	/**
	 * 查询
	 * @param orderid 订单id
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Integer orderid,Model model){
		params.add(new Object[]{"oi.orderid","=",orderid});
		List<Orderitem> orderitemList = orderitemService.findJoin(params);
		request.setAttribute("orderitemList",orderitemList);
		Order order = orderService.selectByPrimaryKey(orderid);
		model.addAttribute("order",order);
		return "front/orderitem/list";
	}
	
}