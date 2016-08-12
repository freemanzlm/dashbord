$(function(){
	var ListingTable = BizReport.ListingTable;
	var local = BizReport.local;
	
	var confirmForm = $("form");
	
	var listingTable = new ListingTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-table",
			customTableConfig: {
                'sAjaxSource': '/promotion/listings/getPromotionListings',
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

		event.preventDefault();
		
		var listings = listingTable.oDataTable.data();
		var attachIndex = 0;
		var container = $(".container");
		var total = container.find("input[type=file]:checked").length;
		var attachSubmit = function() {
			var attachId = listings[attachIndex].skuId;
			var attachIframe = $("iframe[name=iframe"+attachId+"]");
			var attachForm = $("#form"+attachId);
			if($("#href"+attachId).length<=0) {
				attachForm.submit();
			}
			var successCount = container.find("iframe").parent().find("a").length;
			container.isLoading({text: local.getText("promo.request.counting", [successCount, total]), position: "overlay"});
			var timer = setInterval(function() {
				if($("#msg"+attachId).find("b").html().length != 0) {
					container.isLoading('hide');
					clearInterval(timer);
					attachIndex += 1;
					successCount = container.find("iframe").parent().find("a").length;
					if(attachIndex<total) {
						attachSubmit();
					} else {
						if(successCount == total) {
							$.ajax({
								url: "/promotion/listings/submitListings",
								type: 'POST',
								data: data,
								dataType : 'json',
								success : function(json){
									if (json && json.status) {
										window.location.replace("/promotion/"+pageData.promoId);
									} else if (json.data && json.data.length > 0) {
										cbt.alert(local.getText("errorMsg.regDateExpired"));
										window.location.replace("/promotion/"+pageData.promoId);
									} else {
										cbt.alert(local.getText('promo.request.fail'));
									}
								},
								error: function(){
									cbt.alert(local.getText('promo.request.fail'));
								}
							});
						}
					}
				}
			}, 500);
		};
		
		var withoutAttachSubmit = function() {
			$.ajax({
				url: "/promotion/listings/submitListings",
				type: 'POST',
				data: data,
				dataType : 'json',
				success : function(json){
					if (json && json.status) {
						window.location.replace("/promotion/"+pageData.promoId);
					} else if (json.data && json.data.length > 0) {
						cbt.alert(local.getText("errorMsg.regDateExpired"));
						window.location.replace("/promotion/"+pageData.promoId);
					} else {
						cbt.alert(local.getText('promo.request.fail'));
					}
				},
				error: function(){
					cbt.alert(local.getText('promo.request.fail'));
				}
			});
		};
		
		if(total > 0) {
			attachSubmit();
		} else {
			withoutAttachSubmit();
		}
	});	
});