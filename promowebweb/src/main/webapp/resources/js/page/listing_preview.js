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
                'aaSortingFixed': [[0, 'desc']],
                'columns': pageData && pageData.columns,
                'promo': {promoId:pageData.promoId, regType:pageData.regType, currentStep: pageData.currentStep, isRegEnd:pageData.isRegEnd, isListingPreview:pageData.isListingPreview}
            }
		},
		fnDataUpdatedCallback: function(data){
			var listings = data.data;
			if (Array.isArray(listings) && listings.length > 0) {
				confirmForm.find("input[name=listings]").val("[" + listings.map(function(item){
					return "{'itemId': '" + item.itemId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
				}).join(",") + "]");
			} else {
				cbt.alert(local.getText('promo.listings.fetchFailed'));
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
					window.location.replace("/promotion/"+pageData.promoId);
				} else {
					$(document.body).isLoading('hide');
					cbt.alert(local.getText('promo.request.fail'));
				}
			},
			error: function(){
				cbt.alert(local.getText('promo.request.fail'));
			}
		});
	}

	/********************** Upload listings attachments ****************************************/
	var container = $("#listing-table-container"), requriedAttachments = [], optionalAttachments = [], toUploadAttachments = [];
	var attachmentTotalNum = attachmentUploadedNum = currentAttachIndex = 0, uploadingAttachment = false, $attachmentErrorSummary = $('#attachments-errors');
	
	function showAttachmentUploadErrors(container, form) {
		var $container = $(container), $summary = $container.find('p');
		if (!$container || $container.length <=0 || !$container.hasClass('hide')) return; // only show the first listing attachment upload error.
		
		if (form.attr('hasError') == "true") {
			$summary.html($summary.html().replace(/{row}/, parseInt(form.attr('rowIndex')) + 1/*excel row start from 0*/));
			$container.removeClass('hide');
			$(document.body).scrollTop(cbt.util.getPositionInPage(container.get(0)).top);
		} else {
			$container.addClass('hide');
		}		
	}
	
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
				showAttachmentUploadErrors($attachmentErrorSummary, $currentForm);
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
		$attachmentErrorSummary.addClass('hide');
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