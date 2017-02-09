$(function(){
	var ListingTable = BizReport.ListingTable;
	var local = BizReport.local;
	
	var confirmForm = $("form");
	
	var listingTable = new ListingTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-table",
			customTableConfig: {
                'sAjaxSource': '/promotion/listings/getUploadedListings',
                'sScrollY': '',
                columns: pageData && pageData.columns
            }
		},
		fnDataUpdatedCallback: function(data){
			var listings = data.data;
			if (Array.isArray(listings) && listings.length > 0) {
				confirmForm.find("input[name=listings]").val("[" + listings.map(function(item){
					return "{'itemId': '" + item.itemId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
				}).join(",") + "]");
			} else {
				cbt.alert("对不起，您没有上传任何刊登，或者上传刊登失败。");
				confirmForm.find("button").attr("disabled", "disabled");
			}
			
		}});
	listingTable.update({
		promoId: pageData && pageData.promoId
	});
	
	function submitListings() {
		$(document.body).isLoading({text: local.getText('promo.request.sending'), position: "overlay"});
		$.ajax({
			url: "/promotion/listings/submitListings",
			type: 'POST',
			dataType : 'json',
			data: {promoId: pageData && pageData.promoId},
			success : function(json){
				if (json && json.status) {
					$(document.body).isLoading('hide');
					window.location.replace("/promotion/"+pageData.promoId);
				} else if (json.data && json.data.length > 0) {
					$(document.body).isLoading('hide');
					//cbt.alert(local.getText("errorMsg.regDateExpired"));
					window.location.replace("/promotion/"+pageData.promoId);
				} else {
					$(document.body).isLoading('hide');
					//cbt.alert(local.getText('promo.request.fail'));
					window.location.replace("/promotion/"+pageData.promoId);
				}
			},
			error: function(){
				cbt.alert(local.getText('promo.request.fail'));
			}
		});
	}

	/********************** Upload listings attachments ****************************************/
	var container = $("#listing-table-container"), requriedAttachments = [], optionalAttachments, toUploadAttachments;
	var attachmentTotalNum = attachmentUploadedNum = currentAttachIndex = 0, uploadingAttachment = false;
	
	// get the number of attachments that will be uploaded.
	function sumAttachments() {
		var allAttachForms = container.find(".attachment-form");
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
			(attachmentUploadedNum == attachmentTotalNum) && submitListings();
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
				// Note: Before call this method, you must have call hasUploadCompleted() first.
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
	
	var submitBtn = document.getElementById("submit-btn");
	$(submitBtn).click(function(event){
		event.preventDefault();
		if (uploadingAttachment) return;
		
		uploadingAttachment = true;
		sumAttachments();
		uploadingAttachment = !(toUploadAttachments.length <= 0); // no attachment
		
		var listings = listingTable.oDataTable.data();
		if(listings && listings.length > 0) {
			if(attachmentTotalNum > 0) {
				currentAttachIndex = 0;
				attachmentUploadedNum = 0;
				uploadListingAttachments();
			} else {
				submitListings();
			}
		}
	});	
});