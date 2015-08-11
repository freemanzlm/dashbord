(function(namespace) {
	var posManager = {
			center : function(panel) {
				var w = $(window);
				var oW = panel.outerWidth({
					margin : true
				}), oH = panel.outerHeight({
					margin : true
				});
				panel.css("top", Math.max((w.height() - oH) / 2, 0) + "px");
				panel.css("left", Math.max((w.width() - oW) / 2, 0) + "px");
				panel.css("position", "fixed");
			},
			trigger : function(panel, trigger, shiftx, shifty) {
				var oH = trigger.outerHeight({
					margin : true
				});
				
				var pos = trigger.hasOwnProperty("left") ? trigger : trigger.position();
				
				panel.css("top", (oH + pos.top + shifty) + "px");
				panel.css("left", (pos.left + shiftx) + "px");
			},
			customize : function(panel, top, left) {
				var w = $(window);
				var oW = panel.outerWidth({
					margin : true
				}), oH = panel.outerHeight({
					margin : true
				});
				
				if (isNaN(left)) {
					panel.css("left", Math.max((w.width() - oW) / 2, 0) + "px");
				} else {
					panel.css("left", left + "px");
				}
				
				if (isNaN(top)) {
					panel.css("top", Math.max((w.height() - oH) / 2, 0) + "px");
				} else {
					panel.css("top", top + "px");
				}
				
			},
			pointer : function(panel, trigger, type) {
				var ptrDiv = panel.find(".ptr"); // pointer
				var $doc = $(document);
				ptrDiv.removeClass("vert left right top bottom");
				
				var wH = $(window).height();
				var wW = $(window).width();
				var dW = $doc.width();
				var dH = $doc.height();
				var winST = $doc.scrollTop();
				var winSL = $doc.scrollLeft();	
				var ptrW = ptrDiv.height();
				
				var pos = trigger.hasOwnProperty("left") ? trigger : trigger.offset();
				
				if (trigger.hasOwnProperty("left")) {
					var oH = 0;
					var oW = 0;
				} else {
					oH = trigger.outerHeight({
						margin : true
					});
					oW = trigger.outerWidth({
						margin : true
					});
				}
				
				
				var pH = panel.outerHeight({
					margin : true
				});
				var pW = panel.outerWidth({
					margin : true
				});
				
				var offsetX = 0;
				var offsetY = 0;
				
				if (type == 'vertical') {
					ptrDiv.addClass("vert");

					if (pos.left - pW < winSL) {
						offsetX = pos.left + ptrW;
						ptrDiv.addClass("left"); // pointer is at the left of panel
					} else {
						offsetX = pos.left - pW + oW - ptrW;
						ptrDiv.addClass("right"); // pointer is at the right of panel
					}
					
					if (pos.top - pH >= winST) {
						offsetY = pos.top - pH;
						ptrDiv.addClass("bottom"); // pointer is at the bottom of panel
					} else {
						offsetY = pos.top + 16 + oH;
						ptrDiv.addClass("top"); // pointer is at the top of panel
					}
				}
				else {
					if (pos.left + oW + 16 + pW - winSL >= wW) {
						offsetX = pos.left - 16 - pW;
						ptrDiv.addClass("right");
					} else {
						offsetX = pos.left + oW + 16;
						ptrDiv.addClass("left");
					}
					

					if (pos.top + oH + pH - 40 - winST >= wH) {
						offsetY = pos.top - pH + 70;
						ptrDiv.addClass("bottom");
					} else {
						offsetY = pos.top - 40;
						ptrDiv.addClass("top");
					}
				}
				
				panel.css({"left": offsetX, "top": offsetY});
			}
		};
	
	namespace.posManager = posManager;
})(BizReport = BizReport || {});