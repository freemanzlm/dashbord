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
		
		$(formBtn).click(function(event){
			event.preventDefault();
			
			if (!acceptCheckbox.checked) {
				acceptPopup.popup('show');
				return false;
			}
			
			var listings = listingTable.selectedItems;
			var attachIndex = 0;
			var container = $(".dataTable-outer-layer");
			var total = container.find("input[name=item]:checked").parent().parent().find("input[type=file]").length;
			var attachSubmit = function() {
				var attachId = listings[attachIndex].skuId;
				var attachIframe = $("iframe[name=iframe"+attachId+"]");
				var attachForm = $("#form"+attachId);
				var required = $('#listing-table th').eq(attachForm.parent().index()).attr('required');
				if(!required) {
					total = container.find("input[type=file]").filter(function() {
						return $(this).val();
					}).length + container.find("iframe").parent().find("span a").length;
				}
				if($("#href"+attachId).length<=0) {
					attachForm.submit();
				}
				var successCount = container.find("input[name=item]:checked").parent().parent().find("span a").length;
				container.isLoading({text: local.getText("promo.request.counting", [successCount, total]), position: "overlay"});
				var timer = setInterval(function() {
					if($("#msg"+attachId).find("b").html().length != 0) {
						container.isLoading('hide');
						clearInterval(timer);
						/*if(attachIframe.contents().length != 0 && attachIframe.contents().find("body").html().length > 0) {
							if($.parseJSON(attachIframe.contents().find("body").html()).status==true && ahref.length<=0) {
								successCount ++;
							}
						}*/
						attachIndex += 1;
						successCount = container.find("input[name=item]:checked").parent().parent().find("span a").length;
						if(attachIndex<listings.length) {
							attachSubmit();
						} else {
							if (listings && listings.length > 0) {
								if(successCount == listings.length) {
									previewDialog.show();
									previewDialog.listingTable.setData(listings);
								} else {
									return false;
								}
							} else {
								if (pageData && pageData.state == 'Applied') {
									cbt.confirm(local.getText('promo.listings.zeroSubmitted'), submitListings);
								} else {
									cbt.alert(local.getText('promo.listings.applyCondition'));
								}
							}
						}
					}
				}, 500);
			};
			
			if(listings && listings.length > 0) {
				if(total > 0) {
					attachSubmit();
				} else {
					previewDialog.show();
					previewDialog.listingTable.setData(listings);
				}
			} else {
/*				previewDialog.show();
				previewDialog.listingTable.setData(listingTable.oDataTable.data());*/
				cbt.confirm(local.getText('promo.listings.zeroSubmitted'), submitListings);
			}
			
		});
	}
	
	var termsDialog = cbt.termsDialog;
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