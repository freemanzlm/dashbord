package com.ebay.raptor.promotion;

public interface Router {

	public interface Promotion {
		String base = "promotions";
	}
	
	public interface Listing {
		String base = "listings";
		
		String getPromotionListings = "/getPromotionListings";
		String getUploadedListings = "/getUploadedListings";
		String downloadTempldate = "/downloadTemplate";
		String uploadListings = "/uploadListings";
		String reviewUploadedListings = "/reviewUploadedListings";
		String submitListings = "/submitListings";
		String confirmListings = "/confirmListings";
		
		String uploadListingAttachment = "/uploadListingAttachment";
		String listingAttachment = "/attachment/promoId/{promoId}/userId/{userId}/skuId/{skuId}/key/{key}";
	}
}
