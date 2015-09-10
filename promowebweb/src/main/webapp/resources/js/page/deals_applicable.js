$(function(){
	
	var SKUListTable = BizReport.SKUListTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var skuList, uploadForm, fileInput, uploadBtn, uploadIFrame;
	
	uploadIFrame = $("iframe[name=uploadIframe]");
	
	skuList = new SKUListTable();
	skuList.subscribe({
		initialized: function() {
			// if file upload fail, show the error message to user.
			window.scrollTo(window.scrollX, $('.error-msg').offset().top);
		}
	});
	skuList.init({
		dataTableConfig: {
			tableId: "sku-list-table"
		}});
	skuList.update({promoId:pageData.promoId});
	
	uploadForm = $("#upload-form").submit(function(){
		var fileName = fileInput.val();
		if (!fileName || fileName.indexOf(".xls") < 0) {
			alertDialog.alert(locale.getText("promo.deals.onlyXls"));
			return false;
		}
		return !!$(this).find("input[type=file]").attr("value");
	});
	
	fileInput = uploadForm.find("input[type=file]").change(function(){
		checkUploadBtnStatus();
	});
	
	uploadBtn = document.getElementById("upload-btn");
	function checkUploadBtnStatus() {
		if (fileInput.val()) {
			uploadBtn.removeAttribute("disabled");
		} else {
			uploadBtn.setAttribute("disabled", "disabled");
		}
	}
	
	$(uploadBtn).click(function(){
		if (!this.hasAttribute("disabled")) {
			uploadIFrame.on("load", function(){
				if (uploadIFrame.contents().length == 0 || uploadIFrame.contents().find("body").html().indexOf("error") > 0) {
					alertDialog.alert(uploadIFrame.contents().find("body").html());
				} else {
					// refresh current page.
					location.reload();
				}
			});
			
			uploadForm.submit();
		}
	});	
	
	/*var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});*/
	
});