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
import com.webshoprsmex.model.Item;
import com.webshoprsmex.model.Type;
import com.webshoprsmex.service.ItemService;
import com.webshoprsmex.service.TypeService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.DateUtil;

/**
 * 后台管理员商品控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/item")
public class AdminItemController extends BaseController{

	@Autowired
	private ItemService itemService;//注入商品业务类
	@Autowired
	private TypeService typeService;//注入商品类型业务类
	
	/**
	 * 分页带参数查询
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			String itemname){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数商品名称
		params.add(new Object[]{"i.itemname","like",itemname});
		List<Item> list = itemService.findJoin(params);
		PageInfo<Item> pageInfo = new PageInfo<Item>(list);
		map.put("pageBean", pageInfo);
		map.put("itemname", itemname);
		return "admin/item/list";
	}
	
	/**
	 * 跳转到添加或者修改页面
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	public String addOrUpdate(Integer itemid){
		if(itemid!=null){
			//根据主键查找
			Item cItem = itemService.selectByPrimaryKey(itemid);
			request.setAttribute("item",cItem);
		}
		//查询所有商品类型
		List<Type> typeList = typeService.find(null);
		request.setAttribute("typeList",typeList);
		return "admin/item/edit";
	}
	
	/**
	 * 添加或者修改
	 * @return
	 */
	@RequestMapping("/doAddOrUpdate")
	@ResponseBody
	public Object doAddOrUpdate(Item item){
		int success = 0;
		if(item.getId()!=null){
			//更新
			success = itemService.updateByPrimaryKeySelective(item);
		}else{
			item.setCreatetime(DateUtil.getCurrentDate());
			success = itemService.insertSelective(item);//保存
		}
		resultMap.put("success",success);//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径
		resultMap.put("url","admin/item/list");
		return resultMap;
	}
	
	/**
	 * 删除
	 * @param item
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Item item){
		resultMap.put("success",itemService.deleteById(item.getId()));//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url","reload");
		return resultMap;
	}
	
}