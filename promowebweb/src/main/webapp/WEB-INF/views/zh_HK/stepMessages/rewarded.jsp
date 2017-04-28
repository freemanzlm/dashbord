<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:choose>
	<c:when test="${isAwardEnd eq true}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>領取獎勵截止時間為${rewardDeadline}，現時已結束，感謝您的參與。</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	
	<c:when test="${promo.state eq 'AppliableAgain'}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>很遗憾，您的申领未通过审核，请重新提交申领。</h3>
			</div>
			<menu>
				<li>
					<c:if test="${ not empty subsidyTerm and subsidyTerm.ovFlag == 1 }">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填寫獎勵申領確認函</a>
						<br /><br />
					</c:if> 
					<a href="index">返回活動清單</a>
				</li>
			</menu>
		</div>
	</c:when>

	<c:when test="${promo.state eq 'Applied' }">
		<div class="promo-state-message success">
			<div class="message-content">
				<c:choose>
					<c:when test="${ promo.rewardType eq 2 }">
						<h3>您已成功领取等值 ${reward} ${promo.currency}的ebay万里通积分！</h3>
					</c:when>
					<c:when test="${ promo.reward gt 0 }">
						<h3>您已成功领取等值${reward} ${promo.currency}的奖励</h3>
					</c:when>
				</c:choose>
			</div>
		
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	
	<c:when test="${ not empty subsidyTerm and subsidyTerm.ovFlag eq 1 }">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>恭喜！您將獲得等值 ${reward} ${promo.currency} 的獎勵！</h3>
			</div>
			
			<menu>
				<li>
					<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填寫獎勵申領確認函</a>
					<br /><br />
					<a href="index">返回活動清單</a>
				</li>
			</menu>
		</div>
	</c:when>
	
	<c:when test="${ not empty subsidyTerm and subsidyTerm.ovFlag ne 1 }">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>恭喜！您將獲得等值 ${reward} ${promo.currency} 的獎勵！</h3>

				<div class="pretty-text">${ subsidyTerm.successInfo }</div>
				<br />
			</div>
			
			<menu>
				<li>
					<a href="index">返回活動清單</a>
				</li>
			</menu>
		</div>
	</c:when>
</c:choose>