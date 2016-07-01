<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoDlDt}" var="promoDlDt" pattern="yyyy-MM-dd" type="date" />
<c:set var="regType" value="${promo.regType}" />
<!-- whether seller has nominted his listings -->
<c:set var="hasListingsNominated" value="${true}" />

<c:choose>
	<c:when test="${hasListingsNominated eq true}">
		<c:if test="${currentStep eq 'Seller nomination_Need approve'}">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您已成功提交报名！</h3>
					<p class="desc">请耐心等待审核结果。并注意按照后继操作的提示完成后继步骤！</p>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活动列表</a></li>
				</menu>
			</div>
		</c:if>
		
		<c:if test="${currentStep eq 'Seller Feedback' }">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您已成功提交报名！</h3>
					<p class="desc">在报名截止时间内您可以随时修改您的报名。</p>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活动列表</a></li>
				</menu>
			</div>
		</c:if>
	</c:when>
	
	<c:when test="${isNomitionEnd eq true}">
		<div class="promo-state-message">
				<div class="message-content">
					<h3>活动报名已截止，感谢您的参与！</h3>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活动列表</a></li>
				</menu>
			</div>
	</c:when>
</c:choose>
