<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>  

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<div class="signpost mb20">
	<div class="signpost-posts">
		<c:choose>
			<c:when test="${ state eq 'Created' or state eq 'Unknown' }">
				<div class="post current-post"><span class="label">报名</span></div>
				<div class="post"><span class="label">已报名</span></div>
				<div class="post ${rewarding ? '':'last' }"><span class="label">活动进行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Applied' }">
				<div class="post done"><span class="label">报名</span></div>
				<div class="post current-post"><span class="label">已报名</span></div>
				<div class="post ${rewarding ? '':'last' }"><span class="label">活动进行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Started' }">
				<div class="post done"><span class="label">报名</span></div>
				<div class="post done"><span class="label">已报名</span></div>
				<div class="post current-post ${rewarding ? '':'last' }"><span class="label">活动进行中</span></div>
			</c:when>
			<c:otherwise>
				<div class="post done"><span class="label">报名</span></div>
				<div class="post done"><span class="label">已报名</span></div>
				<div class="post done"><span class="label">活动进行中</span></div>
			</c:otherwise>
		</c:choose>
		<c:if test="${ rewarding }">
			<c:choose>
				<c:when test="${ state eq 'SubsidyCounting' }">
					<div class="post current-post"><span class="label">奖励确认中</span></div>
					<div class="post"><span class="label">申领奖励</span></div>
					<div class="post last"><span class="label">活动完成</span></div>
				</c:when>
				<c:when test="${ (state eq 'SubsidyWaiting') or (state eq 'SubsidyAccessed') or 
					(state eq 'SubsidySubmitted') or (state eq 'SubsidyUploaded') or (state eq 'SubsidyRetrievable') or
					(state eq 'SubsidyResubmittable') or (state eq 'SubsidyRetrieveFailed')}">
					<div class="post done"><span class="label">奖励确认中</span></div>
					<div class="post current-post"><span class="label">申领奖励</span></div>
					<div class="post last"><span class="label">活动完成</span></div>
				</c:when>
				<c:when test="${state eq 'SubsidyRetrieved'}">
					<div class="post done"><span class="label">奖励确认中</span></div>
					<div class="post done"><span class="label">申领奖励</span></div>
					<div class="post current-post last"><span class="label">活动完成</span></div>
				</c:when>
				<c:otherwise>
					<div class="post"><span class="label">奖励确认中</span></div>
					<div class="post"><span class="label">申领奖励</span></div>
					<div class="post last"><span class="label">活动完成</span></div>
				</c:otherwise>
			</c:choose>
		</c:if>		
	</div>
</div>  <!-- steps end -->