package com.webshoprsmex.mapper;

import java.util.Map;

import com.webshoprsmex.model.Scorerecord;

/**
 * 评分记录mapper，继承基础mapper，进行简单的增删改查操作
 * 与同名xml文件相映射
 */
public interface ScorerecordMapper extends BaseMapper<Scorerecord>{
    
	/**
	 * 聚合查询扩展方法
	 * @param itemid
	 * @param score
	 * @return
	 */
	Map<String,Object> findCountEx(int itemid,int score);
	
}