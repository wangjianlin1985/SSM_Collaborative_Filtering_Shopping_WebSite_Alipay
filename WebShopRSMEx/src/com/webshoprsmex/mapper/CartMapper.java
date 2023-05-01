package com.webshoprsmex.mapper;

import java.util.List;

import com.webshoprsmex.model.Cart;

/**
 * 购物车mapper，继承基础mapper，进行简单的增删改查操作
 * 与同名xml文件相映射
 */
public interface CartMapper extends BaseMapper<Cart>{
    
	/**
	 * 清空购物车
	 * @param cartList
	 */
	void deleteBatch(List<Cart> cartList);
	
}