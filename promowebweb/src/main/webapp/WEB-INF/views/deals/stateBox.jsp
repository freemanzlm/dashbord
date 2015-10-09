<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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

<c:choose>
	<c:when test="${state eq 'Started' }">
		<div class="active-status-box success">
			<h3>经站点通知，我们很高兴地通知您，您已经正式通过活动报名。</h3>
			<p class="desc">
				活动时间为${ promoStart } 到 ${ promoEnd }, <br /> 我们将在活动结束后尽快公布统计结果，请耐心等待！
			</p>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${state eq 'SubsidyCounting' }">
		<div class="active-status-box success">
			<h3>恭喜您已完成活动！</h3>
			<p class="desc">我们的奖励结果正在统计中， 请耐心等待！</p>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${state eq 'SubsidyRetrieveFailed' }">
		<div class="active-status-box fail">
			<h3>领取失败</h3>
			<c:if test="${ promo.rewardType eq 2 }">
				<p class="desc">领取：等值于${promo.reward }元的${rewardName }
				<br />(注：每一元人民币的奖励，将获得500ebay万里通积分的充值资格)
				<br />抱歉！对万里通的充值遇到问题。请通过邮件联系<a href="mailto:ebay-CC@ebay.com">ebay-CC@ebay.com</a>反映该问题。会有专门人员协助您解决。</p>
			</c:if>
			<menu>
				<li><a href="index" class="btn">返回活动列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="active-status-box success">
			<c:choose>
				<c:when
					test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region == 'CN'}">
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
				<c:when
					test="${ (promo.rewardType eq 1 or promo.rewardType eq 2) and (not empty promo.rewardUrl) and promo.region == 'CN'}">
					<menu>
						<li><c:choose>
								<c:when test="${ state eq 'SubsidySubmitted' }">
									<a href="${promo.rewardUrl}" class="btn">上传奖励申请协议</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyRetrievable' }">
									<a href="${promo.rewardUrl}" class="btn">领取奖励</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyResubmittable' }">
									<a href="${promo.rewardUrl}" class="btn">重新申领奖励</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyUploaded' }">
									<a href="index" class="btn">返回活动列表</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyWaiting' or state eq 'SubsidyAccessed' }">
									<a href="${promo.rewardUrl}" class="btn">填写奖励申请协议</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
								</c:when>
							</c:choose></li>
					</menu>
				</c:when>
				<c:otherwise>
					<menu>
						<li><a href="index" class="btn">返回活动列表</a></li>
					</menu>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>

<!-- active status box end -->