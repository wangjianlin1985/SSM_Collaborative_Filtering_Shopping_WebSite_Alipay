<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>退款记录列表 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>退款记录列表</li>
	        </ol>
	        <form action="${pageContext.request.contextPath}/admin/refundrecord/list" method="post" id="form1">
		        <div class="barboxs">
		            <div class="leftbox">
		                <div class="liselect w300">
		                    <div class="input-group" style="display: inline-block;width:100%">
		                        <input type="text" id="username" name="username" value="${username }" maxlength="255"
		                        	class="form-control example-typeahead" placeholder="请输入用户名">
		                    </div>
		                </div>
		                <div class="liselect w300">
		                    <div class="input-group">
		                        <input type="text" id="orderid" name="orderid" value="${orderid }" maxlength="255"
		                        	class="form-control example-typeahead" placeholder="请输入订单号" oninput = "value=value.replace(/[^\d]/g,'')">
		                        <span class="input-group-btn">
		                            <button class="btn btn-success" onclick="javascript:$('#form1').submit()" title="搜索">
		                            	<i class="fa fa-search"></i>
		                            </button>
		                        </span>
		                    </div>
		                </div>
		            </div>
		        </div>
	        </form>
	        <div class="tablebox">
	            <table class="table table-bordered">
	                <thead>
	                    <tr>
	                        <th class="text-center" width="50"><input type="checkbox" id="check5-all" name="check5-all"></th>
	                        <th class="text-center">序号</th>
	                        <th>用户名</th>
	                        <th>订单号</th>
	                        <th>支付宝交易流水号</th>
	                        <th>退款金额</th>
	                        <th>退款时间</th>
	                    </tr>
	                </thead>
	                <tbody>
	                	<c:choose>
		                  	<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
		                  		<c:forEach var="refundrecord" items="${pageBean.list}" varStatus="status">
				                    <tr>
				                        <td class="text-center"><input type="checkbox" id="check5-td1" name="check5-td1"></td>
				                        <td class="cell-small text-center">${status.index+1 }</td>
				                        <td>${refundrecord.user.username }</td>
				                        <td>${refundrecord.orderid }</td>
				                        <td>${refundrecord.tradeno }</td>
				                        <td>￥${refundrecord.price }</td>
				                        <td>${refundrecord.createtime }</td>
				                    </tr>
		                  		</c:forEach>
		                    </c:when>
		                    <c:otherwise>
		                    	<tr>
			                      	<td colspan="7">暂无数据</td>
			                    </tr>
		                    </c:otherwise>
	                    </c:choose>
	                </tbody>
	            </table>
	        </div>
	        <form action="${pageContext.request.contextPath}/admin/refundrecord/list" method="post" id="form">
	        	<input type="hidden" name="pageNum" value="${pageBean.pageNum}" id="pageNum">
	        	<input type="hidden" name="username" value="${username }" >
	        	<input type="hidden" name="orderid" value="${orderid }" >
	        </form>
	        <%@ include file="../common/page.jsp"%>
    	</div>
	</body>
</html>