$(function(){
	
	var ListingReviewTable = BizReport.ListingReviewTable;
	
	var listingTable = new ListingReviewTable();
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