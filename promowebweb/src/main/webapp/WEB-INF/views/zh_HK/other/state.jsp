<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<c:set var="categoryId" value="6000" />
<c:set var="state" value="Started"></c:set>
<c:set var="rewarding" value="false" />
<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>其它活動</title>
	<meta name="description" content="其它活動">
	<meta name="author" content="eBay: Apps">
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
	<res:useJs value="${res.js.local.js.dialog['TermsDialog.js']}" target="page-js"></res:useJs>
	
</head>

<body>
<div class="container">
	<!--  Global Header -->
	<jsp:include page="../header.jsp"></jsp:include>
	<!-- end: Global Header -->
	
	<div id="page">
		<div id="page-pane">
			<div class="pane">
				<h2>其它活動 ${promo.name}</h2>
				
				<c:choose>
					<c:when test="${ rewarding }">
						<div class="steps-wrapper">
							<div class="steps clr">
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
							</div>
						</div>  <!-- steps end -->
					</c:when>
					<c:otherwise>
						<c:if test="${ state eq 'Started' }">
							<div class="steps-wrapper">
								<div class="steps clr">
									<div class="step current-step"><span>活動進行中</span></div>
								</div>
							</div>  <!-- steps end -->
						</c:if>
					</c:otherwise>
				</c:choose>
				
				<c:if test="${ rewarding }">
					<div class="active-status-box success">
						<c:choose>
							<c:when test="${state eq 'Started' }">
								<h3>活動正在進行中！</h3>
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
								<h3>恭喜，您的奖励为等值888元的ebay万里通积分</h3>
								<p class="desc">
									请在2015年8月8日前点击进入领奖流程完成申领。
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
					</div>			
				</c:if>

				<%@ include file="activity.jsp" %>
				
				<c:if test="${ not rewarding }">
					<div class="mt20" style="text-align: center;">
						<a href="../index" class="btn">返回活動清單</a>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<!-- Global Footer -->
		<jsp:include page="../footer.jsp"></jsp:include>
	<!-- End: Global Footer -->
</div>

<%@ include file="../dialog/alert.jsp" %>
<%@ include file="../dialog/terms.jsp" %>

<res:jsSlot id="body" />
<res:jsSlot id="page-js" />
<res:jsSlot id="exec-js" />
<script type="text/javascript">
	$(".terms-conditions").click(function(event){
		BizReport.termsDialog.show();
	});
</script>
</body>
</html>