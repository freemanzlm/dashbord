<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="isDisplayDialog" value="${((!isInConvWhitelist && isCanSubscribeConv) || (!isInDDSWhitelist && isCanSubscribeDDS)) && !isSubscribeDialogClosed}" /> <!-- //((!isInConvWhitelist && IsCanSubscribeConv) || (!accessDDS && isCanSubscribeDDS)) && !isSubscribeDialogClosed -->
<c:set var="isDisplayNewIcon" value="${(!isInConvWhitelist && isCanSubscribeConv) || (!isInDDSWhitelist && isCanSubscribeDDS)}" /> 

<div class="navigator-top" role="navigation">
	<div class="navigator-bar clr">
		<div class="navigator-title">卖家中心</div>
		<ul class="navigation-list">
			<c:if test="${accessBiz == true}">
				<li class=""><a href="${bizurl}">业务分析报告</a>
					<small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=246" target="_blank"></a></small>
					<c:if test="${isDisplayNewIcon eq true}"><span class="icon icon-new"></span></c:if>
				</li>
			</c:if>
			<li class="no-sub-menu"><a href="${sdurl}">买家体验报告</a><small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=247" target="_blank"></a></small></li>
			<li class="no-sub-menu active">
				<a id="promotion" href="/promotion/index" target="_self">活动促销</a>
				<small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank"></a></small>
				<a><c:if test="${ promoUpdatedNum gt 0 }"><small>${promoUpdatedNum}</small></c:if></a>
			</li>
		</ul>
	</div>	
	<div style="display:none;">
		${promoUpdatedDetail}
	</div>
</div>

<c:if test="${isDisplayDialog eq true }">
<%@ include file="brsubscribe/subscribeDialog.jsp"%>
</c:if>
