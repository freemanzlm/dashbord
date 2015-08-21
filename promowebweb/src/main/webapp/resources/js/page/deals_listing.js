$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var termsDialog = BizReport.termsDialog;
	
	var listingCountJ = $(".my-listing h3 small span");
	
	var listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			if (pageData && (pageData.state != "confirm" || pageData.expired)) {
				listingTable.hideCheckbox();
			}
		},
		selectChange: function(){
			listingCountJ.text(this.selectedItems.length);
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table"
		}});
	listingTable.update();	
	
	var acceptCheckbox = $("#accept").change(function(){
		checkUploadBtnStatus();
	});	
	
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	console.log("hello");
});