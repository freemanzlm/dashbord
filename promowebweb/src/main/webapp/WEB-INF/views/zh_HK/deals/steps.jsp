<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<div class="steps-wrapper">
	<div class="steps">
		<c:choose>
			<c:when test="${ state eq 'Created' or state eq 'Unknown' }">
				<div class="step current-step"><span>報名</span></div>
				<div class="step"><span>已提交預審</span></div>
				<div class="step"><span>預審進行中</span></div>
				<div class="step"><span>正式報名</span></div>
				<div class="step ${rewarding ? '':'last'}"><span>活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Submitted' }">
				<div class="step done"><span>報名</span></div>
				<div class="step current-step"><span>已提交預審</span></div>
				<div class="step"><span>預審進行中</span></div>
				<div class="step"><span>正式報名</span></div>
				<div class="step ${rewarding ? '':'last'}"><span>活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Verifying' }">
				<div class="step done"><span>報名</span></div>
				<div class="step done"><span>已提交預審</span></div>
				<div class="step current-step"><span>預審進行中</span></div>
				<div class="step"><span>正式報名</span></div>
				<div class="step ${rewarding ? '':'last'}"><span>活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'PromotionApproved' }">
				<div class="step done"><span>報名</span></div>
				<div class="step done"><span>已提交預審</span></div>
				<div class="step done"><span>預審進行中</span></div>
				<div class="step current-step"><span>正式報名</span></div>
				<div class="step ${rewarding ? '':'last'}"><span>活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Applied' }">
				<div class="step done"><span>報名</span></div>
				<div class="step done"><span>已提交預審</span></div>
				<div class="step done"><span>預審進行中</span></div>
				<div class="step current-step"><span>已報名</span></div>
				<div class="step ${rewarding ? '':'last'}"><span>活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Started' }">
				<div class="step done"><span>報名</span></div>
				<div class="step done"><span>已提交預審</span></div>
				<div class="step done"><span>預審進行中</span></div>
				<div class="step done"><span>已報名</span></div>
				<div class="step current-step ${rewarding ? '':'last'}"><span>活動進行中</span></div>
			</c:when>
			<c:otherwise>
				<div class="step done"><span>報名</span></div>
				<div class="step done"><span>已提交預審</span></div>
				<div class="step done"><span>預審進行中</span></div>
				<div class="step done"><span>已報名</span></div>
				<div class="step done"><span>活動進行中</span></div>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${ rewarding }">
			<c:choose>
				<c:when test="${ state eq 'SubsidyCounting' }">
					<div class="step current-step"><span>獎勵確認中</span></div>
					<div class="step"><span>申領獎勵</span></div>
					<div class="step last"><span>活動完成</span></div>
				</c:when>
				<c:when test="${ (state eq 'SubsidyWaiting') or (state eq 'SubsidyAccessed') or 
					(state eq 'SubsidySubmitted') or (state eq 'SubsidyUploaded') or (state eq 'SubsidyRetrievable') or
					(state eq 'SubsidyResubmittable') or (state eq 'SubsidyRetrieveFailed')}">
					<div class="step done"><span>獎勵確認中</span></div>
					<div class="step current-step"><span>申領獎勵</span></div>
					<div class="step last"><span>活動完成</span></div>
				</c:when>
				<c:when test="${state eq 'SubsidyRetrieved'}">
					<div class="step done"><span>獎勵確認中</span></div>
					<div class="step done"><span>申領獎勵</span></div>
					<div class="step current-step last"><span>活動完成</span></div>
				</c:when>
				<c:otherwise>
					<div class="step"><span>獎勵確認中</span></div>
					<div class="step"><span>申領獎勵</span></div>
					<div class="step last"><span>活動完成</span></div>
				</c:otherwise>
			</c:choose>
		</c:if>		
	</div>
</div>  <!-- steps end -->