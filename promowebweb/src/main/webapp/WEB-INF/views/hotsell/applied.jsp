<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="listingNum" value="2" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />

<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>爆款促销 </title>
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
	<res:useJs value="${res.js.local.js.dialog['ConfirmDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.table['HotsellListingTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['hotsell_applied.js']}" target="page-js"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>爆款促销 ${promo.name}</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可报名</span></div>
						<div class="step current-step"><span>已提交报名</span></div>
						<div class="step ${ rewarding ? '' : 'last' }"><span>活动进行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>奖励确认中</span></div>
							<div class="step"><span>申领奖励</span></div>
							<div class="step last"><span>活动完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box">
					<h3>您已成功提交报名！请耐心等待预审结果。</h3>
					
					<c:choose>
						<c:when test="${not expired }">
							<%-- <p class="desc">您总共提交了 ${listingNum } 条刊登 </p> --%>
							<p class="desc green">在报名有效期内您可以修改后重新提交。报名有效期内不做审核操作。</p> 
						</c:when>
						<c:otherwise>
							<p class="desc gray">已超过报名有效期，您无法再修改刊登内容</p>
						</c:otherwise>
					</c:choose>
						
					<menu>
						<li>
							<a href="../index" class="btn">返回活动列表</a>
						</li>
					</menu>					
				</div> <!-- active status box end -->
				
				<%@ include file="activity.jsp" %>
		
				<div class="mt20 my-listing">
					<h3>我提交的刊登
						<c:if test="${ not expired }">
							<small>（已选 <span>0</span> 项）</small>
						</c:if>
					</h3>
					<jsp:include page="../table/hotsellListing.jsp"></jsp:include>
				</div>	
				
				<c:if test="${not expired }">
					<div class="page-bottom-actions">
						<form id="listing-form" action="/promotion/hotsell/confirmHotSellListings" method="post">
							<input type="hidden" name="promoId" value=""/>
							<input type="hidden" name="listings" value="[]" />
							<button class="btn" id="form-btn" type="button" title="在报名截止之前，您可以重新勾选报名的刊登。">预览修改报名信息</button>
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
<res:jsSlot id="exec-js" />
</body>
</html>
