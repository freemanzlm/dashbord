$(function(){
	var DealsListingTable = BizReport.DealsListingTable;
	var local = BizReport.local;
	
	var listingCountJ = $(".my-listing h3 small span");
	var formBtn = document.getElementById("form-btn");
	var acceptCheckbox = document.getElementById('accept');
	var form = $("#listing-form");
	var acceptPopup = $(acceptCheckbox).parent().each(function(){
		$(this).popup({"trigger": "mannual", html: this.title});
	});	
	
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
			tableId: "deals-listing-table",
			customTableConfig: {
				asStripeClasses: ['selectable'],
				aoColumnDefs: [{bVisible: true}]
			}
		}});
	listingTable.update({promoId:pageData && pageData.promoId});
	
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
					cbt.alert(local.getText('promo.request.fail'));
				}
			},
			error: function(){
				$(document.body).isLoading('hide');
				cbt.alert(local.getText('promo.request.fail'));
			}
		});
	}
	
	var ListingPreviewDialog = BizReport.ListingPreviewDialog;
	var previewDialog = new ListingPreviewDialog(null, {wrapper: "#listing-preview-dialog", zIndex: 20000, width: 850, body: {
		style: {
			'max-height': "530px",
			overflow: 'auto'
		}
	}});
	
	previewDialog.subscribe({
		ok: function(){
			submitListings();
//			form.submit();
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
			cbt.alert(local.getText('promo.hotsell.applyCondition'));
		}
	});
	
	var termsDialog = cbt.termsDialog;
	termsDialog.subscribe({
		"ok": function() {
			acceptCheckbox.removeAttribute("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});	
	
	// prevent form remembering while user using history.back().
	form.length && form[0].reset();
});