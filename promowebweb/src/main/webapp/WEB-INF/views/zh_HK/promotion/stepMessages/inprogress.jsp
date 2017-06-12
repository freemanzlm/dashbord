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
				<h3>活動報名已截止，感謝您的參與！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${now lt promoStart}">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活動開始時間為${promoStart}，敬請期待！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${now ge promoStart and now le promoEnd}">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活動進行中！</h3>
				<p class="desc">
					活動時間為${ promoStart } 到 ${ promoEnd }, <br /> 我們將在活動結束後儘快公佈統計結果，請耐心等待！
				</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${now gt promoEnd}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活動已截止，我們將儘快公佈統計結果，請耐心等待！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活動進行中！</h3>
				<p class="desc">
					活動時間為${ promoStart } 到 ${ promoEnd }, <br /> 我們將在活動結束後儘快公佈統計結果，請耐心等待！
				</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:otherwise>
</c:choose>
