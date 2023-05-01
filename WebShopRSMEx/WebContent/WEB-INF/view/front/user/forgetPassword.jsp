<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>忘记密码 - 个性化购物商城推荐系统</title>
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
							<h3 class="title">忘记密码</h3>
						</section>
					</div>
					<div class="col-lg-7 col-md-12 mx-auto">
						<div class="login-register-wrapper">
							<div class="tab-content">
								<div class="tab-pane show active">
									<div class="login-form-container">
										<div class="login-register-form">
											<form action="front/user/doForgetPassword" method="post" id="form">
												<div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">用户名：</label>
				                                    <input name="username" value="" id="username" maxlength="255" type="text" placeholder="请输入用户名" 
				                                           class="input-text" style="display: inline-block;width:80%;">
				                                </div>
				                                <div class="form-group">
				                                    <label class="text" style="display: inline-block;width:15%;color: #666;font-weight: 600;">邮箱验证码：</label>
				                                    <input name="valcode" oninput="value=value.replace(/[^\d]/g,'')" id="valcode" maxlength="6" type="text" placeholder="请输入邮箱验证码"
				                                            class="input-text" style="display: inline-block;width:40%">
				                                    <div style="display: inline-block;" align="right">
					                                    <button type="button" id="valcodeButton" class="btn btn-light" onclick="doValcode()">获取验证码</button>
					                                </div>
				                                </div>
				                                <div class="text-center">
				                                    <a href="javascript:void(null)" class="btn btn-dark-ex" id="submitBtn">
				                                    	<span>&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;</span></a>
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
				if($("#username").val()=="" || $("#valcode").val()==""){
					layer.msg("数据不能为空!");
				}else{
					common_ajax_self_result($("#form").attr("action"),$("#form").serialize(),doCallback);
	         	}
			});
		});
	    
	    //回调函数
	    function doCallback(data){
	    	if(data.success>0){
				if(data.message!=null && data.message!=""){
					layer.alert(data.message);
					//setTimeout(function () {location.href=basePath+"login"; }, 5000);
				}else{
					layer.msg("操作成功！");
				}
				if(data.url!=null && data.url!=""){
					if(data.url=="reload"){
	      				setTimeout(function () {window.location.reload(); }, globalTimeout);  
	      			}else{
	      				setTimeout(function () {location.href=basePath+data.url; }, globalTimeout);
	      			}
				}
			}else{
				if(data.message!=null && data.message!=""){
					layer.msg(data.message);
				}else{
					layer.msg("操作失败！");
				}
			}
	    }
		
		//获取验证码
		function doValcode(){
			if($("#username").val()==""){
				layer.msg("请输入用户名!");
			}else{
				$("#valcodeButton").attr("disabled","disabled");
			    var starting = 60;
			    var time = setInterval(function () {
			        if (starting > 0) {
			            starting--;
			            $("#valcodeButton").html(starting+'s后重新发送');
			        } else if (starting == 0) {
			        	$("#valcodeButton").html("获取验证码");
			            clearInterval(time);
			            $("#valcodeButton").attr("disabled",false);
			        }
			    }, 1000);
				common_ajax_other("front/doValcode",$("#form").serialize());
			}
		}
    </script>
</html>