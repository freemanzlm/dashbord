<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
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
								<th class="itemId"><input type="checkbox" class="check-all" /></th>
								<th class="item-id">刊登编号</th>
								<th class="name">SKU名称</th>
								<th class="price">当前刊登单价</th>
								<th class="activity-price">活动单价</th>
								<th class="inventory">刊登库存量</th>
								<th class="state">状态</th>
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
			<button type="button" class="btn btn-s btn-prim ok">提交报名信息</button>
		</div>
	</div>
</div>