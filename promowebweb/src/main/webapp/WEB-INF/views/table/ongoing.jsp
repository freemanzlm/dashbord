<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dataTable-container">
	<table id="ongoing-promo-table" class="dataTable">
		<thead>
			<tr>
				<th class="name">${promo.name}</th>
				<th class="type">活动类型</th>
				<th class="promoDlDt">报名截止日期</th>
				<th class="promoDt">活动时间</th>
				<!-- <th class="reward">奖励金额</th> -->
				<th class="state">活动状态</th>
			</tr>
		</thead>
	</table>
</div>