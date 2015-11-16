$(function(){
	var DealsListingTable = BizReport.DealsListingTable;
	var alertDialog = BizReport.alertDialog;
	var local = BizReport.local;
	
	var confirmForm = $("form");
	
	var listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideCheckbox();
			listingTable.hideStateColumn();
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table",
			customTableConfig: {
                'sAjaxSource': '/promotion/deals/getUploadedListings'
            }
		},
		fnDataUpdatedCallback: function(data){
			var listings = data.data;
			if (Array.isArray(listings) && listings.length > 0) {
				confirmForm.find("input[name=listings]").val("[" + listings.map(function(item){
					return "{'itemId': '" + item.itemId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
				}).join(",") + "]");
			} else {
				alertDialog.alert("对不起，您没有上传任何刊登，或者上传刊登失败。");
				confirmForm.find("button").attr("disabled", "disabled");
			}
			
		}});
	listingTable.update({promoId: pageData && pageData.promoId});

	var submitBtn = document.getElementById("submit-btn"),
		data = {promoId: pageData && pageData.promoId};
	
	$(submitBtn).click(function(){
		$.ajax({
			url: "/promotion/deals/submitDealsListings",
			type: 'POST',
			data: data,
			dataType : 'json',
			success : function(json){
				if (json && json.status) {
					window.location.replace("/promotion/"+pageData.promoId);
				} else if (json.data && json.data.length > 0) {
					alertDialog.alert(local.getText("errorMsg.regDateExpired"));
					window.location.replace("/promotion/"+pageData.promoId);
				} else {
					alertDialog.alert(local.getText('promo.request.fail'));
				}
			},
			error: function(){
				alertDialog.alert(local.getText('promo.request.fail'));
			}
		});
	});	
});