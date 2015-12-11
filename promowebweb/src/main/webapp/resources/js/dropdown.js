!function($) {
	var defaultConfig = {
		optionCreator: function(value, text) {
			return $("<li class='op' tabindex='0' role='menuitem' op-value='"
					+ value + "'>" + text + "</li>");
		}
	};
	
	var toggle='[data-toggle="dropdown"]', Dropdown = function(element, config) {
		var that = this;
		this.element = $(element);
		this.config = $.extend({}, defaultConfig, config);

		this.selectElement = this.element.find("select");
		this.initOptions = this.element.find("option");
		this.options = this.element.find("option");

		// set initialize values
		this.field = $('<input type="hidden"></input>').attr({
			id : that.selectElement.attr("id"),
			name : that.selectElement.attr("name"),
			value: that.selectElement.val()
		});
		
		if (this.selectElement.length > 0) {
			this.defaultValue = this.currentValue = this.selectElement.val();
			this.defaultText = this.currentText = this.options[this.selectElement.get(0).selectedIndex].text;
		}
		
		this.init(config);
	}
	
	Dropdown.prototype = {
		init : function(config) {
			var self = this;

			// build dropdown begin
			this.dropdown = $('<div class="dropdown">');
			
			this.element.append(this.dropdown);
			this.dropdown.append(this.field);

			this.label = $('<input type="text" readonly data-toggle="dropdown">');
			this.label.attr({
				value: this.currentText,
				disabled: this.selectElement.attr("disabled")
			});
			this.dropdown.append(this.label);
			
			this.caret = $('<i class="dropdown-caret">');
			this.dropdown.append(this.caret);

			if (this.selectElement.length > 0){
				self.setDropdownWidth(this.selectElement.outerWidth());
			} else {
				// 26 is left for caret
				self.setDropdownWidth(this.label.outerWidth() + 26);
			}

			this.menu = $('<ul class="dropdown-menu">');
			this.dropdown.append(this.menu);
			
			this.initOptions.each(function() {
				// 'this' is an option element
				self.insertOption(this.text, this.value);
			});
			// build dropdown end
			
			if (config && config.width) {
				self.setDropdownWidth(config.width);
			}
			
			if (config && config.dropdownItems) {
				self.replaceAll(config.dropdownItems);
			}

			this.delegate();
			this.setDropdownWidth();
			
			this.selectElement.remove();

			if (config && config.disabled){
				this.disable();
			}
		},
		
		setDropdownWidth : function(width) {
			if (width > 0) {
				this.dropdown.width(width);
				this.label.width(width);
			} else {
				var selectWidth = this.selectElement.outerWidth();
				this.dropdown.width(selectWidth);
				this.label.width(selectWidth);
			}
		},

		/**
		 * Attach 'click' and 'keypress' on menu options
		 * 
		 * @returns
		 */
		delegate : function() {
			var self = this;
			self.dropdown.on("click", "li", self.onOptionClick.bind(self));
			self.dropdown.on("keypress", "li", self.onOptionPress.bind(self));
			self.dropdown.on("keyup", self.onKeyUp.bind(self));
			// self.label.on("keypress", self.onInputPress.bind(self));
			// self.label.on("keyup", self.onInputKeyUp.bind(self));
			
			self.label.on("click", function() {
				self.dropdown.toggleClass('open');
				return false; // event.stopPropagation().
			});
			
			self.caret.on("click", function() {
				self.dropdown.toggleClass('open');
				return false; // event.stopPropagation().
			});
			
			this.selectElement.parents("form:first").on("reset", function(){
				// when form is reset, this dropdown also need to be reset.
				self.select(self.defaultValue);
			});
		},
		
		value: function() {
			return this.currentValue;
		},
		
		/**
		 * Select by value
		 * 
		 * @param value
		 * @returns
		 */
		select : function(value, byText) {
			var self = this, isSelected = false;
			if(self.label.is(":disabled")){
				return false;
			}
			
			if (byText) {
				// Check text
				$.each(self.options, function(idx, selector){
					var opt = $(selector);
					if(opt.text() === value){
						opt.trigger("click");
						isSelected = true;
						return false;
					}
				});
			} else {
				// only check value
				$.each(self.options, function(idx, selector){
					var opt = $(selector);
					if(opt.attr("op-value") === value){
						opt.trigger("click");
						isSelected = true;
						return false;
					}
				});
			}
			
			return isSelected;
		},
		
		onKeyUp: function(e) {
			var index = this.options.index(this.options.filter(':focus'));
			if (e.keyCode == 38 && index > 0) index--                                        // up
		    if (e.keyCode == 40 && index < this.options.length - 1) index++                  // down
		    
		    this.options.eq(index).focus();
		},

		/**
		 * trigger onclick if ENTER key is pressed
		 * 
		 * @param ev
		 * @returns
		 */
		onOptionPress : function(ev) {
			var code = ev.keyCode || ev.which;
			if (code == '13') {
				ev.target.click();
			}
		},

		/**
		 * Handle menu options click
		 * 
		 * @param ev
		 * @returns
		 */
		onOptionClick : function(ev) {
			var self = this, config = self.config, option = $(ev.currentTarget), opVal = option
					.attr('op-value'), opText = $.trim(option.text());

			if (opVal === self.currentValue && opText === self.currentText) {
				return;
			}

			self.currentValue = opVal;
			self.currentText = opText;
			self.label.val(opText);
			self.field.val($.trim(opVal));

			if (!self.currentValue || self.currentValue === "") {
				self.label.addClass("dropdown-ph");
			} else {
				self.label.removeClass("dropdown-ph");
			}

			self.dropdown.trigger("change", {value: self.currentValue, text: self.currentText});
			self.menu.html(self.options);
		},
		
		insertBatchOptions: function(options) {
			var self = this;
			options.forEach(function(option){
				var op = self.config.optionCreator.call(self, option.value, option.text);
				self.menu.append(op);
			})
			
			self.options = self.dropdown.find("li.op");
		},

		/**
		 * Insert a menu option at at index or at the end
		 * 
		 * @param text
		 *            (Option Text)
		 * @param value
		 *            (Option Value)
		 * @param index
		 *            (Option Index) - optional, should be array index, like 0,
		 *            1, 2, etc.
		 * @returns
		 */
		insertOption : function(text, value, index) {
			var self = this, optionsLen = self.options.length, op = self.config.optionCreator.call(self, value, text);

			/**
			 * 1. if no index is passed or there are no options, then add an
			 * "LI" to the "UL" 2. if index is 0, it should be first item 3.
			 * insert after the position of index -1
			 */

			if (typeof index === 'undefined' || isNaN(index)
					|| optionsLen === 0) {
				self.menu.append(op);
			} else if (index === 0) {
				self.menu.prepend(op);
			} else {
				var pos = index - 1;
				if (pos < 0)
					pos = 0;
				op.insertAfter(self.options[pos]);
			}

			// New option added so refresh the self.options.
			self.options = self.dropdown.find("li.op");

			// Adjust width
			// self.setDropdownWidth(self.width);
		},
		
		/**
		 * enable dropdown
		 * 
		 * @returns
		 */
		enable : function() {
			var self = this;
			if (!self.label.is(":disabled")) {
				return;
			}
			self.field.removeAttr("disabled");
			self.label.removeAttr("disabled");
		},
		
		/**
		 * enable dropdown
		 * 
		 * @returns
		 */
		disable : function() {
			var self = this;
			if (self.label.is(":disabled")) {
				return;
			}
			self.field.attr("disabled", "disabled");
			self.label.attr("disabled", "disabled");
		},
		
		/**
		 * Remove all options.
		 */
		removeAllOptions : function(){
			this.menu.empty();
		},

		replaceAll : function(items) {			
			this.removeAllOptions();
			this.insertBatchOptions(items);
			
			if (items && items.length > 0) {
				this.currentText = items[0].text;
				this.currentValue = items[0].value;
				this.field.attr("value", this.currentValue);
				this.label.attr("value", this.currentText);
			}
		},

		getCurrentValue: function() {
			return this.currentValue;
		},

		getCurrentText: function() {
			return this.currentText;
		}
	}

	/*
	 * DROPDOWN PLUGIN DEFINITION ==========================
	 */

	var old = $.fn.dropdown

	$.fn.dropdown = function(option) {
		var args = Array.prototype.slice.call(arguments, 1);
		return this.each(function() {
			var $this = $(this), data = $this.data('dropdown');
			if (!data) {
				if (typeof option == 'object') {
					$this.data('dropdown', (data = new Dropdown(this, option)));
				} else {
					$this.data('dropdown', (data = new Dropdown(this)));
				}
			}
				
			if (typeof option == 'string') {
				// call dropdown method
				data[option].apply(data, args);
			}				
		});
	}

	$.fn.dropdown.Constructor = Dropdown

	/*
	 * DROPDOWN NO CONFLICT ====================
	 */

	$.fn.dropdown.noConflict = function() {
		$.fn.dropdown = old
		return this
	}
	

	function getParent($this) {
		var selector = $this.attr('data-target'), $parent;

		if (!selector) {
			selector = $this.attr('href');
			selector = selector && /#/.test(selector)
					&& selector.replace(/.*(?=#[^\s]*$)/, '') // strip for ie7
		}

		$parent = $(selector);
		$parent.length || ($parent = $this.parent());

		return $parent;
	}

	function clearMenus() {
		$(toggle).each(function() {
			getParent($(this)).removeClass('open');
		})
	}
	
	$(document).on('click.dropdown touchstart.dropdown', clearMenus)
    	.on('click.dropdown touchstart.dropdown', '.dropdown form', function (e) { e.stopPropagation() })
    	.on('touchstart.dropdown', '.dropdown-menu', function (e) { e.stopPropagation() });
}(window.jQuery);
