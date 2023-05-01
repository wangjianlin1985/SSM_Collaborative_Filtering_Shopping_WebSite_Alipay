package com.webshoprsmex.util;

import java.text.DecimalFormat;
import java.util.List;

/**
 * 评分分析实体类
 */
public class ScoreAnalysis {

	private float avgScore;  //平均评分
	private int scoreCount;  //评分总数
	private int sumScore;  //总评分
	//具体评分分析集合
	List<CurScoreAnalysis> curScoreAnalysisList;
	
	public float getAvgScore() {
		if(sumScore==0 || scoreCount==0){
			avgScore = 0;
		}else{
			avgScore = (float)sumScore/(float)scoreCount;
			DecimalFormat fnum = new DecimalFormat("##0.0");  
			avgScore = Float.parseFloat(fnum.format(avgScore));
		}
		return avgScore;
	}
	
	public void setAvgScore(float avgScore) {
		this.avgScore = avgScore;
	}
	
	public int getScoreCount() {
		return scoreCount;
	}
	
	public void setScoreCount(int scoreCount) {
		this.scoreCount = scoreCount;
	}
	
	public int getSumScore() {
		return sumScore;
	}
	
	public void setSumScore(int sumScore) {
		this.sumScore = sumScore;
	}
	public List<CurScoreAnalysis> getCurScoreAnalysisList() {
		return curScoreAnalysisList;
	}
	
	public void setCurScoreAnalysisList(List<CurScoreAnalysis> curScoreAnalysisList) {
		this.curScoreAnalysisList = curScoreAnalysisList;
	}
	
}