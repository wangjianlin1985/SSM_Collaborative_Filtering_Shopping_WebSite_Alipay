<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>用户列表 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>用户列表</li>
	        </ol>
	        <div class="barboxs">
	            <div class="leftbox">
	                <div class="liselect w300">
	                	<form action="${pageContext.request.contextPath}/admin/user/list" method="post" id="form1">
		                    <div class="input-group">
		                        <input type="text" id="username" name="username" value="${username }" maxlength="255"
		                        	class="form-control example-typeahead" placeholder="请输入用户名">
		                        <span class="input-group-btn">
		                            <button class="btn btn-success" onclick="javascript:$('#form1').submit()" title="搜索">
		                            	<i class="fa fa-search"></i>
		                            </button>
		                        </span>
		                    </div>
	                	</form>
	                </div>
	            </div>
	        </div>
	        <div class="tablebox">
	            <table class="table table-bordered">
	                <thead>
	                    <tr>
	                        <th class="text-center" width="50"><input type="checkbox" id="check5-all" name="check5-all"></th>
	                        <th class="text-center">序号</th>
	                        <th>用户名</th>
	                        <th>邮箱</th>
	                        <th>注册时间</th>
	                        <th class="text-center" width="85">操作</th>
	                    </tr>
	                </thead>
	                <tbody>
	                	<c:choose>
		                  	<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
		                  		<c:forEach var="user" items="${pageBean.list}" varStatus="status">
				                    <tr>
				                        <td class="text-center"><input type="checkbox" id="check5-td1" name="check5-td1"></td>
				                        <td class="cell-small text-center">${status.index+1 }</td>
				                        <td>${user.username }</td>
				                        <td>${user.email }</td>
				                        <td>${user.createtime }</td>
				                        <td class="text-center">
				                            <div class="btn-group">
				                                <a href="${pageContext.request.contextPath}/admin/user/view?userid=${user.id }" class="btn btn-xs btn-info" title="详情">
				                                	<i class="fa fa-info-circle"></i>
				                                </a>
				                                <a href="javascript:doDelete(${user.id })" class="btn btn-xs btn-danger" title="删除">
				                                	<i class="fa fa-trash-o"></i>
				                                </a>
				                            </div>
				                        </td>
				                    </tr>
		                  		</c:forEach>
		                    </c:when>
		                    <c:otherwise>
		                    	<tr>
			                      	<td colspan="6">暂无数据</td>
			                    </tr>
		                    </c:otherwise>
	                    </c:choose>
	                </tbody>
	            </table>
	        </div>
	        <form action="${pageContext.request.contextPath}/admin/user/list" method="post" id="form">
	        	<input type="hidden" name="pageNum" value="${pageBean.pageNum}" id="pageNum">
	        	<input type="hidden" name="username" value="${username }" >
	        </form>
	        <%@ include file="../common/page.jsp"%>
    	</div>
	</body>
	<script>
		//删除
		function doDelete(id){
		    layer.confirm("确认删除？",{icon:7,title:"删除"},function(index){
			   layer.close(index);
			   var data = "id="+id;
		       common_ajax_other("admin/user/delete",data);
		   	});
		}
	</script>
</html>