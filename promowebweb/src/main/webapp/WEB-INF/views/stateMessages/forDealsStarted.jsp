<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:formatDate value="${promo.promoSdt}" var="promoStart" pattern="yyyy-MM-dd" type="date" />
<fmt:formatDate value="${promo.promoEdt}" var="promoEnd" pattern="yyyy-MM-dd" type="date" />

<div class="promo-state-message success">
	<div class="message-content">
		<h3>经站点通知，我们很高兴地通知您，您已经正式通过活动报名。</h3>
		<p class="desc">
			活动时间为${ promoStart } 到 ${ promoEnd }, <br /> 我们将在活动结束后尽快公布统计结果，请耐心等待！
		</p>
	</div>
	<menu>
		<li><a href="index" class="btn">返回活动列表</a></li>
	</menu>
</div>