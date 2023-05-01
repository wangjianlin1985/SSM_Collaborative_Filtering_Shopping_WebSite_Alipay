<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML>
<html>
	<head>
    	<title>支付记录详情 - 个性化购物商城推荐系统</title>
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
										<form action="" method="post" id="form">
											<div class="row">
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">订单号：</label>
													${payrecord.orderid }
												</div>
												<div class="col-12" style="margin-bottom:25px;word-wrap: break-word; word-break: break-all;">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">支付宝交易流水号：</label>
													${payrecord.tradeno }
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">支付金额：</label>
													￥${payrecord.price }
												</div>
												<div class="col-12" style="margin-bottom:25px">
													<label class="text" style="display: inline-block;width:12%;font-weight: 600;">支付时间：</label>
													${payrecord.createtime }
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
		pathname = "${pageContext.request.contextPath}/front/payrecord/list";
    </script>
</html>