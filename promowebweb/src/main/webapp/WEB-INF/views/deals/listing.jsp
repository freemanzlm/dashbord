<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<c:choose>
		<c:when test="${state eq 'inquiry' }">
			<title>Deals - 报名预审中</title>					
		</c:when>
		<c:when test="${state eq 'approved' }">
			<title>Deals - 确认报名刊登</title>
		</c:when>
	</c:choose>
	
	<meta name="description" content="爆款促销 ">
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
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_listing.js']}" target="page-js"></res:useJs>
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
						<div class="step done"><span>已提交报名</span></div>
						<div class="step ${ state eq 'inquiry' ? 'current-step' : 'done' }"><span>报名预审中</span></div>
						<div class="step ${ state eq 'approved' ? 'current-step' : '' }"><span>确认报名刊登</span></div>
						<div class="step"><span>活动进行中</span></div>
						<div class="step"><span>奖励确认中</span></div>
						<div class="step"><span>申领奖励</span></div>
						<div class="step last"><span>活动完成</span></div>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box">
					<c:choose>
						<c:when test="${state eq 'inquiry' }">
							<h3>您已成功提交预审！请耐心等待预审结果。</h3>
							
							<c:choose>
								<c:when test="${ not expired }">
									<p class="desc">需要您确认通过预审的刊登完成报名！</p>
								</c:when>
								<c:otherwise>
									<p class="desc">已超过报名有效期，您无法再修改刊登内容</p>
								</c:otherwise>
							</c:choose>
						</c:when>
						<c:when test="${state eq 'approved' }">
							<h3>您已成功通过预审！</h3>
							<c:choose>
								<c:when test="${ not expired }">
									<p class="desc">请于YYYY-MM-DD前在如下刊登中选择您要参加活动的刊登并提交报名。</p>
								</c:when>
								<c:otherwise>
									<p class="desc">已超过报名有效期，您无法再修改刊登内容</p>
								</c:otherwise>
							</c:choose>							
						</c:when>
					</c:choose>
					
					<menu>
						<a href="../index" class="btn">返回活动列表</a>
					</menu>	
				</div> <!-- active status box end -->
				
				<jsp:include page="activity.jsp"></jsp:include>
				
				<div class="mt20 my-listing">
					<c:choose>
						<c:when test="${expired eq 'inquiry' or expired }">
							<h3>我提交的刊登</h3>
						</c:when>
						<c:when test="${state eq 'approved' and not expired }">
							<h3>我提交的刊登<small>（已选 <span>0</span> 项）</small></h3>
						</c:when>
					</c:choose>
					
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
				
				<c:if test="${(state eq 'approved') && (expired eq false) }">
					<div class="mt20 page-bottom-actions">
						<form action="preview" method="post">
							<input type="hidden" name="listings" value="100000, 4324324324, 4389234, 3432430" />
							<label for="accept"><input type="checkbox" id="accept" disabled/>我已阅读并接受 <a class="terms-conditions" href="javascript:void(0)">法律协议</a></label> <br /><br />
							<button id="form-btn" class="btn" disabled type="submit">预览报名信息</button>
						</form>
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
<jsp:include page="../dialog/terms.jsp"></jsp:include>

<script type="text/javascript">
	var pageData = {
		state: '${ state }',
		expired: ${ expired eq true }
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>
