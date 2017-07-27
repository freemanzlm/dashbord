$(function(){
	
	var ListingTable = BizReport.ListingTable;
	var local = BizReport.local;

	var uploadForm, fileInput, uploadBtn, uploadIFrame, acceptCheckbox, form, formBtn, listingCountJ;
	
	var hasState = false, customTableConfig, listingSortingConfig = []/* empty aaSorting must be [], it can't be null */;
	
	function findStateColumnIndex(columns) {
		for (var i = 0; i < columns.length; i++) {
			if (columns[i]['data'] === 'state') {
				return i;
			}
		}
		
		return -1;
	}
	
	if (pageData && pageData.columns && pageData.columns.length > 1) {
		var stateColumnIndex = findStateColumnIndex(pageData.columns);
		stateColumnIndex > -1 && listingSortingConfig.push([stateColumnIndex, 'asc']);
		console.log(stateColumnIndex);
		
		customTableConfig = {
			'columns': pageData.columns,
			'aaSortingFixed': [[0, 'desc']],
			'aaSorting': listingSortingConfig,
			'promo': {promoId:pageData.promoId, regType:pageData.regType, currentStep: pageData.currentStep, isRegEnd:pageData.isRegEnd, isListingPreview:pageData.isListingPreview}
		};
	}
	
	// only handle excel data errors
	function createExcelErrorsSummary(container, errors) {
		var $container = $(container), $errorList = $container.find('ul'), $summary = $container.find('p'), first = true;
		
		$errorList.empty(); // init		
		if (errors && errors.length > 0) {
			errors.forEach(function(error){
				first && $summary.html($summary.html().replace(/{row}/, error.rowIndex + 1/*excel row start from 0*/)), first = false;
				var li = document.createElement('li');
				li.innerHTML = local.getText('excel.row', [error.rowIndex + 1]) + error.message;
				$errorList.append(li);
			});
			$container.removeClass('hide');
		} else {
			$container.addClass('hide');
		}
	}
	
	// only handle request error
	function createRequestErrorSummary(container, message) {
		var $container = $(container), $summary = $container.find('p');
		message ? $summary.html(message).removeClass('hide') : $summary.addClass('hide');
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
	
	if (document.getElementById('listing-table')) {
		// Listing Table
		listingTable = new ListingTable();
		listingTable.subscribe({
			selectChange: function(){
				listingCountJ.text(this.selectedItems.length);
			}
		}, listingTable);
		listingTable.init({
			dataTableConfig: {
				tableId: "listing-table",
				customTableConfig: customTableConfig
			},
			fnDataUpdatedCallback: function(data){
				var listings = data.data;
				if (Array.isArray(listings) && listings.length > 0) {
					form.find("input[name=listings]").val("[" + listings.map(function(item){
						return '{"skuId": "' + item.skuId + '", "selected": ' + (item.checked ? 1 : 0) + '}';
					}).join(",") + "]");
				}
			}});
		listingTable.update({
			promoId:pageData && pageData.promoId
		});
	}
	
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
					} else if (responseData) {// handle error
						createExcelErrorsSummary('#excel-errors', responseData.errors);
						
						if (responseData.errors && responseData.errors.length > 0) {
							console.log(cbt.util.getPositionInPage(document.getElementById('excel-errors')).top);
							$(document.body).scrollTop(cbt.util.getPositionInPage(document.getElementById('excel-errors')).top);
						}
						
						if (!responseData.errors || responseData.errors.length <= 0) {
							if (responseData.statusCode == 32) {
								createRequestErrorSummary('#request-errors', local.getText("errorMsg.regDateExpired"));
							} 
						}
					} else {
						createRequestErrorSummary('#request-errors', local.getText("errorMsg.uploadListingError"));
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
		function submitListings() {
			$(document.body).isLoading({text: local.getText('promo.request.sending'), position: "overlay"});
			var listings = listingTable.getData();
			listings = listings.filter(function(listing){
				return !(listing.state == 'PretrialFail');
			});
			form.find("input[name=listings]").val("[" + listings.map(function(item){
				return '{"skuId": "' + item.skuId + '", "selected": ' + (item.checked ? 1 : 0) + '}';
			}).join(",") + "]");
			
			var data = form.serialize();
			$.ajax({
				url: form.prop('action'),
				type: 'POST',
				data: data,
				dataType : 'json',
				success : function(json){
					$(document.body).isLoading('hide');
					if (json && json.status) {
						location.reload();
					} else {
						cbt.alert(local.getText('promo.request.fail'));
					}
				},
				error: function(){
					$(document.body).isLoading('hide');
					cbt.alert(local.getText('promo.request.fail'));
				}
			});
		}
		
		/********************** Upload listings attachments ****************************************/
		var ListingPreviewDialog = BizReport.ListingPreviewDialog;
		var previewDialog = new ListingPreviewDialog(null, {wrapper: "#listing-preview-dialog", zIndex: 20000, width: 850, body: {
			style: {
				'max-height': "530px",
				overflow: 'auto'
			}
		}});
		
		previewDialog.subscribe({
			ok: function(){
				submitListings();
			}
		});
		
		function showPreviewDialog(listings) {
			previewDialog.show();
			listings = listings && listings.filter(function(listing){
				return !listing.lock;
			});
			previewDialog.listingTable.setData(listings);
		}
		
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
	
	var termsDialog = cbt.termsDialog;
	termsDialog.subscribe({
		"ok": function() {
			if (acceptCheckbox) {
				acceptCheckbox.removeAttribute("disabled");
			}
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	
	
	if (uploadForm && uploadForm.length > 0) {
		uploadForm.get(0).reset();
	}
//	activityDetail.html(activityDetail.html().replace(/&lt;/g, "<").replace(/&gt;/g, ">"));
});