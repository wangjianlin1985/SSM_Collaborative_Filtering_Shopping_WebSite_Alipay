<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>我的评分 - 个性化购物商城推荐系统</title>
    	<%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
    	<script src="${pageContext.request.contextPath}/static/js/jquery.raty.min.js"></script>
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
													<th scope="col">商品名称</th>
													<th scope="col">评分</th>
													<th scope="col">评分时间</th>
													<th class="text-center" scope="col">操作</th>
												</tr>
											</thead>
											<tbody>
												<c:choose>
						   	  						<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
						   	  							<c:forEach var="scorerecord" items="${pageBean.list}" varStatus="status">
															<tr>
																<td class="text-center">${status.index+1 }</td>
																<td>
																	<a href="${pageContext.request.contextPath}/front/item/view?itemid=${scorerecord.item.id }" 
																	title="${scorerecord.item.itemname }" style="color: inherit;">${scorerecord.item.itemname }</a>
																</td>
																<td>
																	<div id="star_${scorerecord.id }"></div>
			                                                        <script>
			                                                        	$('#star_${scorerecord.id }').raty(
			                                                                {
			                                                                    score:parseFloat('${scorerecord.score}'),
			                                                                    halfShow:true,
			                                                                    readOnly: true,
			                                                                    path: '${pageContext.request.contextPath}/static/images',
			                                                                    hints: ['1分', '2分', '3分', '4分', '5分'],
			                                                                }
			                                                            );
			                                                        </script>
																</td>
																<td>${scorerecord.createtime }</td>
																<td class="text-center">
																	<a href="javascript:doDelete(${scorerecord.id })" title="删除">
																		<span class="trash"><i class="ion-android-delete"></i></span>
																	</a>
																</td>
															</tr>
														</c:forEach>
					                           		</c:when>
										     		<c:otherwise>
										     			<tr><td colspan="5">暂无数据</td><tr>
										     		</c:otherwise>
										   		</c:choose>
											</tbody>
										</table>
										<form action="${pageContext.request.contextPath}/front/scorerecord/list" method="post" id="form">
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
	<script type="text/javascript">
		//删除
	    function doDelete(id){
	        layer.confirm("确认删除？",{icon:7,title:"删除"},function(index){
	    	   layer.close(index);
	    	   var data = "id="+id;
	           common_ajax_other("front/scorerecord/delete",data);
	       	});
	    }
    </script>
</html>