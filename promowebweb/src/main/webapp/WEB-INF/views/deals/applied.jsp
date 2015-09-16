<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<r:includeJquery jsSlot="body" />
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
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
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
						<div class="step done"><span>可报名</span></div>
						<div class="step current-step"><span>已提交预审</span></div>
						<div class="step"><span>报名预审中</span></div>
						<div class="step"><span>确认报名刊登</span></div>
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
					<p class="desc green">需要您确认通过预审的刊登完成报名！</p>
					<menu>
						<li><a href="" class="btn">返回活动列表</a></li>
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
							<h3>重新上传我要提交的刊登</h3>
							
							<div class="body mt20"  style="width: 420px;">
								<p class="mt10">您可以通过下载<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}" target="_self">已提交的刊登</a>修改并重新上传您的刊登参与本活动。</p>
								<p class="mt10">您新上传的数据将完全替换原数据。提交数据需再次接受Deals招募法律协议。</p>
								
								<form id="upload-form" action="/promotion/deals/uploadDealsListings" class="mt30" method="post" enctype="multipart/form-data" target="uploadIframe">
									选择上传您的刊登列表 
									<input type="hidden" name="promoId" value="${promo.promoId}"/>
									<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" name="dealsListings" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <button type="button" class="btn" style="margin-left: 3px;">选择</button></span>
								</form>
								<iframe name="uploadIframe" src="about:blank" frameborder="0" style="display: none;"></iframe>
							</div>
						</div>
					</div>
					
					<div class="mt20 page-bottom-actions">
						<button id="upload-btn" class="btn" title="在报名截止之前，您可以重新勾选报名的刊登。" disabled>预览修改报名信息</button>
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
