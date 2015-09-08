$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var listingTable,  uploadForm, fileInput, uploadBtn;
	
	listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideCheckbox();
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table"
		}});
	listingTable.update({promoId:pageData.promoId});	
	
	uploadForm = $("#upload-form").submit(function(){
		var fileName = fileInput.val();
		if (!fileName || fileName.indexOf(".xls") < 0) {
			alertDialog.alert(locale.getText("promo.deals.onlyXls"));
			return false;
		}
		return true;
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