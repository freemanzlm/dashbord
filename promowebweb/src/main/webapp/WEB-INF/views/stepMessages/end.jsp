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
						<h3>您已成功领取等值 ${reward} ${promo.currency}的ebay万里通积分！</h3>
					</c:when>
					<c:when test="${ promo.reward gt 0 }">
						<h3>您已成功领取等值${reward} ${promo.currency}的奖励</h3>
					</c:when>
				</c:choose>
			</div>
		
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noUpload' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>已过报名有效期，您未上传任何刊登，期待您的下次参与！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'preFail' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>很遗憾，您的报名未通过审核</h3>
				<p class="desc">感谢您的积极参与！期待下次合作。</p>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'notEnrolledBeforeEnrollEndDate' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>已超过报名有效期，您未提交报名，期待您的下次参与！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'claimExpired' }">
		<div class="promo-state-message">
			<div class="message-content">
				<c:choose>
					<c:when test="${subsidy.status eq 'Visited' }">
						<h3>已超过奖励申领有效期，您未提交申领，期待您的下次参与！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Commited' }">
						<h3>已超过奖励申领有效期，您未完成申领，期待您的下次参与！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Uploaded' }">
						<h3>很遗憾，您的申领未通过审核且已超过奖励申领有效期。期待您的下次参与！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Appliable' }">
						<h3>已超过奖励申领有效期，您未完成申领，期待您的下次参与！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'AppliableAgain' }">
						<h3>很遗憾，您的申领未通过审核且已超过奖励申领有效期。期待您的下次参与！</h3>
					</c:when>
					<c:when test="${subsidy.status eq 'Failed' }">
						<h3>抱歉！领取奖励遇到问题，请联系您的客户经理反映情况！</h3>
					</c:when>
					<c:otherwise>
						<h3> 已超过奖励申领有效期，您未提交申领，期待您的下次参与！</h3>
					</c:otherwise>
				</c:choose>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${rewarding and (empty promo.reward or promo.reward le 0) }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遗憾！您的活动表现未达到奖励标准，感谢您对活动的支持！希望下次努力！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noSub'}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>
					<c:when test="${hasListingsNominated }">
						活动已结束，感谢您的参与！
					</c:when>
					<c:otherwise>
						已超过报名有效期，您未提交报名，期待您的下次参与！
					</c:otherwise>
				</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'isDeleted' }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遗憾，您未通过报名资格审核不能参与本活动，如有疑问请联系您的客户经理。</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活动报名已截止，感谢您的参与！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:otherwise>
</c:choose>