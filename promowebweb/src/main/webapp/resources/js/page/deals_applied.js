$(function(){
	
	var ListingSelectTable = BizReport.ListingSelectTable;
	
	var listingTable = new ListingSelectTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-states-table"
		}});
	listingTable.update();	
	
	console.log("hello");
});