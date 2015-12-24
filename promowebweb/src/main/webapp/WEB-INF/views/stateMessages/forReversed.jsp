<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-dd" type="date" />

<div class="promo-state-message">
	<div class="message-content">
		<h3>
			活动时间已调整为<span class="color-cyan">${ promoStart }</span>到<span class="color-cyan">${ promoEnd }</span>，
			请在<span class="color-cyan">${promoDlDt}</span>前重新确认你参加活动的刊登！
		</h3>
	</div>
	<menu>
		<li><a href="#listing" class="btn">正式報名</a></li>
	</menu>
</div>
<!-- active status box end -->