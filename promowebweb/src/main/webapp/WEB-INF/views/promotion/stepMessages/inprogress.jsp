<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd HH:mm" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd-HH:mm" type="date" />
<fmt:formatDate value="${now}" var="now" pattern="yyyy-MM-dd-HH:mm" type="date" />

<c:choose>
	<c:when test="${isRegEnd eq true and hasListingsNominated ne true}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活动报名已截止，感谢您的参与！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${now lt promoStart}">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活动开始时间为${promoStart}，敬请期待！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${now ge promoStart and now le promoEnd}">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活动进行中！</h3>
				<p class="desc">
					活动时间为${ promoStart } 到 ${ promoEnd }, <br /> 我们将在活动结束后尽快公布统计结果，请耐心等待！
				</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${now gt promoEnd}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活动已截止，我们将尽快公布统计结果，请耐心等待！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活动进行中！</h3>
				<p class="desc">
					活动时间为${ promoStart } 到 ${ promoEnd }, <br /> 我们将在活动结束后尽快公布统计结果，请耐心等待！
				</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:otherwise>
</c:choose>
