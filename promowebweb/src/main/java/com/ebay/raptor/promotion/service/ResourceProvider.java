package com.ebay.raptor.promotion.service;

public class ResourceProvider {
	
	public static String TOKEN_TYPE = "Bearer ";
	
	public interface Secure {
		String base = "/promoser/";
	}

	public interface PromotionRes {
		String base = "promotion";
		
		String _isAcceptAgreement = "/isAcceptAgreement";
		String isAcceptAgreement = "/isAcceptAgreement/promoId/{promoId}/uid/{uid}";
		String _acceptAgreement = "/acceptAgreement";
		String acceptAgreement = "/acceptAgreement/promoId/{promoId}/uid/{uid}";

		String _getPromotions = "/getPromotions";
		String getPromotions = "/getPromotions/uid/{uid}";
		String _getUnconfirmedPromotions = "/getUnconfirmedPromotions";
		String getUnconfirmedPromotions = "/getUnconfirmedPromotions/uid/{uid}/isAdmin/{isadmin}";
		String _getIngPromotions = "/getIngPromotions";
		String getIngPromotions = "/getIngPromotions/uid/{uid}";
		String _getSubsidyPromotions = "/getSubsidyPromotions";
		String getSubsidyPromotions = "/getSubsidyPromotions/uid/{uid}";
		String _getEndPromotions = "/getEndPromotions";
		String getEndPromotions = "/getEndPromotions/uid/{uid}";
		String _getUpdatedPromotions = "/getUpdatedPromotions";
		String getUpdatedPromotions = "/getUpdatedPromotions/uid/{uid}"; 
		
		String _getPromotionById = "/getPromotionById";
		String getPromotionById = "/getPromotionById/promoId/{promoId}/uid/{uid}/isAdmin/{isAdmin}";
		String hasListingNominated = "/hasListingNominated/promoId/{promoId}/uid/{uid}";
	}
	
	public interface UserPromotionRes {
		String base = "promotion";
		String userPromo = "/userPromo";
	}
	
	public interface ListingRes {
		String base = "listings";
		String dealsBase = "deals";
		String siteDeals = "sitedeals";
		String hotsellBase = "hotsell";
		
		String _getPromotionListings = "/getPromotionListings";
		String getPromotionListings = "/getPromotionListings/promoId/{promoId}/uid/{uid}";
		String getTempPromotionListings = "/getPromotionListings/promoId/{promoId}/uid/{uid}/type/{type}";
	
		String _getApplicableListings = "/getApplicableListings";
		String getApplicableListings = "/getApplicableListings/promoId/{promoId}/uid/{uid}";

		String _getAppliedListings = "/getAppliedListings";
		String getAppliedListings = "/getAppliedListings/promoId/{promoId}/uid/{uid}";
		String getTempAppliedListings = "/getAppliedListings/promoId/{promoId}/uid/{uid}/type/{type}";

		String _getApprovedListings = "/getApprovedListings";
		String getApprovedListings = "/getApprovedListings/promoId/{promoId}/uid/{uid}";
		String getTempApprovedListings = "/getApprovedListings/promoId/{promoId}/uid/{uid}/type/{type}";

		String _getUploadedListings = "/getUploadedListings";
		String getUploadedListings = "/getUploadedListings/promoId/{promoId}/uid/{uid}";
		String getTempUploadedListings = "/getUploadedListings/promoId/{promoId}/uid/{uid}/type/{type}";

		String _getSubmittedListings = "/getSubmittedListings";
		String getSubmittedListings = "/getSubmittedListings/promoId/{promoId}/uid/{uid}";
		String getTempSubmittedListings = "/getSubmittedListings/promoId/{promoId}/uid/{uid}/type/{type}";

		String reviewUploadedListings = "/reviewUploadedListings";
		String getSKUListingsByPromotionId = "/getSKUListingsByPromotionId/promoId/{promoId}/uid/{uid}";
		String getSKUListingsByPromotionIdAndType = "/getSKUListingsByPromotionId/promoId/{promoId}/uid/{uid}/type/{type}";
		String confirmDealsListings = "/confirmDealsListings";
		String confirmHotSellListings = "/confirmHotSellListings";
		
		String downloadTempldate = "/downloadTemplate";
		String uploadListings = "/uploadListings";
		String submitListings = "/submitListings";
		String confirmListings = "/confirmListings";
		String uploadDealsListings = "/uploadDealsListings";
		String uploadGBHDealsListings = "/uploadGBHDealsListings";
		String uploadAPACDealsListings = "/uploadAPACDealsListings";
		String uploadFRESDealsListings = "/uploadFRESDealsListings";
		
		String submitPromoListings = "/submitPromoListings";
		String getListingsByPromotionIdAndUserIdAndType = "/getPromotionListings/promoId/{promoId}/uid/{uid}/type/{type}";
		
		String uploadListingAttachment = "/uploadListingAttachment";
		@Deprecated
		String downloadListingAttachment = "/downloadListingAttachment/promoId/{promoId}/userId/{userId}/skuId/{skuId}";
		String listingAttachment = "/attachment/promoId/{promoId}/userId/{userId}/skuId/{skuId}/key/{key}";
	}
	
	public interface SubsidyRes {
		String base = "subsidy";
		String getSubSidy = "/getSubsidy/promoId/{promoId}/uid/{uid}";
	}
	
	public interface Token{
		String base = "/token";
		String token = "/iaftoken";
	}
	
	public interface Terms{
		String base = "/promotion/terms";
		
		String hotSell = "/hotsell";
		String deals = "/deals";
		String dealsPreset = "/dealsPreset";
		String other = "/other";
	}
	
}
