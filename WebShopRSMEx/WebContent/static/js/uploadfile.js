/**
 * 文件上传js
 */

/**
 * 上传图片
 */
function uploadImage(url,newFileNameId){
	
	var $list = $("#thelist_"+newFileNameId);
    var $btn = $("#ctlBtn_"+newFileNameId);
    var pickerId = "picker_"+newFileNameId;
    //缩略图高度和宽度 （单位是像素），当宽高度是0~1的时候，是按照百分比计算，具体可以看api文档
    var thumbnailWidth = 100;  
    var thumbnailHeight = 100;
	
	//回显图片
	var fileUrl = $("#"+newFileNameId).val();
	if(fileUrl!=null && fileUrl!=""){
		$list.html('<div id="defaultUrl" class="file-item">'+
			'<img src="'+uploadBasePath+fileUrl+'" '+
			'style="width:'+thumbnailWidth+'px;height:'+thumbnailHeight+'px"/>'+
			'<div class="info">'+fileUrl+'</div></div>');
	}
	
	var uploader = WebUploader.create({
	    // swf文件路径
	    swf: basePath+'/static/webuploader-0.1.5/Uploader.swf',
	    // 文件接收服务端。
	    server: basePath+url,
	    // 选择文件的按钮。可选。
	    // 内部根据当前运行是创建，可能是input元素，也可能是flash.
	    pick: {
            id: "#"+pickerId,
            multiple: false//只能选择一个文件上传
        },
	    resize: false,//不压缩image,默认如果是jpeg，文件上传前会压缩一把再上传！
	    accept: {//只允许选择图片文件。
	        title: 'Images',
            extensions: 'gif,jpg,jpeg,bmp,png',
            mimeTypes: 'image/jpg,image/jpeg,image/png'//修改这行，加快上传文件框的打开速度
	    },
	    //选完文件后，是否自动上传。
	    auto: false,
	    fileNumLimit: 1
	});
	
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
		$list.empty();
		var $li = $('<div id="' + file.id + '" class="file-item ">' +  
	                   '<img>' + 
	                   '<div class="info">' + file.name + '</div>' +  
	                '</div>');
	    var $img = $li.find('img');
        $list.append( $li );// $list为容器jQuery实例  
        // 创建缩略图,如果为非图片文件，可以不用调用此方法。  
        // thumbnailWidth x thumbnailHeight 为 100 x 100  
        uploader.makeThumb( file, function( error, src ) {   //webuploader方法  
        	if ( error ) {  
        		$img.replaceWith('<span>不能预览</span>');  
        		return;  
        	}  
        	$img.attr( 'src', src );
        }, thumbnailWidth, thumbnailHeight ); 
	});
	
	uploader.on('beforeFileQueued',function(file){
	    uploader.reset();    //重置队列文件
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress span');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file,response) {
	    $('#'+file.id ).addClass('upload-state-done');
	    layer.msg("图片上传成功！");
	    console.info(response.newFileName);
	    $("#"+newFileNameId).val(response.newFileName);
	});

	// 文件上传失败，显示上传出错。
	uploader.on('uploadError', function( file ) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');
	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }
	    $error.text('上传失败');
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on('uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	
	$btn.on('click', function(){
		if(($list.text()==null || $list.text()=="") 
				|| $list.find("#defaultUrl").length>0){
			layer.msg("请先选择图片！");
			return false;
		}
        console.log("上传开始...");
        uploader.upload();
        console.log("上传结束...");
      });
	
}	


/**
 * 上传视频（上传其他文件可复制uploadVideo方法）
 */
