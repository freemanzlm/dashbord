$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var termsDialog = BizReport.termsDialog;
	
	var formBtn = document.getElementById("form-btn");	
	
	
	var listingTable = new HotsellListingTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "hotsell-listing-table"
		}});
	listingTable.subscribe({
		initialized: function() {
			if (pageData && pageData.expired) {
				// if it has passed the apply deadline date, user can't select listings and submit again.
				listingTable.hideCheckbox();
			}
		},
		selectChange: function(){
			if (this.hasSelectedItem()) {
				formBtn.removeAttribute("disabled");
			} else {
				formBtn.setAttribute("disabled", "disabled");
			}
		}
	}, listingTable);
	listingTable.update();	
	
	var form = $("form").submit(function(){
		// if user doesn't select a item, form can't be submitted.
		var listing = listingTable.selectedItems;
		if (listing && listing.length > 0) {
			// collect item ids into form hidden input and separated by comma.
			form.find("input[name=listings]").val(listing.map(function(item){
				return item.itemId;
			}).join(","));
			
			return true;
		}		
		
		// no listing selection, no form submission.
		return false;
	});
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();	
	
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
});