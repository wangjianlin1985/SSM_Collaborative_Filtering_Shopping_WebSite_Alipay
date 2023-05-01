package com.webshoprsmex.util;

import java.text.DecimalFormat;

/**
 * 当前评分分析实体类
 */
public class CurScoreAnalysis {

	private int curScore; //当前评分
	private float percent;  //百分比
	private int scoreCount;  //评分总数
	
	public int getCurScore() {
		return curScore;
	}
	
	public void setCurScore(int curScore) {
		this.curScore = curScore;
	}
	
	public float getPercent() {
		return percent;
	}
	
	public void setPercent(float percentTemp) {
		if(percentTemp==0 || scoreCount==0){
			percentTemp = 0;
		}else{
			percentTemp = (float)percentTemp/(float)scoreCount*100;
			DecimalFormat fnum = new DecimalFormat("0");
			percentTemp = Float.parseFloat(fnum.format(percentTemp));
		}
		this.percent = percentTemp;
	}

	public int getScoreCount() {
		return scoreCount;
	}

	public void setScoreCount(int scoreCount) {
		this.scoreCount = scoreCount;
	}
	
}