<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${isAwardEnd eq true}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>领取奖励截止时间为${rewardDeadline}，目前已结束，感谢您的参与。</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	
	<c:when test="${promo.state eq 'AppliableAgain'}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遗憾，您的申领未通过审核，请按照如下提示重新提交申领: </h3>

				<p class="desc">${ "Verification Reject Reason" }</p><br />
			</div>
			<menu>
				<li>
					<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填写奖励申领确认函</a>
					<br /><br />
					<a href="index">返回活动列表</a>
				</li>
			</menu>
		</div>
	</c:when>

	<c:otherwise>
		
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>恭喜！您将获得等值 ${reward} ${promo.currency} 的奖励！</h3>

				<c:if test="${ subsidyTerm.ovFlag != 1 }">
					<p class="desc">${ subsidyTerm.successInfo }</p>
					<br />
				</c:if>
			</div>
			
			<menu>
				<li>
					<c:if test="${ subsidyTerm.ovFlag == 1 }">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填写奖励申领确认函</a>
						<br /><br />
					</c:if> 
					<a href="index">返回活动列表</a>
				</li>
			</menu>

		</div>
	</c:otherwise>
</c:choose>