//这个会拦截所有的ajax请求，是在ajax请求完成后跳转到这里，目的是验证session是否超时，会执行success中的方法后跳转到这里执行，
//不会执行controller中的方法
/*$.ajaxSetup({  
    contentType:"application/x-www-form-urlencoded;charset=utf-8",  
    complete:function(XMLHttpRequest,textStatus){  
          //通过XMLHttpRequest取得响应头，sessionstatus ，会先到ajax的error方法中，然后到这里            
          var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");   
          console.info("textStatus="+textStatus);
          console.info("sessionstatus="+sessionstatus);
          if(sessionstatus=="timeout"){  alert(1);
               //这里怎么处理在你，这里跳转的登录页面  
        		  layer.alert("操作失败！连接超时！");
        		  if (window != top){
        			setTimeout(function () {
                    	top.location.href = basePath+"adminLogin";
                    }, 1000);
        		  }else{
        			  location.href = basePath+"adminLogin";
        		  }
        		  return false; 
       }  
    }  
});*/

//公共ajax提交数据
function common_ajax(url,data){
	var tempUrl = url;
	var options = {
			url: basePath+url,
			type: 'post',
            dataType: 'json',
            data:data,
            success: function (data) {
            	if(data.success>0){
            		layer.msg("操作成功！");
              		if(data.url!=null && data.url!=""){
              			if(data.url=="reload"){
              				setTimeout(function () {window.location.reload(); }, globalTimeout); 
              			}else{
              				setTimeout(function () {location.href=basePath+data.url; }, globalTimeout);
              			}
              		}
              	}else{
              		layer.msg("操作失败！");
              	}
            },
            error:function(data){
            	errorFunction(data,tempUrl);
            }
     };
     $.ajax(options);
}

//公共ajax请求，自行处理返回结果
function common_ajax_self_result(url,data,callback){
	var tempUrl = url;
	var options = {
			url: basePath+url,
			type: 'post',
			dataType: 'json',
			data:data,
			success: function (data) {
				if(callback!=null){
					callback(data);
				}
			},
	        error:function(data){ 
	        	errorFunction(data,tempUrl);
	        }
	};
	$.ajax(options);
}


function common_ajax_other(url,data){
	var tempUrl = url;
	var options = {
			url: basePath+url,
			type: 'post',
			dataType: 'json',
			data:data,
			success: function (data) {
				if(data.success>0){
					if(data.message!=null && data.message!=""){
						layer.msg(data.message);
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
			},
		    error:function(data){
		    	errorFunction(data,tempUrl);
		    }
	};
	$.ajax(options);
}


function common_ajax_callback_other(url,data,callbackSuccess,callbackError){
	var tempUrl = url;
	var options = {
			url: basePath+url,
			type: 'post',
			dataType: 'json',
			data:data,
			success: function (data) {
				if(data.success>0){
					if(data.message!=null && data.message!=""){
						layer.msg(data.message);
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
					if(callbackSuccess!=null){
						callbackSuccess(data);
					}
				}else{
					if(data.message!=null && data.message!=""){
						layer.msg(data.message);
					}else{
						layer.msg("操作失败！");
					}
					if(callbackError!=null){
						callbackError(data);
					}
				}
			},
			error:function(data){
				errorFunction(data,tempUrl);
			}
	};
	$.ajax(options);
}

//如果操作失败，在这里处理
function errorFunction(data,tempUrl){
	console.info("ajaxError...");
	layer.msg("操作失败！请重新登录！" + data);
	var loginUrl = "";
	//这里是直接跳转到admin登录页面,如果是front或者其他，需要添加跳转到front
	if(tempUrl.indexOf("admin")!=-1){
		loginUrl = "admin/login";
	}else{
		if(tempUrl.indexOf("front")!=-1){
			loginUrl = "login";
		}
	}
    if (window != top){
    	setTimeout(function () {
    		top.location.href = basePath+loginUrl;
    	}, globalTimeout);
    }else{
    	/*
    	setTimeout(function () {
    		location.href = basePath+loginUrl;
    	}, globalTimeout);*/
    }
}

	