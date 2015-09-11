<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>

<div class="dialog" id="terms-dialog">
	<a class="close"></a>
	<div class="dialog-header">
		<h2>法律協定</h2>
	</div>
	<div class="dialog-pane">
		<div class="dialog-body">
			<iframe src="${agreement}" frameborder="0" width="810" style="overflow: hidden;"></iframe>
		</div>

		<div class="btns clz">
			<button class="btn btn-s btn-prim ok" style="margin-right: 10px;">確認</button>
		</div>
	</div>
</div>