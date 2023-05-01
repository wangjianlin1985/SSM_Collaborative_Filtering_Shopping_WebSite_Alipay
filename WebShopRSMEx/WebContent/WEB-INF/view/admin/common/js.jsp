<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script src="${pageContext.request.contextPath}/static/js/JQuery2.1.4.js"></script>
<script src="${pageContext.request.contextPath}/static/layer/layer.js" type="text/javascript" charset="utf-8"></script>
<script src="${pageContext.request.contextPath}/static/js/common.js"></script>
<script>
	var basePath = "${pageContext.request.contextPath}/";
	var uploadBasePath = "${pageContext.request.scheme}://"+
			"${pageContext.request.serverName}:"+
			"${pageContext.request.serverPort}/"+
			"${applicationScope.uploadFileDir}/";
	var globalTimeout = 800;
	var isLoginGlobal = ${sessionScope.session_user==null?false:true};
</script>