$(function(){
	var ListingSubmittedTable = BizReport.ListingSubmittedTable;
	
	var confirmForm = $("form");
	
	var listingTable = new ListingSubmittedTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-preview-table"
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
	listingTable.update();
	
});