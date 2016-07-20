<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="previewColumns" value='' ></c:set>

<div id="listing-preview-dialog" class="dialog">
	<a class="close"></a>
	<div class="dialog-header">
		<h2>已选择的刊登预览</h2>
	</div>
	<div class="dialog-pane">
		<div class="dialog-body">
			<div class="mt20">
				<div class="dataTable-container">
					<table id="listing-preview-table" class="dataTable">
						<thead>
							<tr>
								<c:forEach items="${ fieldsDefintions }" var="field">
									<th class="${fn:toLowerCase(field.rawType) } ${field.key} dt-nowrap">${field.title}</th>
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
			<button type="button" class="btn btn-s btn-prim ok">提交正式报名</button>
		</div>
	</div>
</div>