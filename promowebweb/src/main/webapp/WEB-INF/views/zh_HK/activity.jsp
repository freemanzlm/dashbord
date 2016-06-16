<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoDlDt}" var="deadline" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="timeSlot" value="${promoStart} ~ ${promoEnd}"></c:set>
<c:set var="activityContent" value="${promo.desc}"></c:set>

<div class="activity-detail">
	<div class="activity-time">
		
		<c:if test="${promo.type != 3}">
			<strong style="margin-right: 90px;">報名截止時間（北京时間）：${ deadline }</strong>
		</c:if>
		<strong>活動時間（北京时間）：${ timeSlot }</strong>
		<c:if test="${ state == 'rewarding' }">
			<strong style="margin-left: 90px;">獎勵領取截止時間（北京时間）：${ rewardDeadline }</strong>
		</c:if>
	</div>
	<div class="table activity-brief">
		<div class="table-row">
			<div class="table-cell brief-title">活動描述：</div>
			<div class="table-cell pretty-text">
				<div>${ activityContent }</div>
			</div>
		</div>
		<div class="table-row">
			<div class="table-cell brief-title">活動條款：</div>
			<div class="table-cell pretty-text">
				<div>${ activityContent }</div>
			</div>
		</div>
	</div>
	<c:if test="${promo.type != PMPromotionType.STANDARD}">
		<div class="activity-law">
			<a href="javascript:void(0)" class="terms-conditions">點擊閱讀《其他條款》</a>。其他條款為本活動條款的一部分，具有不可分割性。
		</div>
	</c:if>
</div>