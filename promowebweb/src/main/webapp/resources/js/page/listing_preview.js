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

	var submitBtn = document.getElementById("submit-btn"),
		data = {
			promoId: pageData && pageData.promoId
		};
	
	$(submitBtn).click(function(){
		var listingSubmit = function() {
			$(document.body).isLoading({text: local.getText('promo.request.sending'), position: "overlay"});
			$.ajax({
				url: "/promotion/listings/submitListings",
				type: 'POST',
				data: data,
				dataType : 'json',
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
		};
		
		var withoutAttachSubmit = function() {
			listingSubmit();
		};
		
		var listings = listingTable.oDataTable.data();
		var attachIndex = 0;
		var container = $(".dataTable-container");
		var total = container.find("input[type=file]").length;
		var required = $('#listing-table th').eq($("iframe[name=iframe"+listings[0].skuId+"]").parent().index()).attr('required');
		if(!required) {
			total = container.find("input[type=file]").filter(function() {
				return !!($(this).val()|| $(this).parent().parent().parent().find("span a").length);
			}).length ;
		}
		var attachSubmit = function() {
			var attachId = listings[attachIndex].skuId;
			var attachIframe = $("iframe[name=iframe"+attachId+"]");
			var attachForm = $("#form"+attachId);
			/*var required = $('#listing-table th').eq(attachForm.parent().index()).attr('required');
			if(!required) {
				total = container.find("input[type=file]").filter(function() {
					return $(this).val();
				}).length + container.find("iframe").parent().find("span a").length;
			}*/
			if($("#href"+attachId).length<=0) {
				attachForm.submit();
			}
			var successCount = container.find("iframe").parent().find("a").length;
			container.isLoading({text: local.getText("promo.request.counting", [successCount, total]), position: "overlay"});
			var timer = setInterval(function() {
				if(successCount == total){
					container.isLoading('hide');
					clearInterval(timer);
					listingSubmit();
				} else if($("#msg"+attachId).find("b").html().length != 0) {
					container.isLoading('hide');
					clearInterval(timer);
					attachIndex += 1;
					successCount = container.find("iframe").parent().find("span a").length;
					if(attachIndex<total) {
						attachSubmit();
					} else {
						if(successCount == total) {
							listingSubmit();
						}
					}
				}
			}, 500);
		};
		
		
		if(total > 0) {
			attachSubmit();
		} else {
			withoutAttachSubmit();
		}
	});	
});