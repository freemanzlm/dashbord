<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>
<div class="post-pgc mt10 mb10 border-thin">
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
					您当前可申请<b class="color-orange"> <f:formatNumber value="${pgcSeller.remainingQuota}" type="number" maxFractionDigits="0"/> </b>
					个高额度企业帐户<br/>每个帐户可能获得最高
					<b class="color-orange"> <f:formatNumber value="${pgcSeller.limitQty}" type="number" maxFractionDigits="0"/> </b>
					刊登数量额度
				</p>
			</c:when>
			<c:when test="${hasIssue463 eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
				<p class=" mb20">
					当前帐户已入驻为企业帐户<br/>
					您现在可申请<b class="color-orange"> <f:formatNumber value="${pgcSeller.remainingQuota}" type="number" maxFractionDigits="0"/> </b>
					个高额度子帐户<br/>每个子帐户可能获得最高
					<b class="color-orange"> <f:formatNumber value="${pgcSeller.limitQty}" type="number" maxFractionDigits="0"/> </b>
					刊登数量额度
				</p>
			</c:when>
			<c:otherwise>
				<p class=" mb20" >
 					入驻申请成为企业帐户<br/>可能获得最高<b class="color-orange"> <f:formatNumber value="3000" type="number" maxFractionDigits="0"/> </b>刊登数量额度
				</p>
			</c:otherwise>
		</c:choose>
		</div>
		
		<div class="leftfloatdiv">
		<c:choose>
			<c:when test="${hasIssue463 eq true and pgcSeller.limitEligibility eq 'Eligible' and pgcSeller.remainingQuota>0}">
				<p class="text-center"><a href="http://pgc.ebay.com.hk/dashboard_entry/${secretParams}" class="btn btn-green btn-big btn-wider" style="background-color:#599a21;">登录子帐户申请企业入驻</a></p>
			</c:when>
			<c:otherwise>
				<p class="text-center"><a href="http://pgc.ebay.com.hk/dashboard_entry/${secretParams}" class="btn btn-green btn-big btn-wider" style="background-color:#599a21;">登录帐户申请企业入驻</a></p>
			</c:otherwise>
		</c:choose>
		</div>
	</div>
</div>
