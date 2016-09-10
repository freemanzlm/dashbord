<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay万里通积分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="万邑通礼品卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京东卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="邮票" />
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
					<c:when	test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region == 'CN'}">
		
						<h3>恭喜!您的奖励为等值&nbsp;${reward} ${promo.currency}的${rewardName }</h3>
		
						<c:choose>
							<c:when test="${ state eq 'Appliable' }">
								<c:if test="${ promo.rewardType eq 1}">
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
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${ not empty rewardDeadline }">
									<p class="desc">请在${ rewardDeadline }前点击进入领奖流程完成申领。</p>
								</c:if>
							</c:otherwise>
						</c:choose>
		
					</c:when>
					
					<c:when test="${ promo.rewardType eq 3 }">
						<h3>恭喜!您的奖励为等值&nbsp;${reward} ${promo.currency}的${rewardName }</h3>
						
						<c:choose>
							<c:when test="${ state eq 'Appliable' }">
								<div class="note">
									<p>亲爱的${unm}，再次感谢您参与了我们的活动。我们将在20个工作日内向您发放万邑通礼品卡作为奖励。请于收到礼品卡后30日内激活您的万邑通礼品卡，礼品卡激活以及使用规则详情请参考万邑通网站的相关内容
										（<a href="http://www.winit.com.cn/news/gcpolicy.html">http://www.winit.com.cn/news/gcpolicy.html</a>）。
									</p>
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
					test="${ (((promo.rewardType eq 1 or promo.rewardType eq 2) and promo.region == 'CN') or promo.rewardType eq 3)
						and (not empty promo.rewardUrl) }">
					<menu>
						<li>
							<c:choose>
								<c:when test="${ state eq 'Commited' }">
									<a href="${promo.rewardUrl}" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' }>上传奖励申请协议</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
								</c:when>
								
								<c:when test="${ state eq 'Appliable' }">
									<c:choose>
										<c:when test="${ promo.rewardType eq 2 and promo.region == 'CN'}">
											<a href="${promo.rewardUrl}" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' }>领取奖励</a>
											<br />
											<br />
											<a href="index">返回活动列表</a>
										</c:when>
										<c:otherwise>
											<a href="index" class="btn">返回活动列表</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								
								<c:when test="${ state eq 'AppliableAgain' }">
									<a href="${promo.rewardUrl}" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' }>重新申领奖励</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
								</c:when>
								
								<c:when test="${ state eq 'Uploaded' }">
									<a href="index" class="btn">返回活动列表</a>
								</c:when>
								<c:when	test="${ state eq 'Awarding' or state eq 'Visited' }">
									<a href="${promo.rewardUrl}" class="btn"
										${ isAdmin or isPreview ? 'disabled' : '' }>填写奖励申请协议</a>
									<br />
									<br />
									<a href="index">返回活动列表</a>
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
		</div>
	</c:otherwise>
</c:choose>
