(function(namespace) {
	var Dialog = namespace.Dialog;
	
	var AlertDialog = function() {};

	AlertDialog.prototype = new namespace.Widget();

	$.extend(AlertDialog.prototype, {
		init : function(cfg) {
			this.config = cfg;
			this.dialog = new Dialog();
			this.dialog.init({id: "alert-dialog", zIndex: 20000});
			this.defaultTitle = this.dialog.getTitle();
			
			this.delegate();
		},

		delegate : function() {
			var self = this;
			this.dialog.wrapper.find(".ok").click(function(){
				self.publish("ok");
				self.dialog.hide();
			});
		},
		
		alert: function(text, title) {
			if (title) {
				this.dialog.setTitle(title);
				this.titleChanged = true;
			} else if (this.titleChanged) {
				this.dialog.setTitle(this.defaultTitle);
				this.titleChanged = false;
			}
			
			this.dialog.wrapper.find(".alert-content").html(text);
			this.dialog.show();
		},
		
		close: function() {
			this.dialog.hide();
		}

	});

	namespace.alertDialog = new AlertDialog();
	
	$(function(){
		namespace.alertDialog.init();
	});
})(BizReport = BizReport || {});