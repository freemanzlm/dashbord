(function(namespace, $) {
	var Popup = function(element, options) {
		this.init(element, options);
	};

	Popup.prototype = new cbt.Widget();
	
	var defaults = {
		template: '<div class="popup"><div class="anchor"></div><div class="popup-content"></div></div>',
		trigger: 'hover',
		delay: 0,
		html: false,
		wrapper: null
	};

	$.extend(Popup.prototype, {
		init : function(element, options) {
			this.$element = $(element);
			this.options = $.extend({}, defaults, options);
			
			if (options.wrapper) {
				this.$wrapper = $("#" + this.options.wrapper);
			} else {
				this.$wrapper = $(this.options.template);
			}
			
			this.$anchor = this.$wrapper.find(".anchor");
			
			this.delegate();
			this.isShow = false;
		},

		delegate : function() {
			var that = this;
			if (this.options.trigger == 'click') {
				this.$element.bind('click', function(){
					that.show();
				});
			} else if (this.options.trigger != 'mannual') {
				eventIn = this.options.trigger == 'hover' ? 'mouseenter' : 'focus';
				eventOut = this.options.trigger == 'hover' ? 'mouseleave' : 'blur';
				this.$element.bind(eventIn, this.enter);
				this.$element.bind(eventOut, this.leave);
			}
		},
		
		enter: function(e) {
			$(this).data("popup").show(); // this is the element that has popover.
		},

		leave: function (e) {
			$(this).data("popup").hide(); // this is the element htat has popover.
		},
		
		setContent: function() {
			if (this.options.html) {
				if (typeof this.options.html == "string") {
					this.$wrapper.find(".popup-content").html(this.options.html);
				} else if (typeof this.options.html == "function") {
					this.$wrapper.find(".popup-content").html(this.options.html());
				} else {
					// selector
					this.$wrapper.find(".popup-content").html($(this.options.html));
				}
				
			}
		},

		show : function() {
			var that = this;
			
			this.setContent();

			this.contentWrapper = this.$wrapper.find(".popup-content");
			if (this.contentWrapper.html().length <= 0) {
				// no popup content
				return;
			}
			
			$(document.body).append(that.$wrapper.removeClass("left right top bottom"));
			
			this.$wrapper.addClass("open").css({left: 0, top: 0});			
			
			var panelHeight = this.$wrapper.outerHeight();
			var panelWidth = this.$wrapper.outerWidth();
			var trigger_height = this.$element.outerHeight();
			var trigger_width = this.$element.outerWidth();
			var trigger_pos = namespace.util.getPositionInPage(this.$element.get(0));
			var page_size = namespace.util.getPageSize(document);
			
			if (trigger_pos.left + trigger_width + panelWidth >= page_size.width) {
				if (trigger_pos.left - panelWidth > 0) {
					this.options.placement = "left";
				}
			} else if (trigger_pos.left - panelWidth <= 0) {
				if (trigger_pos.left + trigger_width + panelWidth < page_size.width) {
					this.options.placement = "right";
				}
			}

			if (!(this.options.placement == "right" || this.options.placement == "left")) {
				if (trigger_pos.top - panelHeight > 0) {
					this.options.placement = "top";
				} else if (trigger_pos.top - panelHeight <= 0) {
					this.options.placement = "bottom";
				}
			}
			
			this.$wrapper.addClass(this.options.placement);
			
			var left = 0, top = 0, translate = 0;
			
			/**
			 * The size of right and left anchor is 13x24, top and bottom anchor is 24 * 13.
			 */
			switch(this.options.placement) {
			case "bottom":
				// left must great than or equal to 0.
				left = trigger_pos.left - panelWidth / 2 + trigger_width / 2;
				translate = left < 0 ? (0 - left) : 0; 
				this.$wrapper.css({top: trigger_pos.top + trigger_height + 12 + "px", left: (left < 0 ? 0 : left) + "px"});
				this.$anchor.css({top: "-12px", left: panelWidth / 2 - 12 - translate + "px"});
				break;
			case "right":
				// top must great than or equal to 0.
				top = trigger_pos.top + trigger_height / 2 - panelHeight / 2;
				translate = top < 0 ? 0 - top : 0;
				this.$wrapper.css({top: (top < 0 ? 0 : top) + "px", left: trigger_pos.left + trigger_width + 12 + "px"});
				this.$anchor.css({top: panelHeight / 2 - 12 - translate + "px", left: - 12 + "px"});
				break;
			case "left":
				// top must great than or equal to 0.
				top = trigger_pos.top + trigger_height / 2 - panelHeight / 2;
				translate = top < 0 ? 0 - top : 0;
				this.$wrapper.css({top: (top < 0 ? 0 : top) + "px", left: trigger_pos.left - panelWidth - 12 + "px"});
				this.$anchor.css({top: panelHeight / 2 - 12 - translate + "px", left: panelWidth - 2 + "px"});
				break;
			case "top":
				// left must great than or equal to 0.
				left = trigger_pos.left - panelWidth / 2 + trigger_width / 2;
				translate = left < 0 ? (0 - left) : 0; 
				this.$wrapper.css({top: trigger_pos.top - panelHeight - 12 + "px", left: (left < 0 ? 0 : left) + "px"});
				this.$anchor.css({top: panelHeight - 2 + "px", left: panelWidth / 2 - 12 - translate + "px"});
			}
			
//			$(document).mouseup(function(event) {
//				if (that.isShow && $(event.target).parents(".popup").get(0) != that.$wrapper.get(0)) {
//					that.hide();
//				}
//			});
			
			this.$wrapper.mouseleave(function(){
				that.hide();
			});
			
			this.isShow = true;
		},

		hide : function() {
			this.$wrapper.removeClass("open");
			this.isShow = false;
		}
	});

	namespace.Popup = Popup;
	
	$.fn.popup = function ( option ) {
		return this.each(function () {
			var $this = $(this)
			, data = $this.data('popup')
			, options = typeof option == 'object' && option;
			
			if (!data) $this.data('popup', (data = new Popup(this, options)));
			if (typeof option == 'string') data[option]();
		});
	};
})(BizReport = BizReport || {}, jQuery);


// $("a.question").popup({"trigger": "hover", html: "ָ��listing���ع������������ת���ʣ�ת����=�����/�ع��������ڵ�ǰeBayվ�㣨US��UK��DE��AU����L3�����ƽ��ˮƽ��+10%������ͬʱ�ع������ڵ�ǰeBayվ���L3����ƽ��ˮƽ��ƽ��ˮƽ=��L3�������ع���/��L3 live listing������"});