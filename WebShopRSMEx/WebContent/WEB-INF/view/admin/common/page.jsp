<%@page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${pageBean!=null && pageBean.list!=null}">
	<div class="fy">
	    <ul class="pagination">
	        <li><a href="javascript:void(0)">共${pageBean.total}条数据</a></li>
	        <c:if test="${pageBean.pages!=0}">
	        	<li><a href="javascript:void(0)">第${pageBean.pageNum}/${pageBean.pages}页</a></li>
	        </c:if>
	        <c:if test="${pageBean.pageNum!=1 && pageBean.pages!=0}">
		        <li><a href="javascript:toPage(1)">首页</a></li>
		        <li><a href="javascript:toPage(${pageBean.pageNum-1})">上一页</a></li>
		    </c:if>
		    <c:if test="${pageBean.pageNum!=pageBean.pages && pageBean.pages!=0}">
		        <li><a href="javascript:toPage(${pageBean.pageNum+1})">下一页</a></li>
		        <li><a href="javascript:toPage(${pageBean.pages})">尾页</a></li>
		    </c:if>
	    </ul>
	</div>
</c:if>
<script>
	function toPage(pageNum){
		$("#pageNum").val(pageNum);
		$("#form").submit();
	}
</script>