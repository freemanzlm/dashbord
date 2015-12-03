<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="萬邑通禮品卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay萬裏通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>

<div class="promo-state-message success">
	<div class="message-content">
		<c:choose>
			<c:when test="${ ((promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) && promo.region eq 'CN')
				or promo.rewardType eq 3 }">
				<h3>您已成功領取等值${promo.reward gt 0 ? promo.reward : '0' } {promo.currency}的${rewardName }</h3>
			</c:when>
			<c:otherwise>
				<h3>恭喜您已完成本活動！感謝您的參與！</h3>
			</c:otherwise>
		</c:choose>
	</div>

	<menu>
		<li><a href="index" class="btn">返回活動清單</a></li>
	</menu>
</div>
<!-- active status box end -->