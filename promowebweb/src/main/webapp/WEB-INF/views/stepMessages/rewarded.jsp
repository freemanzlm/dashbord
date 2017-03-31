<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay万里通积分" />
	</c:when>
</c:choose>

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
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<c:choose>
					<c:when	test="${ promo.state ne 'End' }">
						<h3>恭喜！您将获得等值 ${reward} ${promo.currency} 的奖励！</h3>
						<menu>
							<li>
								<c:if test="${ subsidyTerm.onlingVettingFlag == 1 }">
									<a class="btn" href="subsidy/acknowledgment?promoId=${promo.promoId }">填写奖励申领确认函</a>
								</c:if>
								<br />
								<br />
								<a href="index">返回活动列表</a>
							</li>
						</menu>
					</c:when>
					
					<c:when test="${ promo.state eq 'End' and promo.endReason eq 'subsidyRetrieved' }">
						<h3>您已成功领取等值&nbsp;${reward} ${promo.currency}&nbsp;的奖励</h3>
						<menu>
							<li>
								<a href="index">返回活动列表</a>
							</li>
						</menu>
					</c:when>
					
					<c:otherwise>
						<h3>恭喜您已完成本活动！接下来我们的客户经理会联系您关于奖励的相关事宜，请注意接收相关的邮件通知。感谢您的参与!</h3>
						<menu>
							<li>
								<a href="index">返回活动列表</a>
							</li>
						</menu>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
	</c:otherwise>
</c:choose>
