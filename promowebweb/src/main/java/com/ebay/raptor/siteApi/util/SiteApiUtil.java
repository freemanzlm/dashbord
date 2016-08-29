package com.ebay.raptor.siteApi.util;

import com.ebay.globalenv.realtimemessaging.EiasUserIdEncoder;

/**
 * Used to decode and encode user id.
 * 
 * @author lyan2
 */
public class SiteApiUtil {
	
	public static void main(String[] args) {
		String token =  encodeUserId("132986458");
		
		System.out.println("Token: " + token);
		System.out.println("User ID: " + decodeUserId(token, false));
		
		token = "nY+sHZ2PrBmdj6wVnY+sEZ2PrA2dj6wFk4CoAJaGpASdj6x9nY+seQ**";
		System.out.println("User ID: " + decodeUserId(token, false));
	}
	
	/**
	 * Get user oracle id from token. Ebay developer site use EiasUserIdEncoder.FILL_CHAR_EQUAL, but cookie use EiasUserIdEncoder.FILL_CHAR_ASTERISK.
	 * In cookie, we can't use "=" character. because it will be removed after passed to server. So we have to use EiasUserIdEncoder.FILL_CHAR_ASTERISK.
	 * @param eiasToken
	 * @return
	 */
	public static String decodeUserId(String eiasToken, boolean fromDeveloperSite) {
		if (eiasToken != null) eiasToken = eiasToken.trim();
		Integer userId = null;
		
		if (fromDeveloperSite) {
			userId = EiasUserIdEncoder.decodeUserId(eiasToken, EiasUserIdEncoder.FILL_CHAR_EQUAL);
		} else {
			userId = EiasUserIdEncoder.decodeUserId(eiasToken, EiasUserIdEncoder.FILL_CHAR_ASTERISK);
		}
		
		return String.valueOf(userId);
	}
	
	/**
	 * Encode userId.
	 * @param userId
	 * @return
	 */
	public static String encodeUserId(String userId) {
		if (userId != null) userId = userId.trim();
		return EiasUserIdEncoder.encodeUserId(Long.valueOf(userId), EiasUserIdEncoder.FILL_CHAR_ASTERISK);
	}
}
