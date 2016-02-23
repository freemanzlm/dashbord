<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="listings-upload">
	<h3>批量上传预审刊登</h3>
	
	<div class="body mt20" style="width: 545px;">
		<p>您可以通过下载<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}&promoSubType=${promo.promoSubType}" target="_self">批量提交模板</a>按格式填写并上传您的刊登参与本活动。</p>
		
		<p id="upload-error-msg" class="error-msg hide"><span class="icon error"></span><b ></b></p>
		
		<form id="upload-form" action="/promotion/deals/uploadDealsListings" class="mt20" method="post" target="uploadIframe" enctype="multipart/form-data">
			选择上传您的刊登列表 
			<span class="file-input"><input type="text" style="height: 22px;" placeholder="选择文件" /> <input type="file" name="dealsListings" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <button class="btn" style="margin-left: 3px;" type="button">选择</button></span>
			<input type="hidden" name="promoId" value="${promo.promoId}"/>
			<input type="hidden" name="promoSubType" value="${promo.promoSubType}"/>
		</form>
		<iframe name="uploadIframe" src="about:blank" frameborder="0" style="display: none;"></iframe>
		
		<div class="clr" style="margin-top: 30px; ">
			<span style="float: left; font-weight: bold;">注：</span>
			<ul>
				<li>请勿修改下载模板的文件格式。</li>
				<li>请勿修改、增减模板中的原有信息,包括每个单元格的属性.</li>
				<li>报名的信息请填写完整，不可留空。除非标记为选填项，或参照模板标题说明。</li>
				<li>不报名的SKU请整行留空，不填写任何待填写信息。</li>
				<li><a href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank">详细请查阅使用指南</a></li>
			</ul>
		</div>
		
	</div>	
</div>