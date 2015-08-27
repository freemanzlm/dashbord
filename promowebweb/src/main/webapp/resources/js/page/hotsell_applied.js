$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var termsDialog = BizReport.termsDialog;
	var locale = BizReport.locale;	
	var confirmDialog = new BizReport.ConfirmDialog();
	
	var listingCountJ = $(".my-listing h3 small span");
	
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
			} else {
				listingCountJ.text(this.selectedItems.length);
			}
		},
		selectChange: function(){
			listingCountJ.text(this.selectedItems.length);
		}
	}, listingTable);
	listingTable.update();
	
	
	var form = $("#listing-form");
	
	confirmDialog.init();
	confirmDialog.subscribe({
		confirm: function() {
			form.submit();
		}
	});
	
	$("#form-btn").click(function(event){
		var listing = listingTable.selectedItems;
		if (listing && listing.length > 0) {
			// collect item ids into form hidden input and separated by comma.
			listing = listingTable.getData();
			form.find("input[name=listings]").val("[" + listing.map(function(item){
				return "{itemId: " + item.itemId + ", selected: " + (item.checked ? 1 : 0) + "}";
			}).join(",") + "]");
			
			form.submit();
		} else {
			event.preventDefault();
			confirmDialog.confirm(locale.getText('promo.hotsell.zeroSubmitted'));
		}
	});
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();	
	
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
});