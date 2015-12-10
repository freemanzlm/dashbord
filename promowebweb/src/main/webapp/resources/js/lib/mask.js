(function($) {
	var namespace = window.cbt = window.cbt || {};

	var defaultMaskConfig = {
		clazz : 'mask',
		alpha : 0.6,
		color : "#000",
		zIndex : 10000
	};

	var Mask = function(config) {
		this.init(config);
	};

	Mask.prototype = {
		init : function(config) {
			this.config = $.extend({}, defaultMaskConfig, config);
			this.mask = $("<div class='" + this.config.clazz + "'><div>");
			$("body").append(this.mask);
		},
		mask : null,
		show : function(panel) {
			var color = this.config.color ? this.config.color : "#000";
			var alpha = this.config.alpha
					&& $.type(this.config.alpha) === "number" ? this.config.alpha
					: 0.6;
			var zIndex = this.config.zIndex
					&& $.type(this.config.zIndex) === "number" ? this.config.zIndex
					: 10000;
			this.mask.css({
				"background-color" : color,
				"z-index" : zIndex,
				"opacity" : alpha,
				"filter" : "alpha(opacity=" + (alpha * 100) + ")",
				"display" : "block"
			});
			panel.css("z-index", zIndex + 10);
		},
		hide : function(trigger, isEnter) {
			this.mask.css("display", "none");
			if (isEnter == 'true') {
				trigger.focus();
			}
		}
	};

	namespace.Mask = Mask;
})(window.jQuery);