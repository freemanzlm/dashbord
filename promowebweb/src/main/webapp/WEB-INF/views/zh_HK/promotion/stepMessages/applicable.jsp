<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

<c:choose>
	<c:when test="${hasListingsNominated eq true}">
		<c:if test="${currentStep eq 'SELLER NOMINATION_NEED APPROVE' and state ne 'ReEnroll'}">
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
		
		<c:if test="${currentStep eq 'SELLER NOMINATION_NEED APPROVE' and state eq 'ReEnroll'}">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您有以下置頂並標紅的報名信息失效，請確認後重新提交報名。</h3>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活動列表</a></li>
				</menu>
			</div>
		</c:if>
		
		<c:if test="${currentStep eq 'SELLER FEEDBACK' and state ne 'ReEnroll'}">
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
		
		<c:if test="${currentStep eq 'SELLER FEEDBACK' and state eq 'ReEnroll'}">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您有以下置頂並標紅的報名信息失效，請確認後重新提交報名。</h3>
				</div>
				<menu>
					<li><a href="index" class="btn">返回活動列表</a></li>
				</menu>
			</div>
		</c:if>
	</c:when>
	
	<c:otherwise>
		<c:choose>
			<c:when test="${isRegEnd eq true}">
				<div class="promo-state-message">
						<div class="message-content">
							<h3>已超過報名有效期，您未提交報名，期待您的下次參與！</h3>
						</div>
						<menu>
							<li><a href="index" class="btn">返回活動列表</a></li>
						</menu>
					</div>
			</c:when>
			<c:otherwise>
				<c:if test="${(fn:containsIgnoreCase(stepList, 'SELLER NOMINATION_NEED APPROVE')) and currentStep eq 'SELLER FEEDBACK'}">
					<div class="promo-state-message success">
						<div class="message-content">
							<h3>您已通過預審！請於<span class="color-cyan">${promoDlDt}</span>前選擇並提交如下通過預審的刊登完成正式報名。</h3>
							<p class="desc">活動時間為<span class="color-cyan">${promoStart }</span>到<span class="color-cyan">${promoEnd }</span></p>
							<p class="desc">活動如有更改，以最終通知為準</p>
						</div>
						<br />
						<menu>
							<li><a href="#listing-form" class="btn">正式報名</a></li>
						</menu>
					</div>
				</c:if>
			</c:otherwise>
		</c:choose>
	</c:otherwise>
</c:choose>
