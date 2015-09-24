$(function(){
	
	var HotsellListingTable = BizReport.HotsellListingTable;
	
	var listingTable = new HotsellListingTable();
	listingTable.subscribe({
		initialized: function() {
			// if it has passed the apply deadline date, user can't select listings and submit again.
			listingTable.hideCheckbox();
		}
	}, listingTable);
	
	listingTable.init({
		dataTableConfig: {
			tableId: "hotsell-listing-table"
		}});
	listingTable.update({promoId:pageData && pageData.promoId});
	
	var termsDialog = BizReport.termsDialog;
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
});