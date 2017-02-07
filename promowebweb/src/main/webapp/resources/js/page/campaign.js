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
		
		/********************** Upload listings attachments ****************************************/
		var container = $("#listing-table-container"), requriedAttachments = [], optionalAttachments, toUploadAttachments;
		var attachmentTotalNum = attachmentUploadedNum = currentAttachIndex = 0, uploadingAttachment = false;
		
		// get the number of attachments that will be uploaded.
		function sumAttachments() {
			var allAttachForms = container.find("input[name=item]:checked").parent().parent().find("form");
			requriedAttachments = allAttachForms.filter(function(){
				return this.hasAttribute('required');
			});
			optionalAttachments = allAttachForms.filter(function(){
				return !this.hasAttribute('required') && !!this['uploadFile'].value;
			});
			
			toUploadAttachments = $.merge(requriedAttachments, optionalAttachments);
			attachmentTotalNum = toUploadAttachments.length;
			console.log(attachmentTotalNum);
		}
		
		function submitAttachments() {
			if (currentAttachIndex == attachmentTotalNum) {
				container.isLoading('hide');
				uploadingAttachment = false; // attachments uploading completed
			} else if (currentAttachIndex == 0) {
				container.isLoading({text: local.getText("promo.request.counting", [attachmentUploadedNum, attachmentTotalNum]), position: "overlay"});
			}
			
			var $currentForm = toUploadAttachments.eq(currentAttachIndex);
						
			// check if an attachment is uploaded
			var checkAttachmentUploaded = function() {
				var $msg = $currentForm.siblings("span.msg");
				
				var timer = setInterval(function() {
					if ($msg.find("b").html().length != 0 && !$currentForm.find("input[type=file]").val()) {
						clearInterval(timer);
						attachmentUploadedNum += 1;
						currentAttachIndex += 1;
						submitAttachments();
					}
				}, 1000);
			}
			
			$currentForm.submit();
			checkAttachmentUploaded();
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
			
			if (toUploadAttachments.length <= 0) {
				uploadingAttachment = false; // no attachment
			}
			
			currentAttachIndex = 0;
			attachmentUploadedNum = 0;
			submitAttachments();
			
			var listings = listingTable.selectedItems;
			
			if(listings && listings.length > 0) {
				if(total > 0) {
					attachSubmit();
				} else {
					previewDialog.show();
					listings = listings && listings.filter(function(listing){
						return !listing.lock;
					});
					previewDialog.listingTable.setData(listings);
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