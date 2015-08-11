(function(namespace) {
	var Dialog = function() {};
	var posManager = namespace.posManager;
	var MaskManager = namespace.MaskManager;

	Dialog.prototype = new namespace.Widget();
	
	var defaultConfig = {
		zIndex: 10000, // mask layer's z-index
		width: '',
		title: 'Title',
		wrapper: '<div class="dialog"><a class="close"></a><div class="dialog-header"><h2>Title</h2></div><div class="dialog-pane"><div class="dialog-body"></div></div></div>'
	};

	$.extend(Dialog.prototype, {
		init : function(cfg) {
			this.config = $.extend({}, defaultConfig, cfg);
			
			if (cfg.id) {
				this.wrapper = $("#" + cfg.id);
			} else {
				this.wrapper = $(this.config.wrapper);
				document.body.appendChild(this.wrapper.get(0));
				
				this.setTitle(this.config.title);
			}
			
			this.anchor = this.wrapper.find(".close");
			
			/* Configuration item zIndex is use to set mask's z-index. Object maskManager will change 
			 * overlay's zIndex to (this.config.zIndex + 10), so, you don't need to set z-index for overlay. */
			this.maskManager = new MaskManager();
			this.maskManager.init({zIndex: this.config.zIndex});
			
			this.wrapper.width(this.config.width);
			
			this.delegate();
			this.isShow = false;
		},

		delegate : function() {
			var self = this;
			this.anchor.click(function(){
				self.hide();
			});
			
			this.wrapper.find(".cancel").click(function(){
				self.hide();
			});
		},
		
		setTitle: function(title) {
			this.wrapper.find(".dialog-header h2").html(title);
		},
		
		getTitle: function() {
			return this.wrapper.find(".dialog-header h2").html();
		},

		show : function(pos) {
			var self = this;			

			this.wrapper.addClass("open");
			this.maskManager.show(this.wrapper);
			if (pos) {
				posManager.customize(this.wrapper, pos.top, pos.left);
			} else {
				posManager.center(this.wrapper);
			}
			
//			$(document).mouseup(function(event) {
//				if (self.isShow && $(event.target).parents("#" + self.config.id).length < 1) {
//					self.hide();
//				}
//			});
			
			this.isShow = true;
			this.publish("show");
		},

		hide : function() {
			this.wrapper.removeClass("open");
			this.maskManager.hide();
			this.isShow = false;
		}
	});

	namespace.Dialog = Dialog;
})(BizReport = BizReport || {});