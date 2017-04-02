<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="post-pgc-tc mt10 mb10 border-thin">
	<div class="viewdiv">
		<!-- <div class="leftfloatdiv" >
			<p class="subtitle2">企业入驻通道</p>
			<p class="subtitle">--为您的新帐户申请更高的初始额度</p>
		</div> -->
		
		<%-- <c:if test="${ pgcSeller.pgcEligibility eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
			<div class="qutoanumberdiv">
				<p class=" mb20">
					您可以注册<b class="color-orange"> <f:formatNumber value="${pgcSeller.remainingQuota}" type="number" maxFractionDigits="0"/> </b>
					个高额度新帐户，<br/>每个帐户将可能获得
					<b class="color-orange"> <f:formatNumber value="${pgcSeller.limitQty}" type="number" maxFractionDigits="0"/> </b>
					的初始刊登数量额度。
				</p>
			</div>
		</c:if> --%>
		
		<div class="qutoanumberdiv">
		<c:choose>
			<c:when test="${!hasIssue463 eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
				<p class=" mb20">
					您可以註冊<b class="color-orange"> <f:formatNumber value="${pgcSeller.remainingQuota}" type="number" maxFractionDigits="0"/> </b>
					個高額度新帳戶<br/>每個帳戶將可能獲得
					<b class="color-orange"> <f:formatNumber value="${pgcSeller.limitQty}" type="number" maxFractionDigits="0"/> </b>
					的初始刊登數量額度
				</p>
			</c:when>
			<c:when test="${hasIssue463 eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
				<p class=" mb20">
					当前帳戶已入駐為企業帳戶<br/>
					您現在可申請<b class="color-orange"> <f:formatNumber value="${pgcSeller.remainingQuota}" type="number" maxFractionDigits="0"/> </b>
					個高額度子帳戶<br/>每個子帳戶可能獲得最高
					<b class="color-orange"> <f:formatNumber value="${pgcSeller.limitQty}" type="number" maxFractionDigits="0"/> </b>
					刊登數量額度
				</p>
			</c:when>
			<c:otherwise>
				<p class=" mb20">
					有客戶經理的賣家可能獲得最高<b class="color-orange"> <f:formatNumber value="3000" type="number" maxFractionDigits="0"/> </b>刊登數量額度
				</p>
			</c:otherwise>
		</c:choose>
		</div>
		
		<div class="leftfloatdiv">
			<c:choose>
			<c:when test="${hasIssue463 eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
				<p class="text-center"><a href="http://pgc.ebay.com.hk/dashboard_entry/${secretParams}" class="btn btn-green btn-big btn-wider">登入子帳戶申請企業入駐</a></p>
			</c:when>
			<c:otherwise>
				<p class="text-center"><a href="http://pgc.ebay.com.hk/dashboard_entry/${secretParams}" class="btn btn-green btn-big btn-wider">登入帳戶申請企業入駐</a></p>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>