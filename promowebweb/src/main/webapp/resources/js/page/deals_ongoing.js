$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	
	var listingTable = new DealsListingTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-submitted-table"
		}});
	listingTable.update();
	
	var termsDialog = BizReport.termsDialog;
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
	console.log("hello");
});