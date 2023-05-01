<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>收藏记录列表 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>收藏记录列表</li>
	        </ol>
	        <form action="${pageContext.request.contextPath}/admin/collect/list" method="post" id="form1">
		        <div class="barboxs">
		            <div class="leftbox">
		                <div class="liselect w300">
		                    <div class="input-group" style="display: inline-block;width:100%">
		                        <input type="text" id="username" name="username" value="${username }" maxlength="255"
		                        	class="form-control example-typeahead" placeholder="请输入用户名">
		                    </div>
		                </div>
		                <div class="liselect w300">
		                    <div class="input-group">
		                        <input type="text" id="itemname" name="itemname" value="${itemname }" maxlength="255"
		                        	class="form-control example-typeahead" placeholder="请输入商品名称">
		                        <span class="input-group-btn">
		                            <button class="btn btn-success" onclick="javascript:$('#form1').submit()" title="搜索">
		                            	<i class="fa fa-search"></i>
		                            </button>
		                        </span>
		                    </div>
		                </div>
		            </div>
		        </div>
	        </form>
	        <div class="tablebox">
	            <table class="table table-bordered">
	                <thead>
	                    <tr>
	                        <th class="text-center" width="50"><input type="checkbox" id="check5-all" name="check5-all"></th>
	                        <th class="text-center">序号</th>
	                        <th>用户名</th>
	                        <th>商品名称</th>
	                        <th>收藏时间</th>
	                        <th class="text-center" width="85">操作</th>
	                    </tr>
	                </thead>
	                <tbody>
	                	<c:choose>
		                  	<c:when test="${pageBean!=null && pageBean.list!=null && pageBean.list.size()>0}">
		                  		<c:forEach var="collect" items="${pageBean.list}" varStatus="status">
				                    <tr>
				                        <td class="text-center"><input type="checkbox" id="check5-td1" name="check5-td1"></td>
				                        <td class="cell-small text-center">${status.index+1 }</td>
				                        <td>${collect.user.username }</td>
				                        <td>${collect.item.itemname }</td>
				                        <td>${collect.createtime }</td>
				                        <td class="text-center">
				                            <div class="btn-group">
				                                <a href="javascript:doDelete(${collect.id })" class="btn btn-xs btn-danger" title="删除">
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
	        <form action="${pageContext.request.contextPath}/admin/collect/list" method="post" id="form">
	        	<input type="hidden" name="pageNum" value="${pageBean.pageNum}" id="pageNum">
	        	<input type="hidden" name="username" value="${username }" >
	        	<input type="hidden" name="itemname" value="${itemname }" >
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
		       common_ajax_other("admin/collect/delete",data);
		   	});
		}
	</script>
</html>