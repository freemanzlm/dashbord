<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="res" uri="http://www.ebay.com/webres"%>
<%@ taglib prefix="rui" uri="http://ebay.com/uicomponents" %>
<%@ taglib prefix="r" uri="http://ebay.com/raptor"%>
<%@ taglib prefix="ghs" uri="http://www.ebay.com/raptor/globalheader" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="categoryId" value="6000" />
<c:set var="rewarding" value="${ !(promo.rewardType eq 0 or promo.rewardType eq -1)}" />
<c:set var="state" value="${ promo.state }" />
<fmt:formatDate value="${promo.rewardClmDt}" var="rewardDeadline" pattern="yyyy-MM-dd" type="date" />

<c:choose>
	<c:when test="${ promo.rewardType eq 1 }">
		<c:set var="rewardType" value="加油卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 2 }">
		<c:set var="rewardType" value="京东卡" />
	</c:when>
	<c:when test="${ promo.rewardType eq 3 }">
		<c:set var="rewardType" value="万邑通" />
	</c:when>
	<c:when test="${ promo.rewardType eq 4 }">
		<c:set var="rewardType" value="ebay万里通积分" />
	</c:when>
	<c:when test="${ promo.rewardType eq 5 }">
		<c:set var="rewardType" value="邮票" />
	</c:when>
</c:choose>

<r:includeJquery jsSlot="body" />
<r:client />

<!DOCTYPE html>
<html>
<head>
	<title>其他活动</title>
	<meta name="description" content="其它活动">
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
				<h2>其它活动 ${promo.name}</h2>
				
				<c:choose>
					<c:when test="${ rewarding }">
						<div class="steps-wrapper">
							<div class="steps clr">
								<c:choose>
									<c:when test="${ state eq 'Created' or state eq 'Unknow' }">
										<div class="step"><span>活动进行中</span></div>
										<div class="step"><span>奖励确认中</span></div>
										<div class="step"><span>申领奖励</span></div>
										<div class="step last"><span>活动完成</span></div>
									</c:when>
									<c:when test="${ state eq 'Started' }">
										<div class="step current-step"><span>活动进行中</span></div>
										<div class="step"><span>奖励确认中</span></div>
										<div class="step"><span>申领奖励</span></div>
										<div class="step last"><span>活动完成</span></div>
									</c:when>
									<c:when test="${ state eq 'SubsidyCounting' }">
										<div class="step done"><span>活动进行中</span></div>
										<div class="step current-step"><span>奖励确认中</span></div>
										<div class="step"><span>申领奖励</span></div>
										<div class="step last"><span>活动完成</span></div>
									</c:when>
									<c:when test="${ state eq 'SubsidyRetrieved' }">
										<div class="step done"><span>活动进行中</span></div>
										<div class="step done"><span>奖励确认中</span></div>
										<div class="step done"><span>申领奖励</span></div>
										<div class="step current-step last"><span>活动完成</span></div>
									</c:when>
									<c:otherwise>
										<div class="step done"><span>活动进行中</span></div>
										<div class="step done"><span>奖励确认中</span></div>
										<div class="step current-step"><span>申领奖励</span></div>
										<div class="step last"><span>活动完成</span></div>
									</c:otherwise>
								</c:choose>
							</div>
						</div>  <!-- steps end -->
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${ state eq 'Created' or state eq 'Unknow' }">
								<div class="steps-wrapper">
									<div class="steps clr">
										<div class="step last"><span>活动进行中</span></div>
									</div>
								</div>
							</c:when>
							<c:when test="${ state eq 'Started' }">
								<div class="steps-wrapper">
									<div class="steps clr">
										<div class="step current-step last"><span>活动进行中</span></div>
									</div>
								</div>
							</c:when>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
				<c:choose>
					<c:when test="${state eq 'Created' or state eq 'Unknow' }">
						<div class="active-status-box">
							<h3>活动还没开始，请耐心等待活动开始！</h3>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>
						</div>
					</c:when>
					<c:when test="${state eq 'Started' }">
						<div class="active-status-box success">
							<h3>活动正在进行中！</h3>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>
						</div>
					</c:when>
					<c:when test="${state eq 'SubsidyCounting' }">
						<div class="active-status-box success">
							<h3>恭喜您已完成活动！</h3>
							<p class="desc">
								奖励结果统计中，请耐心等待！
							</p>
							<menu>
								<li>
									<a href="../index" class="btn">返回活动列表</a>
								</li>
							</menu>
						</div>
					</c:when>
					<c:otherwise>
						<div class="active-status-box success">
							<c:choose>
								<c:when test="${ rewardType eq 1 or rewardType eq 4 }">
									<h3>恭喜，您的奖励为等值${promo.reward }元的${rewardType }</h3>
								</c:when>
								<c:otherwise>
									<h3>恭喜您已完成本活动！接下来我们的客户经理会联系您关于奖励的相关事宜，请注意接收相关的邮件通知。感谢您的参与!</h3>
								</c:otherwise>
							</c:choose>
							
							<c:if test="${ not empty rewardDeadline }">
								<p class="desc">请在${ rewardDeadline }前点击进入领奖流程完成申领。</p>
							</c:if>
							
							<c:if test="${ rewardType eq 1 or rewardType eq 4 }">
								<menu>
									<li>
										<c:choose>
											<c:when test="${ state eq 'SubsidySubmitted' }">
												<a href="#" class="btn">上传奖励申请协议</a>
											</c:when>
											<c:when test="${ state eq 'SubsidyRetrievable' }">
												<a href="#" class="btn">申领奖励</a>
											</c:when>
											<c:when test="${ state eq 'SubsidyResubmittable' }">
												<a href="#" class="btn">重新申领奖励</a>
											</c:when>
											<c:otherwise>
												<a href="#" class="btn">填写奖励申请协议</a>
											</c:otherwise>
										</c:choose>
									</li>
								</menu>
							</c:if>
						</div>
					</c:otherwise>
				</c:choose>

				<%@ include file="activity.jsp" %>
				
				<c:if test="${ not rewarding }">
					<div class="mt20" style="text-align: center;">
						<a href="../index" class="btn">返回活动列表</a>
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
