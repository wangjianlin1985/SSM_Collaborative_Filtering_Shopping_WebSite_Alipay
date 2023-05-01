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
import com.webshoprsmex.model.Type;
import com.webshoprsmex.service.TypeService;
import com.webshoprsmex.util.Constant;

/**
 * 后台管理员商品类型控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "admin/type")
public class AdminTypeController extends BaseController{

	@Autowired
	private TypeService typeService;//注入商品类型业务类
	
	/**
	 * 带参数分页查询
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize,
			String typename){
		//分页
		PageHelper.startPage(pageNum, pageSize);
		//查询参数
		params.add(new Object[]{"typename","like",typename});
		List<Type> list = typeService.find(params);
		PageInfo<Type> pageInfo = new PageInfo<Type>(list);
		map.put("pageBean", pageInfo);
		map.put("typename", typename);
		return "admin/type/list";
	}
	
	/**
	 * 跳转到添加或者修改页面
	 * @return
	 */
	@RequestMapping("/addOrUpdate")
	public String addOrUpdate(Integer typeid){
		if(typeid!=null){
			//查询
			Type cType = typeService.selectByPrimaryKey(typeid);
			request.setAttribute("type",cType);
		}
		return "admin/type/edit";
	}
	
	/**
	 * 添加或者修改
	 * @return
	 */
	@RequestMapping("/doAddOrUpdate")
	@ResponseBody
	public Object doAddOrUpdate(Type type){
		int success = 0;
		if(type.getId()!=null){
			//更新
			success = typeService.updateByPrimaryKeySelective(type);
		}else{
			success = typeService.insertSelective(type);//保存
		}
		resultMap.put("success",success);
		resultMap.put("url","admin/type/list");
		return resultMap;
	}
	
	/**
	 * 删除
	 * @param type
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Type type){
		resultMap.put("success",typeService.deleteById(type.getId()));//返回给页面的响应数据，如果结果大于或者等于1是操作成功，反之操作失败
		//返回给页面的响应数据，不论操作是否成功，只要返回url变量数据，就会再次请求这个url变量路径，reload是刷新当前页面
		resultMap.put("url","reload");
		return resultMap;
	}
	
}