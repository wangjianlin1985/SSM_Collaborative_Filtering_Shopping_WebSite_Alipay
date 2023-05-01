package com.webshoprsmex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.OrderitemMapper;
import com.webshoprsmex.model.Orderitem;

/**
 * 订单详情service业务类，继承泛型基础service
 * 进行复杂业务处理
 */
@Service
public class OrderitemService extends BaseService<Orderitem>{
	
	@Autowired
	private OrderitemMapper orderitemMapper;//注入订单详情mapper

	/**
     * 统计每种商品类型下的销量
     */
	public List<Map<String, Object>> findTypeidCount(){
    	return orderitemMapper.findTypeidCount();
    }
	
	/**
     * 统计近七天营业额
     */
    public List<Map<String, Object>> findOrderPrice(){
    	return orderitemMapper.findOrderPrice();
    }
	
}
