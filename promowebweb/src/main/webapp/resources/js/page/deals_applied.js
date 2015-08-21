$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var termsDialog = BizReport.termsDialog;
	
	var listingTable = new DealsListingTable();
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
	
	var acceptCheckbox = $("#accept").change(function(){
		checkUploadBtnStatus();
	});
	
	acceptCheckbox.parent().popup({"trigger": "hover", html: "阅读完法律协议之后，方可勾选，勾选之前请确认已选择需要上传的文件。"});
	
	var uploadForm = $("#upload-form").submit(function(){
		return !!$(this).find("input[type=file]").attr("value") && acceptCheckbox[0].checked;
	});
	
	var fileInput = uploadForm.find("input[type=file]");
	
	uploadForm.find("input[type=file]").change(function(){
		checkUploadBtnStatus();
	});
	
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