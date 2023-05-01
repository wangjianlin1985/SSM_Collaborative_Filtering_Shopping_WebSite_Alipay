<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>用户详情 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>用户详情</li>
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
		                        ${user.username }
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">邮箱</span>
		                    <div class="inputright">
		                        ${user.email }
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">头像</span>
		                    <div class="inputright">
		                        <img src="/${applicationScope.uploadFileDir }/${user.header }" style="width:100px;height:100px">
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">注册时间</span>
		                    <div class="inputright">
		                        ${user.createtime }
		                    </div>
		                </div>
		        	</form>
	            </div>
	        </div>
    	</div>
	</body>
</html>