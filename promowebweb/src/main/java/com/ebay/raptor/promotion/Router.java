package com.ebay.raptor.promotion;

public interface Router {

	public interface Promotion {
		String base = "promotions";
	}
	
	public interface Listing {
		String base = "listings";
		String getPromotionListings = "/getPromotionListings";
		String getUploadedListings = "/getUploadedListings";
		String submitListings = "/submitListings";
		String confirmListings = "/confirmListings";
	}
}
