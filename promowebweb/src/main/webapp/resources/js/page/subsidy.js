$(function(){
	Vue.config.devtools = false;
	var supportedFileSuffix = /(\.zip|\.pdf|\.jpg|\.jpeg)$/i;
	
	function hasValidSize(inputElement, maxSize) {
		if (inputElement && inputElement.files) {
			for (var i = 0; i < inputElement.files.length; i++)	{
				var file = inputElement.files[i];
				if (file && file.size > maxSize) {
					return false;
				} else {
					continue;
				}
			}
		}
		return true;
	}
	
	function hasValidFileType(inputElement) {
		if (inputElement && inputElement.files) {
			var file = inputElement.files[0];
			if (file && !supportedFileSuffix.test(file.name)) {
				return false;
			}
		}
		return true;
	}
	
	// Vue plugin.
	var app = new Vue({
		el: "#page-pane",
		data: {
			user: {name: pageData.username},
			hasAcceptLetter: false,
			hasSubmitFields: pageData.hasSubmitFields,
			hasUploadLetter: pageData.hasUploadLetter,
			hasApproved: pageData.hasSubsidyApproved,
			isAwardEnd: pageData.isAwardEnd
		},
		methods: {
			validateFileType: function(event) {
				if (!hasValidFileType(event.target)) {
					alert(local.getText("subsidy.attachment.attachmentFileTypeError"));
					event.target.value = "";
				}
			},
			sendSellerCustomFields: function(event){
				var $form = $("#custom-fields-form");
				if ($form.valid()) { // jquery validation plugin
					$(document.body).isLoading({text: local.getText('promo.request.sending'), position: "overlay"});
					$.ajax($form.attr("action"), {
						data: $form.serialize(),
						type : 'POST',
						contentType : 'application/x-www-form-urlencoded',
						dataType : 'json',
						headers: {'Cache-Control': 'no-cache', 'Pragma': 'no-cache'},
						context : this,
						success : function(data) {
							$(document.body).isLoading('hide');
							if (data && data.status === true) {
								this.hasSubmitFields = true;
								// download confirm letter
								window.open("downloadLetter?promoId=" + pageData.promoId);
							}
						},
						error: function() {
							$(document.body).isLoading('hide');
						}
					});
				}
			},
			gotoSecondStep: function(event) {
				$("[aria-controls=pane2]").trigger('click');
			}
		}
	});
	
	window.app = app;
	$("#custom-fields-form").validate(); // jquery validation plugin
	
	var local = BizReport.local;
	var formBtn = document.getElementById("upload-form-btn");
	var $attachmentForms = $("form.attachment-form");
	
	$attachmentForms.submit(function(){
		var $form = $(this), required = $form.attr("required");
		var $fileInput = $form.find("input[type=file]");
		var fileName = $fileInput.val();
		var $errorMsgEle = $form.find(".msg");
		var $uploadIframe = $form.next("iframe");
		var hasUploded = $form.data("hasuploaded");
		
		if(required) { //attachment is a must
			if(!fileName && !hasUploded) {
				$errorMsgEle.css({"color": "red"}).html(local.getText("subsidy.attachment.notEmpty"));
				return false;
			}
		}
		
		if (!fileName) return false;
		
		if (!hasValidSize($fileInput.get(0), 5242880)) {
			// attachment size should be less than 5M.
			$errorMsgEle.css({"color": "red"}).html(local.getText("subsidy.attachment.attachmentSizeError"));
			return false;
		}
		
		if (!hasValidFileType($fileInput.get(0))) {
			// attachment size should be less than 5M.
			$errorMsgEle.css({"color": "red"}).html(local.getText("subsidy.attachment.attachmentFileTypeError"));
			return false;
		}
		
		$form.attr('uploading', !!fileName);
		
		// get attachment upload result.
		$uploadIframe.on("load", function(){
			$form.attr('uploading', false);
			
			if ($uploadIframe.contents().length != 0 && $uploadIframe.contents().find("body").html().length > 0) {
				var response = $uploadIframe.contents().find("body").text();
				var responseData = $.parseJSON(response);
				
				if(responseData.status==true) {
					$errorMsgEle.html('<a href=/promotion/subsidy/downloadAttachmentById/?id=' + responseData.message +'>'+local.getText('subsidy.attachment.attachdownload')+'</a>');
					$form.find(".file-input input").val(""); // clear input[type=file] input value
					$form.data("hasuploaded", true);
				} else if(responseData.message && responseData.message.length > 0) {
					$errorMsgEle.css({"color": "red"}).html(responseData.message);
				} else {
					$errorMsgEle.css({"color": "red"}).html(local.getText("attachmentUploadFailed"));
				}				
			}
		});
		
		return !!$fileInput.val();
	});
	
	if (formBtn) {
		/********************** Upload listings attachments ****************************************/
		var container = $(".confirm-letter-submission-pane"), requriedAttachments = [], optionalAttachments, toUploadAttachments;
		var attachmentTotalNum = attachmentUploadedNum = currentAttachIndex = 0, uploadingAttachment = false;
		
		// get the number of attachments that will be uploaded.
		function sumAttachments() {
			var allAttachForms = container.find("form.form-horizontal");
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
		function subsidyAttachmentsUploaded() {
			currentAttachIndex += 1;
			container.isLoading('hide');
			
			if (currentAttachIndex == attachmentTotalNum) {
				uploadingAttachment = false; // attachments uploading completed
				(attachmentUploadedNum == attachmentTotalNum) && (app.hasUploadLetter = true);
			} else {
				uploadSubsidyAttachments();
			}
		}
		
		// upload single listing's attachments
		function uploadSubsidyAttachments() {
			var $currentForm = toUploadAttachments.eq(currentAttachIndex);
			
			container.isLoading({text: local.getText("promo.request.counting", [attachmentUploadedNum, attachmentTotalNum]), position: "overlay"});
						
			// check if an attachment is uploaded
			var checkAttachmentsUploaded = function() {
				var $msg = $currentForm.find("span.msg");
				
				function hasUploadCompleted() {
					var uploading = $currentForm.attr('uploading');
					return !uploading || $currentForm.attr('uploading') == 'false';
				}
				
				function hasUploadSuccess() {
					// Note: Before call this method, you should have call hasUploadCompleted() first.
					// If uploading succeeded, file input will be emptied and there will have an attachment download link.
					return !$currentForm.find("input[type=file]").val() && $msg.find("a").length > 0;
				}
				
				if (hasUploadCompleted()) {
					hasUploadSuccess() && (attachmentUploadedNum += 1);
					subsidyAttachmentsUploaded();
				} else {
					var timer = setInterval(function() {
						if (hasUploadCompleted()) {
							clearInterval(timer);
							hasUploadSuccess() && (attachmentUploadedNum += 1);
							subsidyAttachmentsUploaded();
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
			
			uploadingAttachment = true;
			sumAttachments();
			uploadingAttachment = !(toUploadAttachments.length <= 0); // no attachment
			
			if(toUploadAttachments && toUploadAttachments.length > 0) {
				if(attachmentTotalNum > 0) {
					currentAttachIndex = 0;
					attachmentUploadedNum = 0;
					uploadSubsidyAttachments();
				} else {
					app.hasUploadLetter = true;
				}
			} else {
				cbt.confirm(local.getText('promo.listings.zeroSubmitted'), submitListings);
			}
			
		});
	}
});