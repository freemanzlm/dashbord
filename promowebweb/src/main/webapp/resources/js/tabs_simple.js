$(function(){
	$("ul[role=tablist], .tab-list").each(function(){
		var $list = $(this);
		$list.on("click", "li[role=tab]", function(event){
			if (!$(event.target).hasClass('icon')) {
				event.preventDefault();
			}
			
			var $tab = $(this);
			$tab.addClass("active").siblings("li[role=tab]").removeClass("active");
			$($tab.find("a[href^='#']").attr("href")).addClass("active").siblings("[role=tabpanel]").removeClass("active");
		});
	});
});
