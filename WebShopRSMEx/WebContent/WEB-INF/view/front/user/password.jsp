<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>密码安全 - 个性化购物商城推荐系统</title>
    	<%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
	<body class="productsgrid-page">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/banner2.jsp"%>
		<div class="section-padding-bottom">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title">设置信息</h3>
						</section>
					</div>
					<%@ include file="../common/menu.jsp"%>
					<div class="col-lg-9 col-12 mb-5">
						<div class="tab-content">
							<div class="tab-pane fade active show">
								<div class="myaccount-content">
									<div class="account-details-form">
										<form action="front/user/updatePassword" method="post" id="form">
											<div class="row">
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">原密码：</label>
													<input id="oldPassword" name="oldPassword" maxlength="255" 
														type="password" style="display: inline-block;width:60%;">
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">新密码：</label>
													<input id="password" name="password" maxlength="255" 
														type="password" style="display: inline-block;width:60%;">
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">确认密码：</label>
													<input id="confPassword" name="confPassword" maxlength="255" 
														type="password" style="display: inline-block;width:60%;">
												</div>
												<div class="col-12" style="margin-top:30px">
													<button type="button" id="submitBtn" class="btn btn-dark-ex">&nbsp;&nbsp;提&nbsp;&nbsp;交&nbsp;&nbsp;</button>
												</div>
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
		<%@ include file="../common/footer.jsp"%>
	</body>
	<script type="text/javascript">
		$(function(){
	        $("#submitBtn").on("click",function(){
                var temp = true;
                $("#form").find("input[type='password']").each(function(){
                    if($(this).val()==null || $(this).val()==""){
                        temp = false;
                        return false;
                    }
                });
                if(!temp){
                    layer.msg("数据不能为空!");return false;
                }
                if($("#password").val()!=$("#confPassword").val()){
                    layer.msg("两次密码不一致!");return false;
                }
                common_ajax_other($("#form").attr("action"),$("#form").serialize());
	        });
	    });
    </script>
</html>