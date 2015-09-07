package com.ebay.raptor.promotion.list.req;

public class ListingWebParam {
	
	public static Long UID = 689917510l;
	
	public static String SFID = "701O0000000QyWPIA0";

	private String promoId;

	private Long uid;

	public String getPromoId() {
//		return promoId == null ? SFID : promoId;
		return SFID;
	}

	public void setPromoId(String promoId) {
		this.promoId = promoId;
	}

	public Long getUid() {
//		return uid == 0? UID : uid;
		return UID;
	}

	public void setUid(Long uid) {
		this.uid = uid;
	}

}
