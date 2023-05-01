<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>信息维护 - 个性化购物商城推荐系统</title>
    	<%@ include file="../common/css.jsp"%>
    	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/webuploader-0.1.5/webuploader.css">
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
										<form action="front/user/update" method="post" id="form">
											<div class="row">
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">用户名：</label>
													<input value="${user.username }" type="text" style="display: inline-block;width:60%;" 
														readonly="readonly">
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">邮箱：</label>
													<input id="email" name="email" value="${user.email }" maxlength="255" 
														placeholder="请输入邮箱" type="text" style="display: inline-block;width:60%;">
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">头像：</label>
													<div style="display: inline-block;width:60%;">
														<input name="header" type="hidden" id="header" value="${user.header}"/>
									                    <div id="uploader_header">
														    <!--用来存放文件信息-->
														    <div id="thelist_header" class="uploader-list"></div>
														    <div class="btns">
														        <div id="picker_header">选择图片</div>
														        <button id="ctlBtn_header" class="btn btn-default" type="button" 
														        	style="padding: .375rem .75rem;border-radius: 0;
														        	font-weight:normal;background-color:#ebebeb">开始上传</button>
														    </div>
														</div>
													</div>
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
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/webuploader-0.1.5/webuploader.min.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/uploadfile.js"></script>
	<script type="text/javascript">
		$(function(){
			//图片上传
	        uploadImage("front/upload","header");
        	$("#submitBtn").on("click",function(){
                var temp = true;
                $("#form").find("input[type='text']").each(function(){
                    if($(this).val()==null || $(this).val()==""){
                        temp = false;
                        return false;
                    }
                });
                if(!temp){
                    layer.msg("数据不能为空!");return false;
                }
                //对电子邮件的验证
                var emailreg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
                if(!emailreg.test($("#email").val())){
                    layer.msg("邮箱格式不正确!");return false;
                }
                if($("#header").val()==null || $("#header").val()==""){
                    layer.msg("请上传图片!");return false;
                }
                common_ajax_other($("#form").attr("action"),$("#form").serialize());
	        });
	    });
    </script>
</html>