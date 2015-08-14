$(function(){
	
	var ListingPreviewTable = BizReport.ListingPreviewTable;
	
	var listingTable = new ListingPreviewTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-preivew-table"
		}});
	listingTable.update();	
	
	console.log("hello");
});