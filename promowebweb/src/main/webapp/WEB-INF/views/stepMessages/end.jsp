<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="endReason" value="${ promo.endReason }" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />

<c:choose>
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
				<h3>很遗憾，您的报名未通过预审</h3>
				<p class="desc">感谢您的积极参与！期待下次合作。</p>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'auFail' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>很遗憾，您的报名未通过审核</h3>
				<p class="desc">感谢您的积极参与！期待下次合作。</p>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noReg' }">
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
				<h3>您的活动奖励申领已过期</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noSub' or (rewarding and (empty promo.reward or promo.reward le 0)) }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遗憾！您的活动表现未达到奖励标准，感谢您对活动的支持！希望下次努力！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活动已结束，感谢您的参与！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</div>
	</c:otherwise>
</c:choose>