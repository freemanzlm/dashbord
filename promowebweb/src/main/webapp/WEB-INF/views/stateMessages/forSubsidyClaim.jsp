<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

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

<div class="promo-state-message success">
	<div class="message-content">
		<c:choose>
			<c:when
				test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region == 'CN'}">
				<h3>恭喜!您的奖励为等值${promo.reward gt 0 ? promo.reward : '0' } 元的${rewardName }</h3>
				<c:choose>
					<c:when test="${ promo.rewardType eq 1}">
						<div class="note">
							<p>再次感谢您参与了我们的活动。我们将通知第三方服务商“澳捷实业有限公司”发放奖励。请予10个工作日以后及时领取，奖励发放地址和时间如下：</p>
							<ol>
								<li>深圳 深圳市福田区深南中路3018号世纪汇广场23/F</li>
								<li>上海 上海市长宁区仙霞路317号远东国际广场B座1509</li>
								<li>北京 北京市海淀区花园东路10号高德大厦803室</li>
								<li>西安 西安市高新一路正信大厦B座203室</li>
							</ol>	
							<p>工作时间为： AM9:00--PM6:00</p>
						</div>
					</c:when>
					<c:otherwise>
						<c:if test="${ not empty rewardDeadline }">
							<p class="desc">请在${ rewardDeadline }前点击进入领奖流程完成申领。</p>
						</c:if>
					</c:otherwise>
				</c:choose>
			</c:when>
			<c:otherwise>
				<h3>恭喜您已完成本活动！接下来我们的客户经理会联系您关于奖励的相关事宜，请注意接收相关的邮件通知。感谢您的参与!</h3>
			</c:otherwise>
		</c:choose>
	</div>
	
	<c:choose>
		<c:when
			test="${ (promo.rewardType eq 1 or promo.rewardType eq 2) and (not empty promo.rewardUrl) and promo.region == 'CN'}">
			<menu>
				<li><c:choose>
						<c:when test="${ state eq 'SubsidySubmitted' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>上传奖励申请协议</a>
							<br />
							<br />
							<a href="index">返回活动列表</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyRetrievable' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>领取奖励</a>
							<br />
							<br />
							<a href="index">返回活动列表</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyResubmittable' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>重新申领奖励</a>
							<br />
							<br />
							<a href="index">返回活动列表</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyUploaded' }">
							<a href="index" class="btn">返回活动列表</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyWaiting' or state eq 'SubsidyAccessed' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>填写奖励申请协议</a>
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