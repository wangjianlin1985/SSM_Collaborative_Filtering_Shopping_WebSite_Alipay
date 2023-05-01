<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>商品添加或者修改 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/static/webuploader-0.1.5/webuploader.css">
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>商品添加或者修改</li>
	        </ol>
	        <div class="tabli">
	            <span class="active">基本信息</span>
	        </div>
	        <div class="tabbody">
	            <div class="bodyli active">
	            	<form action="admin/item/doAddOrUpdate" method="post" id="form">
               	   		<input type="hidden" name="id" value="${item.id}"/>
		                <div class="inputbox">
		                    <span class="title">商品名称</span>
		                    <div class="inputright">
		                        <input type="text" id="itemname" name="itemname" 
                       				value="${item.itemname}" maxlength="255" class="form-control input-sm">
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">商品类型</span>
		                    <div class="inputright">
		                        <select name="typeid" id="typeid" class="form-control input-sm">
                       				<c:forEach var="type" items="${typeList }">
                       					<option value="${type.id }" ${type.id==item.typeid?"selected='selected'":"" }>${type.typename }</option>
                       				</c:forEach>
                       			</select>
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">商品价格</span>
		                    <div class="inputright">
		                        <input type="text" id="price" name="price" 
                       				value="${item.price}" maxlength="10" class="form-control input-sm">
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">商品封面</span>
		                    <div class="inputright">
		                        <input name="image" type="hidden" id="image" value="${item.image}"/>
			                    <div id="uploader_image">
								    <!--用来存放文件信息-->
								    <div id="thelist_image" class="uploader-list"></div>
								    <div class="btns">
								        <div id="picker_image">选择图片</div>
								        <button id="ctlBtn_image" class="btn btn-default" type="button">开始上传</button>
								    </div>
								</div>
		                    </div>
		                </div>
		                <div class="inputbox">
		                    <span class="title">商品简介</span>
		                    <div class="inputright" style="width:700px">
		                        <textarea id="UEcontainer">${item.content }</textarea>
		                        <input type="hidden" name="content" id="contentT"/>
								<script src="${pageContext.request.contextPath}/static/ueditor/lang/zh-cn/zh-cn.js" type="text/javascript" defer="defer"></script>
								<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.config.js"></script>
								<script type="text/javascript" src="${pageContext.request.contextPath}/static/ueditor/ueditor.all.min.js"></script>
								<script type="text/javascript">
									//富文本框开始
									//与滚动效果冲突
									var ueditor = UE.getEditor('UEcontainer', {
										//这里可以选择自己需要的工具按钮名称,此处仅选择如下五个
										   toolbars:[['FullScreen', 'Source', 'fontfamily','fontsize',
													  'forecolor','backcolor','bold','italic','underline',
													  'strikethrough','formatmatch','emotion','justifyleft',
													  'justifyright','justifycenter','justifyjustify',
													  'mergeright','mergedown','mergecells','link','preview']],
										   //关闭elementPath
											elementPathEnabled:false,
										   //默认的编辑区域高度
										   initialFrameHeight:300
									});
									//富文本框结束
								</script>
		                    </div>
		                </div>
		        	</form>
	            </div>
	        </div>
	        <div class="tbfooter">
	            <button class="btn btn-success" id="submitBtn" type="button">
	            	<i class="fa fa-floppy-o"></i> 保存设置
	            </button>
	        </div>
    	</div>
	</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/webuploader-0.1.5/webuploader.min.js"></script>
  	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/uploadfile.js"></script>
	<script>
		$(function(){
	    	//图片上传
	        uploadImage("admin/upload","image");
	    	
	    	//提交
	        $("#submitBtn").on("click",function(){
	        	$("#contentT").val(ueditor.getContent());
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
                if($("#typeid").val()==null || $("#typeid").val()==""){
                    layer.msg("数据不能为空!");return false;
                }
                if($("#image").val()==null || $("#image").val()==""){
                    layer.msg("请上传图片!");return false;
                }
                if($("#contentT").val()==null || $("#contentT").val()==""){
                    layer.msg("数据不能为空!");return false;
                }
                var price = $("#price").val();
    			var priceTest = /^(([1-9]\d*)|\d)(\.\d{1,2})?$/;
    			if(priceTest.test(price)==false){
    				layer.msg("商品价格格式不正确!");return false;
    			}
                common_ajax_other($("#form").attr("action"),$("#form").serialize());
	        });
	    });
	</script>
</html>