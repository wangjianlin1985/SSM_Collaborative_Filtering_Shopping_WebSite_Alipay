package com.webshoprsmex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webshoprsmex.mapper.OrderMapper;
import com.webshoprsmex.mapper.PayrecordMapper;
import com.webshoprsmex.mapper.RefundrecordMapper;
import com.webshoprsmex.model.Order;
import com.webshoprsmex.model.Payrecord;
import com.webshoprsmex.model.Refundrecord;
import com.webshoprsmex.util.DateUtil;

/**
 * 订单service业务类，继承泛型基础service
 * 进行复杂业务处理
 */
@Service
public class OrderService extends BaseService<Order>{
	
	@Autowired
	private OrderMapper orderMapper;//注入订单mapper
	@Autowired
	private PayrecordMapper payrecordMapper;//注入支付记录mapper
	@Autowired
	private RefundrecordMapper refundrecordMapper;//注入退款记录mapper

	/**
	 * 修改订单状态为已支付并添加支付记录
	 * @return
	 */
	public Integer doPay(Integer orderid,String total_amount,String trade_no){
		int success = 0;
		Order order = orderMapper.selectByPrimaryKey(orderid);
		if(order!=null && order.getState()==1){
			//添加支付记录
			String currentTime = DateUtil.getCurrentDate();
			Payrecord payrecord = new Payrecord();
			payrecord.setOrderid(order.getId());
			payrecord.setUserid(order.getUserid());
			payrecord.setPrice(total_amount);
			payrecord.setTradeno(trade_no);
			payrecord.setCreatetime(currentTime);
			success = payrecordMapper.insertSelective(payrecord);
			//更新订单
			order.setState(2);
			order.setPaytime(currentTime);
			success = orderMapper.updateByPrimaryKeySelective(order);
		}
		return success;
	}
	
	/**
	 * 修改订单状态为已退款并添加退款记录
	 * @return
	 */
	public Integer doRefund(Order order,String refund_fee,String trade_no){
		int success = 0;
		//添加支付记录
		String currentTime = DateUtil.getCurrentDate();
		Refundrecord refundrecord = new Refundrecord();
		refundrecord.setOrderid(order.getId());
		refundrecord.setUserid(order.getUserid());
		refundrecord.setPrice(refund_fee);
		refundrecord.setTradeno(trade_no);
		refundrecord.setCreatetime(currentTime);
		success = refundrecordMapper.insertSelective(refundrecord);
		//更新订单
		order.setState(5);
		success = orderMapper.updateByPrimaryKeySelective(order);
		return success;
	}
	
}
