<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<c:set var="dealsType" value="${ 1 }" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals招募</title>
	<meta name="description" content="Deals招募 ">
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
	<res:useCss value="${res.css.local.css['fixedColumns.dataTables.3.2.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.icon_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.signpost_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['extension.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['util.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js['cookie.js']}" target="head"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['dataTables.fixedColumns.3.2.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	
	<c:choose>
		<c:when test="${ dealsType eq 1}">
			<!-- china, brazil -->
			<res:useJs value="${res.js.local.js.table['GBHListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:when test="${ dealsType eq 2}">
			<!-- French and spain -->
			<res:useJs value="${res.js.local.js.table['FrenchListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:when test="${ dealsType eq 3}">
			<!-- French and spain -->
			<res:useJs value="${res.js.local.js.table['USListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:otherwise>
			<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js2"></res:useJs>
		</c:otherwise>
	</c:choose>
	
	<res:useJs value="${res.js.local.js.page['deals_state.js']}" target="page-js2"></res:useJs>
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
				<c:when test="${state eq 'Started' }">
					<%@ include file="../stateMessages/forDealsStarted.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'SubsidyCounting' }">
					<%@ include file="../stateMessages/forSubsidyCounting.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'SubsidyRetrieveFailed' }">
					<%@ include file="../stateMessages/forSubsidyRetrieveFailed.jsp" %>
				</c:when>
				
				<c:otherwise>
					<%@ include file="../stateMessages/forSubsidyClaim.jsp" %>
				</c:otherwise>
			</c:choose>
			
			<%@ include file="activity.jsp" %>
			
			<div class="mt20 my-listing">
				<h3>报名刊登列表</h3>
				<c:choose>
					<c:when test="${dealsType eq 1 }">
						<jsp:include page="../table/gbhListing.jsp"></jsp:include>
					</c:when>
					<c:when test="${dealsType eq 2 }">
						<jsp:include page="../table/frenchListing.jsp"></jsp:include>
					</c:when>
					<c:when test="${dealsType eq 3 }">
						<jsp:include page="../table/usListing.jsp"></jsp:include>
					</c:when>
					<c:otherwise>
						<jsp:include page="../table/dealsListing.jsp"></jsp:include>
					</c:otherwise>
				</c:choose>
			</div>	
		</div>
		
	</div>

	<jsp:include page="../footer.jsp"></jsp:include>
</div>

<%@ include file="../dialog/alert.jsp" %>
<%@ include file="../dialog/terms.jsp" %>

<script type="text/javascript">
	var pageData = {
		expired: ${ expired == true },
		promoId: '${promo.promoId}'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
