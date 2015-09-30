$(function(){
	
	var SKUListTable = BizReport.SKUListTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var skuList, uploadForm, fileInput, uploadBtn, uploadIFrame, acceptCheckbox;
	
	uploadIFrame = $("iframe[name=uploadIframe]");
	uploadBtn = document.getElementById("upload-btn");
	acceptCheckbox = document.getElementById("accept");
	
	var acceptPopup = $(acceptCheckbox).parent().each(function(){
		$(this).popup({"trigger": "mannual", html: this.title});
	});	
	
	skuList = new SKUListTable();
	skuList.init({
		dataTableConfig: {
			tableId: "sku-list-table"
		}});
	
	try {
		skuList.update({promoId: pageData && pageData.promoId});
	} catch(e) {
		skuList.initDataTable();
		console.log('sku list failed to get data');
	}
	
	uploadForm = $("#upload-form").submit(function(){
		if (!acceptCheckbox.checked) {
			acceptPopup.popup('show');
			return false;
		}
		
		var fileName = fileInput.val();
		if (!fileName || fileName.indexOf(".xls") < 0) {
			alertDialog.alert(locale.getText("promo.deals.onlyXls"));
			return false;
		}
		
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
		
		return !!$(this).find("input[type=file]").attr("value");
	});
	
	fileInput = uploadForm.find("input[type=file]");
	
	$(uploadBtn).click(function(event){
		event.preventDefault();
		
		uploadForm.submit();
	});	
	
	var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"ok": function() {
			acceptCheckbox.removeAttribute("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	
	
	uploadForm.get(0).reset();
//	activityDetail.html(activityDetail.html().replace(/&lt;/g, "<").replace(/&gt;/g, ">"));
});