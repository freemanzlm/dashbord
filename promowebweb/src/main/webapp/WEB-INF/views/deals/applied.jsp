<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<c:set var="dealsType" value="${ 2 }" />

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
	<res:useCss value="${res.css.local.css.icon_css}" target="head-css"/>
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
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['alert.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>	
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js['file_input.js']}" target="page-js2"></res:useJs>
	
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
	
	<res:useJs value="${res.js.local.js.page['deals_applied.js']}" target="page-js2"></res:useJs>

</head>

<body>
	<div class="container">
		<!--  Global Header -->
		<jsp:include page="../header.jsp"></jsp:include>
		<!-- end: Global Header -->

		<jsp:include page="../topNavigator.jsp"></jsp:include>

		<div id="page">
			<div id="page-pane">
				<div class="pane">
					<h2>Deals招募 ${promo.name}</h2>

					<%@ include file="steps.jsp"%>

					<%@ include file="../stateMessages/forPretrial.jsp"%>

					<%@ include file="activity.jsp"%>

					<div class="mt20 my-listing">
						<h3>
							<strong>提交预审的刊登</strong>
						</h3>
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

					<c:if test="${ not expired }">
						<div class="mt20">
							<div class="listings-upload">
								<h3>重新提交预审</h3>

								<div class="body mt20" style="width: 490px;">
									<p class="mt10">
										您可以通过下载<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}" target="_self">已提交的刊登物品</a>修改并重新上传。
									</p>
									<p class="mt10">请注意：您新上传的刊登物品将完全替换之前的列表，并需要重新进行预审且接受活动条款。</p>

									<div class="mt10">
										<span style="float: left; font-weight: bold;">注：</span>
										<ul>
											<li>请勿修改下载模板的文件格式。</li>
											<li>请勿修改、增减模板中的原有信息</li>
											<li>请填写完整报名的刊登信息，包括：您的刊登编号（不可重复），当前刊登单价，活动单价，刊登库存量。价格请按活动对应网站的货币计算。</li>
											<li>备货完成时间格式为YYYY-MM-DD，如2015-08-08</li>
											<li>不报名的SKU请留空填写内容，不填写任何待填写信息，模板自带信息请勿修改。</li>
										</ul>
									</div>

									<p id="upload-error-msg" class="error-msg hide">
										<span class="icon error mb-25"></span><em></em>
									</p>

									<form id="upload-form" action="/promotion/deals/uploadDealsListings" class="mt30" method="post"
										enctype="multipart/form-data" target="uploadIframe">
										选择上传您的刊登列表 <input type="hidden" name="promoId" value="${promo.promoId}" /> <span class="file-input"><input
											type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" name="dealsListings"
											accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
											<button type="button" class="btn" style="margin-left: 3px;">选择</button></span>
									</form>
									<iframe name="uploadIframe" src="about:blank" frameborder="0" style="display: none;"></iframe>
								</div>
							</div>
						</div>

						<div class="mt20 page-bottom-actions">
							<label for="accept" title="每次提交报名前请确认点击阅读其他条款，确认接受后方可提交报名。"><input type="checkbox" id="accept" />我已阅读并接受活动条款及
								<a class="terms-conditions" href="javascript:void(0)">其他条款</a></label> <br />
							<br />
							<button id="upload-btn" class="btn" title="在报名截止之前，您可以重新勾选报名的刊登。" ${ isAdmin ? 'disabled' : '' }>预览并重新提交预审</button>
						</div>
					</c:if>

				</div>
			</div>
		</div>

		<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
		<!-- End: Global Footer -->
	</div>

	<%@ include file="../dialog/alert.jsp"%>
	<%@ include file="../dialog/terms.jsp"%>

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
