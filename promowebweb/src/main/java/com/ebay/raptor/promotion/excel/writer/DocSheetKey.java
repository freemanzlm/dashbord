package com.ebay.raptor.promotion.excel.writer;

public final class DocSheetKey {
	public final static String [] listingKeys ={"skuName", "currency", "itemId", "currPrice", "dealsPrice", "stockNum", "stockReadyDate"};
	public final static String [] GBHKeys ={"skuName", "currency", "category", "itemId", "currPrice", "dealsPrice", "qty", "listSite",
		"wwShipOpt", "wwShipChg", "ruShipOpt", "ruShipChg", "cnShipOpt", "cnShipChg", "laShipOpt", "laShipChg", "meShipOpt", "meShipChg",
		"brShipOpt", "brShipChg", "isShipOpt", "isShipChg"};
	public final static String [] FRESKeys ={"skuName", "currency", "category", "itemId", "currPrice", "dealsPrice", "qty", "location",
		"dlvyTime", "shipPrice", "rrpLink"};
	public final static String [] APACKeys ={"skuName", "currency", "category", "itemId", "currPrice", "dealsPrice", "qty", "rrpLink"};
}
