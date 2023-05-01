package com.webshoprsmex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.ItemMapper;
import com.webshoprsmex.mapper.OrderMapper;
import com.webshoprsmex.mapper.OrderitemMapper;
import com.webshoprsmex.mapper.TypeMapper;
import com.webshoprsmex.model.Item;
import com.webshoprsmex.model.Order;
import com.webshoprsmex.model.Orderitem;
import com.webshoprsmex.model.Type;

/**
 * 商品类型service业务类，继承泛型基础service
 * 进行复杂业务处理
 */
@Service
public class TypeService extends BaseService<Type>{

	@Autowired
	private TypeMapper typeMapper;//注入商品类型mapper
	@Autowired
	private ItemMapper itemMapper;//注入商品mapper
	@Autowired
	private OrderMapper orderMapper;//注入订单mapper
	@Autowired
	private OrderitemMapper orderitemMapper;//注入订单详情mapper
	
	/**
     * 根据主键删除
     * @return
     */
    public int deleteById(Integer id){
    	//查询参数集合
		List<Object[]> params = new ArrayList<Object[]>();
		//删除商品
		params.clear();
		params.add(new Object[]{"typeid","=",id});
		List<Item> itemList = itemMapper.find(params);
		for(Item item:itemList){
			//删除订单、订单详情
			//先查询订单详情
			params.clear();
			params.add(new Object[]{"itemid","=",item.getId()});
			List<Orderitem> orderitemList = orderitemMapper.find(params);
			for(Orderitem orderitem:orderitemList){
				//删除订单
				params.clear();
				params.add(new Object[]{"id","=",orderitem.getOrderid()});
				List<Order> orderList = orderMapper.find(params);
				for(Order order:orderList){
					orderMapper.deleteByPrimaryKey(order.getId());
				}
			}
		}
    	return typeMapper.deleteByPrimaryKey(id);
    }
	
}
