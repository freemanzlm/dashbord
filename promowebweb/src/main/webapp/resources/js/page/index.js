$(function(){
	console.log("world");
	
	var OnGoingPromoTable = BizReport.OnGoingPromoTable;
	var RewardingPromoTable = BizReport.OnGoingPromoTable;
	var EndPromoTable = BizReport.EndPromoTable;
	
	var onGogingTable = new OnGoingPromoTable();
	onGogingTable.init({
		dataTableConfig: {
			tableId: "ongoing-promo-table"
		}});
	onGogingTable.initDataTable();
	onGogingTable.update();
	
	var rwardingPromoTable = new RewardingPromoTable();
	rwardingPromoTable.init({
		dataTableConfig: {
			tableId: "rewarding-promo-table"
		}});
	rwardingPromoTable.initDataTable();
	rwardingPromoTable.update();
	
	var endPromoTable = new EndPromoTable();
	endPromoTable.init({
		dataTableConfig: {
			tableId: "end-promo-table"
		}});
	endPromoTable.initDataTable();
	endPromoTable.update();
	
	console.log("hello");
});