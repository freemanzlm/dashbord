<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="columns" value=""></c:set>
<c:set var="readonly" value="${ (promo.regType eq true and isRegEnd eq true) or promo.regType eq false }"></c:set>

<div id="listing-table-container" class="dataTable-container">
	<c:if test="${ not empty fieldsDefintions}">
		<table id="listing-table" class="dataTable">
			<thead>
				<tr>
					<th class="stickyFlag">
						<!-- hidden column for making sticky listings always display first -->
					</th>
					
					<c:if test="${(currentStep eq 'SELLER NOMINATION_NEED APPROVE' or currentStep eq 'SELLER FEEDBACK'  or currentStep eq 'PROMOTION IN PROGRESS') and isRegEnd ne true}">
						<th class="state">
							<c:choose>
								<c:when test="${promo.region eq 'CN' }">
									状态
								</c:when>
								<c:otherwise>
									狀態
								</c:otherwise>
							</c:choose>
						</th>
					</c:if>
					
					<c:forEach items="${ fieldsDefintions }" var="field">
						<c:if test="${field.key ne 'Listing_Local_Currency_base__c'}">
							<th class="${fn:toLowerCase(field.rawType)} dt-nowrap ${field.key}">${field.title}</th>
							<c:set var="columns" value='${columns},{"data":"${field.key}", "bRequired":${field.required}, "bReadonly":${readonly}, "bDisabled":${true}}' ></c:set>
						</c:if>
					</c:forEach>
					
					<th class="lockFlag">
						<c:choose>
							<c:when test="${promo.region eq 'CN' }">
								锁定状态
							</c:when>
							<c:otherwise>
								鎖定狀態
							</c:otherwise>
						</c:choose>
					</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
		
		<c:set var="columns" value='${ fn:substringAfter(columns, ",") }' />
		
		<c:choose>
			<c:when test="${(currentStep eq 'SELLER NOMINATION_NEED APPROVE' or currentStep eq 'SELLER FEEDBACK'  or currentStep eq 'PROMOTION IN PROGRESS') and isRegEnd ne true}">
				<c:set var="columns" value='[{"data":"stickyFlag"},{"data":"state"}, ${ columns },{"data":"lock"}]' />
			</c:when>
			<c:otherwise>
				<c:set var="columns" value='[{"data":"stickyFlag"},${ columns },{"data":"lock"}]' />
			</c:otherwise>
		</c:choose>
		
		<%-- <c:out value="${columns}"></c:out> --%>
	</c:if>
</div>
