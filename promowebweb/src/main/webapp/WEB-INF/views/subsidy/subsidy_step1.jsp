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
						<li role="tab" id="tab2" aria-controls="pane2" class="active">
							<span class="label"> 
								<a href="/promotion/subsidy/subsidyStepTwo?promoId=${promo.promoId }">第二步：上传确认函</a>
							</span>
						</li>
<%-- 						<li role="tab" id="tab2" aria-controls="pane2" class="<c:if test='${subsidy.status eq 2 or subsidy.status eq 3 or subsidy.status eq 5}'>active</c:if>">
							<span class="label"> 
								<a href="/promotion/subsidy/subsidyStepTwo?promoId=${promo.promoId }">第二步：上传确认函</a>
							</span>
						</li> --%>
						<li role="tab" aria-controls="pane3" class="<c:if test='${subsidy.status eq 4}'>active</c:if>">
							<span class="label">
								<a href="#pane3">第三步：领取奖励</a>
							</span>
						</li>
					</ul>
				</div>
			</div>		
				
				<div id="pane1" class="tab-pane confirm-letter-pane" role="tabpanel">
					<c:if test="${promo.rewardType eq 2 and not empty wltAccount}">
						<c:choose>
							<c:when test="${not empty param.isWltFirstBound}">
								<!-- Parameter 'isWltFirstBound' comes from bound backURL parameter -->
								<div class="pane wlt-binding">
									恭喜！您已完成eBay万里通账号的绑定。绑定的eBay账号为：${unm}，对应的万里通账号为：${wltAccount.wltUserId}。
								</div>
							</c:when>
							<c:otherwise>
								<div class="pane wlt-binding">
									请注意：您绑定的<a target="_blank"
										href="http://www.ebay.cn/mkt/leadsform/efu/11183.html">万里通</a>账号是：${wltAccount.wltUserId}，
									<a
										href="http://www.wanlitong.com/myPoint/brandPointSch.do?fromType=avail&pageNo=1&brandPointNo=h5mg&dateType=0&sortFlag=ddd">查积分，积分当钱花。</a>
								</div>
							</c:otherwise>
						</c:choose>
					</c:if>
		
					<h3 class="mt20 mb5 text-center">活动奖励确认函</h3>
					<hr />
					<h4 class="mt20 ml20">卖家基本信息：</h4>
					<form id="custom-fields-form"  class="form-horizontal" method="get">
						<input id="promoId" type="hidden" value="${promo.promoId }" name="promoId" />
						<c:forEach items="${ nonuploadFields }" var="field">
							<div class="form-group">
								<div class="control-label">${field.displayLabel }：</div>
								<div id="items" class="form-field">
									<input type="text" name="${field.key }" value="${field.value }" />
									<c:if test="${field.key eq '_sellerName' }">
									&nbsp;<span>(以下称为“我/本公司”或“卖家”)</span>
									</c:if>
								</div>
							</div>
						</c:forEach>
						<div class="form-group">
							<div class="control-label">eBay账号：</div>
							<div class="form-field">${unm}（不允许修改）</div>
						</div>
						<div class="form-group">
							<div class="control-label">参加的活动：</div>
							<div class="form-field">${promo.name }（不允许修改）（以下简称“活动”）</div>
						</div>
						<div class="form-group">
							<div class="control-label">奖励金额：</div>
							<div class="form-field">${reward}
								${promo.currency}（不允许修改）（以下简称“活动奖励”）</div>
						</div>
						<div class="form-group">
							<div class="control-label">奖励申领截止时间：</div>
							<div class="form-field">北京时间
								${rewardDeadline}（不允许修改）（以下简称“奖励申领时间”）</div>
						</div>
						<div class="form-group">
							<div class="control-label">
								<input id="readFlag" checked="<c:if test='${subsidy.status lt 2 }'>checked</c:if>" type="checkbox" />
							</div>
							<div class="form-field">我已阅读并接受以下确认函内容</div>
						</div>
						<div class="text-center">
							<button type="button" class="btn" id="genPdf" >点击生成PDF供签署</button>
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							 <c:if test="${subsidy.status gt 1}"> 
								<a class="btn" href="subsidyStepTwo?promoId=${promo.promoId}">下一步：上传确认函</a>
							 </c:if> 
						</div>
					</form>
					<hr />
					<div class="pretty-text">${pdfContent}</div>
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
 		$("#genPdf").click(function(){
 			$("#genPdf").attr("disabled","disabled");
 			var id = $("#promoId").val();
 			var data = {};
 			var flag = false;
 			var keyvalues = $("#items>input");
 			for(var x=0;x<keyvalues.length;x++){
 				data.key=keyvalues.eq(x).attr("name");
 				data[keyvalues.eq(x).attr("name")] = keyvalues.eq(x).val();
 				if(""==keyvalues.eq(x).val()){
 					flag = true;
 				} 
 			}
 			if(flag){
 				alert("请完善所有信息");
 				return;
 			}
 			if($("#readFlag").attr("checked")!='checked'){
 				alert("请阅读并确认以下确认函内容");
 				return;
 			}
 			$.ajax({
 				url:"acknowledgment?promoId="+id,
 				type:'POST',
 				data:data,
 				success:function(ret){
 					$("#uploadPdf").attr("disabled",false);
 					$("#genPdf").removeAttr("disabled");
 					$("#tab2").addClass("active");
 					if(ret.status){
 						window.location.href="downloadLetter?promoId="+id;
 					} 
 				}
 			}); 
		});
 		
 		$("#uploadPdf").click(function(){
 			alert(11);
 			var id = $("#promoId").val();
 			window.location.href="subsidyStepTwo?promoId="+id;
 		});
 </script>
</html>
