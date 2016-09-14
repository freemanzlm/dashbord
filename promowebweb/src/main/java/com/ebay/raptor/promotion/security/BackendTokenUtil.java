package com.ebay.raptor.promotion.security;

import java.text.ParseException;
import java.util.Date;

import com.ebay.raptor.promotion.util.DateUtil;
import com.ebay.raptor.promotion.util.StringUtil;

/**
 * If you want to add token parameters, please add parameter key as static field first. All token parameters should be operated under these keys.
 * 
 * @author linguo, lyan2
 */
public class BackendTokenUtil {
	
	// token parameter keys
	public static final String TOKEN_ADMIN_USER = "admin_user";
	public static final String TOKEN_VISIT_IP = "visit_ip";
	public static final String TOKEN_VISIT_DT = "visit_dt";
	public static final String TOKEN_USER_VISIT_IP = "user_visit_ip";
	
	private static DES des = DES.getInstance();
	
	public static void main(String[] args) throws Exception {
		BackendTokenData token = parse("2Ybt%2Fpl4HpHd5LRSGA%2FWvuJRVt8kKY1KKSFD7FWxYCTfdJvufoLwL4MuZd%2FnYjS4dPnDfLBIXASy%0AiIcuv4o5NScFHMzJ0ll5PCgDvOiVADV5Fl9yPmvAaQ%3D%3D", true);
		System.out.println(token.getAdminUserName());
		System.out.println(token.getVisitDt());
		System.out.println(token.getUserVisitIp());
	}

	/**
	 * Parse token text into TokenData instance.
	 * @param token
	 * @return null if token is empty.
	 * @throws Exception 
	 */
	public static BackendTokenData parse(String token, boolean urlDecode) throws Exception {
		BackendTokenData tokenData = null;
		if (token != null && !token.isEmpty()) {
			String decrypedToken = des.decrypt(token, true, urlDecode);
			tokenData = parseDecryptedToken(decrypedToken);
		}
		
		return tokenData;
	};

	/**
	 * Parse token text into TokenData instance.
	 * @param token
	 * @return null if token is empty.
	 * @throws Exception 
	 */
	public static BackendTokenData parse(String token, boolean replace, boolean urlDecode) throws Exception {
		BackendTokenData tokenData = null;
		if (token != null && !token.isEmpty()) {
			String decrypedToken = des.decrypt(token, replace, urlDecode);
			tokenData = parseDecryptedToken(decrypedToken);
		}
		
		return tokenData;
	};
	
	/**
	 * Parse plain token text into TokenData instance.
	 * @param decryptedToken The plain token after decrypted by DES.
	 * @return
	 * @throws ParseException
	 * @throws SDException
	 */
	public static BackendTokenData parseDecryptedToken(String decryptedToken) throws ParseException {
		BackendTokenData tokenData = null;
		if (decryptedToken != null && !decryptedToken.isEmpty()) {
			
			tokenData = new BackendTokenData();
			String[] parameters = decryptedToken.split(";");
			if (parameters != null && parameters.length > 0) {
				for (String parameter : parameters) {
					int eqIndex = parameter.indexOf('=');
					String key = parameter.substring(0, eqIndex);
					if (eqIndex >= parameter.length() - 1) {
						continue;
					}
					String val = parameter.substring(eqIndex + 1);
					val = val.trim();

					if (val.isEmpty() || key.isEmpty()) {
						continue;
					}
					
					switch (key.toLowerCase().trim()) {
					case TOKEN_ADMIN_USER:
						tokenData.setAdminUserName(val);
						break;
					case TOKEN_VISIT_IP:
						tokenData.setVisitIp(val);
						break;
					case TOKEN_VISIT_DT:
						tokenData.setVisitDt(val);
						break;
					case TOKEN_USER_VISIT_IP:
						tokenData.setUserVisitIp(val);
						break;
					}
				}
			}
		}
		
		return tokenData;
	}
	
	/**
	 * Translate token object into token string, it's encrypted by DES.
	 * @param data
	 * @return empty string if data is null.
	 */
	public static String getTokenString(BackendTokenData data) {
		String token = "";
		if (data != null) {
			String plainText = data.toString();
			try {
				token = DES.getInstance().encrypt(plainText, true);
			} catch (Exception e) {
				// ignore...
			}
		}
		
		return token;
	}
	
	/**
	 * Token has admin user name and visit IP. It they don't match, return false.
	 * @param token
	 * @param adminUser
	 * @param ip
	 * @return
	 */
	public static boolean verifyToken (BackendTokenData token, String adminUser, String ip) {
		if (StringUtil.isEmpty(adminUser) || token == null) {
			return false;
		}
		
		if (!adminUser.equalsIgnoreCase(token.getAdminUserName())) {
			return false;
		}

		if (isLoginDateExpired(token.getVisitDt())) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Back end token can only used within one day.
	 * @param dateStr
	 * @return
	 */
	private static boolean isLoginDateExpired(String dateStr) {
		if (StringUtil.isEmpty(dateStr)) {
			return true;
		}
		
		Date dt;
		try {
			dt = DateUtil.parseISODate(dateStr, null);
			dt.setTime(dt.getTime() + 86400000);
		} catch (ParseException e) {
			return true;
		}
		
		Date now = new Date();
		return now.before(dt) ? false : true;
	}	

}