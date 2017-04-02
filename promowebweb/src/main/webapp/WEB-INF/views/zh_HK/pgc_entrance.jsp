<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="post-pgc-tc mt10 mb10 border-thin">
	<div class="viewdiv">
		<div class="qutoanumberdiv">
		<c:choose>
			<c:when test="${!hasIssue463 eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
				<p class=" mb20">
					您當前可申請<b class="color-orange"> <f:formatNumber value="${pgcSeller.remainingQuota}" type="number" maxFractionDigits="0"/> </b>
					個高額度企業帳戶<br/>每個帳戶可能獲得最高
					<b class="color-orange"> <f:formatNumber value="${pgcSeller.limitQty}" type="number" maxFractionDigits="0"/> </b>
					刊登數量額度
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
					入駐申請成為企業帳戶<br/>可能獲得最高<b class="color-orange"> <f:formatNumber value="3000" type="number" maxFractionDigits="0"/> </b>
					刊登數量額度
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