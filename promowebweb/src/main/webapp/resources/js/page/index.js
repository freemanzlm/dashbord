$(function(){
	var OnGoingPromoTable = BizReport.OnGoingPromoTable;
	var RewardingPromoTable = BizReport.RewardingPromoTable;
	var EndPromoTable = BizReport.EndPromoTable;
	var PendingPromoTable = BizReport.PendingPromoTable;
	
	var onGogingTable = new OnGoingPromoTable();
	onGogingTable.init({
		dataTableConfig: {
			tableId: "ongoing-promo-table"
		}});
	onGogingTable.update();
	
	var rewardingPromoTable = new RewardingPromoTable();
	rewardingPromoTable.init({
		dataTableConfig: {
			tableId: "rewarding-promo-table",
			customTableConfig: {
				sAjaxSource: "promotion/getSubsidyPromotions",
				aoColumnDefs: [,,,, {bVisible: !(pageData && (pageData.region == 'HK' || pageData.region == 'TW'))}]
			}
		}});
	rewardingPromoTable.update();
	
	var endPromoTable = new EndPromoTable();
	endPromoTable.init({
		dataTableConfig: {
			tableId: "end-promo-table",
			customTableConfig: {
				sAjaxSource: "promotion/getEndPromotions"
			}
		}});
	endPromoTable.update();
	
	if (document.getElementById('pending-promo-table')) {
		var pendingPromoTable = new PendingPromoTable();
		pendingPromoTable.init({
			dataTableConfig: {
				tableId: "pending-promo-table",
				customTableConfig: {
					sAjaxSource: "promotion/getUnconfirmedPromotions",
					aoColumnDefs: [,,,, {bVisible: !(pageData && (pageData.region == 'HK' || pageData.region == 'TW'))}]
				}
			}});
		pendingPromoTable.update();
	}
});