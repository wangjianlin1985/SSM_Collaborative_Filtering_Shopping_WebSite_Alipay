<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>评论信息修改 - 个性化购物商城推荐系统</title>
    	<%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
	<body class="productsgrid-page">
		<%@ include file="../common/header.jsp"%>
		<%@ include file="../common/banner2.jsp"%>
		<div class="whish-list-section section-padding-bottom">
			<div class="container">
				<div class="row">
					<div class="col-12">
						<section class="section-title">
							<h3 class="title" style="border-bottom:0">设置信息</h3>
						</section>
					</div>
					<%@ include file="../common/menu.jsp"%>
					<div class="col-lg-9 col-12 mb-5">
						<div class="tab-content">
							<div class="tab-pane fade active show">
								<div class="myaccount-content">
									<div class="account-details-form">
										<form action="front/comment/saveOrUpdate" method="post" id="form">
											<input type="hidden" name="id" value="${comment.id }">
											<div class="row">
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">商品名称：</label>
													<input value="${comment.item.itemname }" type="text" style="display: inline-block;width:60%;" 
														readonly="readonly">
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">评论内容：</label>
													<textarea name="content" id="contentT" maxlength="255" 
														style="height: 180px;padding:20px 10px 2px 20px;width: 60%;
														outline: 0;border: 1px solid #e6e6e6;color:#707070">${comment.content }</textarea>
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
		pathname = "${pageContext.request.contextPath}/front/comment/list";
		
		$(function(){
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
                if($("#contentT").val()==null || $("#contentT").val()==""){
                    layer.msg("数据不能为空!");return false;
                }
                common_ajax_other($("#form").attr("action"),$("#form").serialize());
	        });
	    });
    </script>
</html>