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
			if (this.selectedItems.length > 0) {
				formBtn.removeAttribute('disabled');
			} else {
				formBtn.setAttribute('disabled', 'disabled');
			}
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "deals-listing-table",
			customTableConfig: {
				asStripeClasses: ['selectable'],
				aoColumnDefs: [{bVisible: true}]
			}
		}});
	listingTable.update({promoId:pageData.promoId});
	
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
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();
});