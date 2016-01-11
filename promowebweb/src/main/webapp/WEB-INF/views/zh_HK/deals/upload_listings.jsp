<%@ page trimDirectiveWhitespaces="true"
	contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="listings-upload">
	<h3>批量上傳預審刊登</h3>
	
	<div class="body mt20" style="width: 545px;">
		<p>您可以通過下載<a class="template" href="/promotion/deals/downloadSkuList?promoId=${promo.promoId}" target="_self">批量提交範本</a>按格式填寫並上傳您的刊登參與本活動。</p>
		
		<p id="upload-error-msg" class="error-msg hide"><span class="icon error"></span><em ></em></p>
		
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
				<li>請勿修改、增减範本中的原有資訊</li>
				<li>請填寫完整報名的刊登信息，包括：您的刊登編號（不可重複），當前刊登單價，活動單價，刊登庫存量。價格請按活動對應網站的貨幣計算。</li>
				<li>備貨完成時間格式為YYYY-MM-DD，如2015-08-08</li>
				<li>不報名的SKU請留空填寫內容，不填寫任何待填寫資訊，範本自帶資訊請勿修改。</li>
			</ul>
		</div>
		
	</div>	
</div>