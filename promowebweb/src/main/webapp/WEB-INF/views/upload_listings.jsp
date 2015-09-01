<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>

<div class="listings-upload">
	<h3>批量上传我要提交的刊登</h3>
	<p class="mt10">您可以通过下载<a class="template" href="javascript:void(0)" target="_blank">批量提交模板</a>按格式填写并上传您的刊登参与本活动。</p>
	<div class="memo">
		<p>注：</p>
		<ul>
			<li>请勿修改下载模板的文件格式。</li>
			<li>请勿修改、增减模板中的原有信息</li>
			<li>请填写完整报名的刊登信息，包括：您的刊登编号，当前刊登单价，活动单价，刊登库存量。</li>
			<li>不报名的SKU请留空填写内容，不填写任何待天蝎信息，模板自带信息请勿修改。</li>
		</ul>
	</div>
	<form id="upload-form" action="upload" class="mt30" method="post">
		选择上传您的刊登列表 
		<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" accept="application/vnd.ms-excel" /> <button class="btn" style="margin-left: 3px;">选择</button></span>
		<input type="hidden" name="promoId" value="4324324"/>
	</form>
</div>