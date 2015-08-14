$(function(){
	
	var SKUListTable = BizReport.SKUListTable;
	
	var skuList = new SKUListTable();
	skuList.init({
		dataTableConfig: {
			tableId: "sku-list-table"
		}});
	skuList.update();
	
	var uploadForm = $("form").submit(function(){
		return !!$(this).find("input[type=file]").attr("value") && acceptCheckbox[0].checked;
	});
	
	var fileInput = uploadForm.find("input[type=file]");
	
	uploadForm.find("input[type=file]").change(function(){
		checkUploadBtnStatus();
	});
	
	var acceptCheckbox = $("#accept").change(function(){
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
	
	console.log("hello");
});