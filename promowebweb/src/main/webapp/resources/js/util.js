(function(namespace){
	/**
	 * JavaScript does not protect the property name hasOwnProperty; thus, 
	 * if the possibility exists that an object might have a property with this name,
	 * it is necessary to use an external hasOwnProperty to get correct results:
	 */
	function hasOwnProperty(obj, prop) {
		return Object.prototype.hasOwnProperty.call(obj, prop);
	}

	/**
	 * Cycles over properties in an object and calls a function for each
	 * property value. If the function returns a truthy value, then the
	 * iteration is stopped.
	 */
	function eachProp(obj, func) {
		for (var prop in obj) {
			if (hasOwnProperty(obj, prop)) {
				if (func(obj[prop], prop)) {
					break;
				}
			}
		}
	}
	
    function isFunction(it) {
        return Object.prototype.toString.call(it) === '[object Function]';
    }

    if(!Array.isArray) {
    	Array.isArray = function (vArg) {
    		return Object.prototype.toString.call(vArg) === "[object Array]";
    	};
	}
    
    if (!Number.prototype.toPercent) {
    	Number.prototype.toPercent = function(miniFractions) {
        	return (this * 100).toFixed(miniFractions) + '%';
        };
    }
    
    if (!Number.prototype.toUSFixed) {
    	Number.prototype.toUSFixed = function(miniFractions) {
    		miniFractions = miniFractions || 0;
    		var result = this.toLocaleString('en-US', {minimumFractionDigits: miniFractions, maximumFractionDigits: miniFractions});
    		if (result.indexOf('.') == 0) {
    			result = '0' + result;
			}
        	return result;
        };
    }

	/**
	 * Simple function to mix in properties from source into target,
	 * but only if target does not already have a property of the same name.
	 */
	function mixin(target, source, force, deepMixin) {
		if (source) {
			eachProp(source, function (value, prop) {
				if (force || !hasOwnProperty(target, prop)) {
					if (deepMixin && typeof value === 'object' && value &&
						!Array.isArray(value) && !isFunction(value) &&
						!(value instanceof RegExp)) {

						if (!target[prop]) {
							target[prop] = {};
						}
						mixin(target[prop], value, force, deepMixin);
					} else {
						target[prop] = value;
					}
				}
			});
		}
		return target;
	}
	
	function absLeft(node) {
		// original absolute x position of element in document
		return node.offsetLeft + (node.offsetParent && node.offsetParent.nodeType == 1 ? absLeft(node.offsetParent) : 0);
	}

	function absLeftOffset(node) {
		// offset in x coordinate in window because parent node has scrollbar.
		return (node.parentNode && node.parentNode.nodeType == 1 ? absLeftOffset(node.parentNode) : 0) + (node.nodeName.toLowerCase() != "html" && node.nodeName.toLowerCase() != "body" && node.scrollLeft ? -node.scrollLeft : 0);
	}
	
	function absTop(node) {
		// original absolute y coordinate of element in document
		return node.offsetTop + (node.offsetParent && node.offsetParent.nodeType == 1 ? absTop(node.offsetParent) : 0)
	}

	function absTopOffset(node) {
		// offset in y coordinate in window because of parent node has scrollbar.
		return (node.parentNode && node.parentNode.nodeType == 1 ? absTopOffset(node.parentNode) : 0) + (node.nodeName.toLowerCase() != "html" && node.nodeName.toLowerCase() != "body" && node.scrollTop ? -node.scrollTop : 0)
	}
	
	/**
	 * Get the abs x coordinate and y coordinate of node in viewport. Scroll is considered in this function.
	 * @param node
	 * @return {top: xxx, left: xxx}
	 */
	function getPositionInViewPort(node) {
		var left = 0, top = 0;
		while (node && !isNaN(node.offsetLeft) && !isNaN(node.offsetTop)) {
			left += node.offsetLeft - node.scrollLeft;
			top += node.offsetTop - node.scrollTop;
			node = node.offsetParent;
		}
		return {
			top : top,
			left : left
		};
	}
	
	/**
	 * Get the abs x coordinate and y coordinate of node in whole page. Scroll is considered in this function.
	 * @param node
	 * @return {top: xxx, left: xxx}
	 */
	function getPositionInPage(node) {
		var left = 0, top = 0;
		if (node.getBoundingClientRect) {
			left += node.getBoundingClientRect().left + (Math.max(node.ownerDocument.documentElement.scrollLeft, node.ownerDocument.body.scrollLeft) - Math.max(node.ownerDocument.documentElement.clientLeft, node.ownerDocument.documentElement.offsetLeft));
			top += node.getBoundingClientRect().top + (Math.max(node.ownerDocument.documentElement.scrollTop, node.ownerDocument.body.scrollTop) - Math.max(node.ownerDocument.documentElement.clientTop, node.ownerDocument.documentElement.offsetTop));
		} else {
			left += absLeft(node) + absLeftOffset(node);
			top += absTop(a) + absTopOffset(a);
		}

		return {
			top: top,
			left: left
		};
	}
	
	/**
	 * Get whole page's size
	 * 
	 * @return {height: xxx, width: xxx}
	 */
	function getPageSize() {
		return {
			height: Math.max(document.documentElement.scrollHeight, document.body.scrollHeight),
			width:  Math.max(document.documentElement.scrollWidth, document.body.scrollWidth)
		};
	}
	
	window.onerror = function(errorMessage, url, lineNumber, columnNumber, errorObject) {
		console.log("Error thrown in page: %s", url);
		console.log("line: %d, column: %d, message: %s", lineNumber, columnNumber, errorMessage);
		
		// disable default error handler.
		return true;
	};
	
	namespace.util = {
		eachProp: eachProp,
		hasOwnProperty: hasOwnProperty,
		isFunction: isFunction,		
		mixin: mixin,
		getPositionInViewPort: getPositionInViewPort,
		getPositionInPage: getPositionInPage,
		getPageSize: getPageSize
	};
})(BizReport = BizReport || {});
