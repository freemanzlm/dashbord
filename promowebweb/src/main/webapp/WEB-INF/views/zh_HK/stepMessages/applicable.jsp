<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
	<c:when test="${hasListingsNominated eq true}">
		<c:if test="${currentStep eq 'Seller nomination_Need approve'}">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您已成功提交報名！</h3>
					<p class="desc">請耐心等待審核結果。並註意按照後繼操作的提示完成後繼步驟！</p>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活動列表</a></li>
				</menu>
			</div>
		</c:if>
		
		<c:if test="${currentStep eq 'Seller Feedback' }">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您已成功提交報名！</h3>
					<p class="desc">在報名截止時間內您可以隨時修改您的報名。</p>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活動列表</a></li>
				</menu>
			</div>
		</c:if>
	</c:when>
	
	<c:when test="${isNomitionEnd eq true}">
		<div class="promo-state-message">
				<div class="message-content">
					<h3>活動報名已截止，感謝您的參與！</h3>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活動列表</a></li>
				</menu>
			</div>
	</c:when>
</c:choose>
