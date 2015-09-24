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
			window.scrollTo(document.documentElement.scrollLeft, $('.error-msg').offset().top);
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
				// check the response
				if (uploadIFrame.contents().length != 0 && uploadIFrame.contents().find("body").html().length > 0) {
					var response = uploadIFrame.contents().find("body").text();
					var responseData = $.parseJSON(response);
					// verification returns no error 
					if (responseData && responseData.status) {
						window.location.replace("/promotion/deals/reviewUploadedListings?promoId="+pageData.promoId);
					}
					// handle error
					else {
						// show error infor
						if (responseData.message.length > 0) {
							$("#upload-error-msg").removeClass("hide");
							$("#upload-error-msg").find("em").text(responseData.message);
						}
						// redirect to error page
						else {
							window.location.replace("promotion/error");
						}
					}
				} else {
					// redirect to error page
					window.location.replace("promotion/error");
				}
			});
			
			uploadForm.submit();
		}
	});	
	
//	activityDetail.html(activityDetail.html().replace(/&lt;/g, "<").replace(/&gt;/g, ">"));
	
});