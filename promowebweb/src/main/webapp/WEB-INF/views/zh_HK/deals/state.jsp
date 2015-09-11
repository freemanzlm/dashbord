<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="state" value="Started"></c:set>
<c:set var="rewarding" value="true" />
<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>Deals招募</title>
	<meta name="description" content="Deals招募 ">
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
	<res:useJs value="${res.js.local.js['locale_zh_CN.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['Widget.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['MaskManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.lib['posManager.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['Dialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.dialog['AlertDialog.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.dataTables.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['jquery.isloading.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.jquery['DataTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.table['DealsListingTable.js']}" target="page-js"></res:useJs>
	<res:useJs value="${res.js.local.js.page['deals_state.js']}" target="page-js"></res:useJs>
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>Deals ${promo.name}</h2>
				<div class="steps-wrapper">
					<div class="steps clr">
						<div class="step done"><span>可報名</span></div>
						<div class="step done"><span>已提交預審</span></div>
						<div class="step done"><span>報名預審中</span></div>
						<div class="step done"><span>確認報名刊登</span></div>
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
								活動時間為YYYY-MM-DD到YYYY-MM-DD，<br />我們將在活動結束後儘快公佈統計結果，請耐心等待！
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
							<h3>您已成功領取等值888元的ebay萬裏通積分</h3>
							<menu>
								<li>
									<a href="../index" class="btn">返回活動清單</a>
								</li>
							</menu>
						</c:when>
						<c:otherwise>
							<h3>您已成功領取等值888元的ebay萬裏通積分</h3>
							<p class="desc">
								請在2015年8月8日前點擊進入領獎流程完成申領。
							</p>
							<menu>
								<li>
									<c:choose>
										<c:when test="${ state eq 'Rewarding' }">
											<a href="../index" class="btn">填寫獎勵申請協定</a>
										</c:when>
										<c:otherwise>
											<a href="../index" class="btn">上傳獎勵申請協定</a>
										</c:otherwise>
									</c:choose>
								</li>
							</menu>
						</c:otherwise>
					</c:choose>
					
				</div> <!-- active status box end -->
				
				<%@ include file="activity.jsp" %>
				
				<div class="mt20 my-listing">
					<h3>我提交的刊登</h3>
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