<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="endReason" value="${ promo.endReason }" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<fmt:formatNumber value="${promo.reward }" var="reward" minFractionDigits="2"></fmt:formatNumber>

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay萬里通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="萬邑通禮品卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${promo.state eq 'SubsidyRetrieved'}">
		<div class="promo-state-message success">
			<div class="message-content">
				<c:choose>
					<c:when test="${ ((promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region eq 'CN')
						or  promo.rewardType eq 3}">
						<h3>您已成功領取等值 ${reward} ${promo.currency} 的${rewardName }</h3>
					</c:when>
					<c:otherwise>
						<h3>恭喜您已完成本活動！感謝您的參與!</h3>
					</c:otherwise>
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
	<c:when test="${endReason == 'auFail' }">
		<div class="promo-state-message fail">
			<div class="message-content">
				<h3>很遺憾，您的報名未通過審核</h3>
				<p class="desc">感謝您的積極參與！期待下次合作。</p>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noReg' }">
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
				<h3>您的活動獎勵申領已過期</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:when>
	<c:when test="${endReason == 'noSub' or (rewarding and (empty promo.reward or promo.reward le 0)) }">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遺憾！您的活動表現未達到獎勵標準，感謝您對活動的支持！希望下次努力！</h3>
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
				<h3>活動已結束，感謝您的參與！</h3>
			</div>
			<menu><li><a href="index" class="btn">返回活動列表</a></li></menu>
		</div>
	</c:otherwise>
</c:choose>