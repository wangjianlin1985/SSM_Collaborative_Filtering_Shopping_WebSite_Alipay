package com.webshoprsmex.mapper;

import java.util.List;

/**
 * 泛型基础mapper，进行简单的增删改查操作
 * @param <T>
 */
public interface BaseMapper<T> {

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(Integer id);

	/**
	 * 保存（全部属性，包含id）
	 * @param t
	 * @return
	 */
    int insert(T t);

    /**
     * 保存（不为空属性）
     * @param t
     * @return
     */
    int insertSelective(T t);

    /**
     * 根据主键查询
     * @param id
     * @return
     */
    T selectByPrimaryKey(Integer id);

    /**
     * 根据主键更新（不为空属性）
     * @param t
     * @return
     */
    int updateByPrimaryKeySelective(T t);

    /**
     * 根据主键更新（全部）
     * @param t
     * @return
     */
    int updateByPrimaryKey(T t);
    
    /**
     * 带参数查询
     * @param params
     * @return
     */
    List<T> find(List<Object[]> params);
    
    /**
     * 带参数关联查询
     * @param params
     * @return
     */
    List<T> findJoin(List<Object[]> params);
    
    /**
     * 带参数查询一个
     * @param params
     * @return
     */
    T findFirst(List<Object[]> params);
    
    /**
     * 带参数关联查询一个
     * @param params
     * @return
     */
    T findFirstJoin(List<Object[]> params);
    
    /**
     * 带参数聚合查询
     * @param params
     * @return
     */
    Long findCount(List<Object[]> params);
	
}
