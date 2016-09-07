<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 

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
		
		<c:if test="${currentStep eq 'Seller Feedback' and state ne 'ReEnroll'}">
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
		
		<c:if test="${currentStep eq 'Seller Feedback' and state eq 'ReEnroll'}">
			<div class="promo-state-message success">
				<div class="message-content">
					<h3>您有以下顶置并标红的报名信息失效，请确认后重新提交报名。</h3>
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
