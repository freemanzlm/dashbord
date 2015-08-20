$(function(){
	
	var ListingSelectTable = BizReport.ListingSelectTable;
	
	var listingTable = new ListingSelectTable();
	listingTable.subscribe({
		initialized: function() {
			// if it has passed the apply deadline date, user can't select listings and submit again.
			listingTable.hideCheckbox();
		}
	}, listingTable);
	
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-states-table"
		}});
	listingTable.update();
	
	var termsDialog = BizReport.termsDialog;
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
	console.log("hello");
});