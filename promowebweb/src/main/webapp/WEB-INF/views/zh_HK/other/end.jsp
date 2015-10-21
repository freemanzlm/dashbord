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
	<title>其它活動</title>
	<meta name="author" content="其它活動">
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
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>其它活動 ${promo.name}</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step current-step last"><span>活動已結束</span></div>
					</div>
				</div>  <!-- steps end -->
				
				<c:choose>
					<c:when test="${endReason == 'auFail' }">
						<div class="active-status-box fail">
							<div class="message-content">
								<h3>很遺憾，您的報名未通過審核</h3>
								<p class="desc">感谢您的积极参与！期待下次合作。</p>
							</div>
							<menu><li><a href="index" class="btn">返回活動清單</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'claimExpired' }">
						<div class="active-status-box">
							<div class="message-content">
								<h3>您的活動獎勵申領已過期</h3>
							</div>
							<menu><li><a href="index" class="btn">返回活動清單</a></li></menu>
						</div>
					</c:when>
					<c:when test="${endReason == 'noSub' or (rewarding and (empty promo.reward or promo.reward le 0)) }">
						<div class="active-status-box">
							<div class="message-content">
								<h3> 很遺憾！您的活動表現未達到獎勵標准，感謝您對活動的支持！希望下次努力！</h3>
							</div>
							<menu><li><a href="index" class="btn">返回活動清單</a></li></menu>
						</div>
					</c:when>
					<c:otherwise>
						<div class="active-status-box">
							<div class="message-content">
								<h3>活動已結束，感謝您的參與！</h3>
							</div>
							<menu><li><a href="index" class="btn">返回活動清單</a></li></menu>
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

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>
