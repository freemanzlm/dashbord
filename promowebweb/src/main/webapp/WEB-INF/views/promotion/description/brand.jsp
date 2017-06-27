<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="activity-detail">
	<div class="activity-time">
		<c:if test="${ not empty promoDlDt}">
			<strong style="margin-right: 90px;">报名截止时间（北京时间）：${ promoDlDt }</strong>
		</c:if>
	</div>
	<div class="table activity-brief">
		<c:if test="${ not empty promo.desc }">
			<div class="table-row">
				<div class="table-cell brief-title">活动描述：</div>
				<div class="table-cell pretty-text">
					<div>${promo.desc}</div>
				</div>
			</div>
		</c:if>
	</div>
</div>