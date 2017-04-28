<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="endReason" value="${ promo.endReason }" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<fmt:formatNumber value="${promo.reward }" var="reward" minFractionDigits="2"></fmt:formatNumber>

<c:choose>
	<c:when test="${promo.state eq 'SubsidyRetrieved'}">
		<div class="promo-state-message success">
			<div class="message-content">
				<c:choose>
					<c:when test="${ promo.rewardType eq 2 }">
						<h3>您已成功領取等值 ${reward} ${promo.currency}的ebay萬裏通積分！</h3>
					</c:when>
					<c:when test="${ promo.reward gt 0 }">
						<h3>您已成功領取等值${reward} ${promo.currency}的獎勵</h3>
					</c:when>
				</c:choose>
			</div>
		
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noUpload' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>已過報名有效期，您未上傳任何刊登，期待您的下次參與！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'preFail' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>很遺憾，您的報名未通過審核</h3>
				<p class="desc">感謝您的積極參與！期待下次合作。</p>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'notEnrolledBeforeEnrollEndDate' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>已超過報名有效期，您未提交報名，期待您的下次參與！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'claimExpired' }">
		<div class="promo-state-message">
			<div class="message-content">
				<c:choose>
					<c:when test="${subsidy.status eq 'Visited' }">
						<h3>已超過獎勵申領有效期，您未提交申領，期待您的下次參與！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Commited' }">
						<h3>已超過獎勵申領有效期，您未完成申領，期待您的下次參與！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Uploaded' }">
						<h3>很遺憾，您的申領未通過審核且已超過獎勵申領有效期。期待您的下次參與！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Appliable' }">
						<h3>已超過獎勵申領有效期，您未完成申領，期待您的下次參與！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'AppliableAgain' }">
						<h3>很遺憾，您的申領未通過審核且已超過獎勵申領有效期。期待您的下次參與！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Failed' }">
						<h3>抱歉！領取獎勵遇到問題，請聯系您的客戶經理反映情况！</h3>
					</c:when>
					<c:otherwise>
						<h3>已超過獎勵申領有效期，您未提交申領，期待您的下次參與！</h3>
					</c:otherwise>
				</c:choose>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${rewarding and (empty promo.reward or promo.reward le 0) }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遺憾！您的活動表現未達到獎勵標準，感謝您對活動的支持！希望下次努力！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noSub'}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>
					<c:when test="${hasListingsNominated }">
						活動已結束，感謝您的參與！
					</c:when>
					<c:otherwise>
						已超過報名有效期，您未提交報名，期待您的下次參與！
					</c:otherwise>
				</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'isDeleted' }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遺憾，您未通過報名資格審核不能參與本活動，如有疑問請聯繫您的客戶經理。</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活動報名已截止，感謝您的參與！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:otherwise>
</c:choose>