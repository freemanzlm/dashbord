<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay万里通积分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京东卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="邮票" />
	</c:when>
</c:choose>

<div class="promo-state-message success">
	<div class="message-content">
		<c:choose>
			<c:when test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) && promo.region eq 'CN' }">
				<h3>您已成功领取等值${promo.reward gt 0 ? promo.reward : '0' } 元的${rewardName }</h3>
			</c:when>
			<c:otherwise>
				<h3>恭喜您已完成本活动！感谢您的参与!</h3>
			</c:otherwise>
		</c:choose>
	</div>

	<menu>
		<li><a href="index" class="btn">返回活动列表</a></li>
	</menu>
</div>
<!-- active status box end -->