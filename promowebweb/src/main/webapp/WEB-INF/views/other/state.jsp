<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="state" value="ongoing"></c:set>

<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<c:choose>
		<c:when test="${state eq 'ongoing' }">
			<title>Deals - 活动进行中</title>						
		</c:when>
		<c:when test="${state eq 'rewarding' }">
			<title>Deals - 奖励确认中</title>
		</c:when>
		<c:when test="${state eq 'claimable' }">
			<title>Deals - 可申领奖励</title>
		</c:when>
		<c:when test="${state eq 'complete' }">
			<title>Deals - 活动完成</title>
		</c:when>
	</c:choose>
	
	<meta name="description" content="其它活动">
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
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>其它活动 活动名称</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step ${ state eq 'ongoing' ? 'current-step' : 'done' }"><span>活动进行中</span></div>
						<div class="step ${ state eq 'rewarding' ? 'current-step' : (state eq 'claimable' or state eq 'complete' ? 'done' : '') }"><span>奖励确认中</span></div>
						<div class="step ${ state eq 'claimable' ? 'current-step' : (state eq 'complete' ? 'done' : '') }"><span>可申领奖励</span></div>
						<div class="step ${ state eq 'complete' ? 'current-step' : '' } last"><span>活动完成</span></div>
					</div>
				</div>  <!-- steps end -->
						
				<jsp:include page="activity.jsp"></jsp:include>
				
				<div class="mt20" style="text-align: center;">
					<a href="../index" class="btn">返回活动列表</a>
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
