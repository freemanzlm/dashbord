package com.ebay.raptor.promotion.service;

public class ResourceProvider {
	
	public static String TOKEN_TYPE = "Bearer ";
	
	public interface Secure {
		String base = "/promoser/";
	}

	public interface PromotionRes {
		String base = "promotion";

		String listPromotions = "/listPromotions";
		String getPromotionById = "/getPromotionById";
	}
	
	public interface UserPromotionRes {
		String base = "promotion";
		String userPromo = "/userPromo";
	}
	
	public interface ListingRes {
		String base = "listing";
		
		String getApplicableListings = "/getApplicableListings";
		String uploadPresetListings = "/uploadPresetListings";
		String getAppliedListings = "/getAppliedListings";
		String getApprovedListings = "/getApprovedListings";
		String getSKUsByPromotionId = "/getSKUsByPromotionId";
		String uploadDealsListings = "/uploadDealsListings";
		String uploadApprovedDealsListings = "/uploadApprovedDealsListings";
	}
	
	public interface Token{
		String base = "/token";
		String token = "/iaftoken";
	}
	
}
