<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd-hh" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd-hh" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-hh" type="date" />

<c:choose>
	<c:when test="${approved eq true}">
		<div class="promo-state-message ${ not expired ? 'success' : '' }">
			<div class="message-content">
				<h3>您已成功通過預審！請於<span class="color-cyan">${ promoDlDt }</span>前<a href="#listing">選擇並提交</a>如下通過預審的刊登完成正式報名。</h3>
				<p class="desc">活動時間為<span class="color-cyan">${ promoStart } </span>到 <span class="color-cyan">${ promoEnd }</span><br />活動如有更改，以最終通知為準。</p>
			</div>
			<menu><li><a href="#listing" class="btn">正式報名</a></li></menu>
		</div>
	</c:when>
	
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>您已成功提交審核！請耐心等待審核結果。</h3>
				<p class="desc">我們已經完整地收到您的刊登物品列表，並會及時反饋到活動站點，請您耐心等待最終確認。</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:otherwise>
</c:choose>

