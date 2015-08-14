$(function(){
	
	var SKUListTable = BizReport.SKUListTable;
	
	var skuList = new SKUListTable();
	skuList.init({
		dataTableConfig: {
			tableId: "sku-list-table"
		}});
	skuList.update();
	
	$("form").submit(function(){
		return !!$(this).find("input[type=file]").attr("value");
	});
	
	console.log("hello");
});