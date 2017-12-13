package com.ebay.raptor.promotion.nsd.pojo;


public enum TrackingType {
	LogIn(1), EndItem (2), Alert(3), 
	TMPLogIn(10), TMPOverViewTab(11), TMPQCListingTab(12), TMPDefectListingTab(13), TMPLimitTab(14), TMPSalesReportTab(15),
	TMPPreClaim(16), TMPShipDefect(17), TMPNonShipDefect(18),
	Error(20), ExeLong(21),
	CateConversion(30), HImpLViConversion(31), HViLTnxConversion(32), LViHTnxConversion(33), WatchedList(34),
	SKUSad(35), CateSad(36),saleHotPublishInfo(37), ElectronicComp(38), PandAComp(39),
	DirectToBiz(40),VisitToPromo(41),DirectAccoutOverview(42),DirectALertPolicy(43),
	SaleReport(44),OtherSaleHot(45),AccountTrend(46),DefectListing(47),
	SaleReportTrendInfo(440),SaleReportTrendButton(441),
	PolularListingByDash(50),PolularListingByBiz(51),
	NewDashBoardAccount(61),NewDashBoardAlertPolicy(62),NewDashBoardEbayHot(63),NewDashBoardListingDiagNose(64),NewDashBoardPromotion(65),
	ListingInfoAccount(640),ListingInfoDefectTrans(641),ListingInfoShipTrans(642),ListingInfoPicAttr(643),ListingInfoPrice(644),ListingInfoKeyWd(645),ListingInfoViewItem(646),
	DefectTransDownload(6410);
	
	TrackingType (int type) {
		this.setType(type);
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public static TrackingType getAuditType (int type) {
		for (TrackingType auditType : TrackingType.values()) {
			if (type == auditType.getType()) {
				return auditType;
			}
		}

		return null;
	}

	private int type;

}
