$(function(){
	
	var ListingPreviewTable = BizReport.ListingPreviewTable;
	
	var listingTable = new ListingPreviewTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table"
		}});
	listingTable.update();	
	
	console.log("hello");
});