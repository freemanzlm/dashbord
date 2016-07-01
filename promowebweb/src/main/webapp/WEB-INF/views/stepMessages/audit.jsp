<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd-hh" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd-hh" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-hh" type="date" />
<c:set var="regType" value="${promo.regType}" />

<c:choose>
	<c:when test="${approved eq true}">
		<div class="promo-state-message ${ not expired ? 'success' : '' }">
			<div class="message-content">
				<h3>您已成功通过预审！请于<span class="color-cyan">${ promoDlDt }</span>前<a href="#listing">选择并提交</a>如下通过预审的刊登完成正式报名。</h3>
				<p class="desc">活动时间为<span class="color-cyan">${ promoStart } </span>到 <span class="color-cyan">${ promoEnd }</span><br />活动如有更改，以最终通知为准。</p>
			</div>
			<menu><li><a href="#listing" class="btn">正式报名</a></li></menu>
		</div>
	</c:when>
	
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>您已成功提交审核！请耐心等待审核结果。</h3>
				<p class="desc">我们已经完整地收到您的刊登物品列表，并会及时反馈到活动站点，请您耐心等待最终确认。</p>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:otherwise>
</c:choose>

