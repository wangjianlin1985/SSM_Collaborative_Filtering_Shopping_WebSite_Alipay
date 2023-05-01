<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<div class="shop-page-layout section-padding-bottom" style="margin-top: 25px;">
	<div class="container">
		<div class="row">
			<div class="col-12">
				<section class="section-title">
					<h3 class="title">个性化推荐</h3>
				</section>
			</div>
			<div class="col-12">
				<div class="tab-content">
					<div class="tab-pane show active">
						<div class="row grid-view mb-n5">
							<c:choose>
   	  							<c:when test="${recommendItems!=null && recommendItems.size()>0}">
   	  								<c:forEach var="item" items="${recommendItems}" varStatus="status">
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
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>