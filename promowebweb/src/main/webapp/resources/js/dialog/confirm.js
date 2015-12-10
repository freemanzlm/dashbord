////////////////////////////////////////////////////////////
// ConfirmDialog inherits from Dialog. So, change Dialog behavior will affect ConfirmDialog.
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

	var Dialog = cbt.Dialog;
	var _confirmCallback, _cancelCallback;

	var ConfirmDialog = function(element, option) {
		if (element || option){
			this.init(element, option);
		}
	};

	ConfirmDialog.prototype = {
		init : function(element, cfg) {
			this._super(element, cfg);
		},

		delegate : function() {
			var self = this;
			this._super();
			this.wrapper.find(".confirm").click(function(){
				_confirmCallback && _confirmCallback();
				_confirmCallback = null; // don't cache the callback.
				self.close();
			});
			
			this.wrapper.find(".cancel").click(function(){
				_cancelCallback && _cancelCallback();
				_cancelCallback = null; // don't cache the callback.
				self.close();
			});
		},
		
		confirm: function(text) {
			this.setContent(text);
			this.show();
		}

	};
	
	namespace.ConfirmDialog = ConfirmDialog;
	ConfirmDialog.inheritFrom(Dialog);

	var confirmDialog;

	$(function(){
		confirmDialog = new ConfirmDialog(null, {wrapper: "#confirm-dialog", zIndex: 20000, trigger: 'manual', clazz:"dialog dialog-confirm"});
	});
	
	// global alert method
	namespace.confirm = function(text,confirmCallback, cancelCallback){
		_confirmCallback = confirmCallback;
		_cancelCallback = cancelCallback;

		confirmDialog && confirmDialog.confirm(text);
	}
})();