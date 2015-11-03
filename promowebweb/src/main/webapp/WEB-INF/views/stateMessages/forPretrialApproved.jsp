<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-dd" type="date" />

<c:choose>
	<c:when test="${promo.isReversed }">
		<%@ include file="forReversed.jsp" %>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message ${ not expired ? 'success' : '' }">
			<div class="message-content">
				<h3>您已成功通过预审！请于<span class="cyan">${ promoDlDt }</span>前<a href="#listing">选择并提交</a>如下通过预审的刊登完成正式报名。</h3>
				<p class="desc">活动时间为<span class="cyan">${ promoStart } </span>到 <span class="cyan">${ promoEnd }</span><br />活动如有更改，以最终通知为准。</p>
			</div>
			<menu><li><a href="#listing" class="btn">正式报名</a></li></menu>	
		</div>
	</c:otherwise>
</c:choose>