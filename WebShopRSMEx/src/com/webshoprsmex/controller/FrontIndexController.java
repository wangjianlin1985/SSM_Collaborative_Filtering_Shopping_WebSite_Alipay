package com.webshoprsmex.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.mahout.cf.taste.model.DataModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.model.Item;
import com.webshoprsmex.model.Orderitem;
import com.webshoprsmex.model.Scorerecord;
import com.webshoprsmex.model.Type;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.ItemService;
import com.webshoprsmex.service.OrderitemService;
import com.webshoprsmex.service.ScorerecordService;
import com.webshoprsmex.service.TypeService;
import com.webshoprsmex.util.cf.CFDataModelUtil;
import com.webshoprsmex.util.cf.CFUtil;

/**
 * 前台首页控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = {"front","/"})
public class FrontIndexController extends BaseController{
	
	@Autowired
	private ItemService itemService;//注入商品业务类
	@Autowired
	private TypeService typeService;//注入商品类型业务类
	@Autowired
	private ScorerecordService scorerecordService;//注入评分记录业务类
	@Autowired
	private OrderitemService orderitemService;//注入订单详情业务类

	/**
	 * 前台首页
	 * @return
	 */
	@RequestMapping({"/index","/"})
	public String index(ModelMap map){
		//分页
		PageHelper.startPage(1, 10);
		//查询
		List<Item> list = itemService.find(null);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		//查询所有类型
		List<Type> typeList = typeService.find(null);
		map.put("pageBean", pageInfo);
		map.put("typeList", typeList);
		//推荐
		recommend();
		return "front/index/index";
	}
	
	/**
	 * 游客展示热点推荐（根据商品被收藏数量降序推荐）
	 * 登录用户同时进行
	 * 基于用户的协同过滤推荐算法进行推荐（根据评分数据）
	 * 和 基于项目（商品）的协同过滤推荐算法进行推荐（根据购买数据）
	 */
	private void recommend(){
		System.out.println("***热点推荐开始***");
		List<Item> hotItems = itemService.findHot(null);
		request.setAttribute("hotItems", hotItems);
		System.out.println("***热点推荐结束***");
		//获取当前登录用户对象
		User cUser = getCurrentUser();
		if(cUser!=null){
			//定义个性化推荐结果集合
			List<Item> recommendItems = new ArrayList<Item>();
			//定义个性化推荐结果
			String cfItemIdsFinal = "";
			System.out.println("***基于用户的协同过滤推荐算法开始***");
			//用户-商品评分记录矩阵工具类
			CFDataModelUtil cfDataModelUtil = new CFDataModelUtil();
			//实例化协同过滤推荐算法工具类对象
			CFUtil cfUtil = new CFUtil();
			System.out.println("查询所有评分记录");
			//查询所有评分记录
			List<Scorerecord> scorerecordList = scorerecordService.find(null);
			//获取用户-商品评分矩阵
			DataModel model = cfDataModelUtil.getItemScoreDadaModel(scorerecordList, cUser);
			//调用基于用户的协同过滤推荐算法方法
			String cfItemIds = cfUtil.cfByScoreBaseUser(cUser, model);
			//判断是否存在推荐结果
			if(cfItemIds!=null && !cfItemIds.equals("")){
				cfItemIdsFinal = cfItemIds;
			}
			System.out.println("***基于用户的协同过滤推荐算法结束***");
			System.out.println("***基于项目（商品）的协同过滤推荐算法开始***");
			System.out.println("查询所有订单详情记录");
			//查询所有订单详情记录
			List<Orderitem> orderitemList = orderitemService.find(null);
			//调用基于项目（商品）的协同过滤推荐算法方法
			String cfItemIds2 = cfUtil.cfByOrderitemBaseUser(cUser, orderitemList);
			//判断是否存在推荐结果
			if(cfItemIds2!=null && !cfItemIds2.equals("")){
				if(cfItemIdsFinal!=null && !cfItemIdsFinal.equals("")){
					cfItemIdsFinal+=","+cfItemIds2;
				}else{
					cfItemIdsFinal = cfItemIds2;
				}
			}
			System.out.println("***基于项目（商品）的协同过滤推荐算法结束***");
			//将两次推荐结果查询出来
			if(cfItemIdsFinal!=null && !cfItemIdsFinal.equals("")){
				params.clear();
				params.add(new Object[]{"id","in",cfItemIdsFinal});
				recommendItems = itemService.find(params);
			}
			request.setAttribute("recommendItems", recommendItems);
		}
	}
	
}