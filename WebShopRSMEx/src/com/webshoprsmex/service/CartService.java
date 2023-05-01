package com.webshoprsmex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.CartMapper;
import com.webshoprsmex.model.Cart;

/**
 * 购物车service业务类，继承泛型基础service
 * 进行复杂业务处理
 */
@Service
public class CartService extends BaseService<Cart>{
	
	@Autowired
	private CartMapper cartMapper;//注入购物车mapper

	/**
	 * 清空购物车
	 * @param cartList
	 */
	public void deleteBatch(List<Cart> cartList){
		cartMapper.deleteBatch(cartList);
	}
	
}
