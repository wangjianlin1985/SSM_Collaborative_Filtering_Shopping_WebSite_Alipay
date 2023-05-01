<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>商品信息 - 个性化购物商城推荐系统</title>
    	<%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
    	<style type="text/css">
			.progress .bar {
			    float: left;
			    width: 0;
			    height: 100%;
			    font-size: 12px;
			    color: #fff;
			    text-align: center;
			    text-shadow: 0 -1px 0 rgba(0,0,0,0.25);
			    background-color: #faa732;
			    background-image: -moz-linear-gradient(top, #149bdf, #0480be);
			    background-image: -webkit-gradient(linear, 0 0, 0 100%, from(#149bdf), to(#0480be));
			    background-image: -webkit-linear-gradient(top, #149bdf, #0480be);
			    background-image: -o-linear-gradient(top, #149bdf, #0480be);
			    background-image: linear-gradient(to bottom, #faa732, #faa732);
			    background-repeat: repeat-x;
			    filter: progid:DXImageTransform.Microsoft.gradient(startColorstr='#ff149bdf', endColorstr='#ff0480be', GradientType=0);
			    -webkit-box-shadow: inset 0 -1px 0 rgba(0,0,0,0.15);
			    -moz-box-shadow: inset 0 -1px 0 rgba(0,0,0,0.15);
			    box-shadow: inset 0 -1px 0 rgba(0,0,0,0.15);
			    -webkit-box-sizing: border-box;
			    -moz-box-sizing: border-box;
			    box-sizing: border-box;
			    -webkit-transition: width .6s ease;
			    -moz-transition: width .6s ease;
			    -o-transition: width .6s ease;
			    transition: width .6s ease;
			}
		</style>  
  	</head>
	<body class="productsgrid-page">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/banner2.jsp"%>
		<section>
			<div class="container">
				<div class="row mb-n4">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">商品信息</h3>
						</section>
					</div>
					<div class="col-lg-3" style="margin-right:20px">
						<div class="modal-gallery-slider">
							<div class="gallery">
								<div class="">
									<div class="">
										<div class="" style="max-width: 300px;overflow: hidden;">
											<img src="/${applicationScope.uploadFileDir }/${item.image}" 
		                                    	title="${item.itemname }">
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-5 mb-4">
						<div class="modal-product-des">
							<h3 class="modal-product-title">
								<a href="javascript:void(null)">${item.itemname }</a>
							</h3>
							<div class="product-add-to-cart">
								<span class="control-label">商品类型：${type.typename }</span>
								<span class="control-label">商品价格：￥${item.price }</span>
								<span class="control-label">添加时间：${item.createtime }</span>
								<div class="product-variants" style="margin-bottom:0px">
									<div class="product-variants-item">
										<span class="control-label">收藏数量：${collectCount }</span>
									</div>
									<div class="product-variants-item">
										<span class="control-label">购买数量：${orderitemCount }</span>
									</div>
								</div>
								<div class="product-add-to-card">
									<c:if test="${collect==null}">
										<a class="product-add-to-card-item" href="javascript:doCollect(${item.id })"><i
											class="ion-ios-heart-outline"></i>添加收藏</a>
									</c:if>
									<c:if test="${collect!=null}">
										<a class="product-add-to-card-item" href="javascript:doCollect(${item.id })"><i
											class="ion-ios-heart"></i>取消收藏</a>
									</c:if>
									<a class="product-add-to-card-item" href="javascript:void(null)">
										评分：<div id="star" style="display: inline-block"></div>
									</a>
								</div>
								<div class="product-add-to-cart">
									<div class="product-count style d-flex my-4">
										<div class="count d-flex itemCountTemp">
											<form action="${pageContext.request.contextPath}/front/order/confirm" method="post" id="form4" class="row" style="">
												<input type="hidden" name="itemid" value="${item.id}" />
												<input type="number" id="count" name="count" min="1" max="100" step="1" value="1" readonly="readonly">
											</form>
											<div class="button-group">
												<button class="count-btn increment">
													<span class="ion-chevron-up"></span>
												</button>
												<button class="count-btn decrement">
													<span class="ion-chevron-down"></span>
												</button>
											</div>
										</div>
										<div>
											<button class="btn btn-dark-ex addCart" data-itemid="${item.id }">添加到购物车</button>
											<button class="btn btn-dark-ext" onclick="buy()">立即购买</button>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-4" style="text-align: left;float:none;display:inline-block;width:30%;
						border-left: 1px solid #d4d4d4;color:#444;padding-left:25px;padding-top:0px;height:auto">
						<div style="margin-bottom:15px;font-size: 20px;font-weight: 500;color:#444">商品评分分析</div>
						<div class="score" style="display: inline-block;margin-bottom:15px;color:#444">
							平均评分：<div id="star2" style="display: inline-block"></div>
							&nbsp;${scoreAnalysis.avgScore }/5.0
						</div>
						<div style="margin-bottom:15px;color: #444;">共 ${scoreAnalysis.scoreCount } 个用户评分</div>
						<c:forEach var="curScoreAnalysis" items="${scoreAnalysis.curScoreAnalysisList}" varStatus="status">
							<div>
								<div style="display: inline-block;">${curScoreAnalysis.curScore}星&nbsp;&nbsp;</div>
								<div class="progress progress-info" 
									style="display: inline-block;width:62%;margin-bottom: 0px;height: 20px;">
					              	<div class="bar" style="width: ${curScoreAnalysis.percent}%;"></div>
					            </div>
					            <div style="display: inline-block;">&nbsp;&nbsp;${curScoreAnalysis.percent}%</div>
							</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</section>
		<section style="padding: 40px 0;">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">商品详情</h3>
						</section>
					</div>
				</div>
				<div class="tab-content">
					<div class="tab-pane fade active show">
						<div class="row">
							<div class="col-12">
								<div class="single-product-desc">
									<p>${item.content }</p>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<div class="">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">相关推荐</h3>
						</section>
					</div>
					<div class="col-12">
						<div class="tab-content">
							<div class="tab-pane show active">
								<div class="row grid-view mb-n5">
									<c:choose>
		   	  							<c:when test="${relateItems!=null && relateItems.size()>0}">
		   	  								<c:forEach var="item2" items="${relateItems}" varStatus="status">
												<div class="col-sm-6 col-md-4 col-lg-3 mb-5" style="width:20%">
													<div class="product-card">
														<a href="${pageContext.request.contextPath}/front/item/view?itemid=${item2.id}" title="${item2.itemname}"
															class="product-thumb" style="height:204px;overflow: hidden">
															<img src="/${applicationScope.uploadFileDir }/${item2.image}" 
																alt="${item2.itemname}" style="">
														</a>
														<div class="product-content">
															<h4>
																<a href="${pageContext.request.contextPath}/front/item/view?itemid=${item2.id}" title="${item2.itemname}" 
																	class="product-title">
																	<c:if test="${fn:length(item2.itemname)>40}">
													                	${fn:substring(item2.itemname, 0, 40)} ...
													                </c:if>
													                <c:if test="${fn:length(item2.itemname)<=40}">
													                    ${item2.itemname}
													                </c:if>
																</a>
															</h4>
															<h5 style="font-size: 14px;">￥${item2.price }</h5>
														</div>
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
		<section style="padding: 40px 0 0px 0px;">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">商品评论</h3>
						</section>
					</div>
				</div>
				<div class="tab-content">
					<div class="tab-pane fade active show">
						<div class="single-product-desc">
							<div class="row">
								<div class="col-lg-7">
									<div class="review-wrapper">
										<c:choose>
			   	  							<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
			   	  								<c:forEach var="comment" items="${pageBean.list}" varStatus="status">
													<div class="single-review">
														<div class="review-img">
															<img src="/${applicationScope.uploadFileDir }/${comment.user.header }" 
																alt="${comment.user.username }">
														</div>
														<div class="review-content">
															<div class="review-top-wrap">
																<div class="review-left">
																	<div class="review-name">
																		<h4>${comment.user.username }</h4>
																	</div>
																	<div class="rating-product">
																		${comment.createtime }
																	</div>
																</div>
															</div>
															<div class="review-bottom">
																<p style="max-width: 100%;word-wrap: break-word; word-break: break-all;">${comment.content }</p>
															</div>
														</div>
													</div>
												</c:forEach>
		                           			</c:when>
							     			<c:otherwise>
							     				暂无数据
							     			</c:otherwise>
							   			</c:choose>
									</div>
									<form action="${pageContext.request.contextPath}/front/item/view" method="post" id="form">
							  			<input type="hidden" name="pageNum" value="${pageBean.pageNum}" id="pageNum">
							  			<input type="hidden" name="itemid" value="${item.id}">
							  		</form>
							  		<%@include file="../common/page.jsp" %>
								</div>
								<div class="col-lg-5">
									<div class="ratting-form-wrapper">
										<h3>添加评论</h3>
										<div class="ratting-form">
											<form action="#">
												<div class="star-box"></div>
												<div class="row">
													<div class="col-md-12">
														<div class="rating-form-style form-submit">
															<textarea name="comment" id="comment" placeholder="请输入评论内容..." maxlength="255"></textarea>
															<button type="button" onclick="doComment()" class="btn btn-dark-ex">&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;</button>
														</div>
													</div>
												</div>
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</section>
		<%@ include file="../common/footer.jsp"%>
	</body>
	<script src="${pageContext.request.contextPath}/static/js/jquery.raty.min.js"></script>
	<script type="text/javascript">
		//立即购买
		function buy(){
	        if(!doIsLogin())
	            return;
	        $("#form4").submit();
	    }
	
		//评分
	    $('#star').raty(
            {
            	score:parseFloat("${scorerecord.score==null?0:scorerecord.score}"),
				halfShow:true,
				readOnly:false,
				path: '${pageContext.request.contextPath}/static/images',
				hints: ['1分', '2分', '3分', '4分', '5分'],
				click: function(score, evt) {
		            var data = "itemid=${item.id}"+"&score="+score;
		            if(doIsLogin()){
		            	common_ajax_other("front/scorerecord/saveOrUpdate",data);
		            }
		        }
            }
        );
		
	  	//评分分析
		$('#star2').raty(
			{
				score:parseFloat("${scoreAnalysis.avgScore}"),
				halfShow:true,
				readOnly: true,
				path: '${pageContext.request.contextPath}/static/images',
				hints: ['1分', '2分', '3分', '4分', '5分'],
			}
		);
	  	
	  	//添加或者取消收藏
	  	function doCollect(itemid){
	  		if(doIsLogin()){
            	common_ajax_other("front/collect/save","itemid="+itemid);
            }
	  	}
	  	
	 	//评论
		function doComment(){
			var comment = $("#comment").val();
			if(comment==null || comment==""){
				layer.msg("评论内容不能为空！");
				return false;
			}
			if(doIsLogin()){
				var data = "itemid=${item.id}"+"&content="+comment;
				common_ajax_other("front/comment/saveOrUpdate",data);
			}
		}
    </script>
    <script>
		//购买数量,增加或者减少
		$(".itemCountTemp").each(function() {
			var g = $(this),
			j = g.find('input[type="number"]'),
			f = g.find(".increment"),
			k = g.find(".decrement"),
			l = j.attr("min"),
			h = j.attr("max");
			f.on("click",function() {
			    var i = parseFloat(j.val());
			    i = h <= i ? i: i + 1,
			    g.find('input[type="number"]').val(i),
			    g.find('input[type="number"]').trigger("change")
			}),
			k.on("click",function() {
			    var i = parseFloat(j.val());
			    i = i <= l ? i: i - 1,
			    g.find('input[type="number"]').val(i),
			    g.find('input[type="number"]').trigger("change")
			});
		});
	</script>
</html>