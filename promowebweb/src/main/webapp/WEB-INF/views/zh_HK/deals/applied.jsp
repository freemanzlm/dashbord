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
						<div class="step done"><span>可報名</span></div>
						<div class="step current-step"><span>已提交預審</span></div>
						<div class="step"><span>報名預審中</span></div>
						<div class="step"><span>確認報名刊登</span></div>
						<div class="step ${ rewarding ? '' : 'last' }"><span>活動進行中</span></div>
						<c:if test="${ rewarding }">
							<div class="step"><span>獎勵確認中</span></div>
							<div class="step"><span>申領獎勵</span></div>
							<div class="step last"><span>活動完成</span></div>
						</c:if>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box">
					<h3>您已成功提交報名！請耐心等待預審結果。</h3>
					<p class="desc green">需要您確認通過預審的刊登完成報名！</p>
					<menu>
						<li><a href="" class="btn">返回活動清單</a></li>
					</menu>					
				</div> <!-- active status box end -->
				
				<%@ include file="activity.jsp" %>

				<div class="mt20 my-listing">
					<h3><strong>我提交的刊登</strong></h3>
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>	
				
				<c:if test="${ not expired }">
					<div class="mt20">
						<div class="listings-upload">
							<h3>重新上傳我要提交的刊登</h3>
							
							<div class="body mt20"  style="width: 420px;">
								<p class="mt10">您可以通過下載<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}" target="_self">已提交的刊登</a>修改並重新上傳您的刊登參與本活動。</p>
								<p class="mt10">您新上傳的數據將完全替換原數據。</p>
								
								<div class="mt10">
									<span style="float: left; font-weight: bold;">注：</span>
									<ul>
										<li>請勿修改下載範本的檔案格式。</li>
										<li>請勿修改、增减範本中的原有資訊</li>
										<li>請填寫完整報名的刊登資訊，包括：您的刊登編號，當前刊登單價，活動單價，刊登庫存量。價格請按活動對應網站的貨幣計算。</li>
										<li>備貨完成時間格式為YYYYMMDD，如2015.08.08</li>
										<li>不報名的SKU請留空填寫內容，不填寫任何待填寫資訊，範本自帶資訊請勿修改。</li>
									</ul>
								</div>
								
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
						<button id="upload-btn" class="btn" title="在報名截止之前，您可以重新勾選報名的刊登。" disabled>預覽修改報名資訊</button>
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
