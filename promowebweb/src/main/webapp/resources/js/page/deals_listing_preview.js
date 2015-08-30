$(function(){
	var DealsListingTable = BizReport.DealsListingTable;
	var alertDialog = BizReport.alertDialog;
	
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
			tableId: "deals-listing-table"
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
	listingTable.update();
	
	
});