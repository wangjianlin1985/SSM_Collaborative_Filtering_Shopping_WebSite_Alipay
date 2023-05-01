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
 * 前台支付记录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/payrecord")
public class FrontPayrecordController extends BaseController{

	@Autowired
	private PayrecordService payrecordService;//注入支付记录业务类	
	
	/**
	 * 分页带参数查询，支付记录
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		//查询参数集合
		params.add(new Object[]{"u.id","=",getCurrentUser().getId()});
		List<Payrecord> list = payrecordService.findJoin(params);
		PageInfo<Payrecord> pageInfo = new PageInfo<Payrecord>(list);
		map.put("pageBean", pageInfo);
		return "front/payrecord/list";
	}
	
	/**
	 * 支付记录详情
	 * @return
	 */
	@RequestMapping("view")
	public String view(String payrecordid){
		params.add(new Object[]{"c.id","=",Integer.parseInt(payrecordid)});
		params.add(new Object[]{"u.id","=",getCurrentUser().getId()});
		Payrecord payrecord = payrecordService.findFirstJoin(params);
		request.setAttribute("payrecord",payrecord);
		return "front/payrecord/view";
	}
	
}