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
		<c:set var="rewardName" value="ebay萬裏通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardName" value="萬邑通" />
	</c:when>
	<c:when test="${ promo.rewardType eq 6 }">
		<c:set var="rewardName" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardName" value="郵票" />
	</c:when>
</c:choose>


<c:choose>
	<c:when test="${state eq 'Started' }">
		<div class="active-status-box success">
			<h3>活動進行中！</h3>
			<p class="desc">
				活動時間為${ promoStart }到${ promoEnd }，<br /> 我們將在活動結束後儘快公佈統計結果，請耐心等待！
			</p>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${state eq 'SubsidyCounting' }">
		<div class="active-status-box success">
			<h3>恭喜您已完成活動！</h3>
			<p class="desc">我們的獎勵結果正在統計中，請耐心等待！</p>
			<menu>
				<li><a href="index" class="btn">返回活動清單</a></li>
			</menu>
		</div>
	</c:when>
	<c:when test="${state eq 'SubsidyRetrieveFailed' }">
		<div class="active-status-box fail">
			<h3>領取失敗</h3>
			<c:if test="${ promo.rewardType eq 2 }">
				<p class="desc">領取：等值於${promo.reward }元的${rewardName }
					<br />（注：每一元人民幣的獎勵，將獲得500ebay萬裏通積分的充值資格）
					<br /><br />抱歉！對萬裏通的充值遇到問題。請通過郵件聯繫<a href="mailto:ebay-CC@ebay.com">ebay-CC@ebay.com</a>反映該問題。會有專門人員協助您解决。</p>
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
					<h3>恭喜您已完成本活動！接下來我們的客戶經理會聯系您關於獎勵的相關事宜，請注意接收相關的郵件通知。感謝您的參與!</h3>
				</c:otherwise>
			</c:choose>

			<c:if test="${ not empty rewardDeadline }">
				<p class="desc">請在${ rewardDeadline }前點擊進入領獎流程完成申領。</p>
			</c:if>

			<c:choose>
				<c:when
					test="${ (promo.rewardType eq 1 or promo.rewardType eq 2) and (not empty promo.rewardUrl) and promo.region == 'CN'}">
					<menu>
						<li><c:choose>
								<c:when test="${ state eq 'SubsidySubmitted' }">
									<a href="${promo.rewardUrl}" class="btn">上傳獎勵申請協定</a>
									<br />
									<br />
									<a href="index">返回活動清單</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyRetrievable' }">
									<a href="${promo.rewardUrl}" class="btn">領取獎勵</a>
									<br />
									<br />
									<a href="index">返回活動清單</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyResubmittable' }">
									<a href="${promo.rewardUrl}" class="btn">重新申領獎勵</a>
									<br />
									<br />
									<a href="index">返回活動清單</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyUploaded' }">
									<a href="index" class="btn">返回活動清單</a>
								</c:when>
								<c:when test="${ state eq 'SubsidyWaiting' or state eq 'SubsidyAccessed' }">
									<a href="${promo.rewardUrl}" class="btn">填寫獎勵申請協定</a>
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
	</c:otherwise>
</c:choose>

<!-- active status box end -->