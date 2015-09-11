<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="state" value="PromotionApproved" />

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
				<h2>Deals ${promo.name}</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可報名</span></div>
						<div class="step done"><span>返回活動清單</span></div>
						<c:if test="${ state eq 'Verifying' }">
							<div class="step current-step"><span>報名預審中</span></div>
							<div class="step"><span>確認報名刊登</span></div>
						</c:if>
						<c:if test="${ state eq 'PromotionApproved' }">
							<div class="step done"><span>報名預審中</span></div>
							<div class="step current-step"><span>確認報名刊登</span></div>
						</c:if>
						<c:if test="${ state eq 'Applied' }">
							<div class="step done"><span>報名預審中</span></div>
							<div class="step current-step"><span>已提交報名</span></div>
						</c:if>
						<div class="step"><span>活動進行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>獎勵確認中</span></div>
							<div class="step"><span>申領獎勵</span></div>
							<div class="step last"><span>活動完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<c:choose>
					<c:when test="${state eq 'Verifying' }">
						<div class="active-status-box">
							<h3>您已成功提交預審！請耐心等待預審結果。</h3>
							<p class="desc">需要您確認通過預審的刊登完成報名！</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活動清單</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->		
					</c:when>
					<c:when test="${state eq 'PromotionApproved' }">
						<div class="active-status-box ${ not expired ? 'success' : '' }">
							<h3>您已成功通過預審！</h3>
							<p class="desc">請於YYYY-MM-DD前在如下刊登中選擇您要參加活動的刊登並提交報名。</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活動清單</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->
					</c:when>
					<c:when test="${state eq 'Applied' }">
						<div class="active-status-box ${ not expired ? 'success' : '' }">
							<h3>您已成功提交！請耐心等待稽核結果。</h3>
							<c:if test="${ not expired }">
								<p class="desc">在報名有效期內您可以修改後重新提交。</p>
							</c:if>
							<menu>
								<li>
									<a href="../index" class="btn">返回活動清單</a>
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
							<small>（已選<span>0</span>項）</small>
						</c:if>
					</h3>						
					
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
				
				<c:if test="${(state eq 'PromotionApproved') && (expired eq false) }">
					<div class="mt20 page-bottom-actions">
						<form id="listing-form" action="preview" method="post">
							<input type="hidden" name="promoId" value=""/>
							<input type="hidden" name="listings" value="[]" />
							<button id="form-btn" class="btn" type="button">預覽報名資訊</button>
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