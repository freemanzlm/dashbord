$(function(){
	
	var DealsListingTable = BizReport.DealsListingTable;
	var termsDialog = BizReport.termsDialog;
	
	var listingCountJ = $(".my-listing h3 small span");
	
	var form = $("#listing-form");
	
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