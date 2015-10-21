<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-dd" type="date" />

<div class="promo-state-message">
	<div class="message-content">
		<h3>
			活動時間已調整為<span class="cyan">${ promoStart }</span>到<span class="cyan">${ promoEnd }</span>，
			請在<span class="cyan">${promoDlDt}</span>前重新確認你參加活動的刊登！
		</h3>
	</div>
	<menu>
		<li><a href="#listing" class="btn">正式報名</a></li>
	</menu>
</div>