(function() {
	var namespace = window.cbt = window.cbt || {};
	
	var Dialog = namespace.Dialog;
		
	var TermsDialog = function(element, option) {
		if (element || option){
			this.init(element, option);
		}
	};
	
	TermsDialog.prototype = {
		init : function(element, cfg) {
			// AlertDialog inherits from Dialog.
			this._super(element, cfg);
		},

		delegate : function() {
			var self = this;

			this._super();

			this.wrapper.find(".ok").click(function(){
				self.publish && self.publish("ok");
				self.close();
			});
			
			/*this.wrapper.find(".dialog-body").scroll(function(){
				// allow 2 px deviation
				if (this.scrollTop + this.clientHeight >= this.scrollHeight - 2) {
					self.publish("scrollEnd");
				}
			});*/
		}
	};

	TermsDialog.inheritFrom(Dialog);
	
	$(function(){
		namespace.termsDialog = new TermsDialog(null, {wrapper: '#terms-dialog', trigger: 'manual', zIndex: 20000, width: 850, body: {
				style: {
				'max-height': "400px",
				overflow: 'auto'
			}
		}});
		
	});	
	
})();