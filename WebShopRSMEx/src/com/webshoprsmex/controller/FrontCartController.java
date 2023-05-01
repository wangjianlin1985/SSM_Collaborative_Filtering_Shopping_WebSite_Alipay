package com.webshoprsmex.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.webshoprsmex.model.Cart;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.CartService;

/**
 * 前台购物车控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/cart")
public class FrontCartController extends BaseController{

	@Autowired
	private CartService cartService;//购物车业务类
	
	/**
	 * 获取当前用户购物车商品数量，ajax异步请求，返回json格式数据
	 * @return
	 */
	@RequestMapping("/getCartItemCount")
	@ResponseBody
	public Object getCartItemCount(){
		//查询参数
		params.add(new Object[]{"userid","=",getCurrentUser().getId()});
		long cartItemCount = cartService.findCount(params);
		resultMap.put("cartItemCount",cartItemCount);
		return resultMap;
	} 
	
	/**
	 * 购物车列表
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model){
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{"c.userid","=",getCurrentUser().getId()});
		List<Cart> cartList = cartService.findJoin(params);
		model.addAttribute("cartList", cartList);
		return "front/cart/list";
	}
	
	/**
	 * 添加到购物车，ajax异步请求，返回json格式数据
	 * @param itemid 商品id
	 * @param count 商品数量
	 * @return
	 */
	@RequestMapping("/add")
	@ResponseBody
	public Object add(Integer itemid,Integer count){
		int success = 0;
		count = count==null?1:count;
		User cUser = getCurrentUser();
		Cart cart = new Cart();
		cart.setItemid(itemid);
		cart.setUserid(cUser.getId());
		//查询当前用户数据库中的购物车商品
		Cart searchCart = null;
		List<Object[]> params = new ArrayList<Object[]>();
		params.add(new Object[]{"itemid","=",itemid});
		params.add(new Object[]{"userid","=",cUser.getId()});
		List<Cart> cartList = cartService.find(params);
		if(cartList!=null && cartList.size()>0){
			searchCart = cartList.get(0);
		}
		if(searchCart==null){
			cart.setCount(count);
			success = cartService.insertSelective(cart);
		}else{
			searchCart.setCount(searchCart.getCount()+count);
			success = cartService.updateByPrimaryKeySelective(searchCart);
		}
		resultMap.put("success", success);
		resultMap.put("url", "reload");//reload是刷新当前页面
		return resultMap;
	}
	
	/**
	 * 编辑购物车，ajax异步请求，返回json格式数据
	 * @param cartid 购物车id
	 * @param count 商品数量
	 * @return
	 */
	@RequestMapping("/edit")
	@ResponseBody
	public Object edit(Integer cartid,Integer count){
		Cart searchCart = cartService.selectByPrimaryKey(cartid);
		int tempCount = searchCart.getCount() + count;
		if(tempCount > 0){
			searchCart.setCount(tempCount);
			cartService.updateByPrimaryKeySelective(searchCart);
		}
		resultMap.put("success", 1);
		resultMap.put("url", "reload");//reload是刷新当前页面
		return resultMap;
	}
	
	/**
	 * 删除购物车，ajax异步请求，返回json格式数据
	 * @param cartid 购物车id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer cartid){
		int success = cartService.deleteByPrimaryKey(cartid);
		resultMap.put("success", success);
		resultMap.put("url", "reload");//reload是刷新当前页面
		return resultMap;
	}
	
}