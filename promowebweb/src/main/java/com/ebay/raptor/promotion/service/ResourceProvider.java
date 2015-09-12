package com.ebay.raptor.promotion.service;

public class ResourceProvider {
	
	public static String TOKEN_TYPE = "Bearer ";
	
	public interface Secure {
		String base = "/promoser/";
	}

	public interface PromotionRes {
		String base = "promotion";
		
		String isUserAccessable = "/isUserAccessable";

		String _getPromotions = "/getPromotions";
		String getPromotions = "/getPromotions/uid/{uid}";
		String _getIngPromotions = "/getIngPromotions";
		String getIngPromotions = "/getIngPromotions/uid/{uid}";
		String _getSubsidyPromotions = "/getSubsidyPromotions";
		String getSubsidyPromotions = "/getSubsidyPromotions/uid/{uid}";
		String _getEndPromotions = "/getEndPromotions";
		String getEndPromotions = "/getEndPromotions/uid/{uid}";
		
		String _getPromotionById = "/getPromotionById";
		String getPromotionById = "/getPromotionById/promoId/{promoId}/uid/{uid}";
	}
	
	public interface UserPromotionRes {
		String base = "promotion";
		String userPromo = "/userPromo";
	}
	
	public interface ListingRes {
		String dealsBase = "deals";
		String hotsellBase = "hotsell";
		
		String _getApplicableListings = "/getApplicableListings";
		String getApplicableListings = "/getApplicableListings/promoId/{promoId}/uid/{uid}";
		String _getAppliedListings = "/getAppliedListings";
		String getAppliedListings = "/getAppliedListings/promoId/{promoId}/uid/{uid}";
		String _getApprovedListings = "/getApprovedListings";
		String getApprovedListings = "/getApprovedListings/promoId/{promoId}/uid/{uid}";
		String reviewUploadedListings = "/reviewUploadedListings";
		
		String _getSKUsByPromotionId = "/getSKUsByPromotionId";
		String getSKUListingsByPromotionId = "/getSKUListingsByPromotionId/promoId/{promoId}/uid/{uid}";
		String confirmDealsListings = "/confirmDealsListings";
		String confirmHotSellListings = "/confirmHotSellListings";
		String downloadSkuList = "/downloadSkuList";
		String uploadDealsListings = "/uploadDealsListings";
		
		String skuListFileName = "Deals_listing_template"; //TODO Here should be Chinese 
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
