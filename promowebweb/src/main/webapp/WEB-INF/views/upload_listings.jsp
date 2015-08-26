<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>

<div class="listings-upload">
	<h3>批量上传我要提交的刊登</h3>
	<p class="mt10">您可以通过下载<a class="template" href="javascript:void(0)" target="_blank">批量提交模板</a>按格式填写并上传您的刊登参与本活动。</p>
	<form id="upload-form" action="upload" class="mt30" method="post">
		选择上传您的刊登列表 
		<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" accept="*.xsl" /> <button class="btn" style="margin-left: 3px;">选择</button></span>
	</form>
</div>