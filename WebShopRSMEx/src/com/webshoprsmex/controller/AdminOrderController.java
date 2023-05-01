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
import com.webshoprsmex.model.Order;
import com.webshoprsmex.service.OrderService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.DateUtil;

/**
 * 后台管理员订单控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/order")
public class AdminOrderController extends BaseController{

	@Autowired
	private OrderService orderService;//注入订单业务类
	
	/**
	 * 分页查询订单
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,String username,Integer orderid,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			Integer state){
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.add(new Object[]{"o.state","=",state});
		params.add(new Object[]{"u.username","like",username});
		params.add(new Object[]{"o.id","=",orderid});
		List<Order> list = orderService.findJoin(params);
		PageInfo<Order> pageInfo = new PageInfo<Order>(list);
		map.put("pageBean", pageInfo);
		map.put("state", state);
		map.put("username", username);
		map.put("orderid", orderid);
		return "admin/order/list";
	}
	
	/**
	 * 发货，ajax异步请求，返回json格式数据
	 * @param orderid
	 * @return
	 */
	@RequestMapping("/send")
	@ResponseBody
	public Object send(Integer orderid){
		int success = 0;
		Order order = orderService.selectByPrimaryKey(orderid);
		if(order!=null && order.getState()==2){
			order.setState(3);
			order.setSendtime(DateUtil.getCurrentDate());
			success = orderService.updateByPrimaryKeySelective(order);
		}
		resultMap.put("success",success);
		resultMap.put("url","/admin/order/list");
		return resultMap;
	}
	
	/**
	 * 删除，ajax异步请求，返回json格式数据
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer id){
		int success = orderService.deleteByPrimaryKey(id);
		resultMap.put("success",success);
		resultMap.put("url","reload");//是刷新当前页面
		return resultMap;
	}
	
}