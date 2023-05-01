<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>注册 - 个性化购物商城推荐系统</title>
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
							<h3 class="title">用户注册</h3>
						</section>
					</div>
					<div class="col-lg-7 col-md-12 mx-auto">
						<div class="login-register-wrapper">
							<div class="tab-content">
								<div class="tab-pane show active">
									<div class="login-form-container">
										<div class="login-register-form">
											<form action="doRegister" method="post" id="form">
												<div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">用户名：</label>
				                                    <input name="username" id="username" maxlength="255" type="text" placeholder="请输入用户名" 
				                                           class="input-text" style="display: inline-block;width:80%;">
				                                </div>
				                                <div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">邮箱：</label>
				                                    <input name="email" id="email" maxlength="255" type="text" placeholder="请输入邮箱" 
				                                           class="input-text" style="display: inline-block;width:80%;">
				                                </div>
				                                <div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">密码：</label>
				                                    <input name="password" id="password" maxlength="255" type="password" placeholder="请输入密码"
				                                           class="input-text" style="display: inline-block;width:80%">
				                                </div>
				                                <div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">确认密码：</label>
				                                    <input name="passwordConf" id="passwordConf" maxlength="255" type="password" placeholder="请输入确认密码"
				                                           class="input-text" style="display: inline-block;width:80%">
				                                </div>
				                                <div class="text-center">
				                                    <a href="javascript:doRegister()" class="btn btn-dark-ex"><span>&nbsp;&nbsp;注&nbsp;&nbsp;册&nbsp;&nbsp;</span></a>
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
		function doRegister(){
			if($("#username").val()=="" || $("#password").val()==""){
				layer.msg("数据不能为空!");
			}else{
				//对电子邮件的验证
                var emailreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                if(!emailreg.test($("#email").val())){
                    layer.msg("邮箱格式不正确!");return false;
                }
				if($("#password").val()!=$("#passwordConf").val()){
					layer.msg("两次密码不一致!");return false;
				}
				common_ajax_other($("#form").attr("action"),$("#form").serialize());
         	}
		}
    </script>
</html>