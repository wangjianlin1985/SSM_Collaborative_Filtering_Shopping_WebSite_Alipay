<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>商品列表 - 个性化购物商城推荐系统</title>
		<%@ include file="../common/css.jsp"%>
		<%@ include file="../common/js.jsp"%>
	</head>
	<body class="productsgrid-page">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/banner2.jsp"%>
		<div class="shop-page-layout section-padding-bottom">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">商品列表</h3>
						</section>
					</div>
					<div class="col-12">
						<div class="row align-items-center" style="margin-bottom:20px">
							<div class="tag-clouds" id="myTab">
								<span class="widget-title" style="font-weight:500">商品类型：</span>
								<a href="javascript:submitForm('')" class="tag-cloud-link type_all">全部</a>
								<c:if test="${typeList!=null && typeList.size()>0 }">
	                				<c:forEach var="type" items="${typeList}" varStatus="status">
										<a href="javascript:submitForm(${type.id })" class="tag-cloud-link" 
											data-info='${type.id }' title="${type.typename }">${type.typename }</a>
									</c:forEach>
								</c:if>
							</div>
						</div>
						<div class="tab-content">
							<div class="tab-pane show active">
								<div class="row grid-view mb-n5">
									<c:choose>
		   	  							<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
		   	  								<c:forEach var="item" items="${pageBean.list}" varStatus="status">
												<div class="col-sm-6 col-md-4 col-lg-3 mb-5" style="width:20%">
													<div class="product-card">
														<a href="${pageContext.request.contextPath}/front/item/view?itemid=${item.id}" title="${item.itemname}"
															class="product-thumb" style="height:204px;overflow: hidden">
															<img src="/${applicationScope.uploadFileDir }/${item.image}" 
																alt="${item.itemname}" style="">
														</a>
														<div class="product-content">
															<h4>
																<a href="${pageContext.request.contextPath}/front/item/view?itemid=${item.id}" title="${item.itemname}" 
																	class="product-title">
																	<c:if test="${fn:length(item.itemname)>40}">
													                	${fn:substring(item.itemname, 0, 40)} ...
													                </c:if>
													                <c:if test="${fn:length(item.itemname)<=40}">
													                    ${item.itemname}
													                </c:if>
																</a>
															</h4>
															<h5 style="font-size: 14px;">￥${item.price }</h5>
														</div>
														<ul class="actions actions-verticale">
															<li class="action whish-list">
																<button class="addCart" type="button" data-itemid="${item.id}">
																	<i class="icon ion-bag"></i>
																</button>
															</li>
														</ul>
													</div>
												</div>
											</c:forEach>
	                           			</c:when>
						     			<c:otherwise>
						     				暂无数据
						     			</c:otherwise>
						   			</c:choose>
						   			<form action="${pageContext.request.contextPath}/front/item/list" method="post" id="form">
				   		  				<input type="hidden" name="pageNum" value="${pageBean.pageNum}" id="pageNum">
				   		  				<input type="hidden" name="typeid" value="${typeid}" id="typeid">
				   		  				<input type="hidden" name="itemname" value="${itemname}">
				   		  			</form>
									<%@ include file="../common/page.jsp"%>
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
        var typeid = "${typeid}";
        if(typeid!=null && typeid!=""){
            $("#myTab").find("a").each(function(){
                if($(this).attr("data-info")==typeid){
                    $(this).addClass("active");
                    return false;
                }
            });
        }else{
            $(".type_all").addClass("active");
        }
        function submitForm(typeid){
            $("#pageNum").val(1);
            $("#typeid").val(typeid);
            $("#form").submit();
        }
    </script>
</html>