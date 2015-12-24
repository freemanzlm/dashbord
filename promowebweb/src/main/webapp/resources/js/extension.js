///////////////////////////////////////////////////////////////////////
// Custom extension which is not in ecma.
//
// @copyright lyan2@ebay.com, linus.yan@hotmail.com
///////////////////////////////////////////////////////////////////////


/////////////////////////////////////////////////////////////////////////////////////////////
// Function
/////////////////////////////////////////////////////////////////////////////////////////////

/*
Class multi-inheritance implementation and demo. 

var A = function() {
	this.hello = function() {
		console.log("hello, I'm A");
	}
};

var B = function() {};

B.prototype = {
	hello : function() {
		this._super();

		console.log("hello, I'm B");
	}
};

B.extend(A);

var C = function() {};

C.prototype = {
	hello : function() {
		this._super();

		console.log("hello, I'm C");
	}
};



C.extend(B);

var b = new B();
var c = new C();

//b.hello();
c.hello();
 */
(function() {
	Function.prototype.inheritFrom = function(baseClass) {

		// this is a function object.
		var oldPrototype = this.prototype, newPrototype = {}, _super = new baseClass();

		//inherits all properties and methods.
		for ( var name in _super) {
			newPrototype[name] = _super[name];
		}

		for ( var name in oldPrototype) {
			// only override properties in this new Class.
			if (oldPrototype.hasOwnProperty(name)) {
				// only function need _super.
				if (typeof oldPrototype[name] == "function" && typeof _super[name] == "function") {
					newPrototype[name] =  (function(name, fn) {
							return function() {
								var tmp = this._super;
								
								// set super method
								this._super = _super[name];

								var ret = fn.apply(this, arguments);
			
								this._super = tmp;
			
								return ret;
							};
						})(name, oldPrototype[name]);
				} else {
					newPrototype[name] = oldPrototype[name];
				}
			}
		}

		this.prototype = newPrototype;
		
		return this;
	};
})();

/////////////////////////////////////////////////////////////////////////////////////////////
// Number
/////////////////////////////////////////////////////////////////////////////////////////////
if (!Number.prototype.toPercent) {
	Number.prototype.toPercent = function(miniFractions) {
		return (this * 100).toFixed(miniFractions) + '%';
	};
}

if (!Number.prototype.toUSFixed) {
	Number.prototype.toUSFixed = function(miniFractions, separator) {
		miniFractions = miniFractions || 0;
		var n = this.toFixed(miniFractions) + "", separator = separator || ",";
		var arr = n.split(".");
		var re = /(\d{1,3})(?=(\d{3})+$)/g;
		
		return arr[0].replace(re,"$1" + separator) + (arr.length == 2 ? "."+arr[1] : "");
	};
}

/////////////////////////////////////////////////////////////////////////////////////////////
// Date
/////////////////////////////////////////////////////////////////////////////////////////////

/**
 * Get a formatted date string.
 * 
 * @param fmt
 *            Format
 * @returns String Formatted date string.
 */
if (!Date.prototype.format) {
	Date.prototype.format = function(fmt) {
		var o = {
			"M+" : this.getMonth() + 1, // month
			"d+" : this.getDate(), // date
			"h+" : this.getHours(), // hours
			"m+" : this.getMinutes(), // minutes
			"s+" : this.getSeconds(), // seconds
			"q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
			"S" : this.getMilliseconds()
		// milliseconds
		};
		if (/(y+)/.test(fmt))
			fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
					.substr(4 - RegExp.$1.length));
		for ( var k in o)
			if (new RegExp("(" + k + ")").test(fmt))
				fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
						: (("00" + o[k]).substr(("" + o[k]).length)));
		return fmt;
	};
}