<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<c:set var="promoSubType" value="${ promo.promoSubType }" />

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
	<res:useCss value="${res.css.local.css['normalize.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['font.awesome.min.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.icon_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.signpost_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.form_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.popup_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.promotion_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>
	
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
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>	
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<c:choose>
		<c:when test="${ promoSubType eq 'FRES'}">
			<!-- French and spain -->
			<res:useJs value="${res.js.local.js.table['FrenchListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:when test="${ promoSubType eq 'GBH'}">
			<!-- china, brazil -->
			<res:useJs value="${res.js.local.js.table['GBHListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:when test="${ promoSubType eq 'APAC'}">
			<!-- French and spain -->
			<res:useJs value="${res.js.local.js.table['USListingTable.js']}" target="page-js2"></res:useJs>
		</c:when>
		<c:otherwise>
			<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js2"></res:useJs>
		</c:otherwise>
	</c:choose>
	<res:useJs value="${res.js.local.js['file_input.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_applied.js']}" target="page-js2"></res:useJs>
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
			
			<%@ include file="../stateMessages/forPretrial.jsp" %>
			
			<%@ include file="../activity.jsp" %>

			<div class="mt20 my-listing">
				<h3><strong>提交預審的刊登</strong></h3>
				<c:choose>
					<c:when test="${promoSubType eq 'FRES' }">
						<jsp:include page="../table/frenchListing.jsp"></jsp:include>
					</c:when>
					<c:when test="${promoSubType eq 'GBH'}">
						<jsp:include page="../table/gbhListing.jsp"></jsp:include>
					</c:when>
					<c:when test="${promoSubType eq 'APAC'}">
						<jsp:include page="../table/usListing.jsp"></jsp:include>
					</c:when>
					<c:otherwise>
						<jsp:include page="../table/dealsListing.jsp"></jsp:include>
					</c:otherwise>
				</c:choose>
			</div>	
			
			<c:if test="${ not expired }">
				<div class="mt20">
					<div class="listings-upload">
						<h3>重新提交預審</h3>
						
						<div class="body mt20"  style="width: 490px;">
							<p class="mt10">您可以通過下載<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}&promoSubType=${promo.promoSubType}" target="_self">已提交的刊登物品</a>修改並重新上傳。</p>
							<p class="mt10">請注意：您新上傳的刊登物品將完全替換之前的清單，並需要重新進行預審且接受活動條款。</p>
							
							<div class="mt10">
								<span style="float: left; font-weight: bold;">注：</span>
								<ul>
									<li>請勿修改下載範本的檔案格式。</li>
									<li>請勿修改、增减範本中的原有資訊,包括每個單元單元的屬性.</li>
									<li>報名的資訊請填寫完整，不可留空。除非標記為選填項，或參照範本標題說明。</li>
									<li>不報名的SKU請整行留空，不填寫任何待填寫資訊。</li>
									<li><a href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank">詳細請查閱使用指南</a></li>
								</ul>
							</div>
							
							<p id="upload-error-msg" class="error-msg hide"><span class="icon error mb-25"></span><b ></b></p>
							
							<form id="upload-form" action="/promotion/deals/uploadDealsListings" class="mt30" method="post"
										enctype="multipart/form-data" target="uploadIframe">
										<input type="hidden" name="promoId" value="${promo.promoId}" />
										<input type="hidden" name="promoSubType" value="${promo.promoSubType}"/> 
										選擇上傳您的刊登清單 <span class="file-input"><input
											type="text" style="height: 22px;" placeholder="選擇檔案" /> <input type="file" name="dealsListings"
											accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
											<button type="button" class="btn" style="margin-left: 3px;">選擇</button></span>
									</form>
							<iframe name="uploadIframe" src="about:blank" frameborder="0" style="display: none;"></iframe>
						</div>
					</div>
				</div>
				
				<div class="mt20 page-bottom-actions">
					<label for="accept" title="每次提交報名前請確認點擊閱讀其他條款，確認接受後方可提交報名。"><input type="checkbox" id="accept"/>我已閱讀並接受活動條款及 <a class="terms-conditions" href="javascript:void(0)">其他條款</a></label> <br /><br />
					<button id="upload-btn" class="btn" title="在報名截止之前，您可以重新勾選報名的刊登。" ${ isAdmin ? 'disabled' : '' }>預覽並重新提交預審</button>
				</div>
			</c:if>
					
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
		expired: ${ expired == true },
		promoId: '${promo.promoId}',
		promoSubType: '${promo.promoSubType}'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
</body>
</html>
