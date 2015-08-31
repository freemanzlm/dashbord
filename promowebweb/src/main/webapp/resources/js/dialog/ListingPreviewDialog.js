(function(namespace) {
	var Dialog = namespace.Dialog, util = namespace.util;
	var ListingTable = namespace.HotsellListingTable || namespace.DealsListingTable;
	
	var ListingPreviewDialog = function() {};

	ListingPreviewDialog.prototype = new namespace.Widget();

	$.extend(ListingPreviewDialog.prototype, {
		init : function(cfg) {
			this.config = cfg;
			this.dialog = new Dialog();
			this.dialog.init({id: "listing-preview-dialog", zIndex: 20000, width: 850, body: {
				style: {
					'max-height': "530px",
					overflow: 'auto'
				}
			}});
			this.defaultTitle = this.dialog.getTitle();
			
			var listingTable = this.listingTable = new ListingTable();
			listingTable.subscribe({
				initialized: function() {
					listingTable.hideCheckbox();
					listingTable.hideStateColumn();
				}
			}, listingTable);
			listingTable.init({
				dataTableConfig: {
					tableId: "listing-preview-table",
					customTableConfig: {
						'sScrollY': "400",
						'iDisplayLength': 10
					}
				}});
			listingTable.initDataTable();
			
			this.delegate();
		},

		delegate : function() {
			var self = this;
			this.dialog.wrapper.find(".ok").click(function(){
				self.publish("ok");
				self.dialog.hide();
			});
		},
		
		show: function(items, title) {
			if (title) {
				this.dialog.setTitle(title);
				this.titleChanged = true;
			} else if (this.titleChanged) {
				this.dialog.setTitle(this.defaultTitle);
				this.titleChanged = false;
			}
			
			this.dialog.show();
		},
		
		close: function() {
			this.dialog.hide();
		}

	});

	namespace.ListingPreviewDialog = ListingPreviewDialog;
})(BizReport = BizReport || {});