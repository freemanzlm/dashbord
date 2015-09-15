<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />
<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardType" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardType" value="京東卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardType" value="萬邑通" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardType" value="ebay萬裏通積分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 5 }">
		<c:set var="rewardType" value="郵票" />
	</c:when>
</c:choose>

<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals預置</title>
	<meta name="description" content="Deals預置 ">
	<res:cssSlot id="head" />
	<res:cssSlot id="head-css" />
	
	<script type="text/javascript">
		var BizReport = BizReport || {};
	</script>
	<res:jsSlot id="head" />	
	<res:jsSlot id="head-js" />
	
	<%--module "ebay.page" add Resets and Global css --%>
	<r:includeModule name="ebay.UIComponentsResource.page" cssSlot="head" />
	<res:useCss value="${res.css.local.css['jquery.dataTables.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css['dataTables.override.css']}" target="head-css"/>
	<res:useCss value="${res.css.local.css.reset_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.button_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.module_css}" target="head-css" />
	<res:useCss value="${res.css.local.css.dialog_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.layout_css}" target="head-css"/>
	<res:useCss value="${res.css.local.css.app_css}" target="head-css"/>
	
	<res:useJs value="${res.js.local.js['util.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js['locale_zh_HK.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['AlertDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['preset_state.js']}" target="page-js"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				
				<h2>Deals預置 ${promo.name}</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可報名</span></div>
						<div class="step done"><span>已提交報名</span></div>
						<c:choose>
							<c:when test="${ rewarding }">
								<c:choose>
									<c:when test="${ state eq 'Started' }">
										<div class="step current-step"><span>活動進行中</span></div>
										<div class="step"><span>獎勵確認中</span></div>
										<div class="step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:when>
									<c:when test="${ state eq 'SubsidyCounting' }">
										<div class="step done"><span>活動進行中</span></div>
										<div class="step current-step"><span>獎勵確認中</span></div>
										<div class="step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:when>
									<c:when test="${ state eq 'SubsidyRetrieved' }">
										<div class="step done"><span>活動進行中</span></div>
										<div class="step done"><span>獎勵確認中</span></div>
										<div class="step done"><span>申領獎勵</span></div>
										<div class="step current-step last"><span>活動完成</span></div>
									</c:when>
									<c:otherwise>
										<div class="step done"><span>活動進行中</span></div>
										<div class="step done"><span>獎勵確認中</span></div>
										<div class="step current-step"><span>申領獎勵</span></div>
										<div class="step last"><span>活動完成</span></div>
									</c:otherwise>
								</c:choose>
							</c:when>
							<c:otherwise>
								<div class="step ${ rewarding ? 'current-step' : '' } last"><span>活動進行中</span></div>
							</c:otherwise>
						</c:choose>
					</div>
				</div>  <!-- steps end -->
				
				<div class="active-status-box success">					
					
					<c:choose>
						<c:when test="${state eq 'Started' }">
							<h3>恭喜您的報名已完成稽核！</h3>
							<p class="desc">
								活動時間為${ promoStart }到${ promoEnd }，<br />我們將在活動結束後儘快公佈統計結果，請耐心等待！
							</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活動清單</a>
								</li>
							</menu>
						</c:when>
						<c:when test="${state eq 'SubsidyCounting' }">
							<h3>恭喜您已完成活動！</h3>
							
							<p class="desc">獎勵結果統計中，請耐心等待！</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活動清單</a>
								</li>
							</menu>
						</c:when>
						<c:when test="${state eq 'SubsidyRetrieved' }">
							<h3>您已成功领取等值${promo.reward }元的${rewardType }</h3>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${ rewardType eq 1 or rewardType eq 4 }">
									<h3>恭喜，您的奖励为等值${promo.reward }元的${rewardType }</h3>
									<p class="desc">
										請在${ rewardDeadline }前點擊進入領獎流程完成申領。
									</p>
									<menu>
										<li>
											<c:choose>
												<c:when test="${ state eq 'SubsidySubmitted' }">
													<a href="../index" class="btn">上傳獎勵申請協定</a>
												</c:when>
												<c:when test="${ state eq 'SubsidyRetrievable' }">
													<a href="#" class="btn">申領獎勵</a>
												</c:when>
												<c:when test="${ state eq 'SubsidyResubmittable' }">
													<a href="#" class="btn">重新申領獎勵</a>
												</c:when>
												<c:otherwise>
													<a href="../index" class="btn">填寫獎勵申請協定</a>
												</c:otherwise>
											</c:choose>
										</li>
									</menu>
								</c:when>
								<c:otherwise>
									<h3>恭喜您已完成本活動！</h3>
									<p class="desc">
										接下來我們的客戶經理會聯系您關於獎勵的相關事宜，請注意接收相關的郵件通知。感謝您的參與!
									</p>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
									
				</div> <!-- active status box end -->
				
				<%@ include file="activity.jsp" %>
				
				<div class="mt20">
					<jsp:include page="../table/dealsListing.jsp"></jsp:include>
				</div>
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/alert.jsp" %>

<script type="text/javascript">
	var pageData = {
		promoId: '${promo.promoId}'
	};
</script>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
</body>
</html>
