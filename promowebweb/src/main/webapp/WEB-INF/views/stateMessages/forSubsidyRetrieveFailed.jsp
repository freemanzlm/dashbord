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

<div class="promo-state-message fail">
	<div class="message-content">
		<h3>领取失败</h3>
		<c:if test="${ promo.rewardType eq 2 }">
			<p class="desc">领取：等值于${promo.reward gt 0 ? promo.reward : '0' } 元的${rewardName }
			<br />(注：每一元人民币的奖励，将获得500ebay万里通积分的充值资格)
			<br />抱歉！对万里通的充值遇到问题。请通过邮件联系<a href="mailto:ebay-CC@ebay.com">ebay-CC@ebay.com</a>反映该问题。会有专门人员协助您解决。</p>
		</c:if>
	</div>
	<menu>
		<li><a href="index" class="btn">返回活动列表</a></li>
	</menu>
</div>