<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="true" />
<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<c:choose>
		<c:when test="${state eq 'ongoing' }">
			<title>Deals招募 - 活动进行中</title>						
		</c:when>
		<c:when test="${state eq 'rewardCounting' }">
			<title>Deals招募 - 奖励确认中</title>
		</c:when>
		<c:when test="${state eq 'rewarding' }">
			<title>Deals招募 - 申领奖励</title>
		</c:when>
		<c:when test="${state eq 'complete' }">
			<title>Deals招募 - 活动完成</title>
		</c:when>
	</c:choose>
	
	<meta name="description" content="Deals招募 ">
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
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['preset_state.js']}" target="page-js"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<c:set var="state" value="ongoing"></c:set>
				
				<h2>爆款促销 活动名称</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可报名</span></div>
						<div class="step done"><span>已提交报名</span></div>
						<c:choose>
							<c:when test="${ rewarding }">
								<div class="step ${ state eq 'ongoing' ? 'current-step' : 'done' }"><span>活动进行中</span></div>
								<div class="step ${ state eq 'rewardCounting' ? 'current-step' : (state eq 'rewarding' or state eq 'complete' ? 'done' : '') }"><span>奖励确认中</span></div>
								<div class="step ${ state eq 'rewarding' ? 'current-step' : (state eq 'complete' ? 'done' : '') }"><span>申领奖励</span></div>
								<div class="step ${ state eq 'complete' ? 'current-step' : '' } last"><span>活动完成</span></div>
							</c:when>
							<c:otherwise>
								<div class="step ${ rewarding ? 'current-step' : '' } last"><span>活动进行中</span></div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box success">					
					
					<c:choose>
						<c:when test="${state eq 'ongoing' }">
							<h3>恭喜，您的报名已完成审核！</h3>
							<p class="desc">
								活动时间为YYYY-MM-DD 到  YYYY-MM-DD, <br />
								我们将在活动结束后尽快公布统计结果，请耐心等待！
							</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>							
						</c:when>
						<c:when test="${state eq 'rewardCounting' }">
							<h3>恭喜您已完成活动！</h3>
							<p class="desc">
								奖励结果统计中，请耐心等待！
							</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>
						</c:when>
						<c:when test="${state eq 'rewarding' or state eq 'agreement' }">
							<h3>恭喜，您的奖励为等值888元的ebay万里通积分</h3>
							<p class="desc">
								请在2015年8月8日前点击进入领奖流程完成申领。
							</p>
							<menu>
								<li>
									<c:choose>
										<c:when test="${ state eq 'rewarding' }">
											<a href="../index" class="btn">填写奖励申请协议</a>
										</c:when>
										<c:otherwise>
											<a href="../index" class="btn">上传奖励申请协议</a>
										</c:otherwise>
									</c:choose>
								</li>
							</menu>
						</c:when>
						<c:when test="${state eq 'complete' }">
							<h3>您已成功领取等值888元的ebay万里通积分</h3>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>
						</c:when>
					</c:choose>
					
									
				</div> <!-- active status box end -->
				
				<jsp:include page="activity.jsp"></jsp:include>
				
				<div class="mt20">
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/alert.jsp" %>
<%@ include file="../dialog/terms.jsp" %>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>
