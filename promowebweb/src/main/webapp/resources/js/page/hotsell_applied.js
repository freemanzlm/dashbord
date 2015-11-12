$(function(){
	var HotsellListingTable = BizReport.HotsellListingTable;
	var termsDialog = BizReport.termsDialog;
	var alertDialog = BizReport.alertDialog;
	var local = BizReport.local;	
	var confirmDialog = new BizReport.ConfirmDialog();
	
	var customTableConfig = pageData && pageData.expired ? {} : {
		asStripeClasses: ['selectable'],
		aoColumnDefs: [{bVisible: true}]
	};
	
	var listingCountJ = $(".my-listing h3 small span");
	var acceptCheckbox = document.getElementById('accept');
	var formBtn = document.getElementById("form-btn");
	
	var acceptPopup = $(acceptCheckbox).parent().each(function(){
		$(this).popup({"trigger": "mannual", html: this.title});
	});	
	
	var listingTable = new HotsellListingTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "hotsell-listing-table",
			customTableConfig: customTableConfig
		}});
	listingTable.subscribe({
		selectChange: function(){
			listingCountJ.text(this.selectedItems.length);
		}
	}, listingTable);
	listingTable.update({promoId:pageData && pageData.promoId});
	
	var form = $("#listing-form");
	
	function submitListings() {
		$(document.body).isLoading({text: local.getText('promo.request.sending'), position: "overlay"});
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
					alertDialog.alert(local.getText('promo.request.fail'));
				}
			},
			error: function(){
				$(document.body).isLoading('hide');
				alertDialog.alert(local.getText('promo.request.fail'));
			}
		});
	}
	
	confirmDialog.init();
	confirmDialog.subscribe({
		confirm: function() {
			submitListings();
		}
	});
	
	var ListingPreviewDialog = BizReport.ListingPreviewDialog;
	var previewDialog = new ListingPreviewDialog();
	previewDialog.init();
	previewDialog.subscribe({
		ok: function(){
			submitListings();
		}
	});
	

	$(formBtn).click(function(event){
		event.preventDefault();
		
		if (!acceptCheckbox.checked) {
			acceptPopup.popup('show');
			return false;
		}
		
		var listings = listingTable.selectedItems;
		if (listings && listings.length > 0) {
			previewDialog.show();
			previewDialog.listingTable.setData(listings);
		} else {
			confirmDialog.confirm(local.getText('promo.hotsell.zeroSubmitted'));
		}
	});
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();	
	
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
});