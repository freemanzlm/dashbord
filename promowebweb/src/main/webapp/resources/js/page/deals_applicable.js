$(function(){
	
	var SKUListTable = BizReport.SKUListTable;
	var locale = BizReport.locale;
	
	var skuList = new SKUListTable();
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
	
	var uploadForm = $("#upload-form").submit(function(){
		return !!$(this).find("input[type=file]").attr("value") && acceptCheckbox[0].checked;
	});
	
	var fileInput = uploadForm.find("input[type=file]");
	
	uploadForm.find("input[type=file]").change(function(){
		checkUploadBtnStatus();
	});
	
	var acceptCheckbox = $("#accept").change(function(){
		checkUploadBtnStatus();
	});
	
	acceptCheckbox.parent().popup({"trigger": "hover", html: locale.getText('promo.terms.upload')});
	
	var uploadBtn = document.getElementById("upload-btn");
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
	
	var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
	
	console.log("hello");
});