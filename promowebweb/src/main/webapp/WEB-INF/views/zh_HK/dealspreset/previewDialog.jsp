<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
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
								<th class="itemId"><input type="checkbox" class="check-all" /></th>
								<th class="item-id">刊登編號</th>
								<th class="name">SKU名稱</th>
								<th class="price">當前刊登單價</th>
								<th class="activity-price">活動單價</th>
								<th class="inventory">刊登庫存量</th>
								<th class="state">狀態</th>
								<th class="currency"></th>
							</tr>
						</thead>
						<tbody>
							
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="page-bottom-actions">
			<a class="cancel" href="javascript:void(0)">返回修改</a>
			<button type="button" class="btn btn-s btn-prim ok">提交報名</button>
		</div>
	</div>
</div>