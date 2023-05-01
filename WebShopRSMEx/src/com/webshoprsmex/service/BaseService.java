package com.webshoprsmex.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.BaseMapper;

/**
 * 泛型基础service业务类
 * 进行复杂业务处理
 */
@Service
public class BaseService<T> {

	@Autowired
	private BaseMapper<T> baseMapper;
	
	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	public int deleteByPrimaryKey(Integer id){
		return baseMapper.deleteByPrimaryKey(id);
	}

	/**
	 * 保存（全部属性，包含id）
	 * @param t
	 * @return
	 */
	public int insert(T t){
		return baseMapper.insert(t);
	}

	/**
     * 保存（不为空属性）
     * @param t
     * @return
     */
	public int insertSelective(T t){
		return baseMapper.insertSelective(t);
	}

	/**
     * 根据主键查询
     * @param id
     * @return
     */
	public T selectByPrimaryKey(Integer id){
		return baseMapper.selectByPrimaryKey(id);
	}

	/**
     * 根据主键更新（不为空属性）
     * @param t
     * @return
     */
	public int updateByPrimaryKeySelective(T t){
		return baseMapper.updateByPrimaryKeySelective(t);
	}

	/**
     * 根据主键更新（全部）
     * @param t
     * @return
     */
	public int updateByPrimaryKey(T t){
		return baseMapper.updateByPrimaryKey(t);
	}
	
	/**
     * 带参数查询
     * @param params
     * @return
     */
	public List<T> find(List<Object[]> params){
		return baseMapper.find(params);
	}
    
    /**
     * 带参数关联查询
     * @param params
     * @return
     */
	public List<T> findJoin(List<Object[]> params){
		return baseMapper.findJoin(params);
	}
    
    /**
     * 带参数查询一个
     * @param params
     * @return
     */
	public T findFirst(List<Object[]> params){
		return baseMapper.findFirst(params);
	}
    
    /**
     * 带参数关联查询一个
     * @param params
     * @return
     */
	public T findFirstJoin(List<Object[]> params){
		return baseMapper.findFirstJoin(params);
	}
    
    /**
     * 带参数聚合查询
     * @param params
     * @return
     */
	public Long findCount(List<Object[]> params){
		return baseMapper.findCount(params);
	}
	
}
