$(function(){
var HotsellApplicableTable = BizReport.HotsellApplicableTable;
	
	var listingTable = new HotsellApplicableTable();
	listingTable.init({
		dataTableConfig: {
			tableId: "listing-table"
		}});
	listingTable.update();
	
	var formBtn = document.getElementById("form-btn");
	var acceptCheckbox = $("#accept").change(function(){
		if (this.checked) {
			formBtn.removeAttribute("disabled");
		} else {
			formBtn.setAttribute("disabled", "disabled");
		}
	});
	
	var termsDialog = BizReport.termsDialog;
	termsDialog.subscribe({
		"scrollEnd": function() {
			acceptCheckbox.removeAttr("disabled");
		}
	});
	$(".terms-conditions").click(function(event){
		termsDialog.show();
	});
});