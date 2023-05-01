<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="col-lg-3 col-12 mb-5">
	<div class="myaccount-tab-menu nav" id="menuid">
		<a href="${pageContext.request.contextPath}/front/user/view">个人中心</a>
		<a href="${pageContext.request.contextPath}/front/user/edit">信息维护</a>
		<a href="${pageContext.request.contextPath}/front/user/editPassword">密码安全</a>
		<a href="${pageContext.request.contextPath}/front/order/list">我的订单</a>
		<a href="${pageContext.request.contextPath}/front/payrecord/list">支付记录</a>
		<a href="${pageContext.request.contextPath}/front/refundrecord/list">退款记录</a>
		<a href="${pageContext.request.contextPath}/front/scorerecord/list">我的评分</a>
		<a href="${pageContext.request.contextPath}/front/collect/list">我的收藏</a>
		<a href="${pageContext.request.contextPath}/front/comment/list">我的评论</a>
	</div>
</div>
<script>
	var pathname = pathname?pathname:"";//pathname是为一个页面中有多个操作
	//菜单选中js
 	window.onload = function(){
    	$("#menuid").find("a").each(function(){
			if($(this).attr("href")==window.location.pathname
					|| $(this).attr("href")==pathname){
				$(this).addClass("active");
				return;
			}
		});
 	}
</script>