$(function(){
	
	var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"scrollEnd": function() {
			console.log("you got the end");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
});