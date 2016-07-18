<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  

<%-- stepList comes from SalesForce --%>
<%-- <c:set var="stepList" value="${ 'Draft>Nomination eDM in approve flow>Nomination eDM approved>Seller nomination_Need approve>Promotion Submitted>Promotion Approved>Notification eDM in approve flow>Notification eDM approved>Seller Feedback>Promotion in progress>Promotion in validation>Promotion validated' }" /> --%>
<c:set var="stepList" value="${ promo.stepList }" />
<c:set var="hasValidCurrentStep" value="${ promo.hasValidCurrentStep }" />
<c:set var="hasGotCurrentStep" value="${ false }" />
<c:set var="isCurrentStep" value="${ false }" />

<c:if test="${not empty stepList}">
	<div class="signpost mb20">
		<div class="signpost-posts">
			<c:forTokens items="${ stepList }" delims=">" var="step">
				<c:choose>
					<c:when test="${step eq currentStep}">
						<c:set var="hasGotCurrentStep" value="${ true }" />
						<c:set var="isCurrentStep" value="${ true }" />
					</c:when>
					<c:otherwise>
						<c:set var="isCurrentStep" value="${ false }" />
					</c:otherwise>
				</c:choose>
				
				<c:if test="${step eq 'Seller nomination_Need approve'}">
					<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">活動報名</span></div>
				</c:if>
				<c:if test="${step eq 'Seller Feedback'}">
					<c:choose>
						<c:when test="${fn:containsIgnoreCase(stepList, 'Seller nomination_Need approve') }">
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">正式報名</span></div>
						</c:when>
						<c:otherwise>
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">活動報名</span></div>
						</c:otherwise>
					</c:choose>
				</c:if>
				<c:if test="${step eq 'Promotion Submitted'}">
					<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">報名審核</span></div>
				</c:if>
				<c:if test="${step eq 'Promotion in progress'}">
					<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">活動進行</span></div>
				</c:if>				
				<c:if test="${step eq 'Promotion in validation'}">
					<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">獎勵審核</span></div>
				</c:if>
				<c:if test="${step eq 'Promotion validated'}">
					<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">申領獎勵</span></div>
				</c:if>
			</c:forTokens>
			
			<c:choose>
				<c:when test="${ currentStep eq 'Promotion end' }">
					<div class="post current-post last""><span class="label">活動結束</span></div>
				</c:when>
				<c:otherwise>
					<div class="post last""><span class="label">活動結束</span></div>
				</c:otherwise>
			</c:choose>			
		</div>
	</div>
</c:if>