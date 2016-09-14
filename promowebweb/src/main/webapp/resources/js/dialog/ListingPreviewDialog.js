(function(namespace) {
	
	var Dialog = cbt.Dialog;
	var ListingTable = namespace.ListingTable;
	
	var ListingPreviewDialog = function(element, option) {
		if (element || option){
			this.init(element, option);
		}
	};
	
	ListingPreviewDialog.prototype = {
		init : function(element, cfg) {
			var that = this;
			this._super(element, cfg);
			
			var listingTable = this.listingTable = new ListingTable();
			listingTable.init({
				dataTableConfig: {
					tableId: "listing-preview-table",
					customTableConfig: {
						'sScrollY': "400",
						'iDisplayLength': 10,
						'columns':pageData && pageData.previewColumns
					}
				}});
			
			listingTable.subscribe({
				dataupdated: function() {
					that.resizeUpdate();
				}
			});
		},

		delegate : function() {
			var self = this;
			
			this._super();
			
			this.wrapper.find(".ok").click(function(){
				self.publish("ok");
				self.close();
			});
		},
		
		show: function() {
			this._super();
			this.listingTable.initDataTable();
		}

	};
	
	ListingPreviewDialog.inheritFrom(Dialog);


	namespace.ListingPreviewDialog = ListingPreviewDialog;
})(BizReport = BizReport || {});