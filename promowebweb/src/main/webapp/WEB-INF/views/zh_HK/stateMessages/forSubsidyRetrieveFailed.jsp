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
		<c:set var="rewardName" value="萬邑通" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay萬裏通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>

<div class="promo-state-message fail">
	<div class="message-content">
		<h3>領取失敗</h3>
		<c:if test="${ promo.rewardType eq 2 }">
			<p class="desc">領取：等值於${promo.reward gt 0 ? promo.reward : '0' } 元的${rewardName }
				<br />（注：每一元人民幣的獎勵，將獲得500ebay萬裏通積分的充值資格）
				<br />抱歉！對萬裏通的充值遇到問題。請通過郵件聯繫<a href="mailto:ebay-CC@ebay.com">ebay-CC@ebay.com</a>反映該問題。會有專門人員協助您解决。</p>
		</c:if>
	</div>
	<menu>
		<li><a href="index" class="btn">返回活动列表</a></li>
	</menu>
</div>