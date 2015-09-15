$(function(){
	var DealsListingTable = BizReport.DealsListingTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var listingCountJ = $(".my-listing h3 small span");
	var formBtn = document.getElementById("form-btn");
	var form = $("#listing-form");
	
	var listingTable = new DealsListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideStateColumn();
		},
		selectChange: function(){
			listingCountJ.text(this.selectedItems.length);
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table"
		}});
	listingTable.update({promoId:pageData.promoId});
	
	/*var form = $("#listing-form").submit(function(){
		// if user doesn't select a item, form can't be submitted.
		var listing = listingTable.selectedItems;
		if (listing && listing.length > 0) {
			listings = listingTable.getData();
			form.find("input[name=listings]").val("[" + listings.map(function(item){
				return '{"skuId": "' + item.skuId + '", "selected": ' + (item.checked ? 1 : 0) + '}';
			}).join(",") + "]");
			return true;
		}
		
		return false;
	});*/
	
	function submitListings() {
		var listings = listingTable.getData();
		form.find("input[name=listings]").val("[" + listings.map(function(item){
			return '{"skuId": "' + item.skuId + '", "selected": ' + (item.checked ? 1 : 0) + '}';
		}).join(",") + "]");
		
		var data = form.serialize();
		$.ajax({
			url: form.prop('action'),
			type: 'POST',
			data: data,
//			contentType: 'application/json',
			dataType : 'json',
			success : function(json){
				if (json && json.status) {
					location.reload();
				} else {
					alertDialog.alert(locale.getText('promo.request.fail'));
				}
			},
			error: function(){
				alertDialog.alert(locale.getText('promo.request.fail'));
			}
		});
	}
	
/*	$(formBtn).click(function(event){
		var listing = listingTable.selectedItems;
		if (listing && listing.length > 0) {
			// collect item ids into form hidden input and separated by comma.
			form.find("input[name=listings]").val("[" + listings.map(function(item){
				return "{'itemId': '" + item.itemId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
			}).join(",") + "]");
			
			form.submit();
		} else {
			event.preventDefault();
			alertDialog.alert(locale.getText('promo.hotsell.applyCondition'));
		}
	});*/
	
	var ListingPreviewDialog = BizReport.ListingPreviewDialog;
	var previewDialog = new ListingPreviewDialog();
	previewDialog.init();
	previewDialog.subscribe({
		ok: function(){
			submitListings();
//			form.submit();
		}
	});
	
	$(formBtn).click(function(event){
		event.preventDefault();
		var listings = listingTable.selectedItems;
		if (listings && listings.length > 0) {
			previewDialog.show();
			previewDialog.listingTable.setData(listings);
		} else {
			alertDialog.alert(locale.getText('promo.hotsell.applyCondition'));
		}
	});
	
	/*var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	*/
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();
});