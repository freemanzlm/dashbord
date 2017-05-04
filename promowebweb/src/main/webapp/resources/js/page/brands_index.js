$(function(){
	var BrandRegPromoTable = BizReport.BrandRegPromoTable;
	var PassedBrandsTable = BizReport.PassedBrandsTable;
	var OnGoingPromoTable = BizReport.OnGoingPromoTable;	
	var RewardingPromoTable = BizReport.RewardingPromoTable;
	var EndPromoTable = BizReport.EndPromoTable;
	var PendingPromoTable = BizReport.PendingPromoTable;
	
	var brandsRegTable = new BrandRegPromoTable();
	brandsRegTable.init({
		dataTableConfig: {
			tableId: "brand-reg-promotions-table"
		}});
	brandsRegTable.update();
	
	var passedBrandsTable = new PassedBrandsTable();
	passedBrandsTable.init({
		dataTableConfig: {
			tableId: "passed-brands-table"
		}});
	passedBrandsTable.update();
	
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
				sAjaxSource: "promotion/getSubsidyPromotions"
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
					sAjaxSource: "promotion/getUnconfirmedPromotions"
				}
			}});
		pendingPromoTable.update();
	}
});