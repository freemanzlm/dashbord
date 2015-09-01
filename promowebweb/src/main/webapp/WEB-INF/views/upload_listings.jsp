<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:set var="errorMsg" value="[34324324]的信息填写不完整，请修改后重新上传。"></c:set>

<div class="listings-upload">
	<h3>批量上传我要提交的刊登</h3>
	
	<div class="memo mt20">
		<p>您可以通过下载<a class="template" href="javascript:void(0)" target="_blank">批量提交模板</a>按格式填写并上传您的刊登参与本活动。</p>
		<div class="clr" style="margin-top: 30px; ">
			<span style="float: left; font-weight: bold;">注：</span>
			<ul>
				<li>请勿修改下载模板的文件格式。</li>
				<li>请勿修改、增减模板中的原有信息</li>
				<li>请填写完整报名的刊登信息，包括：您的刊登编号，当前刊登单价，活动单价，刊登库存量。</li>
				<li>不报名的SKU请留空填写内容，不填写任何待天蝎信息，模板自带信息请勿修改。</li>
			</ul>
		</div>
		
		<c:if test="${ not empty errorMsg }">
			<p class="error-msg"><span class="icon error"></span>${ errorMsg }</p>
		</c:if>
	</div>
	<form id="upload-form" action="upload" class="mt20" method="post">
		选择上传您的刊登列表 
		<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" accept="application/vnd.ms-excel" /> <button class="btn" style="margin-left: 3px;">选择</button></span>
		<input type="hidden" name="promoId" value="4324324"/>
	</form>
</div>