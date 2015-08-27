$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var termsDialog = BizReport.termsDialog;
	var confirmDialog = new BizReport.ConfirmDialog();
	var locale = BizReport.locale;
	
	var listingCountJ = $(".my-listing h3 small span");
	
	var form = $("#listing-form");
	
	var listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			if (pageData && (pageData.state != "approved" || pageData.expired)) {
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
		},
		fnDataUpdatedCallback: function(data){
			var listings = data.data;
			if (Array.isArray(listings) && listings.length > 0) {
				form.find("input[name=listings]").val("[" + listings.map(function(item){
					return "{itemId: " + item.itemId + ", selected: " + (item.checked ? 1 : 0) + "}";
				}).join(",") + "]");
			}
		}});
	listingTable.update();	
	
	var formBtn = document.getElementById("form-btn");
	var acceptCheckbox = $("#accept").change(function(){
		if (this.checked) {
			formBtn.removeAttribute("disabled");
		} else {
			formBtn.setAttribute("disabled", "disabled");
		}
	});	
	
	confirmDialog.init();
	confirmDialog.subscribe({
		confirm: function() {
			form.submit();
		}
	});
	
	$("#form-btn").click(function(event){
		var listing = listingTable.getData();
		form.find("input[name=listings]").val("[" + listing.map(function(item){
			return "{itemId: " + item.itemId + ", selected: " + (item.checked ? 1 : 0) + "}";
		}).join(",") + "]");
		
		listing = listingTable.selectedItems;
		
		if (listing && listing.length > 0) {
			// collect item ids into form hidden input and separated by comma.
			form.submit();
		} else {
			event.preventDefault();
			confirmDialog.confirm(locale.getText('promo.hotsell.zeroSubmitted'));
		}
	});
	
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();
});