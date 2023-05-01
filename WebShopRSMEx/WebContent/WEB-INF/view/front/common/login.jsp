<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>登录 - 个性化购物商城推荐系统</title>
		<%@ include file="../common/css.jsp"%>
		<%@ include file="../common/js.jsp"%>
	</head>
	<body class="productsgrid-page">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/banner2.jsp"%>
		<div class="login-register-area section-padding-bottom">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">用户登录</h3>
						</section>
					</div>
					<div class="col-lg-7 col-md-12 mx-auto">
						<div class="login-register-wrapper">
							<div class="tab-content">
								<div class="tab-pane show active">
									<div class="login-form-container">
										<div class="login-register-form">
											<form action="doLogin" method="post" id="form">
												<div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">用户名：</label>
				                                    <input name="username" value="" id="username" maxlength="255" type="text" placeholder="请输入用户名" 
				                                           class="input-text" style="display: inline-block;width:80%;">
				                                </div>
				                                <div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">密码：</label>
				                                    <input name="password" value="" id="password" maxlength="255" type="password" placeholder="请输入密码"
				                                           class="input-text" style="display: inline-block;width:80%">
				                                </div>
				                                <div class="text-center">
				                                    <a href="javascript:void(null)" class="btn btn-dark-ex" id="submitBtn">
				                                    	<span>&nbsp;&nbsp;登&nbsp;&nbsp;录&nbsp;&nbsp;</span></a>
				                                </div>
				                                <div class="" align="right">
				                                    <a href="${pageContext.request.contextPath}/front/user/forgetPassword" class="" id="">
				                                    	<span>忘记密码？</span></a>
				                                </div>	
											</form>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="../common/footer.jsp"%>
	</body>
	<script type="text/javascript">
		jQuery(document).ready(function() {
			$("#submitBtn").on("click",function(){
				if($("#username").val()=="" || $("#password").val()==""){
					layer.msg("数据不能为空!");
				}else{
					common_ajax_other($("#form").attr("action"),$("#form").serialize());
	         	}
			});
		});
    </script>
</html>