<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="col-12 mb-5">
	<nav aria-label="Page navigation">
		<ul class="pagination justify-content-center" style="line-height: 35px;">
			<li class="page-item">
				<span>共${pageBean.total}条数据&nbsp;&nbsp;${pageBean.pages}页</span>
			</li>
			<c:if test="${pageBean.pageNum!=1 && pageBean.pages!=0}">
				<li class="page-item">
					<a href="javascript:toPage(1)" class="page-link">&nbsp;&nbsp;首页&nbsp;&nbsp;</a>
					&nbsp;&nbsp;
					<a href="javascript:toPage(${pageBean.pageNum-1})" class="page-link">
						&nbsp;&nbsp;<span class="ion-android-arrow-dropleft"></span>&nbsp;&nbsp;上一页&nbsp;&nbsp;
					</a>
				</li>
			</c:if>
			<c:if test="${pageBean.pages!=0}">
				<li class="page-item active">
					<a href="javascript:toPage(${pageBean.pageNum})" class="page-link">${pageBean.pageNum}</a>
				</li>
			</c:if>
			<c:if test="${pageBean.pageNum!=pageBean.pages && pageBean.pages!=0}">
				<li class="page-item">
					<a href="javascript:toPage(${pageBean.pageNum+1})" class="page-link">
						&nbsp;&nbsp;下一页&nbsp;&nbsp;<span class="ion-android-arrow-dropright"></span>&nbsp;&nbsp;
					</a>
					&nbsp;&nbsp;
					<a href="javascript:toPage(${pageBean.pages})" class="page-link">&nbsp;&nbsp;尾页&nbsp;&nbsp;</a>
				</li>
			</c:if>
		</ul>
	</nav>
</div>
<script>
    function toPage(pageNum){
        $("#pageNum").val(pageNum);
        $("#form").submit();
    }
</script>