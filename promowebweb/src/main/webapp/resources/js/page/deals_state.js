$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable || BizReport.GBHListingTable || BizReport.FrenchListingTable || BizReport.USListingTable;
	
	var listingTable = new DealsListingTable();
	
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table",
			customTableConfig: {
				columns: [{bVisible: false}] // hide the first column which has checkbox
			}
		}});
	listingTable.update({promoId:pageData && pageData.promoId});	
	
	var termsDialog = cbt.termsDialog;
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	
	
});