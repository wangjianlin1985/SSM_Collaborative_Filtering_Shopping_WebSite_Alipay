<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<div class="left">
	<div class="navbar">
	    <dl>
	        <dt>
	        	<a href="${pageContext.request.contextPath}/admin/content" target="rightiframe">
	        		<i class="fa fa-fw fa-tachometer"></i><span>控制面板</span>
	        	</a>
	        </dt>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-user"></i> <span>用户管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/user/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>用户列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-tags"></i> <span>商品类型管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/type/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>商品类型列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-newspaper-o"></i> <span>商品管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/item/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>商品列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-paypal"></i> <span>订单管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/order/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>订单列表</span>
	                </a>
	            </div>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/payrecord/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>支付记录列表</span>
	                </a>
	            </div>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/refundrecord/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>退款记录列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-star"></i> <span>评分记录管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/scorerecord/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>评分记录列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-heart"></i> <span>收藏记录管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/collect/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>收藏记录列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	    <dl>
	        <dt><i class="fa fa-fw fa-send"></i> <span>评论记录管理</span></dt>
	        <dd>
	            <div class="li">
	                <a href="${pageContext.request.contextPath}/admin/comment/list" target="rightiframe">
	                	<i class="fa fa-fw fa-angle-right"></i><span>评论记录列表</span>
	                </a>
	            </div>
	        </dd>
	    </dl>
	</div>
</div>