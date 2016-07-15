<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd-hh" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd-hh" type="date" />

<c:choose>
	<c:when test="${isPromotionStoped eq true}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>活動已截止，我們將盡快公布統計結果，請耐心等待！</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>活动进行中！</h3>
				<p class="desc">
					活动时间为${ promoStart } 到 ${ promoEnd }, <br /> 我們將在活動結束後盡快公布統計結果，請耐心等待！
				</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:otherwise>
</c:choose>
