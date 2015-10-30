<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardName" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="萬邑通" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardName" value="ebay萬裏通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>

<div class="promo-state-message success">
	<div class="message-content">
		<c:choose>
			<c:when test="${ (promo.rewardType eq 1 or promo.rewardType eq 2 or promo.rewardType eq 6) and promo.region == 'CN'}">
			
				<h3>恭喜!您的奖励为等值${promo.reward gt 0 ? promo.reward : '0' } 元的${rewardName }</h3>
				
				<c:choose>
					<c:when test="${ state eq 'SubsidyRetrievable' }">
						<c:if test="${ promo.rewardType eq 1}">
							<div class="note">
								<p>再次感謝您參與了我們的活動。我們將通知協力廠商服務商“澳捷實業有限公司”發放獎勵。請予10個工作日以後及時領取，獎勵發放地址和時間如下：</p>
								<ol>
									<li>深圳深圳市福田區深南中路3018號世紀匯廣場23/F</li>
									<li>上海上海市長寧區仙霞路317號遠東國際廣場B座1509</li>
									<li>北京北京市海澱區花園東路10號高德塔樓803室</li>
									<li>西安西安市高新一路正信塔樓B座203室</li>
								</ol>
								<p>工作時間為：AM9:00--PM6:00</p>
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
			<c:otherwise>
				<h3>恭喜您已完成本活動！接下來我們的客戶經理會聯系您關於獎勵的相關事宜，請注意接收相關的郵件通知。感謝您的參與!</h3>
			</c:otherwise>
		</c:choose>
	</div>

	<c:choose>
		<c:when
			test="${ (promo.rewardType eq 1 or promo.rewardType eq 2) and (not empty promo.rewardUrl) and promo.region == 'CN'}">
			<menu>
				<li><c:choose>
						<c:when test="${ state eq 'SubsidySubmitted' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>上傳獎勵申請協定</a>
							<br />
							<br />
							<a href="index">返回活動清單</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyRetrievable' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>領取獎勵</a>
							<br />
							<br />
							<a href="index">返回活動清單</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyResubmittable' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>重新申領獎勵</a>
							<br />
							<br />
							<a href="index">返回活動清單</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyUploaded' }">
							<a href="index" class="btn">返回活動清單</a>
						</c:when>
						<c:when test="${ state eq 'SubsidyWaiting' or state eq 'SubsidyAccessed' }">
							<a href="${promo.rewardUrl}" class="btn" ${ isAdmin ? 'disabled' : '' }>填寫獎勵申請協定</a>
							<br />
							<br />
							<a href="index">返回活動清單</a>
						</c:when>
					</c:choose></li>
			</menu>
		</c:when>
		<c:otherwise>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</c:otherwise>
	</c:choose>
</div>