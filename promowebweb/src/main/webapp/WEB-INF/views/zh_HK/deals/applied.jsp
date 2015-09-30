<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
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
	<res:useCss value="${res.css.local.css.form_css}" target="head-css" />
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
	<res:useJs value="${res.js.local.js['file_input.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_applied.js']}" target="page-js2"></res:useJs>
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
						<div class="step done"><span>報名</span></div>
						<div class="step current-step"><span>已提交預審</span></div>
						<div class="step"><span>報名預審中</span></div>
						<div class="step"><span>正式報名</span></div>
						<div class="step ${ rewarding ? '' : 'last' }"><span>活動進行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>獎勵確認中</span></div>
							<div class="step"><span>申領獎勵</span></div>
							<div class="step last"><span>活動完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box success">
					<h3>您已成功提交預審！請耐心等待預審結果並提交正式報名。</h3>
					<p class="desc">我們已經完整地收到您的刊登物品清單，並會及時回饋到活動網站，請您耐心等待最終確認。</p>
					<menu>
						<li><a href="" class="btn">返回活動清單</a></li>
					</menu>					
				</div> <!-- active status box end -->
				
				<%@ include file="activity.jsp" %>

				<div class="mt20 my-listing">
					<h3><strong>提交预审的刊登</strong></h3>
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>	
				
				<c:if test="${ not expired }">
					<div class="mt20">
						<div class="listings-upload">
							<h3>重新提交預審</h3>
							
							<div class="body mt20"  style="width: 420px;">
								<p class="mt10">您可以通過下載<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}" target="_self">已提交的刊登物品</a>修改並重新上傳。</p>
								<p class="mt10">請注意：您新上傳的刊登物品將完全替換之前的清單，並需要重新進行預審且接受活動條款。</p>
								
								<div class="mt10">
									<span style="float: left; font-weight: bold;">注：</span>
									<ul>
										<li>請勿修改下載範本的檔案格式。</li>
										<li>請勿修改、增减範本中的原有資訊</li>
										<li>請填寫完整報名的刊登資訊，包括：您的刊登編號，當前刊登單價，活動單價，刊登庫存量。價格請按活動對應網站的貨幣計算。</li>
										<li>備貨完成時間格式為YYYY-MM-DD，如2015-08-08</li>
										<li>不報名的SKU請留空填寫內容，不填寫任何待填寫資訊，範本自帶資訊請勿修改。</li>
									</ul>
								</div>
								
								<p id="upload-error-msg" class="error-msg hide"><span class="icon error mb-25"></span><em ></em></p>
								
								<form id="upload-form" action="/promotion/deals/uploadDealsListings" class="mt30" method="post" enctype="multipart/form-data" target="uploadIframe">
									選擇上傳您的刊登清單
									<input type="hidden" name="promoId" value="${promo.promoId}"/>
									<span class="file-input"><input type="text" style="height: 22px;" placeholder="選擇檔案" /> <input type="file" name="dealsListings" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <button type="button" class="btn" style="margin-left: 3px;">選擇</button></span>
								</form>
								<iframe name="uploadIframe" src="about:blank" frameborder="0" style="display: none;"></iframe>
							</div>
						</div>
					</div>
					
					<div class="mt20 page-bottom-actions">
						<label for="accept" title="每次提交報名前請確認點擊閱讀其他條款，確認接受後方可提交報名。"><input type="checkbox" id="accept"/>我已閱讀並接受活動條款及 <a class="terms-conditions" href="javascript:void(0)">其他條款</a></label> <br /><br />
						<button id="upload-btn" class="btn" title="在報名截止之前，您可以重新勾選報名的刊登。">預覽並重新提交預審</button>
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
<%@ include file="../dialog/terms.jsp" %>

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
