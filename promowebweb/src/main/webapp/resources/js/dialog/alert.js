////////////////////////////////////////////////////////////
// AlertDialog inherits from Dialog. So, change Dialog behavior will affect AlertDialog.
// 
// Javascript dependencies:
// ../common/extension.js
// ../mask/mask.js
// ../common/posManager.js
// dialog.js
//
// @copyright lyan2@ebay.com, linus.yan@hotmail.com
////////////////////////////////////////////////////////////
(function() {

	var namespace = window.cbt = window.cbt || {};

	var Dialog = namespace.Dialog;
	
	var AlertDialog = function(element, option) {
		if (element || option){
			this.init(element, option);
		}
	};

	AlertDialog.prototype = {
		init : function(element, cfg) {
			// AlertDialog inherits from Dialog.
			this._super(element, cfg);
			this.defaultTitle = this.getTitle();
		},

		delegate : function() {
			var self = this;

			this._super();

			this.wrapper.find(".ok").click(function(){
				self.publish && self.publish("ok");
				self.close();
			});
		},
		
		alert: function(text, title) {
			if (title) {
				this.setTitle(title);
				this.titleChanged = true;
			} else if (this.titleChanged) {
				this.setTitle(this.defaultTitle);
			}

			this.setContent(text);

			this.show();
		}
	};

	namespace.AlertDialog = AlertDialog;
	AlertDialog.inheritFrom(Dialog);

	var alertDialog;

	$(function(){
		alertDialog = new AlertDialog(null, {wrapper: "#alert-dialog", zIndex: 20000, trigger: 'manual', clazz:"dialog dialog-alert"});
	});
	
	// global alert method
	namespace.alert = function(text, title){
		alertDialog && alertDialog.alert(text, title);
	}

})();