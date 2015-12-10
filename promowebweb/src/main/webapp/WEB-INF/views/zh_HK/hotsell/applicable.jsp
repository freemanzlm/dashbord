<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>爆款促銷 </title>
	<meta name="description" content="爆款促銷 ">
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
	<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.signpost_css3_css}" target="head-css"/>
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
	<res:useJs value="${res.js.local.js['local_zh_HK.js']}" target="page-js"></res:useJs>
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
	<res:useJs value="${res.js.local.js.table['HotsellListingTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['hotsell_applicable.js']}" target="page-js2"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->

	<jsp:include page="../topNavigator.jsp"></jsp:include>
	
	<div id="page-pane">
		<div class="pane">
			<h2>爆款促銷 ${promo.name}</h2>
			
			<%@ include file="../steps.jsp" %>
			
			<c:if test="${promo.isReversed }">		
				<%@ include file="../stateMessages/forReversed.jsp" %>
			</c:if>
			
			<%@ include file="activity.jsp" %>
			
			<div class="mt20 my-listing">
				<h3>選擇報名刊登<small>（已選 <span>0</span> 項）</small></h3>
				<jsp:include page="../table/hotsellListing.jsp"></jsp:include>
			</div>
			
			<div class="mt20" style="text-align: center;">
				<form id="listing-form" action="/promotion/hotsell/confirmHotSellListings" method="post">
					<input type="hidden" name="promoId" value="${promo.promoId}"/>
					<input type="hidden" name="listings" value="[]" />
					<label for="accept" title="每次提交報名前請確認點擊閱讀其他條款，確認接受後方可提交報名。"><input type="checkbox" id="accept" disabled/>我已閱讀並接受活動條款及 <a class="terms-conditions" href="javascript:void(0)">其他條款</a></label> <br /><br />
					<button id="form-btn" class="btn" type="button" ${ isAdmin ? 'disabled' : '' }>預覽並提交報名</button>
					<br /><br /> <a href="index">返回活動清單</a>
				</form>
			</div>
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
		expired: ${ expired == true },
		promoId: '${ promo.promoId }'
	};
</script>
<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
