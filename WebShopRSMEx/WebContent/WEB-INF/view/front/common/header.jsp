<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<header>
	<div class="header-middle-default d-none d-lg-block active-sticky">
		<div class="container">
			<div class="row align-items-center">
				<div class="col-6 col-lg-3" style="width:31%">
					<a href="${pageContext.request.contextPath}/" 
						style="color:inherit;font-size:28px" title="个性化购物商城推荐系统">个性化购物商城推荐系统</a>
				</div>
				<div class="col-6 col-lg-6 text-center" style="width:42%">
					<div class="blog-serch-form">
						<form action="${pageContext.request.contextPath}/front/item/list" method="post">
							<input class="form-control" type="text" id="itemname" name="itemname" value="${itemname }"  
								placeholder="请输入商品名称..." maxlength="255" >
							<button class="form-search-btn" type="submit" title="搜索">
								<span class="ion-ios-search-strong"></span>
							</button>
						</form>
					</div>
				</div>
				<div class="col-6 col-lg-3" align="center">
					<ul class="" style="font-size: 1rem;">
						<c:if test="${sessionScope.session_user==null}">
							<li class="quick-link-item" style="display:inline-block;">
								<a href="${pageContext.request.contextPath}/login" style="color: inherit;">登录</a>
							</li>
							<li class="quick-link-item" style="display:inline-block;">
								<a href="${pageContext.request.contextPath}/register" style="color: inherit;">注册</a>
							</li>
						</c:if>
						<c:if test="${sessionScope.session_user!=null}">
							<li class="quick-link-item main-menu-item" style="display:inline-block;">
								<a href="javascript:void(0)" style="color: inherit;height:44px;line-height:44px;font-size: 1rem;" 
									class="dungar-menu-item-title topbar-nav-link" 
									title="${sessionScope.session_user.username }">
									<c:if test="${fn:length(sessionScope.session_user.username)>5}">
					                	用户：${fn:substring(sessionScope.session_user.username, 0, 5)} ...
					                </c:if>
					                <c:if test="${fn:length(sessionScope.session_user.username)<=5}">
					                   	用户：${sessionScope.session_user.username }
					                </c:if>
								</a>
								<ul class="sub-menu" style="font-size: 14px;text-align: left;">
									<li class="sub-menu-item">
										<a href="${pageContext.request.contextPath}/front/user/view" class="sub-menu-link">设置信息</a>
									</li>
									<li class="sub-menu-item">
										<a href="${pageContext.request.contextPath}/front/order/list" class="sub-menu-link">我的订单</a>
									</li>
									<li class="sub-menu-item">
										<a href="${pageContext.request.contextPath}/quit" class="sub-menu-link">退出登录</a>
									</li>
								</ul>
							</li>
							<li class="quick-link-item" style="display:inline-block;">
								<a href="${pageContext.request.contextPath}/front/cart/list" style="color: inherit;height:44px;line-height:44px;font-size: 1rem;" 
									class="quick-link-link shopping-cart" title="购物车">
									<span class="wishlist-count" id="cartItemCount">0</span>
								</a>
							</li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
</header>