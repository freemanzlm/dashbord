$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var termsDialog = BizReport.termsDialog;
	
	var listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			// if it has passed the apply deadline date, user can't select listings and submit again.
			listingTable.hideCheckbox();
		}
	}, listingTable);
	
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table"
		}});
	listingTable.update();	
	
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
	console.log("hello");
});