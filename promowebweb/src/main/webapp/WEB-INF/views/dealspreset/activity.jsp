<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoDlDt}" var="deadline" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<c:set var="timeSlot" value="${promoStart} ~ ${promoEnd}"></c:set>
<c:set var="activityContent" value="${promo.desc}"></c:set>

<div class="activity-detail">
	<div class="activity-time">
		<strong>报名截止时间：${ deadline }</strong>
		<strong style="margin-left: 90px;">活动时间：${ timeSlot }</strong>
		<c:if test="${ state == 'rewarding' }">
			<strong style="margin-left: 90px;">奖励领取截止时间：${ rewardDeadline }</strong>
		</c:if>
	</div>
	<div class="table activity-brief">
		<div class="table-row">
			<div class="table-cell" style="width: 64px;">活动简介：</div>
			<div class="table-cell" style="width: 1014px;">
				<iframe id="activity-desc-iframe" src="/promotion/static/activityDesc.html" style="width: 100%;"></iframe>
			</div>
		</div>
	</div>
	
	<div class="" id="activity-desc" style="display: none;">
		${ activityContent }
	</div>
	
</div>

<script type="text/javascript">
$(function(){
	var activityDetail = $("#activity-desc"), descIframe = $('#activity-desc-iframe');
	try {
		descIframe.on('load', function(){
			$(this.contentDocument.body).html(activityDetail.html());
			descIframe.height(this.contentDocument.documentElement.scrollHeight);
		});
		
		// iframe may have been loaded before load event listener registration.
		$(descIframe.contents()[0].body).html(activityDetail.html());
		descIframe.height(descIframe.contents()[0].documentElement.scrollHeight);
	} catch(e) {
		
	}
});
</script>	