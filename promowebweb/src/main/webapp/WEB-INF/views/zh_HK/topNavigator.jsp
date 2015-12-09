<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="navigator-top" role="navigation">
	<div class="navigator-bar clr">
		<div class="navigator-title">賣家中心</div>
		<ul class="navigation-list">	
			<li class="no-sub-menu"><a href="${sdurl}">買家體驗報告</a><small><a class="icon help" href="http://community.ebay.cn/portal.php?mod=view&aid=205#sell01" target="_blank"></a></small></li>
			<c:if test="${accessBiz == true}">
				<li class=""><a href="${bizurl}">業務分析報告</a><small><a class="icon newTextIcon" href="#" target="_blank"></a></small></li>
			</c:if>
			<li class="no-sub-menu active"><a id="promotion" href="/promotion/index" target="_self">活動促銷<c:if test="${ promoUpdatedNum gt 0 }"><small>${promoUpdatedNum}</small></c:if></a></li>
		</ul>
	</div>
	<div style="display:none;">
		${promoUpdatedDetail}
	</div>
</div>