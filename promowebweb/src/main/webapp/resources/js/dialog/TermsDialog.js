(function(namespace) {
	var Dialog = namespace.Dialog, util = namespace.util;
	
	var TermsDialog = function() {};

	TermsDialog.prototype = new namespace.Widget();

	$.extend(TermsDialog.prototype, {
		init : function(cfg) {
			this.config = cfg;
			this.dialog = new Dialog();
			this.dialog.init({id: "terms-dialog", zIndex: 20000, width: 850, body: {
				style: {
					'max-height': "400px",
					overflow: 'auto'
				}
			}});
			this.defaultTitle = this.dialog.getTitle();
			
			this.delegate();
		},

		delegate : function() {
			var self = this;
			this.dialog.wrapper.find(".ok").click(function(){
				self.publish("ok");
				self.dialog.hide();
			});
			
			var frame = this.dialog.wrapper.find("iframe").on("load", function(){
				// if there is iframe in dialog, don't use iframe's scrollbar.
				var iframe = this;
				function setFrameSize() {
					if (iframe.contentDocument.body && iframe.contentDocument.body.innerHTML.length > 0) {
						frame.height(iframe.contentDocument.documentElement.scrollHeight);
						frame.width(iframe.contentDocument.documentElement.scrollWidth);
					} else {
						setTimeout(setFrameSize, 100);
					}
				}
				
				setFrameSize();
			});
			
			this.dialog.body.scroll(function(){
				// allow 2 px deviation
				if (this.scrollTop + this.clientHeight >= this.scrollHeight - 2) {
					self.publish("scrollEnd");
				}
			});
		},
		
		show: function(text, title) {
			if (title) {
				this.dialog.setTitle(title);
				this.titleChanged = true;
			} else if (this.titleChanged) {
				this.dialog.setTitle(this.defaultTitle);
				this.titleChanged = false;
			}
			
			this.dialog.wrapper.find(".dialog-body").html(text);
			this.dialog.show();
		},
		
		close: function() {
			this.dialog.hide();
		}

	});

	namespace.termsDialog = new TermsDialog();
	
	$(function(){
		namespace.termsDialog.init();
	});
})(BizReport = BizReport || {});