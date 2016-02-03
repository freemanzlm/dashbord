<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="listings-upload">
	<h3>批量上傳預審刊登</h3>
	
	<div class="body mt20" style="width: 545px;">
		<p>您可以通過下載<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}&promoSubType=${promo.promoSubType}" target="_self">批量提交範本</a>按格式填寫並上傳您的刊登參與本活動。</p>
		
		<p id="upload-error-msg" class="error-msg hide"><span class="icon error"></span><b></b></p>
		
		<form id="upload-form" action="/promotion/deals/uploadDealsListings" class="mt20" method="post" target="uploadIframe" enctype="multipart/form-data">
			選擇上傳您的刊登清單
			<span class="file-input"><input type="text" style="height: 22px;" placeholder="選擇檔案" /> <input type="file" name="dealsListings" accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" /> <button class="btn" style="margin-left: 3px;" type="button">選擇</button></span>
			<input type="hidden" name="promoId" value="${promo.promoId}"/>
			<input type="hidden" name="promoSubType" value="${promo.promoSubType}"/> 
		</form>
		<iframe name="uploadIframe" src="about:blank" frameborder="0" style="display: none;"></iframe>
		
		<div class="clr" style="margin-top: 30px; ">
			<span style="float: left; font-weight: bold;">注：</span>
			<ul>
				<li>請勿修改下載範本的檔案格式。</li>
				<li>請勿修改、增减範本中的原有資訊,包括每個單元單元的屬性.</li>
				<li>報名的資訊請填寫完整，不可留空。除非標記為選填項，或參照範本標題說明。</li>
				<li>不報名的SKU請整行留空，不填寫任何待填寫資訊。<a href="http://community.ebay.cn/portal.php?mod=view&aid=250" target="_blank">詳細請查閱使用指南</a></li>
			</ul>
		</div>
		
	</div>	
</div>