function uploadVideo(url,newFileNameId){
	
	var $list = $("#thelist_"+newFileNameId);
    var $btn = $("#ctlBtn_"+newFileNameId);
    var pickerId = "picker_"+newFileNameId;  
    
    //回显文件名称
	var fileUrl = $("#"+newFileNameId).val();
	if(fileUrl!=null && fileUrl!=""){
		$list.html('<div id="defaultUrl" class="file-item">'+
			'<div class="info">'+fileUrl+'</div></div>');
	}
    
	var uploader = WebUploader.create({
	    // swf文件路径
		swf: basePath + '/static/webuploader-0.1.5/Uploader.swf',
	    // 文件接收服务端。
	    server: basePath+url,
	    pick: {
            id: "#"+pickerId,
            //只能选择一个文件上传
            multiple: false
        },
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	    accept: {
	    	 extensions: 'mp4,flv',
		     mimeTypes: 	'.mp4,.flv,' //修改这行，加快上传文件框的打开速度
	    },
	 // 选完文件后，是否自动上传。
	    auto: false,
	    fileNumLimit: 1,
	    fileSizeLimit:50 * 1024 * 1024//50MB
	});
	
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
		$list.empty();
	    var $li = $(
	            '<div id="' + file.id + '" class="file-item">' +
	                '<div class="info">' + file.name + '</div>' +
	            '</div>'
	            );
	    // $list为容器jQuery实例
	    $list.append( $li ); 
	});
	
	uploader.on('beforeFileQueued',function(file){
	    uploader.reset();    //重置队列文件
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress span');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file,response) {
	    $( '#'+file.id ).addClass('upload-state-done');
	    layer.msg("文件上传成功！");
	    $("#"+newFileNameId).val(response.newFileName);
	});

	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');
	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }
	    $error.text('上传失败');
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	
	$btn.on( 'click', function() {  
		if(($list.text()==null || $list.text()=="")
				|| $list.find("#defaultUrl").length>0){
			layer.msg("请先选择视频！");
			return false;
		}
        console.log("上传开始...");  
        uploader.upload();  
        console.log("上传结束...");  
      });
	
}	


/**
 * 上传视频（上传其他文件可复制uploadVideo方法）
 */
function uploadMp3(url,newFileNameId){
	
	var $list = $("#thelist_"+newFileNameId);
    var $btn = $("#ctlBtn_"+newFileNameId);
    var pickerId = "picker_"+newFileNameId;  
    
    //回显文件名称
	var fileUrl = $("#"+newFileNameId).val();
	if(fileUrl!=null && fileUrl!=""){
		$list.html('<div id="defaultUrl" class="file-item">'+
			'<div class="info">'+fileUrl+'</div></div>');
	}
    
	var uploader = WebUploader.create({
	    // swf文件路径
		swf: basePath + '/static/webuploader-0.1.5/Uploader.swf',
	    // 文件接收服务端。
	    server: basePath+url,
	    pick: {
            id: "#"+pickerId,
            //只能选择一个文件上传
            multiple: false
        },
	    // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
	    resize: false,
	    accept: {
	    	 extensions: 'mp3',
		     mimeTypes: 	'.mp3' //修改这行，加快上传文件框的打开速度
	    },
	 // 选完文件后，是否自动上传。
	    auto: false,
	    fileNumLimit: 1,
	    fileSizeLimit:50 * 1024 * 1024//50MB
	});
	
	// 当有文件添加进来的时候
	uploader.on( 'fileQueued', function( file ) {
		$list.empty();
	    var $li = $(
	            '<div id="' + file.id + '" class="file-item">' +
	                '<div class="info">' + file.name + '</div>' +
	            '</div>'
	            );
	    // $list为容器jQuery实例
	    $list.append( $li ); 
	});
	
	uploader.on('beforeFileQueued',function(file){
	    uploader.reset();    //重置队列文件
	});
	
	// 文件上传过程中创建进度条实时显示。
	uploader.on( 'uploadProgress', function( file, percentage ) {
	    var $li = $( '#'+file.id ),
	        $percent = $li.find('.progress span');
	});

	// 文件上传成功，给item添加成功class, 用样式标记上传成功。
	uploader.on( 'uploadSuccess', function( file,response) {
	    $( '#'+file.id ).addClass('upload-state-done');
	    layer.msg("文件上传成功！");
	    $("#"+newFileNameId).val(response.newFileName);
	});

	// 文件上传失败，显示上传出错。
	uploader.on( 'uploadError', function( file ) {
	    var $li = $( '#'+file.id ),
	        $error = $li.find('div.error');
	    // 避免重复创建
	    if ( !$error.length ) {
	        $error = $('<div class="error"></div>').appendTo( $li );
	    }
	    $error.text('上传失败');
	});

	// 完成上传完了，成功或者失败，先删除进度条。
	uploader.on( 'uploadComplete', function( file ) {
	    $( '#'+file.id ).find('.progress').remove();
	});
	
	$btn.on( 'click', function() {  
		if(($list.text()==null || $list.text()=="")
				|| $list.find("#defaultUrl").length>0){
			layer.msg("请先选择视频！");
			return false;
		}
        console.log("上传开始...");  
        uploader.upload();  
        console.log("上传结束...");  
      });
	
}


	


