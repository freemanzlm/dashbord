$(function(){
	var HotsellApplicableTable = BizReport.HotsellApplicableTable;
	
	var listingCountJ = $(".my-listing h3 small span");
	
	var listingTable = new HotsellApplicableTable();
	listingTable.subscribe({
		selectChange: function(){
			listingCountJ.text(this.selectedItems.length);
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-table"
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
	
	var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
	
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
		
		return false;
	});
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();
});