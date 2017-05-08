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
				<h3>很遗憾，您的申领未通过审核，请重新提交申领. </h3>
			</div>
			<menu>
				<li>
					<c:if test="${ not empty subsidyTerm and subsidyTerm.ovFlag == 1 }">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填写奖励申领确认函</a>
						<br /><br />
					</c:if> 
					<a href="index">返回活动列表</a>
				</li>
			</menu>
		</div>
	</c:when>

	<c:when test="${promo.state eq 'Applied' }">
		<div class="promo-state-message success">
			<div class="message-content">
				<c:choose>
					<c:when test="${ promo.rewardType eq 2 }">
						<h3>您已成功领取等值 ${promo.reward} ${promo.currency}的ebay万里通积分！</h3>
					</c:when>
					<c:when test="${ promo.reward gt 0 }">
						<h3>您已成功领取等值${promo.reward} ${promo.currency}的奖励</h3>
					</c:when>
				</c:choose>
			</div>
		
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	
	<c:when test="${ not empty subsidyTerm and subsidyTerm.ovFlag eq 1 }">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>恭喜！您将获得等值 ${promo.reward} ${promo.currency} 的奖励！</h3>
			</div>
			
			<menu>
				<li>
					<c:if test="${subsidy.status eq 0 or subsidy.status eq 1 }">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填写领奖协议</a>
					</c:if> 
					<c:if test="${subsidy.status eq 2}">
						<a class="btn" href="subsidy/subsidyStepTwo?promoId=${promo.promoId }">上传领奖协议</a>
					</c:if> 
					<c:if test="${subsidy.status eq 3 or  subsidy.status eq 4 }">
						<p>奖励申领中，请耐心等待。</p>
					</c:if> 
					<br /><br />
					<a href="index">返回活动列表</a>
				</li>
			</menu>
		</div>
	</c:when>
	
	<c:when test="${ not empty subsidyTerm and subsidyTerm.ovFlag ne 1 }">
		<div class="promo-state-message success">
			<div class="message-content">
				<h3>恭喜！您将获得等值 ${promo.reward} ${promo.currency} 的奖励！</h3>

				<div class="pretty-text">${ subsidyTerm.successInfo }</div>
				<br />
			</div>
			
			<menu>
				<li>
					<a href="index">返回活动列表</a>
				</li>
			</menu>
		</div>
	</c:when>
</c:choose>