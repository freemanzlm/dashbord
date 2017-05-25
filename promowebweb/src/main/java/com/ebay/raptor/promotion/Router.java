package com.ebay.raptor.promotion;

public interface Router {

	public interface Promotion {
		String base = "promotion";
		
		String getUnconfirmedPromotions = "/getUnconfirmedPromotions";
		String getIngPromotions = "/getIngPromotions";
		String getSubsidyPromotions = "/getSubsidyPromotions";
		String getEndPromotions = "/getEndPromotions";
		String brandRegPromotions = "brandRegPromotions";
		String endedDealsPromotions = "/endedDealsPromotions";
		String ongoingDealsPromotions = "/ongoingDealsPromotions";
		String awardingDealsPromotions = "/awardingDealsPromotions";
		String unpublishedDealsPromotions = "/unpublishedDealsPromotions";
		String endedBrandPromotions = "/endedBrandPromotions";
		String ongoingBrandPromotions = "/ongoingBrandPromotions";
		String awardingBrandPromotions = "/awardingBrandPromotions";
		String unpublishedBrandPromotions = "/unpublishedBrandPromotions";
		
		String getPromotionById = "/getPromotionById";
		String promoStatistics = "/promoStatistics";
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
	
	public interface Subsidy {
		String base = "subsidy";
		
		String acknowledgment = "/acknowledgment";
		String downloadLetter = "/downloadLetter";
		String upload = "/upload";
		String uploadAttachment = "/uploadAttachment";
		String downloadAttachment = "/downloadAttachment";
		String downloadAttachmentById = "/downloadAttachmentById";
		
		String bindWlt = "/bindWlt";
	}
	
	public interface Brand {
		String base = "brands";
		
		String passed = "/passed";
	}
}
