<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<!-- campaign time -->
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd HH:mm" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd HH:mm" type="date" />
<!-- enroll or confirm deadline -->
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-dd HH:mm" type="date" />
<!-- reward claim deadline -->
<fmt:formatDate value="${promo.rewardDlDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />
<fmt:formatNumber value="${promo.reward }" var="reward" minFractionDigits="2"></fmt:formatNumber>
<c:set var="timeSlot" value="${promoStart} ~ ${promoEnd}"></c:set>
<c:set var="state" value="${ promo.state }"></c:set>
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="now" value="<%=new java.util.Date()%>" />
			
<c:choose>
	<c:when test="${promo.endReason ne 'claimExpired' and promo.endReason ne 'subsidyRetrieved' and promo.state == 'End'}">
		<%@ include file="stepMessages/end.jsp" %>
	</c:when>
	<c:when test="${currentStep eq 'SELLER NOMINATION_NEED APPROVE' or currentStep eq 'SELLER FEEDBACK'}">
		<%@ include file="stepMessages/applicable.jsp" %>
	</c:when>
	
	<c:when test="${currentStep eq 'PROMOTION SUBMITTED' }">
		<%@ include file="stepMessages/audit.jsp" %>
	</c:when>
	
	<c:when test="${currentStep eq 'PROMOTION IN PROGRESS'}">
		<%@ include file="stepMessages/inprogress.jsp" %>
	</c:when>
	
	<c:when test="${currentStep eq 'PROMOTION IN VALIDATION' }">
		<%@ include file="stepMessages/rewarding.jsp" %>
	</c:when>
	
	<c:when test="${currentStep eq 'PROMOTION VALIDATED' }">
		<%@ include file="stepMessages/rewarded.jsp" %>
	</c:when>
	
	<c:when test="${(currentStep eq 'NOTIFICATION EDM APPROVED' and not promo.publishFlag) or isAdmin }">
		<%@ include file="stepMessages/notificationEdm.jsp" %>
	</c:when>
	
	<c:otherwise>
		<c:if test="${ currentStep eq 'PROMOTION END' }">
			<%@ include file="stepMessages/end.jsp" %>
		</c:if>
	</c:otherwise>
</c:choose>