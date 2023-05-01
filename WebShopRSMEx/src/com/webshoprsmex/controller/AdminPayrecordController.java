package com.webshoprsmex.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.model.Payrecord;
import com.webshoprsmex.service.PayrecordService;
import com.webshoprsmex.util.Constant;

/**
 * 后台管理员支付记录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/payrecord")
public class AdminPayrecordController extends BaseController{

	@Autowired
	private PayrecordService payrecordService;//注入支付记录业务类
	
	/**
	 * 带参数分页查询
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			Integer orderid,String username){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数集合
		params.add(new Object[]{"c.orderid","=",orderid});
		params.add(new Object[]{"u.username","like",username});
		List<Payrecord> list = payrecordService.findJoin(params);
		PageInfo<Payrecord> pageInfo = new PageInfo<Payrecord>(list);
		map.put("pageBean", pageInfo);
		map.put("orderid", orderid);
		map.put("username", username);
		return "admin/payrecord/list";
	}
	
}