<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>商品类型添加或者修改 - 个性化购物商城推荐系统</title>
	    <%@ include file="../common/css.jsp"%>
    	<%@ include file="../common/js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>商品类型添加或者修改</li>
	        </ol>
	        <div class="tabli">
	            <span class="active">基本信息</span>
	        </div>
	        <div class="tabbody">
	            <div class="bodyli active">
	            	<form action="admin/type/doAddOrUpdate" method="post" id="form">
               	   		<input type="hidden" name="id" value="${type.id}"/>
               	   		<input type="text" id="hiddenTextTemp" value="hiddenTextTemp" 
               	   			name="hiddenTextTemp" style="display:none" />
		                <div class="inputbox">
		                    <span class="title">商品类型名称</span>
		                    <div class="inputright">
		                        <input type="text" id="typename" name="typename" 
                       				value="${type.typename}" maxlength="255" class="form-control input-sm">
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
	<script>
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
                common_ajax_other($("#form").attr("action"),$("#form").serialize());
	        });
	    });
	</script>
</html>