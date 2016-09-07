<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="previewColumns" value='' ></c:set>

<div id="listing-preview-dialog" class="dialog">
	<a class="close"></a>
	<div class="dialog-header">
		<h2>已選擇的刊登預覽</h2>
	</div>
	<div class="dialog-pane">
		<div class="dialog-body">
			<div class="mt20">
				<div class="dataTable-container">
					<table id="listing-preview-table" class="dataTable">
						<thead>
							<tr>
								<c:forEach items="${ fieldsDefintions }" var="field">
									<th class="${fn:toLowerCase(field.rawType) } dt-nowrap ${field.key}">${field.title}</th>
									<c:set var="previewColumns" value='${previewColumns},{"data":"${field.key}"}' ></c:set>
								</c:forEach>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		
		<c:set var="previewColumns" value='[${ fn:substringAfter(previewColumns, ",") }]' />

		<div class="page-bottom-actions">
			<a class="cancel" href="javascript:void(0)">返回修改</a>
			<button type="button" class="btn btn-s btn-prim ok">
				<c:choose>
				<c:when test="${promo.currentStep eq 'Seller nomination_Need approve'}">
					提交報名
				</c:when>
				<c:otherwise>
					提交正式報名
				</c:otherwise>
				</c:choose>
			</button>
		</div>
	</div>
</div>