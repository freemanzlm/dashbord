$(function(){
	console.log("world");
	
	var OnGoingPromoTable = BizReport.OnGoingPromoTable;
	var RewardingPromoTable = BizReport.RewardingPromoTable;
	var EndPromoTable = BizReport.EndPromoTable;
	
	var onGogingTable = new OnGoingPromoTable();
	onGogingTable.init({
		dataTableConfig: {
			tableId: "ongoing-promo-table"
		}});
	onGogingTable.update();
	
	var rwardingPromoTable = new RewardingPromoTable();
	rwardingPromoTable.init({
		dataTableConfig: {
			tableId: "rewarding-promo-table",
			customTableConfig: {
				sAjaxSource: "promotion/getSubsidyPromotions", //'js/data/reward.json'
			}
		}});
	rwardingPromoTable.update();
	
	var endPromoTable = new EndPromoTable();
	endPromoTable.init({
		dataTableConfig: {
			tableId: "end-promo-table",
			customTableConfig: {
				sAjaxSource: "promotion/getEndPromotions", //'js/data/end.json'
			}
		}});
	endPromoTable.update();
	
	console.log("hello");
});