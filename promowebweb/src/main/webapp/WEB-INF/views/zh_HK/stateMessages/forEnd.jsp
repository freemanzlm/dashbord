<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="endReason" value="${ promo.endReason }" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />

<c:choose>
	<c:when test="${endReason == 'noUpload' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>已過報名有效期，您未上傳任何刊登，期待您的下次參與！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noReg' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>已超過報名有效期，您未提交報名，期待您的下次參與！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'preFail' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>很遺憾，您的報名未通過預審</h3>
				<p class="desc">感謝您的積極參與！期待下次合作。</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'auFail' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>很遺憾，您的報名未通過審核</h3>
				<p class="desc">感谢您的积极参与！期待下次合作。</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'claimExpired' }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>您的活動獎勵申領已過期</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noSub' or (rewarding and (empty promo.reward or promo.reward le 0)) }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遺憾！您的活動表現未達到獎勵標准，感謝您對活動的支持！希望下次努力！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活動已結束，感謝您的參與！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:otherwise>
</c:choose>
