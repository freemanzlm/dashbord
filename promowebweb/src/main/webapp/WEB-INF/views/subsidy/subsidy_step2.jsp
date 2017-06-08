<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents"%>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<c:set var="isPreview" value="${ promo.isPreview }" />
<c:set var="currentStep"
	value="${ isPreview ? promo.draftPreviewStep : promo.currentStep }" />
<c:set var="visibleCurrentStep" value="${ promo.visibleCurrentStep }" />
<!-- visible step list -->
<c:set var="stepList" value="${ promo.stepList }" />
<c:set var="regType" value="${ promo.regType }" />

<fmt:formatDate var="rewardDeadline" value="${promo.rewardDlDt}"
	pattern="yyyy-MM-dd" type="date" />
<fmt:formatNumber var="reward" value="${promo.reward }"
	minFractionDigits="2"></fmt:formatNumber>

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
<title>${promo.name }</title>
<meta name="description" content="${promo.name }">
<meta name="author" content="eBay: Apps">
<script type="text/javascript">
	var BizReport = BizReport || {};
</script>
<res:cssSlot id="head" />
<res:cssSlot id="head-css" />
<res:jsSlot id="head" />
<res:jsSlot id="head-js" />

<%--module "ebay.page" add Resets and Global css --%>
<res:useCss value="${res.css.local.css['normalize.css']}"
	target="head-css" />
<res:useCss value="${res.css.local.css['font.awesome.min.css']}"
	target="head-css" />
<res:useCss value="${res.css.local.css['jquery.dataTables.1.10.css']}"
	target="head-css" />
<res:useCss value="${res.css.local.css['dataTables.override.css']}"
	target="head-css" />
<res:useCss value="${res.css.local.css.reset_css}" target="head-css" />
<res:useCss value="${res.css.local.css.icon_css}" target="head-css" />
<res:useCss value="${res.css.local.css.button_css}" target="head-css" />
<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css" />
<res:useCss value="${res.css.local.css.signpost_css}" target="head-css" />
<res:useCss value="${res.css.local.less.module_less}" target="head-css" />
<res:useCss value="${res.css.local.less.form_layout_less}"
	target="head-css" />
<res:useCss value="${res.css.local.less.form_less}" target="head-css" />
<res:useCss value="${res.css.local.css.prettyText_css}"
	target="head-css" />
<res:useCss value="${res.css.local.css.dialog_css}" target="head-css" />
<res:useCss value="${res.css.local.css.popup_css}" target="head-css" />
<res:useCss value="${res.css.local.less.tabs_less}" target="head-css" />
<res:useCss value="${res.css.local.css.layout_css}" target="head-css" />
<res:useCss value="${res.css.local.less.award_less}" target="head-css" />
<res:useCss value="${res.css.local.css.header_css}" target="head-css" />
<res:useCss value="${res.css.local.css.topNavigation_css}"
	target="head-css" />
<res:useCss value="${res.css.local.css.promotion_css}" target="head-css" />
<res:useCss value="${res.css.local.css.base_css}" target="head-css" />
<res:useJs value="${res.js.local.js['extension.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js['util.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js['local_zh_CN.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js['cookie.js']}" target="head-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['widget.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['mask.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['posManager.js']}"
	target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}"
	target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.lib['vue.js']}" target="page-js"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['dialog.js']}"
	target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['alert.js']}"
	target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['confirm.js']}"
	target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}"
	target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js['popup.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js.page['subsidy.js']}"
	target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js['file_input.js']}" target="page-js2"></res:useJs>
<res:useJs value="${res.js.local.js['tabs_simple.js']}"
	target="page-js2"></res:useJs>
</head>

<body>
	<div class="container">
		<!--  Global Header -->
		<jsp:include page="../header.jsp"></jsp:include>
		<!-- end: Global Header -->
		<jsp:include page="../topNavigator.jsp"></jsp:include>

		<div class="pane">	
			<div class="tabbable confirm-letter-steps">
				<div class="tab-list-container clr">
					<ul class="tab-list clr" role="tablist">
						<li role="tab" aria-controls="pane1" class="<c:if test='${subsidy.status eq 1 or subsidy.status eq 2 or subsidy.status eq 3 or subsidy.status eq 5}'>active</c:if>">
							<span class="label"> 
								<a href="/promotion/subsidy/acknowledgment?promoId=${promo.promoId }">第一步：填写确认函</a>
							</span>
						</li>
						<li role="tab" aria-controls="pane2" class="<c:if test='${subsidy.status eq 2 or subsidy.status eq 3 or subsidy.status eq 5}'>active</c:if>">
							<span class="label"> 
								<a href="/promotion/subsidy/subsidyStepTwo?promoId=${promo.promoId }">第二步：上传确认函</a>
							</span>
						</li>
						<li role="tab" aria-controls="pane3" class="<c:if test='${subsidy.status eq 4}'>active</c:if>">
							<span class="label">
								<a href="#pane3">第三步：领取奖励</a>
							</span>
						</li>
					</ul>
				</div>
			</div>		
			
			<div id="pane2" class="tab-pane confirm-letter-submission-pane"	 role="tabpanel">
				<div class="hint" style="align:center;">
					<p>为了方便核实您的上传信息，确保您能尽快领取相关奖励，请仔细阅读以下内容：</p>
					<ol>
						<li>上传文件格式为“.jpg”、“.pdf”或“ZIP”（多文件情况），单个上传文件不超过5M；</li>
						<li>上传文件内容须清晰，可识别且应完整齐全；</li>
						<li>不得在同一文件栏重复上传多个文件，一旦在同一上传文件栏重复上传文件时，我们将以提交前最后上传的那份为准；</li>
						<li>若未能按照上述要求操作的，我们将作“审核未通过”处理；</li>
						<li>上传截止日期为${rewardDeadline}，未在该规定时间内上传文件的卖家将被视为自动放弃本活动奖励。</li>
					</ol>
				</div>
				<form id="form" class="form-horizontal" action="upload?promoId=${promo.promoId }" method="post"	enctype="multipart/form-data">
					<c:forEach items="${ uploadFields }" var="field" >
						<div class="form-group">
							<div class="control-label">${field.displayLabel }：</div>
							<div class="form-field">
									 <input type="file"	 name="${field.key }" />
									<button type="button" class="btn" style="margin-left: 3px;">选择</button>
								 <span class="font-bold msg"> 
									<c:if test="${ not empty field.value }">
										</br>
										<a href="/promotion/subsidy/downloadAttachmentById?id=${field.value}">下载附件</a>
									</c:if>
								</span>
							</div>
						</div>
					</c:forEach>
					<div class="text-center" style="margin-top: 40px;">
						<button  type="submit" class="btn" style="width: 120px;">上传</button>
					</div>
				</form>
			</div>
		</div>
		<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
		<!-- End: Global Footer -->
	</div>
	<c:choose>
		<c:when test="${promo.region eq 'CN'}">
			<%@ include file="../dialog/terms.jsp"%>
		</c:when>
		<c:otherwise>
			<%@ include file="../zh_HK/dialog/terms.jsp"%>
		</c:otherwise>
	</c:choose>

	<%@ include file="../previewDialog.jsp"%>
	<res:jsSlot id="body" />
	<res:jsSlot id="page-js" />
	<res:jsSlot id="page-js2" />
	<res:jsSlot id="exec-js" />

</body>
<script type="text/javascript">
</script>
</html>
