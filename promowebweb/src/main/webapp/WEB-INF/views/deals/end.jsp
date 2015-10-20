<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="state" value="${ promo.state }" />
<c:set var="endReason" value="${ promo.endReason }" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals招募</title>
	<meta name="description" content="Deals招募 ">
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
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['locale_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js2"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js2"></res:useJs>
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
						<c:choose>
							<c:when test="${ endReason == 'preFail' }">
								<div class="step done"><span>报名</span></div>
								<div class="step done"><span>已提交预审</span></div>
								<div class="step current-step last"><span>预审失败</span></div>
							</c:when>
							<c:otherwise>
								<div class="step current-step last"><span>活动已结束</span></div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>  <!-- steps end -->
				
				<c:choose>
					<c:when test="${endReason == 'noUpload' }">
						<div class="active-status-box fail">
							<h3>已过报名有效期，您未上传任何刊登，期待您的下次参与！</h3>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'preFail' }">
						<div class="active-status-box fail">
							<h3>很遗憾，您的报名未通过预审</h3>
							<p class="desc">感谢您的积极参与！期待下次合作。</p>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'auFail' }">
						<div class="active-status-box fail">
							<h3>很遗憾，您的报名未通过审核</h3>
							<p class="desc">感谢您的积极参与！期待下次合作。</p>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'noReg' }">
						<div class="active-status-box fail">
							<h3>以超过报名有效期，您未提交报名，期待您的下次参与！</h3>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'claimExpired' }">
						<div class="active-status-box">
							<h3>您的活动奖励申领已过期</h3>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'noSub' or (rewarding and (empty promo.reward or promo.reward le 0)) }">
						<div class="active-status-box">
							<h3>很遗憾！您的活动表现未达到奖励标准，感谢您对活动的支持！希望下次努力！</h3>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:when>
					<c:otherwise>
						<div class="active-status-box">
							<h3>活动已结束，感谢您的参与！</h3>
							<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
						</div>
					</c:otherwise>
				</c:choose>
				
				<%@ include file="activity.jsp" %>
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/terms.jsp" %>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="page-js2" />
<res:jsSlot id="exec-js" />
<script type="text/javascript">
	$(".terms-conditions").click(function(event){
		BizReport.termsDialog.show();
	});
</script>
</body>
</html>
