<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  	<head>
	    <title>后台管理员登录 - 个性化购物商城推荐系统</title>
	    <%@ include file="./css.jsp"%>
    	<%@ include file="./js.jsp"%>
  	</head>
  	<body>
	    <div id="login">
	        <form action="admin/doLogin" id="form" method="post">
		        <div class="center">
		            <dl>
		                <dt><i class="gi gi-leaf"></i><span> 个性化购物商城推荐系统 | 管理员登录</span></dt>
		                <dd><span><i class="fa fa-fw fa-user"></i></span><input 
		                	type="text" placeholder="请输入账号" id="username" name="username" value="admin" maxlength="255"></dd>
		                <dd><span><i class="fa fa-fw fa-lock"></i></span><input 
		                	type="password" placeholder="请输入密码" id="password" name="password" value="admin" maxlength="255"></dd>
		                <dd><button id="submitBtn" type="button">登录</button></dd>
		            </dl>
		        </div>
	    	</form>
	    </div>
	</body>
	<script>
		$(function(){
			$("#submitBtn").on("click",function(){
				var temp = true;
				$("#form input[type='text']").each(function(){
					if($(this).val()==null || $(this).val()==""){
						temp = false;
						return false;
					}
				});
				if($("#password").val()==null || $("#password").val()==""){
					temp = false;
				}
				if(!temp){
					layer.msg("数据不能为空!");return false;
				}
				common_ajax_other($("#form").attr("action"),$("#form").serialize());
			});
		});
	</script>
</html>