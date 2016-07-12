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
					<c:if test="${(currentStep eq 'Seller nomination_Need approve' or currentStep eq 'Seller Feedback' ) and  not regType  }">
						<th class="check"><input type="checkbox" class="check-all" /></th>
					</c:if>
					
					<c:forEach items="${ fieldsDefintions }" var="field">
						<th class="${fn:toLowerCase(field.rawType)} dt-nowrap">${field.title}</th>
						<c:set var="columns" value='${columns},{"data":"${field.key}"}' ></c:set>
					</c:forEach>
					
					<c:if test="${(currentStep eq 'Seller nomination_Need approve' or currentStep eq 'Seller Feedback' ) and  not regType  }">
						<th class="state">状态</th>
					</c:if>
				</tr>
			</thead>
			<tbody>
				
			</tbody>
		</table>
		
		<c:choose>
			<c:when test="${(currentStep eq 'Seller nomination_Need approve' or currentStep eq 'Seller Feedback' ) and  not regType  }">
				<c:set var="columns" value='[{"data:":"itemId"},${ fn:substringAfter(columns, ",")},{"data":"state"}]' />
			</c:when>
			<c:otherwise>
				<c:set var="columns" value="[${ fn:substringAfter(columns, ',') }]" />
			</c:otherwise>
		</c:choose>
		
		<%-- <c:out value="${columns}"></c:out> --%>
	</c:if>
</div>