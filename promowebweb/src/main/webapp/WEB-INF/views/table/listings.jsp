<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="columns" value=""></c:set>

<div class="dataTable-container">
	<c:if test="${ not empty fieldsDefintions}">
		<table id="listing-table" class="dataTable">
			<thead>
				<tr>
					<!-- <th class="check"><input type="checkbox" class="check-all" /></th> -->
					<c:forEach items="${ fieldsDefintions }" var="field">
						<th class="${fn:toLowerCase(field.rawType)}">${field.title}</th>
						<c:set var="columns" value='${columns},{"data":"${field.key}"}' ></c:set>
					</c:forEach>
					<!-- <th class="state">状态</th> -->
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		
		<c:set var="columns" value="[${ fn:substringAfter(columns, ',') }]" />
		<%-- <c:out value="${columns}"></c:out> --%>
	</c:if>
</div>