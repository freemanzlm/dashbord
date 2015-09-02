$(function(){
	
	var SKUListTable = BizReport.SKUListTable;
	var alertDialog = BizReport.alertDialog;
	var termsDialog = BizReport.termsDialog;
	var locale = BizReport.locale;
	
	var skuList, uploadForm, fileInput, acceptCheckbox, uploadBtn;
	
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
	skuList.update();
	
	uploadForm = $("#upload-form").submit(function(){
		var fileName = fileInput.val();
		if (!fileName || fileName.indexOf(".xls") < 0) {
			alertDialog.alert(locale.getText("promo.deals.onlyXls"));
			return false;
		}
		return !!$(this).find("input[type=file]").attr("value") && acceptCheckbox[0].checked;
	});
	
	fileInput = uploadForm.find("input[type=file]");
	
	uploadForm.find("input[type=file]").change(function(){
		checkUploadBtnStatus();
	});
	
	acceptCheckbox = $("#accept").change(function(){
		checkUploadBtnStatus();
	});
	
	acceptCheckbox.parent().popup({"trigger": "hover", html: locale.getText('promo.deals.upload')});
	
	uploadBtn = document.getElementById("upload-btn");
	function checkUploadBtnStatus() {
		if (acceptCheckbox[0].checked && fileInput.val()) {
			uploadBtn.removeAttribute("disabled");
		} else {
			uploadBtn.setAttribute("disabled", "disabled");
		}
	}
	
	$(uploadBtn).click(function(){
		if (!this.hasAttribute("disabled")) {
			uploadForm.submit();
		}
	});
	
	
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
});