$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var alertDialog = BizReport.alertDialog;	
	
	var confirmForm = $("form");
	
	var listingTable = new HotsellListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideCheckbox();
			listingTable.hideStateColumn();
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "hotsell-listing-table"
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
	listingTable.update({promoId:pageData.promoId});
	
	
});