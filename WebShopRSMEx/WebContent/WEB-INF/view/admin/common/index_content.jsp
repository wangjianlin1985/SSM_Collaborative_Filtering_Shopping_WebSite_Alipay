<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
  	<head>
    	<title>控制面板 - 个性化购物商城推荐系统</title>
	    <%@ include file="./css.jsp"%>
    	<%@ include file="./js.jsp"%>
  	</head>
  	<body>
    	<div id="body">
	        <ol class="breadcrumb">
	            <li class="active"><a href="javascript:void(0)">系统</a></li>
	            <li>控制面板</li>
	        </ol>
	        <div class="barboxs">
	            <div class="leftbox">
	                <div class="liselect w300">
	                </div>
	            </div>
	        </div>
	        <div class="tablebox">
	            <table class="table table-bordered">
	                <thead>
	                    <tr>
	                        <th colspan="2" class="text-center">数据统计</th>
	                    </tr>
	                </thead>
	                <tbody>
	                    <tr>
	                        <td class="text-center" style="width:50%">用户数量</td>
	                        <td class="text-center" style="width:50%">${userCount }</td>
	                    </tr>
	                    <tr>
	                        <td class="text-center" style="width:50%">商品类型数量</td>
	                        <td class="text-center" style="width:50%">${typeCount }</td>
	                    </tr>
	                    <tr>
	                        <td class="text-center" style="width:50%">商品数量</td>
	                        <td class="text-center" style="width:50%">${itemCount }</td>
	                    </tr>
	                    <tr>
	                        <td class="text-center" style="width:50%">订单数量</td>
	                        <td class="text-center" style="width:50%">${orderCount }</td>
	                    </tr>
	                    <tr>
	                        <td class="text-center" style="width:50%">评分记录数量</td>
	                        <td class="text-center" style="width:50%">${scorerecordCount }</td>
	                    </tr>
	                    <tr>
	                        <td class="text-center" style="width:50%">收藏记录数量</td>
	                        <td class="text-center" style="width:50%">${collectCount }</td>
	                    </tr>
	                    <tr>
	                        <td class="text-center" style="width:50%">评论记录数量</td>
	                        <td class="text-center" style="width:50%">${commentCount }</td>
	                    </tr>
	                    <tr>
	                        <td colspan="2" style="width:100%;padding-top:20px">
	                        	<div style="width:auto;height:auto" align="center">
					        		<div id="main" style="width:100%;height:400px;display:inline-block;"></div>
					        		<div id="main2" style="width:100%;height:400px;display:inline-block;"></div>
					           </div>
	                        </td>
	                    </tr>
	                </tbody>
	            </table>
	        </div>
    	</div>
	</body>
</html>
<script src="${pageContext.request.contextPath}/static/js/jquery-1.12.4.min.js"></script>
<script src="${pageContext.request.contextPath}/static/js/echarts.min.js"></script>
<script>
	var typeOrderListTemp = ${typeOrderList==null?"":typeOrderList};
	var data1 = new Array();
	if(typeOrderListTemp!=""){
		for(var i=0;i<typeOrderListTemp.length;i++){
			var data = {};
			data.value = typeOrderListTemp[i][0];
			data.name = typeOrderListTemp[i][1];
			data1[data1.length] = data;
		}
	}
	// 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));
	// 指定图表的配置项和数据
        var option1 = {
        	    title: {
        	        text: '商品类型销量统计图',
        	        left: 'center'
        	    },
        	    tooltip: {
        	        trigger: 'item',
        	        formatter: '{a} <br/>{b} : {c} ({d}%)'
        	    },
        	    series: [
        	        {
        	            name: '销量',
        	            type: 'pie',
        	            radius: '55%',
        	            center: ['50%', '60%'],
        	            data: data1,
        	            emphasis: {
        	                itemStyle: {
        	                    shadowBlur: 10,
        	                    shadowOffsetX: 0,
        	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
        	                }
        	            }
        	        }
        	    ]
        	};
	
    	
        // 使用刚指定的配置项和数据显示图表。
        if(data1.length>0){
        	myChart.setOption(option1);	
        }else{
        	$("#main").css("display","none");
        }
</script>
<script>
	var orderPriceListTemp = ${orderPriceList==null?"":orderPriceList};
	var data2 = new Array();
	var data3 = new Array();
	var data4 = new Array();
	if(orderPriceListTemp!=""){
		for(var i=0;i<orderPriceListTemp.length;i++){
			var data = {};
			data.value = orderPriceListTemp[i][0];
			data.name = orderPriceListTemp[i][1];
			data2[data2.length] = data;
			data3[data3.length] = data.value;
			data4[data4.length] = data.name;
		}
	}
	console.info(data3);
	console.info(data4);
	// 基于准备好的dom，初始化echarts实例
    var myChart2 = echarts.init(document.getElementById('main2'));
	// 指定图表的配置项和数据
        var option2 = {
        		title: {
        	        text: '近一周营业额统计图',
        	        left: 'center'
        	    },
        		color: ['#3398DB'],
        	    tooltip: {
        	        trigger: 'axis',
        	        axisPointer: {            // 坐标轴指示器，坐标轴触发有效
        	            type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
        	        }
        	    },
        	    grid: {
        	        left: '3%',
        	        right: '4%',
        	        bottom: '3%',
        	        containLabel: true
        	    },
        	    xAxis: [
        	        {
        	            type: 'category',
        	            data: data4,
        	            axisTick: {
        	                alignWithLabel: true
        	            }
        	        }
        	    ],
        	    yAxis: [
        	        {
        	            type: 'value'
        	        }
        	    ],
        	    series: [
        	        {
        	            name: '销售金额',
        	            type: 'bar',
        	            barWidth: '60%',
        	            data: data3
        	        }
        	    ]
        	};
    	
        // 使用刚指定的配置项和数据显示图表。
        if(data2.length>0){
        	myChart2.setOption(option2);	
        }else{
        	$("#main2").css("display","none");
        }
</script>