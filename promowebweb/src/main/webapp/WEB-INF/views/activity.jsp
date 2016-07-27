<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="activity-detail">
	<div class="activity-time">
		<c:if test="${ not empty promoDlDt}">
			<strong style="margin-right: 90px;">报名截止时间（北京时间）：${ promoDlDt }</strong>
		</c:if>
		
		<strong>活动时间（北京时间）：${ timeSlot }</strong>
		
		<c:if test="${ state == 'rewarding' and not empty rewardDeadline }">
			<strong style="margin-left: 90px;">奖励领取截止时间（北京时间）：${ rewardDeadline }</strong>
		</c:if>
	</div>
	<div class="table activity-brief">
		<c:if test="${ not empty promo.desc }">
		<div class="table-row">
			<div class="table-cell brief-title">活动描述：</div>
			<div class="table-cell pretty-text">
				<div>${promo.desc}</div>
			</div>
		</div>
		</c:if>
		
		<c:if test="${ not empty promo.itemDesc }">
			<div class="table-row">
				<div class="table-cell brief-title">活动条款：</div>
				<div class="table-cell pretty-text">
					<div>${promo.itemDesc}</div>
				</div>
			</div>
		</c:if>
	</div>
	<c:if test="${promo.type != 3}">
		<div class="activity-law">
			<a href="javascript:void(0)" class="terms-conditions">点击阅读《其他条款》</a>。其他条款为本活动条款的一部分，具有不可分割性。
		</div>
	</c:if>	
</div>