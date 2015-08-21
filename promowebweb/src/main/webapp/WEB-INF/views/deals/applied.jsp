<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="categoryId" value="6000" />

<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals - 已提交报名</title>
	<meta name="description" content="爆款促销 - 已提交报名">
	<meta name="author" content="eBay: Apps">
	<res:cssSlot id="head" />
	<res:cssSlot id="head-css" />
	
	<script type="text/javascript">
		var BizReport = BizReport || {};
	</script>
	<res:jsSlot id="head" />	
	<res:jsSlot id="head-js" />
	
	<%--module "ebay.page" add Resets and Global css --%>
	<r:includeModule name="ebay.UIComponentsResource.page" cssSlot="head" />
	<res:useCss value="${res.css.local.css['jquery.dataTables.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.form_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['locale_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['AlertDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.table['ListingSelectTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_applied.js']}" target="page-js"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>Deals 活动名称</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可报名</span></div>
						<div class="step current-step"><span>已提交报名</span></div>
						<div class="step"><span>活动进行中</span></div>
						<div class="step"><span>奖励确认中</span></div>
						<div class="step"><span>可申领奖励</span></div>
						<div class="step last"><span>活动完成</span></div>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box">
					<h3>您已成功提交报名！请耐心等待预审结果。</h3>
					<p class="desc">
						<c:choose>
							<c:when test="${not expired }">
								在报名有效期内，您可以重新选择预报名的刊登，并重新提交 
							</c:when>
							<c:otherwise>
								已超过报名有效期，您无法再修改刊登内容
							</c:otherwise>
						</c:choose>
					</p>
					<menu>
						<a href="" class="btn">返回活动列表</a>
					</menu>					
				</div> <!-- active status box end -->
				
				<div class="activity-detail">
					<div class="activity-time">
						<strong>报名截止时间：2015.02.01</strong>
						<strong style="margin-left: 90px;">活动时间：2015.02.06 - 2015.04.01</strong>
					</div>
					<div class="table activity-brief">
						<div class="table-row">
							<div class="table-cell" style="width: 64px;">活动简介：</div>
							<div class="table-cell">
								<p>1.此活动仅限eBay美国网站（ebay.com）、eBay英国网站（ebay.co.uk）、eBay德国网站（ebay.de）、
										eBay澳洲网站（ebay.com.au）及eBay汽车网站（ebay.com/motors）（“活动网站”）</p>
								<p>2.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
										的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
								<p>3.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
										的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
								<p>4.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
										的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
								<p>5.卖家必须在中国时间2015年2月1日00:00之前登陆eBay热卖品牌降价促销活动列表，勾选及确认要参加活动的刊登物品，并确保与中国时间2015年2月5日15：00前将在活动网站刊登物品
										的售价控制在eBay指定售价之内直至活动结束或者确保于中国时间2015年2月5日15：00前将在活动网站刊登物品的售价控制在eBay指定售价之内直至销量超过eBay指定最低销量的两倍以上。</p>
							</div>
						</div>
					</div>
					
					<div class="activity-law">
						<strong>法律协议：点击查看 <a href="javascript:void(0)">法律协议</a></strong>
					</div>
				</div>

				<div class="mt20 my-listing">
					<h3><strong>我提交的刊登</strong></h3>
					<jsp:include page="../table/listingStates.jsp"></jsp:include>
				</div>	
				
				<c:if test="${ not expired }">
					<div class="mt20">
						<div class="listings-upload">
							<h3>重新上传我要提交的刊登</h3>
							<p class="mt10">您可以通过下载<a href="javascript:void(0)" target="_blank">已提交的刊登</a>修改并重新上传您的刊登参与本活动。</p>
							<p class="mt10">您新上传的数据将完全替换原数据。提交数据需再次接受Deals招募法律协议。</p>
							<form action="upload" class="mt30" method="post">
								选择上传您的刊登列表 
								<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" accept="*.xsl" /> <button class="btn" style="margin-left: 3px;">选择</button></span>
							</form>
						</div>
					</div>
					
					<div class="mt20 page-bottom-actions">
						<button class="btn" title="在报名截止之前，您可以重新勾选报名的刊登。">预览修改报名信息</button>
					</div>	
				</c:if>
						
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/alert.jsp" %>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>
