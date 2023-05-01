<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>评论详情 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>评论详情</li>
	        </ol>
	        <div class="tabli">
	            <span class="active">基本信息</span>
	        </div>
	        <div class="tabbody">
	            <div class="bodyli active">
	            	<form action="admin/item/doAddOrUpdate" method="post" id="form">
               	   		<input type="hidden" name="id" value="${item.id}"/>
		                <div class="inputbox">
		                    <span class="title">用户名</span>
		                    <div class="inputright">
		                        ${comment.user.username }
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">商品名称</span>
		                    <div class="inputright">
		                        ${comment.item.itemname }
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">评论内容</span>
		                    <div class="inputright" style="word-wrap: break-word; word-break: break-all;">
		                        ${comment.content }
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">评论时间</span>
		                    <div class="inputright">
		                        ${comment.createtime }
		                    </div>
		                </div>
		        	</form>
	            </div>
	        </div>
    	</div>
	</body>
</html>