<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-dd" type="date" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals招募</title>
	<meta name="description" content="Deals招募">
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
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.popup_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['locale_zh_HK.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['AlertDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>	
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['preset_applicable.js']}" target="page-js2"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>Deals招募 ${promo.name}</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step current-step"><span>報名</span></div>
						<div class="step"><span>已報名</span></div>
						<div class="step ${ rewarding ? '' : 'last' }"><span>活動進行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>獎勵確認中</span></div>
							<div class="step"><span>申領獎勵</span></div>
							<div class="step last"><span>活動完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<c:if test="${promoUpdated }">
					<div class="active-status-box">
						<h3>活動時間已調整為<span class="cyan">${ promoStart }</span>到<span class="cyan">${ promoEnd }</span>，請在<span class="cyan">${promoDlDt}</span>前重新確認你參加活動的刊登！</h3>
					</div>
				</c:if>
				
				<%@ include file="activity.jsp" %>
				
				<div class="mt20 my-listing">
					<h3>選擇報名刊登<small>（已選  <span>0</span> 項）</small></h3>
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
				
				<div class="mt20" style="text-align: center;">
					<form id="listing-form" action="/promotion/deals/confirmDealsListings" method="post">
						<input type="hidden" name="promoId" value="${promo.promoId}"/>
						<input type="hidden" name="listings" value="[]" />
						<label for="accept" title="每次提交報名前請確認點擊閱讀其他條款，確認接受後方可提交報名。"><input type="checkbox" id="accept" disabled/>我已閱讀並接受活動條款及 <a class="terms-conditions" href="javascript:void(0)">其他條款</a></label> <br /><br />
						<button id="form-btn" class="btn" type="button" ${ isAdmin ? 'disabled' : '' }>預覽並提交報名</button>
						<br /><br /> <a href="index">返回活動清單</a>
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
<%@ include file="previewDialog.jsp" %>

<script type="text/javascript">
	var pageData = {
		promoId: '${promo.promoId}'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
