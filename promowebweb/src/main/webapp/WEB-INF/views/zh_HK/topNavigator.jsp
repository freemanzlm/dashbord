<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="isDisplayDialog" value="${((!isInConvWhitelist && isCanSubscribeConv) || (!isInDDSWhitelist && isCanSubscribeDDS)) && !isSubscribeDialogClosed}" /> <!-- //((!isInConvWhitelist && IsCanSubscribeConv) || (!accessDDS && isCanSubscribeDDS)) && !isSubscribeDialogClosed -->
<c:set var="isDisplayNewIcon" value="${(!isInConvWhitelist && isCanSubscribeConv) || (!isInDDSWhitelist && isCanSubscribeDDS)}" /> 

<div class="navigator-top" role="navigation" v-cloak>
	<div class="navigator-bar clr">
		<div class="navigator-title">賣家中心</div>
		<ul class="navigation-list">	
			<c:if test="${accessBiz == true}">
				<li class=""><a href="${bizurl}">業務分析報告</a>
					<small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=246" target="_blank"></a></small>
					<c:if test="${isDisplayNewIcon eq true}"><span class="icon icon-new"></span></c:if>
				</li>
			</c:if>
			<li class="no-sub-menu"><a href="${sdurl}">買家體驗報告</a><small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=247" target="_blank"></a></small></li>
			<li class="active">
				<a id="promotion" href="/promotion/index" target="_self">活動促銷</a>
				<small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank"></a></small>
				<small class="counter" v:if="statistics.all > 0">{{statistics.all}}</small>
			</li>
		</ul>
		<div class="latestNotification" style="display:block;"><a href="javascript:void" style="cursor: pointer;" >最新通知</a></div>
	</div>
	
	<ul class="secondary-nav-list" role="menubar">
		<li role="menuitem" class="${fn:containsIgnoreCase(requestURL, '/promotion/index') ? 'active': ''}">
			<a href="/promotion/index">活動促銷<small class="counter" v:if="statistics.all > 0">{{statistics.all}}</small></a>
		</li>
		<li role="menuitem" class="${fn:containsIgnoreCase(requestURL, '/promotion/brands') ? 'active': ''}">
			<a href="/promotion/brands">品牌認證與推廣<small class="counter" v-if="statistics.brand > 0 || statistics.vetting > 0">{{statistics.brand + statistics.vetting}}</small></a>
		</li>
		<li role="menuitem" class="${fn:containsIgnoreCase(requestURL, '/promotion/deals') ? 'active': ''}">
			<a href="/promotion/deals">Deals活動<small class="counter" v-if="statistics.deals > 0">{{statistics.deals}}</small></a>
		</li>
	</ul>
	<div style="display:none;">
		${promoUpdatedDetail}
	</div>
</div>

<c:if test="${isDisplayDialog eq true }">
<%@ include file="brsubscribe/subscribeDialog.jsp"%>
</c:if>

<!-- notification -->
<%@ include file="notification/notificationDialog.jsp"%>