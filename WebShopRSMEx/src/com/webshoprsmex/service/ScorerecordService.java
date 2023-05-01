package com.webshoprsmex.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.ScorerecordMapper;
import com.webshoprsmex.model.Scorerecord;

/**
 * 评分记录service业务类，继承泛型基础service
 * 进行复杂业务处理
 */
@Service
public class ScorerecordService extends BaseService<Scorerecord>{

	@Autowired
	private ScorerecordMapper scorerecordMapper;//注入评分mapper
	
	/**
	 * 聚合查询扩展方法
	 * @param itemid
	 * @param score
	 * @return
	 */
	public Map<String,Object> findCountEx(int itemid,int score){
		return scorerecordMapper.findCountEx(itemid, score);
	}
	
}
