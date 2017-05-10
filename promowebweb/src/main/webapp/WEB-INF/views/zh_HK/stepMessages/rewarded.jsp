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
				<c:if test="${ not empty wltAccount }">
					<p class="wlt-binding">
						请注意：您绑定的<a target="_blank" href="http://www.ebay.cn/mkt/leadsform/efu/11183.html">万里通</a>账号是：${wltAccount.wltUserId}，
						<a href="http://www.wanlitong.com/myPoint/brandPointSch.do?fromType=avail&pageNo=1&brandPointNo=h5mg&dateType=0&sortFlag=ddd">查积分，积分当钱花。</a>
					</p>
				</c:if>
			</div>
			
			<menu>
				<li>
					<c:if test="${subsidy.status eq 0 or subsidy.status eq 1 }">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填寫領獎協定</a>
					</c:if> 
					<c:if test="${subsidy.status eq 2}">
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">上傳領獎協定</a>
					</c:if> 
					<c:if test="${subsidy.status eq 3 }">
						<p>獎勵申領稽核中，請耐心等待。</p> <br />
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">修改已上傳的確認函</a>
					</c:if>
					<c:if test="${subsidy.status eq 4 }">
						<h3>恭喜！您將獲得等值 ${reward} ${promo.currency} 的獎勵！</h3>
						<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">領取獎勵</a>
					</c:if>
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