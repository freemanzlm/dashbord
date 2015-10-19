$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var listingTable,  uploadForm, fileInput, uploadBtn, uploadIFrame;
	
	uploadBtn = document.getElementById("upload-btn");
	acceptCheckbox = document.getElementById("accept");
	
	uploadIFrame = $("iframe[name=uploadIframe]");
	
	var acceptPopup = $(acceptCheckbox).parent().each(function(){
		$(this).popup({"trigger": "mannual", html: this.title});
	});
	
	listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideCheckbox();
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table",
			customTableConfig: {
                'sAjaxSource': '/promotion/deals/getSubmittedListings'
            }
		}});
	listingTable.update({promoId:pageData && pageData.promoId});
	
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
			if (uploadIFrame.contents().length != 0 && uploadIFrame.contents().find("body").html().length > 0) {
				var response = uploadIFrame.contents().find("body").text();
				var responseData = $.parseJSON(response);
				// verification returns no error 
				if (responseData.status) {
					window.location.replace("/promotion/deals/reviewUploadedListings?promoId="+pageData.promoId);
				}
				// handle error
				else {
					// show error infor
					if (responseData.message && responseData.message.length > 0) {
						$("#upload-error-msg").removeClass("hide");
						$("#upload-error-msg").find("em").text(responseData.message);
					} else if (responseData.data && responseData.data.length > 0) {
						alertDialog.alert(locale.getText("errorMsg.regDateExpired"));
						window.location.replace("/promotion/" + pageData.promoId);
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
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	
	
	uploadForm.length && uploadForm.get(0).reset();
});