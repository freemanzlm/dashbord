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
					<c:forEach items="${ fieldsDefintions }" var="field">
						<c:if test="${field.key ne 'Listing_Local_Currency_base__c'}">
							<th class="${fn:toLowerCase(field.rawType)} dt-nowrap ${field.key}" ${field.required?'required':''}>${field.title}</th>
							<c:set var="columns" value='${columns},{"data":"${field.key}"}' ></c:set>
						</c:if>
					</c:forEach>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		
		<c:set var="columns" value='[${ fn:substringAfter(columns, ",")}]' />
		
		<%-- <c:out value="${columns}"></c:out> --%>
	</c:if>
</div>