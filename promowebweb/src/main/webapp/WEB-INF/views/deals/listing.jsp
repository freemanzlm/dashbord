<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.popup_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['locale_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['AlertDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['ConfirmDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>	
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['ListingPreviewDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_listing.js']}" target="page-js2"></res:useJs>
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
				
				<%@ include file="steps.jsp" %>
				
				<c:choose>
					<c:when test="${state eq 'Verifying' }">
						<div class="active-status-box">
							<div class="message-content">
								<h3>您已成功提交预审！请耐心等待预审结果并提交正式报名。</h3>
								<p class="desc">我们已经完整地收到您的刊登物品列表，并会及时反馈到活动站点，请您耐心等待最终确认。</p>
							</div>
							<menu>
								<li>
									<a href="index" class="btn">返回活动列表</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->		
					</c:when>
					
					<c:when test="${state eq 'PromotionApproved' }">
						<c:choose>
							<c:when test="${promo.isReversed }">
								<div class="active-status-box">
									<div class="message-content">
										<h3>活动时间已调整为<span class="cyan">${ promoStart }</span>到<span class="cyan">${ promoEnd }</span>，请在<span class="cyan">${promoDlDt}</span>前重新确认你参加活动的刊登！</h3>
									</div>
									<menu><li><a href="#listing" class="btn">正式报名</a></li></menu>
								</div>
							</c:when>
							<c:otherwise>
								<div class="active-status-box ${ not expired ? 'success' : '' }">
									<div class="message-content">
										<h3>您已成功通过预审！请于<span class="cyan">${ promoDlDt }</span>前<a href="#listing">选择并提交</a>如下通过预审的刊登完成正式报名。</h3>
										<p class="desc">活动时间为<span class="cyan">${ promoStart } </span>到 <span class="cyan">${ promoEnd }</span><br />活动如有更改，以最终通知为准。</p>
									</div>
									<menu><li><a href="#listing" class="btn">正式报名</a></li></menu>	
								</div>
							</c:otherwise>
						</c:choose>
					</c:when>
					
					<c:when test="${state eq 'Applied' }">
						<div class="active-status-box success">
							<div class="message-content">
								<h3>您已正式报名成功！请耐心等待活动开始。</h3>
								
								<p class="desc">
									<c:choose>
										<c:when test="${ expired eq true }">已超过报名有效期，您无法再修改报名刊登。</c:when>
										<c:otherwise>在报名截止时间前您可以随时修改您选择的刊登。</c:otherwise>
									</c:choose>
								</p>
							</div>
							<menu>
								<li>
									<a href="index" class="btn">返回活动列表</a>
								</li>
							</menu>	
						</div> <!-- active status box end -->
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
