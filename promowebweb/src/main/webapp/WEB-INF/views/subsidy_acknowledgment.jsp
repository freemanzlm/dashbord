<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<c:set var="isPreview" value="${ promo.isPreview }" />
<c:set var="currentStep" value="${ isPreview ? promo.draftPreviewStep : promo.currentStep }" />
<c:set var="visibleCurrentStep" value="${ promo.visibleCurrentStep }" />
<!-- visible step list -->
<c:set var="stepList" value="${ promo.stepList }" />
<c:set var="regType" value="${ promo.regType }" />

<fmt:formatDate value="${promo.rewardDlDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
<title>${promo.name }</title>
<meta name="description" content="${promo.name }">
<meta name="author" content="eBay: Apps">
<res:cssSlot id="head" />
<res:cssSlot id="head-css" />

<script type="text/javascript">
	var BizReport = BizReport || {};
</script>
<res:jsSlot id="head" />
<res:jsSlot id="head-js" />

<%--module "ebay.page" add Resets and Global css --%>
<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css" />
<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css" />
<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css" />
<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css" />
<res:useCss value="${res.css.local.css.reset_css}" target="head-css" />
<res:useCss value="${res.css.local.css.icon_css}" target="head-css" />
<res:useCss value="${res.css.local.css.button_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css" />
<res:useCss value="${res.css.local.css.signpost_css}" target="head-css" />
<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
<res:useCss value="${res.css.local.less.form_layout_less}" target="head-css" />
<res:useCss value="${res.css.local.css.form_css}" target="head-css" />
<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dialog_css}" target="head-css" />
<res:useCss value="${res.css.local.css.popup_css}" target="head-css" />
<res:useCss value="${res.css.local.css.tabs_css}" target="head-css" />
<res:useCss value="${res.css.local.css.layout_css}" target="head-css" />
<res:useCss value="${res.css.local.less.award_less}" target="head-css" />
<res:useCss value="${res.css.local.css.header_css}" target="head-css" />
<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css" />
<res:useCss value="${res.css.local.css.promotion_css}" target="head-css" />
<res:useCss value="${res.css.local.css.base_css}" target="head-css" />

<res:useJs value="${res.js.local.js['extension.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js['util.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js['cookie.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>

<res:useJs value="${res.js.local.js['tabs_simple.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['confirm.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js['file_input.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.table['ListingTable.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.page['campaign.js']}" target="page-js2"></res:useJs>
</head>

<body>
	<div class="container">
		<!--  Global Header -->
		<jsp:include page="header.jsp"></jsp:include>
		<!-- end: Global Header -->

		<jsp:include page="topNavigator.jsp"></jsp:include>
		<div id="page-pane">
			<div class="pane">
				<h2>${promo.name}</h2>

				<%@ include file="steps.jsp"%>

				<%@ include file="subsidy/fill_contract.jsp"%>
				

		<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
		<!-- End: Global Footer -->
	</div>

	<%@ include file="dialog/alert.jsp"%>
	<%@ include file="dialog/confirm.jsp"%>
	<c:choose>
		<c:when test="${promo.region eq 'CN'}">
			<%@ include file="dialog/terms.jsp"%>
		</c:when>
		<c:otherwise>
			<%@ include file="zh_HK/dialog/terms.jsp"%>
		</c:otherwise>
	</c:choose>

	<%@ include file="previewDialog.jsp"%>

	<script type="text/javascript">
		var pageData = {
			promoId : '${promo.promoId}',
			currentStep : '${currentStep}',
			regType : '${promo.regType}'
		};
	</script>

	<script type="text/javascript">
		var real_current_step = '${currentStep}';
		var isAdmin = '${isAdmin}';
		var publishFlag = '${promo.publishFlag}';
		if (real_current_step == 'NOTIFICATION EDM APPROVED'
				&& (publishFlag == 'false' || isAdmin == 'true')) {
			$(".signpost .post").toggleClass("done", false);
			$(".signpost .post").toggleClass("current-post", false);
		}
		var endReason = '${promo.endReason}';
		var state = '${promo.state}';
		if ((endReason != 'claimExpired' && endReason != 'subsidyRetrieved')
				&& state == 'End') {
			$(".signpost .post").remove();
			$(".signpost-posts")
					.html(
							"<div class='post current-post'><span class='label'>活动结束</span></div>");
		}
	</script>

	<res:jsSlot id="body" />
	<res:jsSlot id="page-js" />
	<res:jsSlot id="page-js2" />
	<res:jsSlot id="exec-js" />
</body>
</html>
