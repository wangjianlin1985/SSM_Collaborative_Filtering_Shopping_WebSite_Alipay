package com.webshoprsmex.mapper;

import java.util.List;

import com.webshoprsmex.model.Item;

/**
 * 商品mapper，继承基础mapper，进行简单的增删改查操作
 * 与同名xml文件相映射
 */
public interface ItemMapper extends BaseMapper<Item>{
	
	/**
     * 热点推荐，根据收藏数量降序推荐，带参数
     * @param params
     * @return
     */
    List<Item> findHot(List<Object[]> params);
    
    /**
     * 相关推荐，随机查找同类型下商品
     * @return
     */
    List<Item> findByTypeid(Integer itemid,Integer typeid);
   
}