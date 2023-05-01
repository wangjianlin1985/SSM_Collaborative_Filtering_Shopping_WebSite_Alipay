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
import com.webshoprsmex.model.Refundrecord;
import com.webshoprsmex.service.RefundrecordService;
import com.webshoprsmex.util.Constant;

/**
 * 前台退款记录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/refundrecord")
public class FrontRefundrecordController extends BaseController{

	@Autowired
	private RefundrecordService refundrecordService;//注入退款记录业务类	
	
	/**
	 * 分页带参数查询，退款记录
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
		List<Refundrecord> list = refundrecordService.findJoin(params);
		PageInfo<Refundrecord> pageInfo = new PageInfo<Refundrecord>(list);
		map.put("pageBean", pageInfo);
		return "front/refundrecord/list";
	}
	
	/**
	 * 支付记录详情
	 * @return
	 */
	@RequestMapping("view")
	public String view(String refundrecordid){
		params.add(new Object[]{"c.id","=",Integer.parseInt(refundrecordid)});
		params.add(new Object[]{"u.id","=",getCurrentUser().getId()});
		Refundrecord refundrecord = refundrecordService.findFirstJoin(params);
		request.setAttribute("refundrecord",refundrecord);
		return "front/refundrecord/view";
	}
	
}