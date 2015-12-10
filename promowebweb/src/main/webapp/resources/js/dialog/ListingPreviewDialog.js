(function(namespace) {
	
	var Dialog = cbt.Dialog;
	var ListingTable = namespace.HotsellListingTable || namespace.DealsListingTable;
	
	var ListingPreviewDialog = function(element, option) {
		if (element || option){
			this.init(element, option);
		}
	};
	
	ListingPreviewDialog.prototype = {
		init : function(element, cfg) {
			this._super(element, cfg);
			
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
		},

		delegate : function() {
			var self = this;
			
			this._super();
			
			this.wrapper.find(".ok").click(function(){
				self.publish("ok");
				self.close();
			});
		}

	};
	
	ListingPreviewDialog.inheritFrom(Dialog);


	namespace.ListingPreviewDialog = ListingPreviewDialog;
})(BizReport = BizReport || {});