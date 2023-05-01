package com.webshoprsmex.util.cf;

import java.util.List;

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.common.FastIDSet;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.GenericBooleanPrefDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericBooleanPrefItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.UncenteredCosineSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.webshoprsmex.model.Orderitem;
import com.webshoprsmex.model.User;

/**
 * 协同过滤算法工具类
 * 基于用户的协同过滤推荐算法实现原理：
 * 1、根据用户评分信息构建用户-商品评分矩阵
 * 2、根据用户-商品评分矩阵计算用户之间的相似度
 * 3、根据用户之间的相似度得到目标用户的最近邻居KNN
 * 4、预测目标用户未评分商品的评分并进行推荐
 * 基于项目（商品）的协同过滤推荐算法实现原理：
 * 1、根据用户订单信息构建用户-商品购买数据矩阵
 * 2、根据用户-商品购买数据矩阵计算商品之间的相似度
 * 3、预测目标用户未购买商品的喜好值并进行推荐
 */
public class CFUtil {
	
	//协同过滤输出运算过程工具类
	private CFPrintUtil cfPrintUtil = new CFPrintUtil();
	
	/**
	 * 基于用户根据商品评分推荐
	 * @param cUser 当前登录用户
	 * @param model 用户-商品评分矩阵
	 */
	public String cfByScoreBaseUser(User cUser,DataModel model){
		System.out.println("******基于用户根据商品评分推荐开始******");
		//推荐结果
		String cfitemids = "";
		if(model==null){
			System.out.println("***用户-商品评分矩阵为空***");
		}else{
			try {
				//定义相似度计算对象，余弦算法，
				UserSimilarity userSimilarity =  new UncenteredCosineSimilarity(model);
				//输出目标用户与其他用户之间的相似度，
				cfPrintUtil.printSimilarityBaseUser(model, userSimilarity, 
						cUser.getId());
				//定义最近邻对象
				UserNeighborhood neighborhood = new NearestNUserNeighborhood(
						CFConstantUtil.knn, userSimilarity, model);
				long[] tempKnn = neighborhood.getUserNeighborhood(cUser.getId());
				//输出目标用户的最近邻居
				cfPrintUtil.printKNNBaseUser(tempKnn);
				//定义推荐引擎
				Recommender recommender = new CachingRecommender(
						new GenericUserBasedRecommender(model, neighborhood, userSimilarity));
				//推荐
				List<RecommendedItem> recommendations = recommender.recommend(
						cUser.getId(), CFConstantUtil.cfCount);
				//输出推荐结果
				cfPrintUtil.printRecommendResult(recommendations, cUser.getId());
	            for (RecommendedItem ritem : recommendations) {
	                cfitemids+=ritem.getItemID()+",";
	            }
	            if(!cfitemids.equals("")){
	            	cfitemids = cfitemids.substring(0,cfitemids.lastIndexOf(","));
	            }
			} catch (TasteException e) {
				e.printStackTrace();
			}
		}
		System.out.println("******基于用户根据商品评分推荐结束******");
		return cfitemids;
	}
	
	/**
	 * 根据购买商品进行推荐
	 * @param cUser 当前登录用户
	 * @param orderitemList 订单详情集合
	 * @return
	 */
	public String cfByOrderitemBaseUser(User cUser,List<Orderitem> orderitemList){
		System.out.println("******基于商品根据购买商品数据推荐开始******");
		try{
			//判断订单集合是否为空
			if(orderitemList!=null && orderitemList.size()>0){
				//定义用户-商品矩阵
				FastByIDMap<FastIDSet> map = new FastByIDMap<FastIDSet>();
				//标记当前用户是否有购买数据
				boolean flag = true;
				//遍历订单数据
				for(Orderitem orderitem:orderitemList){
					//用户id
					int userid = orderitem.getUserid();
					//判断用户id是否是当前用户
					if(cUser.getId()==userid){
						flag = false;
					}
					//商品id
					int productId = orderitem.getItemid();
					//商品set集合
					FastIDSet fastIDSet = null;
					//判断矩阵中是否有用户
					if(map.containsKey(userid)){
						fastIDSet = map.get(userid);
					}else{
						fastIDSet = new FastIDSet();
					}
					//集合中插入商品id
					fastIDSet.add(productId);
					//矩阵中保存用户-商品信息
					map.put(userid,fastIDSet);
				}
				//如果当前用户没有购买商品那么推荐结束
				if(flag){
					System.out.println("当前用户没有购买商品！");
					System.out.println("******基于商品根据购买商品数据推荐结束******");
					return null;
				}
				//定义用户-商品model
				GenericBooleanPrefDataModel model = new GenericBooleanPrefDataModel(map);
				//定义基于商品的相似度计算方法（jaccard系数计算方法）
				ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
				//获取矩阵中的所有商品
				LongPrimitiveIterator iterator = model.getItemIDs();
				//获取第一个商品
				long idTemp = iterator.next();
				//遍历其他商品
				while(iterator.hasNext()){
					long id = iterator.next();
					//计算相似度
					double sim = similarity.itemSimilarity(idTemp, id);
					System.out.println("商品:"+idTemp+"  与商品："+id+"  相似度="+sim);
				}
				//推荐
				Recommender recommender = new GenericBooleanPrefItemBasedRecommender(model, similarity);
				//推荐n个
				List<RecommendedItem> recommendations = recommender.recommend(
						cUser.getId(), CFConstantUtil.cfCount);
				if(recommendations==null || recommendations.size()==0){
					System.out.println("没有推荐结果！");
					return null;
				}
				String cfItemIds = "";
				for(RecommendedItem ri:recommendations){//循环得到推荐商品对象
					int itemid = (int) ri.getItemID();//推荐商品id
					cfItemIds+=itemid+",";
				}
				cfItemIds = cfItemIds.substring(0,cfItemIds.lastIndexOf(","));
				System.out.println("推荐商品id："+cfItemIds);
				return cfItemIds;
			}else{
				System.out.println("没有订单数据！");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		System.out.println("******基于商品根据购买商品数据推荐结束******");
		return "";
	}
	
}