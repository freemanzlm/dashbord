(function(namespace) {
	var Dialog = namespace.Dialog;
	
	var ConfirmDialog = function() {};

	ConfirmDialog.prototype = new namespace.Widget();

	$.extend(ConfirmDialog.prototype, {
		init : function(cfg) {
			this.config = cfg;
			this.dialog = new Dialog();
			this.dialog.init({id: "confirm-dialog", zIndex: 20000});
			
			this.delegate();
		},

		delegate : function() {
			var self = this;
			this.dialog.wrapper.find(".confirm").click(function(){
				self.publish("confirm");
				self.dialog.hide();
			});
			
			this.dialog.wrapper.find(".cancel").click(function(){
				self.publish("cancel");
				self.dialog.hide();
			});
		},
		
		confirm: function(text) {
			this.dialog.wrapper.find(".dialog-content").html(text);
			this.dialog.show();
		}

	});
	
	namespace.ConfirmDialog = ConfirmDialog;
})(BizReport = BizReport || {});