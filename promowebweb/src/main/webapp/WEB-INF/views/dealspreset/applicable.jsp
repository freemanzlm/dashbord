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
	<title>Deals预置</title>
	<meta name="description" content="Deals预置">
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
	<res:useJs value="${res.js.local.js.page['preset_applicable.js']}" target="page-js"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>爆款促销 活动名称</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step current-step"><span>可报名</span></div>
						<div class="step"><span>已提交报名</span></div>
						<div class="step ${ rewarding ? '' : 'last' }"><span>活动进行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>奖励确认中</span></div>
							<div class="step"><span>申领奖励</span></div>
							<div class="step last"><span>活动完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<jsp:include page="activity.jsp"></jsp:include>
				
				<div class="mt20 my-listing">
					<h3>选择我的刊登报名 <small>（已选 <span>0</span> 项）</small></h3>
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
				
				<div class="mt20" style="text-align: center;">
					<form id="listing-form" action="preview" method="post">
						<input type="hidden" name="promoId" value="4324324"/>
						<input type="hidden" name="listings" value="[]" />
						<label for="accept"><input type="checkbox" id="accept" ${ termsAccpted ? '' : 'disabled' }/>我已阅读并接受 <a class="terms-conditions" href="javascript:void(0)">法律协议</a></label> <br /><br />
						<button id="form-btn" class="btn" type="submit" disabled>预览报名信息</button>
					</form>
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
