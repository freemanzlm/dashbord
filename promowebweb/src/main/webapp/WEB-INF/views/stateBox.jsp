<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay万里通积分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京东卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="邮票" />
	</c:when>	
</c:choose>

<div class="active-status-box success">
	<c:choose>
		<c:when test="${state eq 'Started' }">
			<h3>活动进行中！</h3>
			<p class="desc">
				活动时间为${ promoStart } 到 ${ promoEnd }, <br />
				我们将在活动结束后尽快公布统计结果，请耐心等待！
			</p>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</c:when>
		<c:when test="${state eq 'SubsidyCounting' }">
			<h3>恭喜您已完成活动！</h3>
			<p class="desc">我们的奖励结果正在统计中， 请耐心等待！</p>
			<menu><li><a href="index" class="btn">返回活动列表</a></li></menu>
		</c:when>
		<c:otherwise>
			<c:choose>
				<c:when test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region == 'CN'}">
					<h3>恭喜!您的奖励为等值${promo.reward }元的${rewardName }</h3>
				</c:when>
				<c:otherwise>
					<h3>恭喜您已完成本活动！接下来我们的客户经理会联系您关于奖励的相关事宜，请注意接收相关的邮件通知。感谢您的参与!</h3>
				</c:otherwise>
			</c:choose>

			<c:if test="${ not empty rewardDeadline }">
				<p class="desc">请在${ rewardDeadline }前点击进入领奖流程完成申领。</p>
			</c:if>

			<c:choose>
				<c:when test="${ (promo.rewardType eq 1 or promo.rewardType eq 2) and (not empty promo.rewardUrl) and promo.region == 'CN'}">
					<menu>
						<li><c:choose>
								<c:when test="${ state eq 'SubsidySubmitted' }">
									<a href="${promo.rewardUrl}" class="btn">上传奖励申请协议</a>
									<br /><br /><a href="index">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyRetrievable' }">
									<a href="${promo.rewardUrl}" class="btn">领取奖励</a>
									<br /><br /><a href="index">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyResubmittable' }">
									<a href="${promo.rewardUrl}" class="btn">重新申领奖励</a>
									<br /><br /><a href="index">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyUploaded' }">
									<a href="index" class="btn">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyWaiting' or state eq 'SubsidyAccessed' }">
									<a href="${promo.rewardUrl}" class="btn">填写奖励申请协议</a>
									<br /><br /><a href="index">返回活动列表</a>
								</c:when>
							</c:choose>
						</li>
					</menu>
				</c:when>
				<c:otherwise>
					<menu>
						<li><a href="index" class="btn">返回活动列表</a></li>
					</menu>
				</c:otherwise>
			</c:choose>

		</c:otherwise>
	</c:choose>

</div>
<!-- active status box end -->