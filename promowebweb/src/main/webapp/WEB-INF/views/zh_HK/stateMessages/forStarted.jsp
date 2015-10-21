<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />

<div class="promo-state-message success">
	<div class="message-content">
		<h3>活動進行中！</h3>
		<p class="desc">
			活動時間為${ promoStart }到${ promoEnd }，<br /> 我們將在活動結束後儘快公佈統計結果，請耐心等待！
		</p>
	</div>
	<menu>
		<li><a href="index" class="btn">返回活動清單</a></li>
	</menu>
</div>