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
			
			this.dialog.body.scroll(function(){
				// allow 2 px deviation
				if (this.scrollTop + this.clientHeight >= this.scrollHeight - 2) {
					self.publish("scrollEnd");
				}
			});
		},
		
		show: function(text, title) {
			var that = this;
			if (title) {
				this.dialog.setTitle(title);
				this.titleChanged = true;
			} else if (this.titleChanged) {
				this.dialog.setTitle(this.defaultTitle);
				this.titleChanged = false;
			}			
			
			this.dialog.wrapper.find(".dialog-body").html(text);
			this.dialog.show();
			
			var frame = this.dialog.wrapper.find("iframe");
			var iframe = frame[0];
			frame.width(this.dialog.body[0].scrollWidth);
			
			function setFrameSize() {
				// remove iframe scrollbars.
				if (iframe.contentDocument.body && iframe.contentDocument.body.innerHTML.length > 0) {
					var docEle = iframe.contentDocument.documentElement;
					frame.height(docEle.scrollHeight);
					if (docEle.scrollWidth > that.dialog.wrapper.width()) {
						frame.width(docEle.scrollWidth);
					}
				} else {
					setTimeout(setFrameSize, 100);
				}
			}
			
			setFrameSize();
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