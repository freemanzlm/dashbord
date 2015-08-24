package com.ebay.raptor.promotion.service;

public class ResourceProvider {
	
	public static String TOKEN_TYPE = "Bearer ";
	
	public interface Secure {
		String base = "/promoser/";
	}

	public interface PromotionRes {
		String base = "promotion";
		String listing = "/listing";
	}
	
	public interface UserPromotionRes {
		String base = "promotion";
		String userPromo = "/userPromo";
	}
	
	public interface ListingRes {
		String base = "listing";
		String listing = "/listing";
		String skus = "/skus";
	}
	
	public interface Token{
		String base = "/token";
		String token = "/iaftoken";
	}
	
}
