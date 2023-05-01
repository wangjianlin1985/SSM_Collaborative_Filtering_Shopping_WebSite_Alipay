package com.webshoprsmex.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.model.Collect;
import com.webshoprsmex.model.Comment;
import com.webshoprsmex.model.Item;
import com.webshoprsmex.model.Scorerecord;
import com.webshoprsmex.model.Type;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.CollectService;
import com.webshoprsmex.service.CommentService;
import com.webshoprsmex.service.ItemService;
import com.webshoprsmex.service.OrderitemService;
import com.webshoprsmex.service.ScorerecordService;
import com.webshoprsmex.service.TypeService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.CurScoreAnalysis;
import com.webshoprsmex.util.ScoreAnalysis;

/**
 * 前台商品控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/item")
public class FrontItemController extends BaseController{
	
	@Autowired
	private ItemService itemService;//注入商品业务类
	@Autowired
	private TypeService typeService;//注入商品类型业务类
	@Autowired
	private ScorerecordService scorerecordService;//注入评分记录业务类
	@Autowired
	private CollectService collectService;//注入收藏记录业务类
	@Autowired
	private CommentService commentService;//注入评论记录业务类
	@Autowired
	private OrderitemService orderitemService;//注入订单详情业务类

	/**
	 * 商品列表（带参数分页查询）
	 * @return
	 */
	@RequestMapping({"/list","/"})
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			String itemname,Integer typeid){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.add(new Object[]{"itemname","like",itemname});
		params.add(new Object[]{"typeid","=",typeid});
		//查询
		List<Item> list = itemService.find(params);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		//查询所有类型
		List<Type> typeList = typeService.find(null);
		map.put("pageBean", pageInfo);
		map.put("typeList", typeList);
		map.put("itemname", itemname);
		map.put("typeid", typeid);
		return "front/item/list";
	}
	
	/**
	 * 商品详情
	 * @param itemid
	 * @return
	 */
	@RequestMapping("/view")
	public String view(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = "6") int pageSize,
			String itemid){
		//查询当前商品
		Item item = itemService.selectByPrimaryKey(Integer.parseInt(itemid));
		request.setAttribute("item",item);
		//当前商品类型
		Type type = typeService.selectByPrimaryKey(item.getTypeid());
		request.setAttribute("type",type);
		//分页查询商品评论
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.clear();
		params.add(new Object[]{"c.itemid","=",itemid});
		//查询评论
		List<Comment> commentList = commentService.findJoin(params);
		PageInfo<Comment> pageInfo = new PageInfo<Comment>(commentList);
		map.put("pageBean", pageInfo);
		//被收藏数量
		//查询参数
		params.clear();
		params.add(new Object[]{"itemid","=",itemid});
		long collectCount = collectService.findCount(params);
		map.put("collectCount", collectCount);
		//被购买数量
		params.clear();
		params.add(new Object[]{"itemid","=",itemid});
		long orderitemCount = orderitemService.findCount(params);
		map.put("orderitemCount", orderitemCount);
		//评分分析
		ScoreAnalysis scoreAnalysis = new ScoreAnalysis();
		//查找商品的总评分和评分数量
		Map<String,Object> scoreCountMap = scorerecordService.findCountEx(item.getId(),0);
		Object[] scoreCountObjectArray = new Object[]{0,0};
		if(scoreCountMap!=null && scoreCountMap.size()>0){
			if(scoreCountMap.get("count")!=null && !scoreCountMap.get("count").equals("")){
				scoreCountObjectArray[0] = scoreCountMap.get("count");
			}
			if(scoreCountMap.get("totalscore")!=null && !scoreCountMap.get("totalscore").equals("")){
				scoreCountObjectArray[1] = scoreCountMap.get("totalscore");
			}
		}
		//评分数量
		scoreAnalysis.setScoreCount(Integer.parseInt(scoreCountObjectArray[0].toString()));
		//总评分
		scoreAnalysis.setSumScore(Integer.parseInt(scoreCountObjectArray[1].toString()));
		//具体评分分析集合
		List<CurScoreAnalysis> curScoreAnalysisList = new ArrayList<CurScoreAnalysis>();
		//遍历评分倒序
		for(int i=5;i>0;i--){
			CurScoreAnalysis curScoreAnalysis = new CurScoreAnalysis();
			Map<String,Object> scoreCountMapTemp = scorerecordService.findCountEx(item.getId(),i);
			Object[] scoreCountObjectArrayTemp = new Object[]{0,0};
			if(scoreCountMapTemp!=null && scoreCountMapTemp.size()>0){
				if(scoreCountMapTemp.get("count")!=null && !scoreCountMapTemp.get("count").equals("")){
					scoreCountObjectArrayTemp[0] = scoreCountMapTemp.get("count");
				}
				if(scoreCountMapTemp.get("totalscore")!=null && !scoreCountMapTemp.get("totalscore").equals("")){
					scoreCountObjectArrayTemp[1] = scoreCountMapTemp.get("totalscore");
				}
			}
			curScoreAnalysis.setCurScore(i);
			curScoreAnalysis.setScoreCount(scoreAnalysis.getScoreCount());
			curScoreAnalysis.setPercent(Integer.parseInt(scoreCountObjectArrayTemp[0].toString()));
			curScoreAnalysisList.add(curScoreAnalysis);
		}
		scoreAnalysis.setCurScoreAnalysisList(curScoreAnalysisList);
		request.setAttribute("scoreAnalysis",scoreAnalysis);
		//当前登录用户
		User cUser = getCurrentUser();
		if(cUser!=null){
			//查询当前登录用户对当前商品的评分
			params.clear();
			params.add(new Object[]{"userid","=",cUser.getId()});
			params.add(new Object[]{"itemid","=",item.getId()});
			Scorerecord scorerecord = scorerecordService.findFirst(params);
			request.setAttribute("scorerecord",scorerecord);
			//查询当前登录用户对当前商品的收藏
			params.clear();
			params.add(new Object[]{"userid","=",cUser.getId()});
			params.add(new Object[]{"itemid","=",item.getId()});
			Collect collect = collectService.findFirst(params);
			request.setAttribute("collect",collect);
		}
		//相关推荐
		List<Item> relateItems = itemService.findByTypeid(item.getId(), item.getTypeid());
		request.setAttribute("relateItems",relateItems);
		return "front/item/view";
	}

}