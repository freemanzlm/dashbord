$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var termsDialog = BizReport.termsDialog;
	var alertDialog = BizReport.alertDialog;
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
	listingTable.update({promoId:pageData.promoId});
	
	var form = $("#listing-form");
	/*var form = $("#listing-form").submit(function(){
		// if user doesn't select a item, form can't be submitted.
		listings = listingTable.getData();
		form.find("input[name=listings]").val("[" + listings.map(function(item){
			return '{"skuId": "' + item.skuId + '", "selected": ' + (item.checked ? 1 : 0) + '}';
		}).join(",") + "]");
		
		return true;
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
			contentType: 'application/json',
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
	
	confirmDialog.init();
	confirmDialog.subscribe({
		confirm: function() {
			submitListings();
//			form.submit();
		}
	});
	
/*	$("#form-btn").click(function(event){
		var listing = listingTable.selectedItems;
		if (listing && listing.length > 0) {
			// collect item ids into form hidden input and separated by comma.
			listing = listingTable.getData();
			form.find("input[name=listings]").val("[" + listing.map(function(item){
				return "{'itemId': '" + item.itemId + "', 'selected': " + (item.checked ? 1 : 0) + "}";
			}).join(",") + "]");
			
			form.submit();
		} else {
			event.preventDefault();
			confirmDialog.confirm(locale.getText('promo.hotsell.zeroSubmitted'));
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
	
	$("#form-btn").click(function(event){
		event.preventDefault();
		var listings = listingTable.selectedItems;
		if (listings && listings.length > 0) {
			previewDialog.show();
			previewDialog.listingTable.setData(listings);
		} else {
			confirmDialog.confirm(locale.getText('promo.hotsell.zeroSubmitted'));
		}
	});
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();	
	
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
});