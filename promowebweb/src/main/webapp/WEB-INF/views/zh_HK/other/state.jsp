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
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="萬邑通" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay萬裏通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>

<r:includeJquery jsSlot="head" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>其它活動</title>
	<meta name="description" content="其它活動">
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
				
				<c:choose>
					<c:when test="${ rewarding }">
						<div class="steps-wrapper">
							<div class="steps clr">
								<c:choose>
									<c:when test="${ state eq 'Created' or state eq 'Unknown' }">
										<div class="step"><span>活動進行中</span></div>
										<div class="step"><span>獎勵確認中</span></div>
										<div class="step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:when>
									<c:when test="${ state eq 'Started' }">
										<div class="step current-step"><span>活動進行中</span></div>
										<div class="step"><span>獎勵確認中</span></div>
										<div class="step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:when>
									<c:when test="${ state eq 'SubsidyCounting' }">
										<div class="step done"><span>活動進行中</span></div>
										<div class="step current-step"><span>獎勵確認中</span></div>
										<div class="step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:when>
									<c:otherwise>
										<div class="step done"><span>活動進行中</span></div>
										<div class="step done"><span>獎勵確認中</span></div>
										<div class="step current-step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>  <!-- steps end -->
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${ state eq 'Created' or state eq 'Unknow' }">
								<div class="steps-wrapper">
									<div class="steps clr">
										<div class="step last"><span>活動進行中</span></div>
									</div>
								</div>
							</c:when>
							<c:when test="${ state eq 'Started' }">
								<div class="steps-wrapper">
									<div class="steps clr">
										<div class="step current-step last"><span>活動進行中</span></div>
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
