$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var listingCountJ = $(".my-listing h3 small span");
	
	var listingTable = new HotsellListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideStateColumn();
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
	listingTable.init({
		dataTableConfig: {
			tableId: "hotsell-listing-table"
		}});
	listingTable.update({promoId:pageData.promoId});
	
	var formBtn = document.getElementById("form-btn");
	var acceptCheckbox = $("#accept").change(function(){
		if (this.checked) {
			formBtn.removeAttribute("disabled");
		} else {
			formBtn.setAttribute("disabled", "disabled");
		}
	});
	
	var form = $("form").submit(function(){
		// if user doesn't select a item, form can't be submitted.
		var listings = listingTable.selectedItems;
		if (listings && listings.length > 0) {
			listings = listingTable.getData();
			form.find("input[name=listings]").val("[" + listings.map(function(item){
				return "{'skuId': '" + item.skuId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
			}).join(",") + "]");
			
			return true;
		}
		
		return false;
	});
	
//	$(formBtn).click(function(event){
//		var listing = listingTable.selectedItems;
//		if (listing && listing.length > 0) {
//			// collect item ids into form hidden input and separated by comma.
//			listing = listingTable.getData();
//			form.find("input[name=listings]").val("[" + listing.map(function(item){
//				return "{'itemId': '" + item.itemId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
//			}).join(",") + "]");
//			
//			form.submit();
//		} else {
//			event.preventDefault();
//			alertDialog.alert(locale.getText('promo.hotsell.applyCondition'));
//		}
//	});
	
	// for test
	var ListingPreviewDialog = BizReport.ListingPreviewDialog;
	var previewDialog = new ListingPreviewDialog();
	previewDialog.init();
	previewDialog.subscribe({
		ok: function(){
			form.submit();
		}
	});
	
	$(formBtn).click(function(event){
		var listings = listingTable.selectedItems;
		if (listings && listings.length > 0) {
			previewDialog.show();
			previewDialog.listingTable.setData(listings);
		} else {
			event.preventDefault();
			alertDialog.alert(locale.getText('promo.hotsell.applyCondition'));
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
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();
	
	acceptCheckbox.parent().each(function(){
		$(this).popup({"trigger": "hover", html: this.title});
	});	
});