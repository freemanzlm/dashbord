<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<r:includeJquery jsSlot="body" />
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
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_listing.js']}" target="page-js"></res:useJs>
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
						<div class="step done"><span>可报名</span></div>
						<div class="step done"><span>已提交预审</span></div>
						<c:if test="${ state eq 'Verifying' }">
							<div class="step current-step"><span>报名预审中</span></div>
							<div class="step"><span>确认报名刊登</span></div>
						</c:if>
						<c:if test="${ state eq 'PromotionApproved' }">
							<div class="step done"><span>报名预审中</span></div>
							<div class="step current-step"><span>确认报名刊登</span></div>
						</c:if>
						<c:if test="${ state eq 'Applied' }">
							<div class="step done"><span>报名预审中</span></div>
							<div class="step current-step"><span>已提交报名</span></div>
						</c:if>
						<div class="step"><span>活动进行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>奖励确认中</span></div>
							<div class="step"><span>申领奖励</span></div>
							<div class="step last"><span>活动完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<c:choose>
					<c:when test="${state eq 'Verifying' }">
						<div class="active-status-box">
							<h3>您已成功提交预审！请耐心等待预审结果。</h3>
							<p class="desc">需要您确认通过预审的刊登完成报名！</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->		
					</c:when>
					<c:when test="${state eq 'PromotionApproved' }">
						<div class="active-status-box ${ not expired ? 'success' : '' }">
							<h3>您已成功通过预审！</h3>
							<p class="desc">请于YYYY-MM-DD前在如下刊登中选择您要参加活动的刊登并提交报名。</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->
					</c:when>
					<c:when test="${state eq 'Applied' }">
						<div class="active-status-box ${ not expired ? 'success' : '' }">
							<h3>您已成功提交！请耐心等待审核结果。</h3>
							<c:if test="${ not expired }">
								<p class="desc">在报名有效期内您可以修改后重新提交。</p>
							</c:if>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->
					</c:when>
				</c:choose>
				
				<%@ include file="activity.jsp" %>
				
				<div class="mt20 my-listing">
					<h3>
						我提交的刊登
						<c:if test="${state eq 'PromotionApproved' and not expired }">
							<small>（已选 <span>0</span> 项）</small>
						</c:if>
					</h3>						
					
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
				
				<c:if test="${((state eq 'PromotionApproved') or (state eq 'Applied')) && (expired eq false) }">
					<div class="mt20 page-bottom-actions">
						<form id="listing-form" action="/promotion/deals/confirmDealsListings" target="_self" method="post">
							<input type="hidden" name="promoId" value="${promo.promoId}"/>
							<input type="hidden" name="listings" value="[]" />
							<button id="form-btn" class="btn" type="button">预览报名信息</button>
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
<res:jsSlot id="exec-js" />
</body>
</html>
