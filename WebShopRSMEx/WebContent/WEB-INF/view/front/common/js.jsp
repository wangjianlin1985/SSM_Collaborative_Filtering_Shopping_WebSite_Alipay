<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.12.4.min.js"></script>
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
	//操作权限登录提示
	function doIsLogin(){
		if(!isLoginGlobal){
			layer.open({
			  type: 1,
			  skin: '', //加上边框layui-layer-rim
			  shade: 0.4,
              shadeClose: true,
			  title: "请先登录", //不显示标题
			  area: ['450px', '300px'], //宽高
			  content: '<div style="width:100%;height:100%;vertical-align: middle;text-align: center;" align="center">' +
                  '<span class="sign__text" style="margin-top: 40px;display: inline-block;">' +
                        '已有账号? <a href="${pageContext.request.contextPath}/login">点击登录!</a></span>' + '&nbsp;&nbsp;&nbsp;&nbsp;' +
                  '<span class="sign__text" style="margin-top: 40px;display: inline-block;">' +
                        '没有账号? <a href="${pageContext.request.contextPath}/register">点击注册!</a></span></div>'
			});
		}
		return isLoginGlobal;
	}
	//添加到购物车
	$(function(){
		$(".addCart").click(function(){
			if(!doIsLogin())
				return;
			var itemid = $(this).attr("data-itemid");
			var itemcount = $("#count").val();
			if(itemcount==null || itemcount==""){
				itemcount = 1;
			}
			common_ajax_other("front/cart/add",
				"itemid="+itemid+"&count="+itemcount);
		});
	});
	//查询当前登录用户购物车数量
	$(function(){
		if(isLoginGlobal){
			loadCartItemCount();
		}
	});
	//获取购物车商品数量方法
	function loadCartItemCount(){
		common_ajax_self_result('front/cart/getCartItemCount',
	    	"",loadCartItemCountCallbackSuccess)
	}
	//回调函数方法
    function loadCartItemCountCallbackSuccess(data){
        $("#cartItemCount").text(data.cartItemCount);
    }
</script>