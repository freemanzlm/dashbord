$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var alertDialog = BizReport.alertDialog;
	var termsDialog = BizReport.termsDialog;
	var locale = BizReport.locale;
	
	var listingTable, acceptCheckbox, uploadForm, fileInput, uploadBtn;
	
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
	listingTable.update();	
	
	acceptCheckbox = $("#accept").change(function(){
		checkUploadBtnStatus();
	});
	
	acceptCheckbox.parent().popup({"trigger": "hover", html: "阅读完法律协议之后，方可勾选，勾选之前请确认已选择需要上传的文件。"});
	
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
	
	console.log("hello");
});