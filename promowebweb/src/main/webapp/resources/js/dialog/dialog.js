(function() {
	var namespace = window.cbt = window.cbt || {};

	var Dialog = function(element, option) {
		// prevent wrong usage of function.inheritFrom method.
		if (element || option){
			this.init(element, option);
		}
	};
	var posManager = namespace.posManager;
	var Mask = namespace.Mask;

	Dialog.prototype = namespace.Widget ? new namespace.Widget() : {};
	
	var defaultConfig = {
		body: {
			style: {
				'max-height': "400px"
			}
		},
		bCloseOnOut: false,
		clazz: 'dialog',
		content: 'default content',
		id: '', // HTML element id
		modal: true, // has mask or not,
		title: 'Title',
		trigger: 'manual', // auto or manual
		width: '', // dialog width
		wrapper: '<div><a class="close"></a><div class="dialog-header"><h2 class="dialog-title">Dialog Title</h2></div><div class="dialog-pane"><div class="dialog-body"></div></div></div>', // selector or html template
		zIndex: 10000 // mask layer's z-index,
	};

	$.extend(Dialog.prototype, {
		init : function(element, cfg) {
			this.config = $.extend({}, defaultConfig, cfg);

			if (cfg && cfg.wrapper)	{
				this.wrapper = $(cfg.wrapper);
			} else {
				this.wrapper = $(this.config.wrapper);
				this.setTitle(this.config.title);
				document.body.appendChild(this.wrapper.get(0));
			}

			this.config.id && this.wrapper.attr({id: this.config.id});
			this.wrapper.addClass(this.config.clazz);
			
			if (element){
				this.element = $(element);
				this.setContent(element);
			} else if (cfg.wrapper == undefined) {
				this.setContent(this.config.content);
			}

			this.anchor = this.wrapper.find(".close");
			
			/* Configuration item zIndex is use to set mask's z-index. Object maskManager will change 
			 * overlay's zIndex to (this.config.zIndex + 10), so, you don't need to set z-index for overlay. */
			if (this.config.modal){
				this.mask = new Mask({zIndex: this.config.zIndex});
			}
			
			this.config.width && this.wrapper.width(this.config.width);
			this.config.body.style && this.wrapper.find(".dialog-body").css(this.config.body.style);
			
			this.delegate();
			this.isShow = false;

			if (this.config.trigger == 'auto'){
				this.show();
			}
		},

		delegate : function() {
			var self = this;
			this.anchor.click(function(){
				self.close();
			});
			
			this.wrapper.find(".cancel, .close").click(function(){
				self.close();
			});
			
			$(document).keyup(function(e){
				// "ESC" button is pressed.
				if (e.keyCode == 27){
					self.isShow && self.close();
				}
			});

			if (self.config.bCloseOnOut){
				$(document).mouseup(function(event) {
					if (!$.contains(self.wrapper.get(0), event.target))	{
						self.close();
					}
				});
			}
		},
		
		setTitle: function(title) {
			this.wrapper.find(".dialog-title").html(title);
		},

		getTitle: function() {
			return this.wrapper.find(".dialog-title").html();
		},

		setContent: function(content) {
			this.wrapper.find(".dialog-body").html(content);
		},

		show : function(pos) {
			var self = this;			

			this.wrapper.addClass("open");
			this.mask && this.mask.show(this.wrapper);

			if (pos) {
				posManager.customize(this.wrapper, pos.top, pos.left);
			} else {
				posManager.center(this.wrapper);
			}
			
			this.isShow = true;
			this.element && this.element.trigger('show');
			this.publish && this.publish('show');
		},

		close : function() {
			this.wrapper.removeClass("open");
			this.mask && this.mask.hide();
			this.isShow = false;

			this.element && this.element.trigger('close');
			this.publish && this.publish('close');
		},
		
		resizeUpdate: function() {
			posManager.center(this.wrapper);
		}
	});

	/*
	 * DIALOG PLUGIN DEFINITION ==========================
	 */

	var old = $.fn.dialog;

	$.fn.dialog = function(option) {
		var args = Array.prototype.slice.call(arguments, 1);
		return this.each(function() {
			var $this = $(this), data = $this.data('dialog');
			if (!data) {
				if (typeof option == 'object') {
					if (!option.trigger){
						option.trigger = "auto";
					}
					$this.data('dialog', (data = new Dialog(this, option)));					
				} else {
					$this.data('dialog', (data = new Dialog(this, {trigger: "auto"})));
				}
			}
			
			if (typeof option == 'string') {
				// call method
				data[option].apply(data, args);
			} else {
				// when dialog is initialzed, show the dialog immediately.
				if (data.config.trigger == 'auto'){
					data['show'].apply(data, args);
				}
			}				
		})
	}

	$.fn.dialog.Constructor = Dialog

	$.fn.dialog.noConflict = function() {
		$.fn.dialog = old;
		return this;
	}

	namespace.Dialog = Dialog;
})();