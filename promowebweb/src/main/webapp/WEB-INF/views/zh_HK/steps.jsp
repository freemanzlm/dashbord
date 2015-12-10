<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>

<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />

<div class="signpost mb20">
	<div class="signpost-posts">
		<c:choose>
			<c:when test="${ state eq 'Created' or state eq 'Unknown' }">
				<div class="post current-post"><span class="label">報名</span></div>
				<div class="post"><span class="label">已提交預審</span></div>
				<div class="post"><span class="label">預審進行中</span></div>
				<div class="post"><span class="label">正式報名</span></div>
				<div class="post ${rewarding ? '':'last'}"><span class="label">活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Applied' }">
				<div class="post done"><span class="label">報名</span></div>
				<div class="post current-post"><span class="label">已報名</span></div>
				<div class="post ${rewarding ? '':'last'}"><span class="label">活動進行中</span></div>
			</c:when>
			<c:when test="${ state eq 'Started' }">
				<div class="post done"><span class="label">報名</span></div>
				<div class="post done"><span class="label">已報名</span></div>
				<div class="post current-post ${rewarding ? '':'last'}"><span class="label">活動進行中</span></div>
			</c:when>
			<c:otherwise>
				<div class="post done"><span class="label">報名</span></div>
				<div class="post done"><span class="label">已報名</span></div>
				<div class="post done"><span class="label">活動進行中</span></div>
			</c:otherwise>
		</c:choose>
		
		<c:if test="${ rewarding }">
			<c:choose>
				<c:when test="${ state eq 'SubsidyCounting' }">
					<div class="post current-post"><span class="label">獎勵確認中</span></div>
					<div class="post"><span class="label">申領獎勵</span></div>
					<div class="post last"><span class="label">活動完成</span></div>
				</c:when>
				<c:when test="${ (state eq 'SubsidyWaiting') or (state eq 'SubsidyAccessed') or 
					(state eq 'SubsidySubmitted') or (state eq 'SubsidyUploaded') or (state eq 'SubsidyRetrievable') or
					(state eq 'SubsidyResubmittable') or (state eq 'SubsidyRetrieveFailed')}">
					<div class="post done"><span class="label">獎勵確認中</span></div>
					<div class="post current-post"><span class="label">申領獎勵</span></div>
					<div class="post last"><span class="label">活動完成</span></div>
				</c:when>
				<c:when test="${state eq 'SubsidyRetrieved'}">
					<div class="post done"><span class="label">獎勵確認中</span></div>
					<div class="post done"><span class="label">申領獎勵</span></div>
					<div class="post current-post last"><span class="label">活動完成</span></div>
				</c:when>
				<c:otherwise>
					<div class="post"><span class="label">獎勵確認中</span></div>
					<div class="post"><span class="label">申領獎勵</span></div>
					<div class="post last"><span class="label">活動完成</span></div>
				</c:otherwise>
			</c:choose>
		</c:if>		
	</div>
</div>  <!-- steps end -->