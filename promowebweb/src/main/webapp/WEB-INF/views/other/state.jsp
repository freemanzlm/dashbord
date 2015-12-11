<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>其它活动 </title>
	<meta name="description" content="其它活动">
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
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dropdown_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.signpost_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.prettyText_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.header_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.topNavigation_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.base_css}" target="head-css"/>

	<res:useJs value="${res.js.local.js['cookie.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['dropdown.js']}" target="page-js"></res:useJs>
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->

	<jsp:include page="../topNavigator.jsp"></jsp:include>
	
	<div id="page-pane">
		<div class="pane">
			<h2>其它活动 ${promo.name}</h2>
			
			<c:choose>
				<c:when test="${ rewarding }">
					<div class="signpost mb20">
						<div class="signpost-posts">
							<c:choose>
								<c:when test="${ state eq 'Created' or state eq 'Unknown' }">
									<div class="post"><span class="label">活动进行中</span></div>
									<div class="post"><span class="label">奖励确认中</span></div>
									<div class="post"><span class="label">申领奖励</span></div>
									<div class="post last"><span class="label">活动完成</span></div>
								</c:when>
								<c:when test="${ state eq 'Started' }">
									<div class="post current-post"><span class="label">活动进行中</span></div>
									<div class="post"><span class="label">奖励确认中</span></div>
									<div class="post"><span class="label">申领奖励</span></div>
									<div class="post last"><span class="label">活动完成</span></div>
								</c:when>
								<c:when test="${ state eq 'SubsidyCounting' }">
									<div class="post done"><span class="label">活动进行中</span></div>
									<div class="post current-post"><span class="label">奖励确认中</span></div>
									<div class="post"><span class="label">申领奖励</span></div>
									<div class="post last"><span class="label">活动完成</span></div>
								</c:when>
								<c:otherwise>
									<div class="post done"><span class="label">活动进行中</span></div>
									<div class="post done"><span class="label">奖励确认中</span></div>
									<div class="post current-post"><span class="label">申领奖励</span></div>
									<div class="post last"><span class="label">活动完成</span></div>
								</c:otherwise>
							</c:choose>
						</div>
					</div>  <!-- steps end -->
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${ state eq 'Created' or state eq 'Unknow' }">
							<div class="signpost mb20">
								<div class="signpost-posts">
									<div class="post last"><span class="label">活动进行中</span></div>
								</div>
							</div>
						</c:when>
						<c:when test="${ state eq 'Started' }">
							<div class="signpost mb20">
								<div class="signpost-posts">
									<div class="post current-post last"><span class="label">活动进行中</span></div>
								</div>
							</div>
						</c:when>
					</c:choose>
				</c:otherwise>
			</c:choose>
			
			<c:choose>
				<c:when test="${state eq 'Created' or state eq 'Unknow' }">
					<%@ include file="../stateMessages/forOtherCreated.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'Started' }">
					<%@ include file="../stateMessages/forStarted.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'SubsidyCounting' }">
					<%@ include file="../stateMessages/forSubsidyCounting.jsp" %>
				</c:when>
				
				<c:when test="${state eq 'SubsidyRetrieveFailed' }">
					<%@ include file="../stateMessages/forSubsidyRetrieveFailed.jsp" %>
				</c:when>
				
				<c:otherwise>
					<%@ include file="../stateMessages/forSubsidyClaim.jsp" %>
				</c:otherwise>
			</c:choose>

			<%@ include file="activity.jsp" %>
			
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
