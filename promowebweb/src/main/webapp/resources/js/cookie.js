// --------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------
// behaviours - Manage cookies;
// author:		Linus.yan;
// email:  linus.yan@hotmail.com, lyan2@ebay.com
// --------------------------------------------------------------------------------------------------------------------------------
// --------------------------------------------------------------------------------------------------------------------------------
(function() {
	var namespace = window.cbt = window.cbt || {};

	var Cookie = function() {};
	
	function trim(text) {
		return (text || "").replace(/^\s+|\s+$/g, "");
	}

	Cookie.prototype = {
		create : function(name, value, days) {
			var expires = "";
			if (days) {
				var date = new Date();
				date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
				expires = "; expires=" + date.toGMTString();
			}
			document.cookie = (name + "=" + value + expires + "; path=/");
		},

		read : function(name) {
			var nameEQ = name + "=";
			var ca = document.cookie.split(';');
			for ( var i = 0; i < ca.length; i++) {
				var c = ca[i];
				c = trim(c);
				if (c.indexOf(nameEQ) == 0) {
					return c.substring(nameEQ.length, c.length);
				}
			}
			return null;
		},

		remove : function(name) {
			this.create(name, "", -1);
		}
	};

	namespace.cookie = new Cookie();
})();

// --------------------------------------------------------------------------------------------------------------------------------
// --- end of file ---
// -------------------------------------------------------------------------------------------------------------------------------
