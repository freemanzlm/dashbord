<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  

<%-- stepList comes from SalesForce --%>
<c:set var="hasValidCurrentStep" value="${ promo.hasValidCurrentStep }" />
<c:set var="hasGotCurrentStep" value="${ false }" />
<c:set var="isCurrentStep" value="${ false }" />

<c:if test="${not empty stepList}">
	<div class="signpost mb20">
		<div class="signpost-posts">
			<c:choose>
				<c:when test="${promo.state ne 'End'}">
					<c:forTokens items="${ stepList }" delims=">" var="step">
						<c:choose>
							<c:when test="${step eq visibleCurrentStep}">
								<c:set var="hasGotCurrentStep" value="${ true }" />
								<c:set var="isCurrentStep" value="${ true }" />
							</c:when>
							<c:otherwise>
								<c:set var="isCurrentStep" value="${ false }" />
							</c:otherwise>
						</c:choose>
						
						<c:if test="${step eq 'SELLER NOMINATION_NEED APPROVE'}">
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">活動報名中</span></div>
						</c:if>
						<c:if test="${step eq 'SELLER FEEDBACK'}">
							<c:choose>
								<c:when test="${fn:containsIgnoreCase(stepList, 'SELLER NOMINATION_NEED APPROVE') }">
									<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">正式報名</span></div>
								</c:when>
								<c:otherwise>
									<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">活動報名</span></div>
								</c:otherwise>
							</c:choose>
						</c:if>
						<c:if test="${step eq 'PROMOTION SUBMITTED'}">
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">報名審核中</span></div>
						</c:if>
						<c:if test="${step eq 'PROMOTION IN PROGRESS'}">
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">活動進行中</span></div>
						</c:if>				
						<c:if test="${step eq 'PROMOTION IN VALIDATION'}">
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">獎勵審核中</span></div>
						</c:if>
						<c:if test="${step eq 'PROMOTION VALIDATED'}">
							<div class="post ${!hasGotCurrentStep ? (hasValidCurrentStep ? 'done' : '') : (isCurrentStep ? 'current-post' : '')}"><span class="label">申領獎勵</span></div>
						</c:if>
					</c:forTokens>
					
					<c:choose>
						<c:when test="${ (currentStep eq 'PROMOTION END')}">
							<div class="post current-post last"><span class="label">活動結束</span></div>
						</c:when>
						
						<c:otherwise>
							<div class="post last"><span class="label">活動結束</span></div>
						</c:otherwise>
					</c:choose>	
				</c:when>
				
				<c:when test="${promo.state eq 'End' }">
					<div class="post current-post last"><span class="label">活動結束</span></div>
				</c:when>	
			</c:choose>
		</div>
	</div>
</c:if>