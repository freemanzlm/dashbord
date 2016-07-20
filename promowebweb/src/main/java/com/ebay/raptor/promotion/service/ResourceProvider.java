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
		String getUnconfirmedPromotions = "/getUnconfirmedPromotions/uid/{uid}";
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

		String _getSKUsByPromotionId = "/getSKUsByPromotionId";
		String getSKUsByPromotionId = "/getSKUsByPromotionId/promoId/{promoId}/uid/{uid}";

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
		String downloadSkuList = "/downloadSkuList";
		String uploadListings = "/uploadListings";
		String confirmListings = "/confirmDealsListings";
		String uploadDealsListings = "/uploadDealsListings";
		String uploadGBHDealsListings = "/uploadGBHDealsListings";
		String uploadAPACDealsListings = "/uploadAPACDealsListings";
		String uploadFRESDealsListings = "/uploadFRESDealsListings";
		String submitDealsListings = "/submitDealsListings";
		String getListingsByPromotionIdAndUserIdAndType = "/getPromotionListings/promoId/{promoId}/uid/{uid}/type/{type}";
		
		String skuListFileName = "Deals_listing_template"; //TODO Here should be Chinese 
		String gbhSkuListFileName = "Deals_listing_template_GBH"; //TODO Here should be Chinese 
		String fresSkuListFileName = "Deals_listing_template_FRES"; //TODO Here should be Chinese 
		String apacSkuListFileName = "Deals_listing_template_APAC"; //TODO Here should be Chinese 
		
		String uploadListingAttachment = "uploadListingAttachment";
		String downloadListingAttachment = "downloadListingAttachment";
	}
	
	public interface SubsidyRes {
		String base = "subsidy";
		String getSubSidy = "/{sid}";
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
