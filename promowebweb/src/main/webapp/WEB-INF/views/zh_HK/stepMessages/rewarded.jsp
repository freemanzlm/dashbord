<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay萬里通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="萬邑通禮品卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>

<c:choose>
	<c:when test="${isAwardEnd eq true}">
		<div class="promo-state-message">
			<div class="message-content">
				<h3>領取獎勵截止時間為${rewardDeadline}，目前已結束，感謝您的參與。</h3>
			</div>
			<menu>
				<li><a href="index" class="btn">返回活動列表</a></li>
			</menu>
		</div>
	</c:when>
	<c:otherwise>
		<div class="promo-state-message success">
			<div class="message-content">
				<c:choose>
					<c:when	test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region == 'CN'}">
		
						<h3>恭喜!您的獎勵為等值 ${reward} ${promo.currency}的${rewardName }</h3>
		
						<c:choose>
							<c:when test="${ state eq 'SubsidyRetrievable' }">
								<c:if test="${ promo.rewardType eq 1}">
									<div class="note">
										<p>再次感謝您參與了我們的活動。我們將通知第三方服務商“澳捷實業有限公司”發放獎勵。請予10個工作日以後及時領取，獎勵發放地址和時間如下：</p>
										<ol>
											<li>深圳 深圳市福田區深南中路3018號世紀匯廣場23/F</li>
											<li>上海 上海市長寧區仙霞路317號遠東國際廣場B座1509</li>
											<li>北京 北京市海淀區花園東路10號高德大廈803室</li>
											<li>西安 西安市高新一路正信大廈B座203室</li>
										</ol>
										<p>工作時間為： AM9:00--PM6:00</p>
									</div>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${ not empty rewardDeadline }">
									<p class="desc">請在${ rewardDeadline }前點擊進入領獎流程完成申領。</p>
								</c:if>
							</c:otherwise>
						</c:choose>
		
					</c:when>
					
					<c:when test="${ promo.rewardType eq 3 }">
						<h3>恭喜!您的獎勵為等值 ${reward} ${promo.currency}的${rewardName }</h3>
						
						<c:choose>
							<c:when test="${ state eq 'SubsidyRetrievable' }">
								<div class="note">
									<p>親愛的${unm}，再次感謝您參與了我們的活動。我們將在20個工作日內向您發放萬邑通禮品卡作為獎勵。請於收到禮品卡後30日內激活您的萬邑通禮品卡，禮品卡激活以及使用規則詳情請參考萬邑通網站的相關內容
										（<a href="http://www.winit.com.cn/news/gcpolicy.html">http://www.winit.com.cn/news/gcpolicy.html</a>）。
									</p>
								</div>
							</c:when>
							<c:otherwise>
								<c:if test="${ not empty rewardDeadline }">
									<p class="desc">請在${ rewardDeadline }前點擊進入領獎流程完成申領。</p>
								</c:if>
							</c:otherwise>
						</c:choose>
						
					</c:when>
					
					<c:otherwise>
						<h3>恭喜您已完成本活動！接下來我們的客戶經理會聯繫您關於獎勵的相關事宜，請注意接收相關的郵件通知。感謝您的參與!</h3>
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
								<c:when test="${ state eq 'SubsidySubmitted' }">
									<a href="${promo.rewardUrl}" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' }>上傳獎勵申請協議</a>
									<br />
									<br />
									<a href="index">返回活動列表</a>
								</c:when>
								
								<c:when test="${ state eq 'SubsidyRetrievable' }">
									<c:choose>
										<c:when test="${ promo.rewardType eq 2 and promo.region == 'CN'}">
											<a href="${promo.rewardUrl}" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' }>領取獎勵</a>
											<br />
											<br />
											<a href="index">返回活動列表</a>
										</c:when>
										<c:otherwise>
											<a href="index" class="btn">返回活動列表</a>
										</c:otherwise>
									</c:choose>
								</c:when>
								
								<c:when test="${ state eq 'SubsidyResubmittable' }">
									<a href="${promo.rewardUrl}" class="btn" ${ isAdmin or isPreview ? 'disabled' : '' }>重新申領獎勵</a>
									<br />
									<br />
									<a href="index">返回活動列表</a>
								</c:when>
								
								<c:when test="${ state eq 'SubsidyUploaded' }">
									<a href="index" class="btn">返回活動列表</a>
								</c:when>
								<c:when	test="${ state eq 'SubsidyWaiting' or state eq 'SubsidyAccessed' }">
									<a href="${promo.rewardUrl}" class="btn"
										${ isAdmin or isPreview ? 'disabled' : '' }>填寫獎勵申請協議</a>
									<br />
									<br />
									<a href="index">返回活動列表</a>
								</c:when>
							</c:choose>
						</li>
					</menu>
				</c:when>
				<c:otherwise>
					<menu>
						<li><a href="index" class="btn">返回活動列表</a></li>
					</menu>
				</c:otherwise>
			</c:choose>
		</div>
	</c:otherwise>
</c:choose>
