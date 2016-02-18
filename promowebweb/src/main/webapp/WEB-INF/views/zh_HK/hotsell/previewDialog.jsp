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
								<th class="check"><input type="checkbox" class="check-all" /></th>
								<th class="name">刊登</th>
								<th class="target-price">目標單價</th>
								<th class="target-volume">目標銷量</th>
								<!-- <th class="target-sales">目標銷售額</th> -->
								<th class="compensate-per">補償單價</th>
								<th class="compensate">最大補償幅度</th>
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