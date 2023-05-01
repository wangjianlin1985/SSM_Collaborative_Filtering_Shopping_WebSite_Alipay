<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
 	<head>
	    <title>后台管理员 - 个性化购物商城推荐系统</title>
	    <%@ include file="./css.jsp"%>
    	<%@ include file="./js.jsp"%>
	</head>
	<body>
   		<div id="system">
	        <header>
	            <div class="left">
	                <a href="${pageContext.request.contextPath}/admin" style="font-size:17px">
	                    <i class="gi gi-leaf"></i> 个性化购物商城推荐系统 | 后台管理员
	                </a>
	            </div>
	            <div class="right">
	                <a href="javascript:void(0)" title="${sessionScope.session_admin.username }">
	                    	管理员：${sessionScope.session_admin.username }
	                </a>
	                <a href="${pageContext.request.contextPath}/admin/quit" title="退出登录">
	                    <i class="fa fa-power-off"></i>
	                </a>
	            </div>
	        </header>
	        <section class="body">
	            <%@ include file="./index_left.jsp"%>
	            <div class="right">
	                <iframe src="${pageContext.request.contextPath}/admin/content" 
	                	frameborder="0" id="rightiframe" name="rightiframe"></iframe>
	            </div>
	        </section>
   		</div>
	</body>
	<script>
		$('.navbar dl dt').click(function () {
            if (!$(this).siblings('dd').hasClass('active')) {
                $('.navbar dl dd').removeClass('active');
                $(this).siblings('dd').removeClass('active');
                $(this).siblings('dd').addClass('active');
            } else {
                $('.navbar dl dd').removeClass('active');
                $(this).siblings('dd').removeClass('active');
            }
	
            if (!$(this).hasClass('activeTop')) {
                $('.navbar dl dt').removeClass('activeTop');
                $(this).removeClass('activeTop');
                $(this).addClass('activeTop');
            } else {
                $('.navbar dl dt').removeClass('activeTop');
                $(this).removeClass('activeTop');
            }
        });
	
        $('.navbar .li a').click(function(){
            $('.navbar .li a').removeClass('activelinks');
            $(this).addClass('activelinks');
        });
   </script>
</html>