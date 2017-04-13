$(function(){
	
	var fileTypeReg = /\.(doc|docx|xls|xlsx|jpg|gif|zip|rar|pdf)$/i;
	
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
	
	var app = new Vue({
		el: "#page-pane",
		data: {
			user: {name: pageData.username},
			hasAcceptLetter: false,
			hasSubmitFields: false,
			hasUploadLetter: false,
			hasApproved: false
		},
		methods: {
			sendSellerCustomFields: function(event){
				var $form = $("#custom-fields-form");
				$.ajax($form.attr("action"), {
					data: $form.serialize(),
					type : 'POST',
					contentType : 'application/x-www-form-urlencoded',
					dataType : 'json',
					headers: {'Cache-Control': 'no-cache', 'Pragma': 'no-cache'},
					context : this,
					success : function(data) {
						if (data && data.status === true) {
							this.hasSubmitFields = true;
							// download confirm letter
							window.open("generateLetter?promoId=" + pageData.promoId);
							console.log("hello");
						}
					},
					error: function() {
						console.log(this)
					}
				});
			},
			gotoSecondStep: function(event) {
				$("[aria-controls=pane2]").trigger('click');
			}
		}
	});
	
	window.app = app;
	
	var local = BizReport.local;

	var uploadForm, acceptCheckbox, formBtn;
	
	acceptCheckbox = document.getElementById("accept");
	formBtn = document.getElementById("upload-form-btn");
	
	var acceptPopup = $(acceptCheckbox).parent().each(function(){
		$(this).popup({"trigger": "mannual", html: this.title});
	});
	
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
		
		$form.attr('uploading', !!fileName);
		
		// get attachment upload result.
		$uploadIframe.on("load", function(){
			$form.attr('uploading', false);
			
			if ($uploadIframe.contents().length != 0 && $uploadIframe.contents().find("body").html().length > 0) {
				var response = $uploadIframe.contents().find("body").text();
				var responseData = $.parseJSON(response);
				
				if(responseData.status==true) {
					$errorMsgEle.html('<a href=/promotion/subsidy/downloadAttachment/?promoId=' + pageData.promoId + '&key=' + $form.find("input[name=key]").val() +'>'+local.getText('promo.listings.attachdownload')+'</a>');
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
	
	if (uploadForm && uploadForm.length > 0) {
		uploadForm.get(0).reset();
	}
});