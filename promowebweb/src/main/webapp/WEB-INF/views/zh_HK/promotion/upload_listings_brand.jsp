<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="listings-upload">
	<h3>提交認證</h3>
	
	<div class="body mt20" style="width: 545px;">
		<p class="mt10 font-bold">您可以通過下載<a class="template" href="/promotion/listings/downloadTemplate?promoId=${promo.promoId}" target="_self">品牌認證提交範本</a>按格式填寫並上傳您的刊登參與本活動。</p>
		
		<p class="mt10 font-bold">請注意：您新上傳的資訊將全量替換之前的版本，系統以您最後上傳的版本為准。</p>
		
		<div class="clr" style="margin-top: 30px; ">
			<span style="float: left; font-weight: bold;">註：</span>
			<ul>
				<li>通過"確認報名"列標記參加活動的刊登或信息。標記為N的數據行不會被提交。</li>
				<li>請勿修改下載模板的文件格式。</li>
				<li>請勿修改、增減模板中的原有信息,包括每個單元格的屬性.</li>
				<li>報名的信息請填寫完整，不可留空。除非標記為選填項，或參照模板標題說明。</li>
				<li><a href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank">詳細請查閲使用指南</a></li>
			</ul>
		</div>
		
		<div id="request-error" class="errors-summary mt10 hide">
			<p class="mb3"></p>
		</div>
		
		<div id="excel-errors" class="errors-summary mt10 hide">
			<p class="mb3">请注意，您提交的文件存在填写错误，请检查<span class="color-red">第{row}行</span>的信息。建议您再次提交前检查有没有类似的填写错误，避免再次提交失败。详细错误信息如下：</p>
			<ul>
				
			</ul>
		</div>
		
		<form id="upload-form" action="/promotion/listings/uploadListings" class="mt20" method="post" target="uploadIframe" enctype="multipart/form-data">
			<span class="font-bold font075">選擇上傳您的品牌認證清單：</span> 
			<span class="file-input"><input type="text" style="height: 22px;" placeholder="選擇文件" /> <input type="file" name="uploadFile" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <button class="btn" style="margin-left: 3px;" type="button">選擇</button></span>
			<input type="hidden" name="promoId" value="${promo.promoId}"/>
		</form>
		<iframe name="uploadIframe" src="about:blank" style="display: none;"></iframe>
	</div>	
</div>