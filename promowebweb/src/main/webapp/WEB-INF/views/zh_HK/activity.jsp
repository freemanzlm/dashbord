<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="activity-detail">
	<div class="activity-time">
		<c:if test="${ not empty promoDlDt}">
			<strong style="margin-right: 90px;">報名截止時間（北京時間）：${ promoDlDt }</strong>
		</c:if>
		
		<strong>活動時間（北京時間）：${ timeSlot }</strong>
		
		<c:if test="${ state == 'rewarding' and not empty rewardDeadline }">
			<strong style="margin-left: 90px;">獎勵領取截止時間（北京時間）：${ rewardDeadline }</strong>
		</c:if>
	</div>
	<div class="table activity-brief">
		<c:if test="${ not empty promo.desc }">
		<div class="table-row">
			<div class="table-cell brief-title">活動描述：</div>
			<div class="table-cell pretty-text">
				<div>${promo.desc}</div>
			</div>
		</div>
		</c:if>
		
		<c:if test="${ not empty promo.itemDesc }">
			<div class="table-row">
				<div class="table-cell brief-title">活動條款：</div>
				<div class="table-cell pretty-text">
					<div>${promo.itemDesc}</div>
				</div>
			</div>
		</c:if>
		
		<c:if test="${not promo.legalTermFlag}">
			<div class="table-row">
				<div class="table-cell brief-title">其他條款：</div>
				<div class="table-cell pretty-text">
					<div><a href="javascript:void(0)" class="terms-conditions">點擊閲讀《其他條款》</a>。其他條款為本活動條款的一部分，具有不可分割性。</div>
				</div>
			</div>
		</c:if>
	</div>
</div>