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
import com.webshoprsmex.model.Scorerecord;
import com.webshoprsmex.service.ScorerecordService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.DateUtil;

/**
 * 前台评分记录控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/scorerecord")
public class FrontScorerecordController extends BaseController{

	@Autowired
	private ScorerecordService scorerecordService;//注入评分记录业务类
	
	/**
	 * 带参数分页查询
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.add(new Object[]{"s.userid","=",getCurrentUser().getId()});
		List<Scorerecord> list = scorerecordService.findJoin(params);
		PageInfo<Scorerecord> pageInfo = new PageInfo<Scorerecord>(list);
		map.put("pageBean", pageInfo);
		return "front/scorerecord/list";
	}
	
	/**
	 * 添加评分
	 * @param scorerecord
	 * @return
	 */
	@RequestMapping("/saveOrUpdate")
	@ResponseBody
	public Object saveOrUpdate(Scorerecord scorerecord){
		int success = 0;
		//当前登录用户对当前商品的评分
		params.add(new Object[]{"userid","=",getCurrentUser().getId()});
		params.add(new Object[]{"itemid","=",scorerecord.getItemid()});
		List<Scorerecord> scorerecordList = scorerecordService.find(params);
		if(scorerecordList!=null && scorerecordList.size()>0){
			Scorerecord scorerecordTemp = scorerecordList.get(0);
			scorerecordTemp.setScore(scorerecord.getScore());
			success = scorerecordService.updateByPrimaryKeySelective(scorerecordTemp);
		}else{
			scorerecord.setUserid(getCurrentUser().getId());
			scorerecord.setCreatetime(DateUtil.getCurrentDate());
			scorerecordService.insertSelective(scorerecord);
			success = 1;
		}
		resultMap.put("success",success);//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url","reload");
		return resultMap;
	}
	
	/**
	 * 删除
	 * @param scorerecord
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Scorerecord scorerecord){
		resultMap.put("success",scorerecordService.deleteByPrimaryKey(scorerecord.getId()));//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url","reload");
		return resultMap;
	}
	
}