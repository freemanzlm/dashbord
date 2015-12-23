<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals招募</title>
	<meta name="description" content="Deals招募 ">
	<meta name="author" content="eBay: Apps">
	<res:cssSlot id="head" />
	<res:cssSlot id="head-css" />
	
	<script type="text/javascript">
		var BizReport = BizReport || {};
	</script>
	<res:jsSlot id="head" />	
	<res:jsSlot id="head-js" />
	
	<%--module "ebay.page" add Resets and Global css --%>
	<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.signpost_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.popup_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['extension.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['cookie.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['confirm.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>	
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	
	<c:choose>
		<c:when test="${ promo.type eq 1}">
			<!-- dashboard upload -->
			<res:useJs value="${res.js.local.js.table['GBHListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:otherwise>
			<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js2"></res:useJs>
		</c:otherwise>
	</c:choose>
	
	<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_listing.js']}" target="page-js2"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->

	<jsp:include page="../topNavigator.jsp"></jsp:include>
	
	<div id="page-pane">
		<div class="pane">
			<h2>Deals招募 ${promo.name}</h2>
			
			<%@ include file="steps.jsp" %>
			
			<c:choose>
				<c:when test="${state eq 'Verifying' }">
					<%@ include file="../stateMessages/forPretrialing.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'PromotionApproved' }">
					<%@ include file="../stateMessages/forPretrialApproved.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'Applied' }">
					<%@ include file="../stateMessages/forDealsApplied.jsp" %>
				</c:when>
			</c:choose>
			
			<%@ include file="activity.jsp" %>
			
			<div class="mt20 my-listing">
				<h3>
					<a name="listing" id="listing"></a>
					<c:choose>
						<c:when test="${ state eq 'Verifying' }">
							提交预审的刊登
						</c:when>
						<c:otherwise>
							选择报名刊登
						</c:otherwise>
					</c:choose>
					
					<c:if test="${(state eq 'PromotionApproved' or state eq 'Applied' ) and (not expired) }">
						<small>（已选 <span>0</span> 项）</small>
					</c:if>
				</h3>						
				
				<jsp:include page="../table/dealsListing.jsp"></jsp:include>
			</div>
			
			<c:if test="${((state eq 'PromotionApproved') or (state eq 'Applied')) and (not expired) }">
				<div class="mt20 page-bottom-actions">
					<form id="listing-form" action="/promotion/deals/confirmDealsListings" target="_self" method="post">
						<input type="hidden" name="promoId" value="${promo.promoId}"/>
						<input type="hidden" name="listings" value="[]" />
						<label for="accept" title="每次提交报名前请确认点击阅读其他条款，确认接受后方可提交报名。"><input type="checkbox" id="accept"/>我已阅读并接受活动条款及 <a class="terms-conditions" href="javascript:void(0)">其他条款</a></label> <br /><br />
						<c:choose>
							<c:when test="${ state eq 'Applied' }">
								<button id="form-btn" class="btn" type="button" ${ isAdmin ? 'disabled' : '' }>预览并修改正式报名</button>
							</c:when>
							<c:otherwise>
								<button id="form-btn" class="btn" type="button" ${ isAdmin ? 'disabled' : '' }>预览并提交正式报名</button>
								<br /><br /> <a href="index">返回活动列表</a>
							</c:otherwise>
						</c:choose>
						
					</form>
				</div>	
			</c:if>
			
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/alert.jsp" %>
<%@ include file="../dialog/confirm.jsp" %>
<%@ include file="../dialog/terms.jsp" %>
<%@ include file="previewDialog.jsp" %>

<script type="text/javascript">
	var pageData = {
		state: '${ state }',
		expired: ${ expired eq true },
		promoId: '${promo.promoId}'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
