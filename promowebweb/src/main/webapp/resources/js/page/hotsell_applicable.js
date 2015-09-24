$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var alertDialog = BizReport.alertDialog;
	var locale = BizReport.locale;
	
	var listingCountJ = $(".my-listing h3 small span"), form = $("#listing-form");
	
	var listingTable = new HotsellListingTable();
	listingTable.subscribe({
		initialized: function() {
			listingTable.hideStateColumn();
			if (!(pageData && pageData.expired)) {
				// display selected items count when promotion application deadline is not expired.
				listingCountJ.text(this.selectedItems.length);
			}
		},
		selectChange: function(){
			listingCountJ.text(this.selectedItems.length);
		}
	}, listingTable);
	listingTable.init({
		dataTableConfig: {
			tableId: "hotsell-listing-table",
			customTableConfig: {
				asStripeClasses: ['selectable'],
				aoColumnDefs: [{bVisible: true}]
			}
		}});
	listingTable.update({promoId:pageData && pageData.promoId});
	
	function submitListings() {
		$(document.body).isLoading({text: locale.getText('promo.request.sending'), position: "overlay"});
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
				$(document.body).isLoading('hide');
				if (json && json.status) {
					location.reload();
				} else {
					alertDialog.alert(locale.getText('promo.request.fail'));
				}
			},
			error: function(){
				$(document.body).isLoading('hide');
				alertDialog.alert(locale.getText('promo.request.fail'));
			}
		});
	}
	
	var formBtn = document.getElementById("form-btn");
	var acceptCheckbox = $("#accept").change(function(){
		if (this.checked) {
			formBtn.removeAttribute("disabled");
		} else {
			formBtn.setAttribute("disabled", "disabled");
		}
	});
	
	// for test
	var ListingPreviewDialog = BizReport.ListingPreviewDialog;
	var previewDialog = new ListingPreviewDialog();
	previewDialog.init();
	previewDialog.subscribe({
		ok: function(){
			submitListings();
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
		"ok": function() {
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