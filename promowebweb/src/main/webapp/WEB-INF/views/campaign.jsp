<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader"%>

<c:set var="currentStep" value="${ promo.visibleCurrentStep }" />
<c:set var="regType" value="${ promo.regType }" />
<c:set var="hasListingsNominated" value="${false}" />
<c:set var="isPreview" value="${ promo.isPreview }" />

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
<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css" />
<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css" />
<res:useCss value="${res.css.local.css.reset_css}" target="head-css" />
<res:useCss value="${res.css.local.css.icon_css}" target="head-css" />
<res:useCss value="${res.css.local.css.button_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css" />
<res:useCss value="${res.css.local.css.signpost_css}" target="head-css"/>
<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
<res:useCss value="${res.css.local.css.form_css}" target="head-css" />
<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dialog_css}" target="head-css" />
<res:useCss value="${res.css.local.css.popup_css}" target="head-css" />
<res:useCss value="${res.css.local.css.layout_css}" target="head-css" />
<res:useCss value="${res.css.local.css.header_css}" target="head-css" />
<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css" />
<res:useCss value="${res.css.local.css.promotion_css}" target="head-css" />
<res:useCss value="${res.css.local.css.base_css}" target="head-css" />

<res:useJs value="${res.js.local.js['extension.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js['util.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js['cookie.js']}" target="head"></res:useJs>
<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>

<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
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
				
				<%@ include file="state.jsp"%>

				<%@ include file="activity.jsp"%>

				<c:if test="${(currentStep eq 'Seller nomination_Need approve' or (currentStep eq 'Seller Feedback' and not fn:containsIgnoreCase(stepList, 'Seller nomination_Need approve'))) and  regType  }">
					<div class="mt20">
						<%@ include file="upload_listings.jsp"%>
					</div>
					
					<div class="mt20 page-bottom-actions">
						<label for="accept" title="每次提交报名前请确认点击阅读其他条款，确认接受后方可提交报名。"><input type="checkbox" id="accept" disabled />我已阅读并接受活动条款及
							<a class="terms-conditions" href="javascript:void(0)">其他条款</a></label> <br /> <br />
						<button id="upload-btn" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' } type="button">预览并提交报名</button>
						<br /> <br /> <a href="index">返回活动列表</a>
					</div>
				</c:if>
				
				<c:if test="${(currentStep eq 'Seller nomination_Need approve' or currentStep eq 'Seller Feedback') and  not regType  }">
				
					<!-- 非上传形式报名, 或者正式报名 -->
					<div class="mt20 my-listing">
						<h3>选择报名刊登 <small>（已选 <span>0</span> 项）</small></h3>
						<%@ include file="table/listings.jsp"%>
					</div>
					
					<div class="mt20 page-bottom-actions">
						<form id="listing-form" action="/promotion/listings/confirmListings" target="_self" method="post">
							<input type="hidden" name="promoId" value="${promo.promoId}"/>
							<input type="hidden" name="listings" value="[]" />
							<label for="accept" title="每次提交报名前请确认点击阅读其他条款，确认接受后方可提交报名。"><input type="checkbox" id="accept"/>我已阅读并接受活动条款及 <a class="terms-conditions" href="javascript:void(0)">其他条款</a></label> <br /><br />
							<button id="form-btn" class="btn" type="button" ${ isAdmin or isPreview ? 'disabled' : '' }>预览并提交报名</button>
							<br /><br /> <a href="index">返回活动列表</a>
						</form>
					</div>
						
				</c:if>
				
				<c:if test="${currentStep eq 'Promotion Submitted' or currentStep eq 'Promotion in progress'
					or currentStep eq 'Promotion in validation' or currentStep eq 'Promotion validated' }">
					<div class="mt20 my-listing">
						<h3><strong>提交的刊登</strong></h3>
						<%@ include file="table/listings.jsp"%>
					</div>
				</c:if>
			</div>
		</div>

		<!-- Global Footer -->
		<jsp:include page="footer.jsp"></jsp:include>
		<!-- End: Global Footer -->
	</div>

	<%@ include file="dialog/alert.jsp"%>
	<%@ include file="dialog/confirm.jsp" %>
	<%@ include file="dialog/terms.jsp"%>
	<%@ include file="previewDialog.jsp" %>

	<script type="text/javascript">
		var pageData = {
			promoId : '${promo.promoId}',
			currentStep: '${currentStep}',
			columns: JSON.parse('${not empty columns ? columns : "[]"}'),
			previewColumns: JSON.parse('${not empty previewColumns ? previewColumns : "[]"}')
		};
	</script>

	<res:jsSlot id="body" />
	<res:jsSlot id="page-js" />
	<res:jsSlot id="page-js2" />
	<res:jsSlot id="exec-js" />
</body>
</html>
