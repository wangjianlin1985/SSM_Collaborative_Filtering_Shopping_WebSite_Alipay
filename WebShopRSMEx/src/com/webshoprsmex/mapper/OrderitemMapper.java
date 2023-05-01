package com.webshoprsmex.mapper;

import java.util.List;
import java.util.Map;

import com.webshoprsmex.model.Orderitem;

/**
 * 订单详情mapper，继承基础mapper，进行简单的增删改查操作
 * 与同名xml文件相映射
 */
public interface OrderitemMapper extends BaseMapper<Orderitem>{
	
	/**
     * 统计每种商品类型下的销量
     */
    List<Map<String, Object>> findTypeidCount();
    
    /**
     * 统计近七天营业额
     */
    List<Map<String, Object>> findOrderPrice();
    
}