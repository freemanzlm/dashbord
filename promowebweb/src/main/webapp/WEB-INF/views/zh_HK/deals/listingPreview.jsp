<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="dealsType" value="${ 2 }" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>已選擇的刊登預覽</title>
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
	<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.icon_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.form_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.popup_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['extension.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['util.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['local_zh_HK.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['cookie.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<c:choose>
		<c:when test="${ promoSubType eq 'GBH'}">
			<!-- china, brazil -->
			<res:useJs value="${res.js.local.js.table['GBHListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:when test="${ promoSubType eq 'FRES'}">
			<!-- French and spain -->
			<res:useJs value="${res.js.local.js.table['FrenchListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:when test="${ promoSubType eq 'APAC'}">
			<!-- French and spain -->
			<res:useJs value="${res.js.local.js.table['USListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:otherwise>
			<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js2"></res:useJs>
		</c:otherwise>
	</c:choose>
	<res:useJs value="${res.js.local.js.page['deals_listing_preview.js']}" target="page-js2"></res:useJs>	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>已選擇的刊登預覽</h2>
				
				<div class="mt20">
					<c:choose>
						<c:when test="${promoSubType eq 'GBH' }">
							<jsp:include page="../table/gbhListing.jsp"></jsp:include>
						</c:when>
						<c:when test="${promoSubType eq 'FRES' }">
							<jsp:include page="../table/frenchListing.jsp"></jsp:include>
						</c:when>
						<c:when test="${promoSubType eq 'APAC' }">
							<jsp:include page="../table/usListing.jsp"></jsp:include>
						</c:when>
						<c:otherwise>
							<jsp:include page="../table/dealsListing.jsp"></jsp:include>
						</c:otherwise>
					</c:choose>
				</div>
				
				<div class="mt20 page-bottom-actions">
					<div id="submit-form">
						<a href="/promotion/${promoId}">返回修改</a>
						<button id="submit-btn" class="btn">提交預審</button>
					</div>
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

<script type="text/javascript">
	var pageData = {
		promoId: '${promoId}'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>