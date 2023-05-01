package com.webshoprsmex.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.webshoprsmex.config.AlipayConfig;
import com.webshoprsmex.model.Cart;
import com.webshoprsmex.model.Item;
import com.webshoprsmex.model.Order;
import com.webshoprsmex.model.Orderitem;
import com.webshoprsmex.model.Payrecord;
import com.webshoprsmex.model.User;
import com.webshoprsmex.service.CartService;
import com.webshoprsmex.service.ItemService;
import com.webshoprsmex.service.OrderService;
import com.webshoprsmex.service.OrderitemService;
import com.webshoprsmex.service.PayrecordService;
import com.webshoprsmex.service.RefundrecordService;
import com.webshoprsmex.util.Constant;
import com.webshoprsmex.util.DateUtil;

/**
 * 前台订单控制器
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "front/order")
public class FrontOrderController extends BaseController{

	@Autowired
	private ItemService itemService;//注入商品业务类
	@Autowired
	private CartService cartService;//注入购物车业务类
	@Autowired
	private OrderService orderService;//注入订单业务类	
	@Autowired
	private OrderitemService orderitemService;//注入订单详情业务类	
	@Autowired
	private PayrecordService payrecordService;//注入支付记录业务类	
	@Autowired
	private RefundrecordService refundrecordService;//注入退款记录业务类	
	
	/**
	 * 分页带参数查询，我的订单
	 * @param map
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@RequestMapping("/list")
	public String list(ModelMap map,
			@RequestParam(value="pageNum", defaultValue="1") int pageNum,
			@RequestParam(value="pageSize", defaultValue = Constant.pageSize) int pageSize){
		PageHelper.startPage(pageNum, pageSize);
		//查询参数集合
		params.add(new Object[]{"userid","=",getCurrentUser().getId()});
		List<Order> list = orderService.find(params);
		PageInfo<Order> pageInfo = new PageInfo<Order>(list);
		map.put("pageBean", pageInfo);
		return "front/order/list";
	}
	
	/**
	 * 跳转到支付页面
	 * @param orderid
	 * @return
	 */
	@RequestMapping("/pay")
	public String pay(Integer orderid,Model model){
		Order order = orderService.selectByPrimaryKey(orderid);
		if(order!=null && order.getUserid()==getCurrentUser().getId() && order.getState()==1){
			params.add(new Object[]{"oi.orderid","=",orderid});
			List<Orderitem> orderitemList = orderitemService.findJoin(params);
			model.addAttribute("order",order);
			model.addAttribute("orderitemList",orderitemList);
		}
		return "front/order/pay";
	}
	
	/**
	 * 支付，跳转到支付宝支付页面
	 * @param orderid 订单编号
	 * @param bankname 支付方式
	 * @param banknum 支付账号
	 * @return 返回字符串（html格式，是一个表单和一个提交表单的js）
	 * @throws Exception 
	 */
	@RequestMapping(value = "/doPay", produces = "text/html; charset=UTF-8")
	@ResponseBody
	public String doPay(Integer orderid,String bankname,String banknum) throws Exception{
		//查询订单，只有在订单未支付状态才允许支付
		Order order = orderService.selectByPrimaryKey(orderid);
		if(order!=null && order.getUserid()==getCurrentUser().getId() && order.getState()==1){
			//修改订单的 支付方式和支付账号
			order.setBankname(bankname);
			order.setBanknum(banknum);
			orderService.updateByPrimaryKeySelective(order);
			//获得初始化的AlipayClient
			AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, 
					AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", 
					AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
			//设置请求参数
			AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
			alipayRequest.setReturnUrl(AlipayConfig.return_url);
			alipayRequest.setNotifyUrl(AlipayConfig.notify_url);
			//商户订单号，商户网站订单系统中唯一订单号，必填
			String out_trade_no = new String(String.valueOf(orderid).getBytes("ISO-8859-1"),"UTF-8");
			//付款金额，必填
			String total_amount = new String(order.getTotalprice().getBytes("ISO-8859-1"),"UTF-8");
			//商品名称，必填，长度256
			String subject = "购物订单！" + DateUtil.getCurrentDate();
			//商品描述，可空
			String body = "";
			//请求设置参数
			alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
					+ "\"total_amount\":\""+ total_amount +"\"," 
					+ "\"subject\":\""+ subject +"\"," 
					+ "\"body\":\""+ body +"\"," 
					+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
			//请求
			String result = alipayClient.pageExecute(alipayRequest).getBody();
			return result;
		}else{
			return  "操作异常！";
		}
	}
	
	/**
	 * 支付宝支付成功后返回接口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping("/doPayReturn")
	public String doPayReturn(ModelMap modelMap) throws Exception{
		int success = 0;//支付成功或者失败标记，大于0：成功，0：失败
		String message = "";//操作成功或者失败提示
		//获取支付宝GET过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String,String[]> requestParams = request.getParameterMap();
		//遍历响应信息
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用
			valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		System.out.println(params);
		boolean signVerified = false;
		try{
			//调用SDK验证签名
			signVerified = AlipaySignature.rsaCheckV1(params, AlipayConfig.alipay_public_key, 
					AlipayConfig.charset, AlipayConfig.sign_type); 
		}catch(Exception exception){
			exception.printStackTrace();
		}
		//验证签名
		if(signVerified) {
			//商户订单号trade_no
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//支付宝交易号out_trade_no
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");
			//付款金额total_amount
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");
			//保存返回数据
			modelMap.put("out_trade_no", out_trade_no);
			modelMap.put("trade_no", trade_no);
			modelMap.put("total_amount", total_amount);
			//修改订单状态并添加支付记录
			Integer orderid = Integer.parseInt(out_trade_no);
			success = orderService.doPay(orderid, total_amount, trade_no);
		}else {
			//验签失败
			message = "验签失败！请联系管理员！";
		}
		modelMap.put("success", success);
		modelMap.put("message", message);
		return "front/order/payreturn";
	}
	
	/**
	 * 申请退款，跳转到支付宝退款页面
	 * @param orderid 订单编号
	 * @throws Exception 
	 */
	@RequestMapping(value = "/doRefund")
	public String doRefund(Integer orderid,ModelMap modelMap) throws Exception{
		int success = 0;//退款成功标记，大于0：成功，0：失败
		String message = "";//退款成功或者失败提示
		//查询订单，只有在订单付款后才允许退款
		Order order = orderService.selectByPrimaryKey(orderid);
		if(order!=null && order.getUserid()==getCurrentUser().getId() 
				&& order.getState()>1 && order.getState()!=5){
			//查询交易记录
			params.clear();
			params.add(new Object[]{"orderid","=",orderid});
			Payrecord payrecord = payrecordService.findFirst(params);
			if(payrecord!=null){
				//获得初始化的AlipayClient
				AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, 
						AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", 
						AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);
				//设置请求参数
				AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
				//商户订单号，商户网站订单系统中唯一订单号
				String out_trade_no = new String(String.valueOf(orderid).getBytes("ISO-8859-1"),"UTF-8");
				//支付宝交易号
				String trade_no = new String(payrecord.getTradeno().getBytes("ISO-8859-1"),"UTF-8");
				//请二选一设置
				//需要退款的金额，该金额不能大于订单金额，必填
				String refund_amount = new String(payrecord.getPrice().getBytes("ISO-8859-1"),"UTF-8");
				//退款的原因说明
				String refund_reason = "其他";
				//标识一次退款请求，同一笔交易多次退款需要保证唯一，如需部分退款，则此参数必传
				String out_request_no = "";
				alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
						+ "\"trade_no\":\""+ trade_no +"\"," 
						+ "\"refund_amount\":\""+ refund_amount +"\"," 
						+ "\"refund_reason\":\""+ refund_reason +"\"," 
						+ "\"out_request_no\":\""+ out_request_no +"\"}");
				//请求，返回json格式数据
				String result = alipayClient.execute(alipayRequest).getBody();
				//解析返回结果json格式
				JSONObject jsonObject = JSONObject.parseObject(result);
				JSONObject jsonObject2 = (JSONObject) jsonObject.get("alipay_trade_refund_response");
				String code = jsonObject2.getString("code");
				String msg = jsonObject2.getString("msg");
				trade_no = jsonObject2.getString("trade_no");//与支付的交易记录号一样
				String refund_fee = jsonObject2.getString("refund_fee");//退款金额
				System.out.println(code);
				System.out.println(msg);
				//判断是否退款成功
				if(code.equals("10000") && msg.equals("Success")){
					//退款成功，修改订单状态、添加退款记录
					success = orderService.doRefund(order, refund_fee, trade_no);
					//保存返回数据
					modelMap.put("out_trade_no", out_trade_no);
					modelMap.put("trade_no", trade_no);
					modelMap.put("refund_fee", refund_fee);
				}else{
					//退款失败
					success = 0;
					message = "退款失败！请稍后再试或联系管理员！";
				}
			}
		}else{
			success = 0;
			message = "退款失败！请稍后再试或联系管理员！";
		}
		modelMap.put("success", success);
		modelMap.put("message", message);
		return "front/order/refundreturn";
	}
	
	/**
	 * 确认订单
	 * @param itemid 商品id
	 * @param count 商品数量
	 * @param paramFlag 从购物车来还是商品直接过来标记
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/confirm")
	public String confirm(Integer itemid,Integer count,
			String paramFlag,ModelMap modelMap){
		List<Cart> cartListResult = null;
		if("cart".equals(paramFlag)){// 购物车
			params.add(new Object[]{"c.userid","=",getCurrentUser().getId()});
			cartListResult = cartService.findJoin(params);
			if(cartListResult==null || cartListResult.size()==0){
				return "redirect:/front/cart/list";
			}
		}else{//商品
			Cart cart = new Cart();
			cart.setItemid(itemid);
			cart.setCount(count);
			Item item = itemService.selectByPrimaryKey(itemid);
			cart.setItem(item);
			cartListResult = new ArrayList<Cart>();
			cartListResult.add(cart);
		}
		modelMap.put("cartListResult", cartListResult);
		modelMap.put("paramFlag", paramFlag);
		return "front/order/confirm";
	}
	
	/**
	 * 提交订单
	 * @param itemid 商品id
	 * @param count 商品数量
	 * @param paramFlag 从购物车来还是商品直接过来标记
	 * @param address 收货地址
	 * @param phone 联系电话
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Integer itemid,Integer count,
			String paramFlag,String address,String phone,ModelMap modelMap){
		List<Cart> cartListResult = null;
		User cUser = getCurrentUser();
		Integer orderid = null;
		if("cart".equals(paramFlag)){//购物车
			params.add(new Object[]{"c.userid","=",cUser.getId()});
			cartListResult = cartService.findJoin(params);
		}else{ //商品
			Cart cart = new Cart();
			cart.setItemid(itemid);
			cart.setCount(count);
			Item item = itemService.selectByPrimaryKey(itemid);
			cart.setItem(item);
			cartListResult = new ArrayList<Cart>();
			cartListResult.add(cart);
		}
		if(cartListResult==null || cartListResult.size()==0){
			return "redirect:/front/cart/list";
		}else{
			Order order = new Order();
			order.setUserid(cUser.getId());
			order.setAddress(address);
			order.setPhone(phone);
			order.setCreatetime(DateUtil.getCurrentDate());
			order.setState(1);
			orderService.insertSelective(order);
			Float totalPrice = 0.0f;
			//保存订单详情
			for(Cart cart:cartListResult){
				Orderitem orderitem = new Orderitem();
				orderitem.setOrderid(order.getId());
				orderitem.setPrice(cart.getItem().getPrice());
				orderitem.setItemid(cart.getItem().getId());
				orderitem.setUserid(cUser.getId());
				orderitem.setCount(cart.getCount());
				Float price = Float.valueOf(orderitem.getPrice());
				//金额四舍五入
				DecimalFormat fnum = new DecimalFormat( "##0.00 ");
				orderitem.setTotalprice(fnum.format(price*orderitem.getCount()));
				totalPrice+=price*(float)orderitem.getCount();
				orderitemService.insertSelective(orderitem);
			}
			//金额四舍五入
			DecimalFormat fnum = new DecimalFormat( "##0.00 ");
			order.setTotalprice(fnum.format(totalPrice));
			orderService.updateByPrimaryKeySelective(order);
			orderid = order.getId();
			//清空购物车
			if("cart".equals(paramFlag)){
				cartService.deleteBatch(cartListResult);
			}
		}
		return "redirect:/front/order/pay?orderid="+orderid;
	}
	
	/**
	 * 删除未付款订单，ajax异步请求，返回json格式数据
	 * @param orderid 订单id
	 * @return
	 */
	@RequestMapping("/delete")
	@ResponseBody
	public Object delete(Integer orderid){
		int success = orderService.deleteByPrimaryKey(orderid);
		resultMap.put("success",success);
		resultMap.put("url","front/order/list");
		return resultMap;
	}
	
	/**
	 * 收货，ajax异步请求，返回json格式数据
	 * @param orderid
	 * @return
	 */
	@RequestMapping("/receive")
	@ResponseBody
	public Object receive(Integer orderid){
		int success = 0;
		Order order = orderService.selectByPrimaryKey(orderid);
		if(order!=null && order.getState()==3){
			order.setState(4);
			order.setReceivetime(DateUtil.getCurrentDate());
			success = orderService.updateByPrimaryKeySelective(order);
		}
		resultMap.put("success",success);
		resultMap.put("url","front/order/list");
		return resultMap;
	}
	
}