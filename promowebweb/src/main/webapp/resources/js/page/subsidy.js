$(function(){
	
	var ListingTable = BizReport.ListingTable;
	var local = BizReport.local;

	var uploadForm, fileInput, uploadBtn, uploadIFrame, acceptCheckbox, form, formBtn, listingCountJ;
	
	var hasState = false, customTableConfig;
	
	//var successCount = 0;
	
	if (pageData && pageData.columns && pageData.columns.length > 1) {
		hasState = pageData.columns[pageData.columns.length - 1]['data'] == 'state';
		
		customTableConfig = {
			'columns': pageData.columns,
			'aaSorting': (hasState ? [[pageData.columns.length - 1, 'desc']] : null)
		};
	}
	
	uploadIFrame = $("iframe[name=uploadIframe]");
	uploadBtn = document.getElementById("upload-btn");
	acceptCheckbox = document.getElementById("accept");
	form = $("#listing-form");
	formBtn = document.getElementById("form-btn");
	listingCountJ = $(".my-listing h3 small span");
	
	var acceptPopup = $(acceptCheckbox).parent().each(function(){
		$(this).popup({"trigger": "mannual", html: this.title});
	});
	
	
	if (document.getElementById('upload-form')) {
		uploadForm = $("#upload-form").submit(function(){
			if (!acceptCheckbox.checked) {
				acceptPopup.popup('show');
				return false;
			}
			
			var fileName = fileInput.val();
			if (!fileName || fileName.indexOf(".xls") < 0) {
				cbt.alert(local.getText("promo.listings.onlyXls"));
				return false;
			}
			
			$(document.body).isLoading({text: local.getText('promo.request.sending'), position: "overlay"});
			
			uploadIFrame.on("load", function(){
				$(document.body).isLoading('hide');
				
				// check the response
				if (uploadIFrame.contents().length != 0 && uploadIFrame.contents().find("body").html().length > 0) {
					var response = uploadIFrame.contents().find("body").text();
					var responseData = $.parseJSON(response);
					// verification returns no error 
					if (responseData && responseData.status) {
						window.location.replace("/promotion/listings/reviewUploadedListings?promoId="+pageData.promoId);
					}
					// handle error
					else {
						// show error infor
						if (responseData.message && responseData.message.length > 0) {
							$("#upload-error-msg").removeClass("hide");
							$("#upload-error-msg").find("b").html(responseData.message);
						} else if (responseData.data && responseData.data.length > 0) {
							var errCode = parseInt(responseData.data);
							
							if (errCode == 32) {
								cbt.alert(local.getText("errorMsg.regDateExpired"));
							} else {
								cbt.alert(local.getText("errorMsg.uploadListingError"));
							}
							
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
	}
	
	if (formBtn) {
		// confirm listings to apply.		
		
		/********************** Upload listings attachments ****************************************/
		var container = $("#listing-table-container"), requriedAttachments = [], optionalAttachments, toUploadAttachments;
		var attachmentTotalNum = attachmentUploadedNum = currentAttachIndex = 0, uploadingAttachment = false;
		
		// get the number of attachments that will be uploaded.
		function sumAttachments() {
			var allAttachForms = container.find("input[name=item]:checked").parent().parent().find(".attachment-form");
			requriedAttachments = allAttachForms.filter(function(){
				return this.hasAttribute('required');
			});
			optionalAttachments = allAttachForms.filter(function(){
				return !this.hasAttribute('required') && !!this['uploadFile'].value;
			});
			
			toUploadAttachments = $.merge(requriedAttachments, optionalAttachments);
			attachmentTotalNum = toUploadAttachments.length;
		}
		
		// single listing's attachments have been uploaded
		function listingAttachmentsUploaded() {
			currentAttachIndex += 1;
			container.isLoading('hide');
			
			if (currentAttachIndex == attachmentTotalNum) {
				uploadingAttachment = false; // attachments uploading completed
				(attachmentUploadedNum == attachmentTotalNum) && showPreviewDialog(listingTable.selectedItems);
			} else {
				uploadListingAttachments();
			}
		}
		
		// upload single listing's attachments
		function uploadListingAttachments() {
			var $currentForm = toUploadAttachments.eq(currentAttachIndex);
			
			container.isLoading({text: local.getText("promo.request.counting", [attachmentUploadedNum, attachmentTotalNum]), position: "overlay"});
						
			// check if an attachment is uploaded
			var checkAttachmentsUploaded = function() {
				var $msg = $currentForm.siblings("span.msg");
				
				function hasUploadCompleted() {
					return $currentForm.attr('uploading') == "false";
				}
				
				function hasUploadSuccess() {
					// Note: Before call this method, you should have call hasUploadCompleted() first.
					// If uploading succeeded, file input will be emptied and there will have an attachment download link.
					return !$currentForm.find("input[type=file]").val() && $msg.find("a").length > 0;
				}
				
				if (hasUploadCompleted()) {
					hasUploadSuccess() && (attachmentUploadedNum += 1);
					listingAttachmentsUploaded();
				} else {
					var timer = setInterval(function() {
						if (hasUploadCompleted()) {
							clearInterval(timer);
							hasUploadSuccess() && (attachmentUploadedNum += 1);
							listingAttachmentsUploaded();
						}
					}, 500);
				}
			}
			
			$currentForm.submit();
			checkAttachmentsUploaded();
		}
		
		$(formBtn).click(function(event){
			event.preventDefault();
			if (uploadingAttachment) return;
			
			if (!acceptCheckbox.checked) {
				acceptPopup.popup('show');
				return false;
			}
			
			uploadingAttachment = true;
			sumAttachments();
			uploadingAttachment = !(toUploadAttachments.length <= 0); // no attachment
			
			var listings = listingTable.selectedItems;
			if(listings && listings.length > 0) {
				if(attachmentTotalNum > 0) {
					currentAttachIndex = 0;
					attachmentUploadedNum = 0;
					uploadListingAttachments();
				} else {
					showPreviewDialog(listings);
				}
			} else {
				cbt.confirm(local.getText('promo.listings.zeroSubmitted'), submitListings);
			}
			
		});
	}
	
	if (uploadForm && uploadForm.length > 0) {
		uploadForm.get(0).reset();
	}
});