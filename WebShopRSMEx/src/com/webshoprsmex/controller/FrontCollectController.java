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
import com.webshoprsmex.model.Collect;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.CollectService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.DateUtil;

/**
 * 前台收藏记录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/collect")
public class FrontCollectController extends BaseController{

	@Autowired
	private CollectService collectService;//注入收藏记录业务类
	
	/**
	 * 分页带参数查询，我的收藏记录
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize){
		//查询参数集合
		User cUser = getCurrentUser();
		PageHelper.startPage(pageNum, pageSize);
		params.add(new Object[]{"c.userid","=",cUser.getId()});
		List<Collect> list = collectService.findJoin(params);
		PageInfo<Collect> pageInfo = new PageInfo<Collect>(list);
		map.put("pageBean", pageInfo);
		return "front/collect/list";
	}
	
	/**
	 * 添加/取消收藏，ajax异步请求，返回json格式数据
	 * @param collect 收藏对象
	 * @return
	 */
	@RequestMapping("/save")
	@ResponseBody
	public Object save(Collect collect){
		int success = 0;
		//当前登录用户
		User cUser = getCurrentUser();
		params.add(new Object[]{"userid","=",cUser.getId()});
		params.add(new Object[]{"itemid","=",collect.getItemid()});
		Collect collect2 = collectService.findFirst(params);
		if(collect2==null){//添加
			collect.setCreatetime(DateUtil.getCurrentDate());
			collect.setUserid(cUser.getId());
			success = collectService.insertSelective(collect);
		}else{//取消
			success = collectService.deleteByPrimaryKey(collect2.getId());
		}
		resultMap.put("success",success);
		resultMap.put("url","reload");
		return resultMap;
	}
	
}