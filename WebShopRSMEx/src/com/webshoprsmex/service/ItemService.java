package com.webshoprsmex.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.ItemMapper;
import com.webshoprsmex.mapper.OrderMapper;
import com.webshoprsmex.mapper.OrderitemMapper;
import com.webshoprsmex.model.Item;
import com.webshoprsmex.model.Order;
import com.webshoprsmex.model.Orderitem;

/**
 * 商品service业务类，继承泛型基础service
 * 进行复杂业务处理
 */
@Service
public class ItemService extends BaseService<Item>{
	
	@Autowired
	private ItemMapper itemMapper;//注入商品mapper
	@Autowired
	private OrderMapper orderMapper;//注入订单mapper
	@Autowired
	private OrderitemMapper orderitemMapper;//注入订单详情mapper
	
	/**
     * 热点推荐，根据收藏数量降序推荐，带参数
     * @param params
     * @return
     */
    public List<Item> findHot(List<Object[]> params){
    	return itemMapper.findHot(params);
    }
    
    /**
     * 相关推荐，随机查找同类型下商品
     * @return
     */
    public List<Item> findByTypeid(Integer itemid,Integer typeid){
    	return itemMapper.findByTypeid(itemid, typeid);
    }
    
    /**
     * 根据主键删除商品
     * @return
     */
    public int deleteById(Integer id){
    	//查询参数集合
		List<Object[]> params = new ArrayList<Object[]>();
		//删除订单、订单详情
		//先查询订单详情
		params.clear();
		params.add(new Object[]{"itemid","=",id});
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
    	return itemMapper.deleteByPrimaryKey(id);
    }

}