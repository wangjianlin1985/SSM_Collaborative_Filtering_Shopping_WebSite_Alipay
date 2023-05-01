<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>支付记录 - 个性化购物商城推荐系统</title>
    	<%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
	<body class="productsgrid-page">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/banner2.jsp"%>
		<div class="whish-list-section section-padding-bottom">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title" style="border-bottom:0">设置信息</h3>
						</section>
					</div>
					<%@ include file="../common/menu.jsp"%>
					<div class="col-lg-9 col-12 mb-5">
						<div class="tab-content">
							<div class="tab-pane fade active show">
								<div class="myaccount-content">
									<div class="table-responsive">
										<table class="table">
											<thead class="thead-light">
												<tr>
													<th class="text-center" scope="col">序号</th>
													<th scope="col">订单号</th>
													<th scope="col">支付宝交易流水号</th>
													<th scope="col">支付金额</th>
													<th scope="col">支付时间</th>
													<th scope="col">操作</th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
						   	  						<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
						   	  							<c:forEach var="payrecord" items="${pageBean.list}" varStatus="status">
															<tr>
																<td class="text-center">${status.index+1 }</td>
																<td>${payrecord.orderid }</td>
																<td>${payrecord.tradeno }</td>
																<td>￥${payrecord.price }</td>
																<td>${payrecord.createtime }</td>
																<td class="text-center">
																	<a href="${pageContext.request.contextPath}/front/payrecord/view?payrecordid=${payrecord.id }" title="详情">
																		<span class="trash"><i class="icon ion-ios-information"></i></span>
																	</a>
																</td>
															</tr>
														</c:forEach>
					                           		</c:when>
										     		<c:otherwise>
										     			<tr><td colspan="6">暂无数据</td><tr>
										     		</c:otherwise>
										   		</c:choose>
											</tbody>
										</table>
										<form action="${pageContext.request.contextPath}/front/payrecord/list" method="post" id="form">
				    		  				<input type="hidden" name="pageNum" value="${pageBean.pageNum}" id="pageNum">
				    		  			</form>
										<%@include file="../common/page.jsp" %>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../common/footer.jsp"%>
	</body>
</html>