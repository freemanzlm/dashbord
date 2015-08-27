package com.ebay.raptor.promotion.service;

public class ResourceProvider {
	
	public static String TOKEN_TYPE = "Bearer ";
	
	public interface Secure {
		String base = "/promoser/";
	}

	public interface PromotionRes {
		String base = "promotion";

		String _getPromotions = "/getPromotions";
		String getPromotions = "/getPromotions/uid/{uid}";
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
		
		String getSKUsByPromotionId = "/getSKUsByPromotionId";
		String uploadPresetListings = "/uploadPresetListings";
		String uploadDealsListings = "/uploadDealsListings";
		String uploadApprovedDealsListings = "/uploadApprovedDealsListings";
	}
	
	public interface Token{
		String base = "/token";
		String token = "/iaftoken";
	}
	
}
