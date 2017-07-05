<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:set var="isDisplayDialog" value="${((!isInConvWhitelist && isCanSubscribeConv) || (!isInDDSWhitelist && isCanSubscribeDDS)) && !isSubscribeDialogClosed}" /> <!-- //((!isInConvWhitelist && IsCanSubscribeConv) || (!accessDDS && isCanSubscribeDDS)) && !isSubscribeDialogClosed -->
<c:set var="isDisplayNewIcon" value="${(!isInConvWhitelist && isCanSubscribeConv) || (!isInDDSWhitelist && isCanSubscribeDDS)}" /> 

<div id="navigator" class="navigator-top" role="navigation">
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
				<a id="promotion" href="/promotion/index" target="_self">營銷活動</a>
				<small><a class="fa fa-question-circle" href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank"></a></small>
				<small class="counter" v-if="statistics.all > 0" v-cloak>{{statistics.all}}</small>
			</li>
		</ul>
		<div class="latestNotification" style="display:none;"><a href="javascript:void" style="cursor: pointer;" >最新通知</a></div>
	</div>
	
	<ul class="secondary-nav-list" role="menubar">
		<li role="menuitem" class="${fn:containsIgnoreCase(requestURL, '/promotion/index') ? 'active': ''}">
			<a href="/promotion/index">全部活動<small class="counter" v-if="statistics.all > 0" v-cloak>{{statistics.all}}</small></a>
		</li>
		<li role="menuitem" class="${fn:containsIgnoreCase(requestURL, '/promotion/brands') ? 'active': ''}">
			<a href="/promotion/brands">中國品牌智造計劃<small class="counter" v-if="statistics.brand > 0 || statistics.vetting > 0" v-cloak>{{statistics.brand + statistics.vetting}}</small></a>
		</li>
		<li role="menuitem" class="${fn:containsIgnoreCase(requestURL, '/promotion/deals') ? 'active': ''}">
			<a href="/promotion/deals">Deals活動<small class="counter" v-if="statistics.deals > 0" v-cloak>{{statistics.deals}}</small></a>
		</li>
	</ul>
</div>

<c:if test="${isDisplayDialog eq true }">
<%@ include file="brsubscribe/subscribeDialog.jsp"%>
</c:if>

<script>
$(function(){
	// Vue plugin.
	var topNav = new Vue({
		el: "#navigator",
		data: {
			statistics: {
				all: 0,
				promotion: 0,
				brand: 0,
				vetting: 0,
				deals: 0
			}
		},
		methods: {
		}
	});
	
	$.ajax("/promotion/promotion/promoStatistics", {
		type : 'GET',
		dataType : 'json',
		data: {timestamp:Date.now()},
		headers: {'Cache-Control': 'no-cache', 'Pragma': 'no-cache'},
		context : this,
		success : function(data) {
			if (data && data.status === true && data.data) {
				topNav.statistics.promotion = data.data.promotion || 0;
				topNav.statistics.brand = data.data.brand || 0;
				topNav.statistics.vetting = data.data.vetting || 0;
				topNav.statistics.deals = data.data.deals || 0;
				topNav.statistics.all = data.data.all || (topNav.statistics.promotion + topNav.statistics.brand + topNav.statistics.vetting + topNav.statistics.deals);  
			}
		}
	});
	
	window.topNav = topNav;
});
</script>

<!-- notification -->
<%@ include file="notification/notifications.jsp"%>