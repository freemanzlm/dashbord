<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="dataTable-container">
	<table id="passed-brands-table" class="dataTable">
		<thead>
			<tr>
				<th class="itemName">品牌名称</th>
				<th class="lastAuditDt">上次审核时间</th>
				<th class="nextAuditDt">下次审核时间</th>				
				<th class="defectState">不良率状态</th>
				<th class="failAmount">不良率不达标次数</th>
			</tr>
		</thead>
	</table>
</div>