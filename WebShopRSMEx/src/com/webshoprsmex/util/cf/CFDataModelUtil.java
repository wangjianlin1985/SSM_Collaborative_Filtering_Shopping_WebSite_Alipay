package com.webshoprsmex.util.cf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericPreference;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.Preference;
import org.apache.mahout.cf.taste.model.PreferenceArray;

import com.webshoprsmex.model.Scorerecord;
import com.webshoprsmex.model.User;

/**
 * 构建用户-商品操作行为矩阵
 */
public class CFDataModelUtil {

	/**
	 * 构建用户-商品评分矩阵
	 * @param scorerecordList 商品评分集合
	 * @param cUser 当前用户
	 * @return
	 */
	public DataModel getItemScoreDadaModel(List<Scorerecord> scorerecordList,
			User cUser){
		DataModel model = null;
		System.out.println("******构建用户-商品评分矩阵开始******");
		Boolean flag = false;//标记当前登录的用户是否有商品评分记录
		//如果数据库中有商品评分记录
		if(scorerecordList!=null && scorerecordList.size()>0){
			//定义map保存用户id和商品评分集合
			Map<Integer,List<Preference>> map = new HashMap<Integer,List<Preference>>();
			//遍历矩阵
			for(Scorerecord scorerecord:scorerecordList){
				int userid = scorerecord.getUserid();//用户id
				if(userid==cUser.getId()){
					flag = true;
				}
				int itemid = scorerecord.getItemid();//商品id
				float preference = scorerecord.getScore();//评分
				List<Preference> preferenceList = null;
				if(map.containsKey(userid)){
					preferenceList = map.get(userid);
				}else{
					preferenceList = new ArrayList<Preference>();
				}
				preferenceList.add(new GenericPreference(userid, itemid, preference));
				map.put(userid, preferenceList);
			}
			if(!flag){
				System.out.println("***当前登录用户没有商品评分记录！***");
			}else{
				//定义用户-商品评分map集合
				FastByIDMap<PreferenceArray> preferences = new FastByIDMap<PreferenceArray>();
				Set<Integer> set = map.keySet();
				for(Integer i:set){
					List<Preference> preferenceList = map.get(i);
					preferences.put(i,new GenericUserPreferenceArray(preferenceList));
				}
				//实例化用户-商品评分矩阵
			    model = new GenericDataModel(preferences);
			}
		}else{
			System.out.println("******数据库中没有商品评分记录！******");
		}
		System.out.println("******构建用户-商品评分矩阵结束******");
		return model;
	}
	
}
