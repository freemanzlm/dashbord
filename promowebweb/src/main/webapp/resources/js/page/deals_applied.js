$(function(){
	
	var ListingSelectTable = BizReport.ListingSelectTable;
	
	var listingTable = new ListingSelectTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-select-table"
		}});
	listingTable.update();	
	
	console.log("hello");
});