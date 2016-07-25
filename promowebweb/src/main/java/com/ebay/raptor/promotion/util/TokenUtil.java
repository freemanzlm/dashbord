package com.ebay.raptor.promotion.util;

import java.io.IOException;
import java.text.ParseException;

import com.ebay.app.raptor.cbtcommon.security.DES;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * If you want to add token parameters, please add parameter key as static field first. All token parameters should be operated under these keys.
 * 
 * @author lyan2
 */
public class TokenUtil {
	private static ObjectMapper mapper = new ObjectMapper();
	private static Logger logger = Logger.getInstance(TokenUtil.class);
	
	// token parameter keys
	public static final String TOKEN_USER_ID = "userId";
	public static final String TOKEN_USER_NAME = "userName";
	public static final String TOKEN_IS_ADMIN = "isAdmin";
	public static final String TOKEN_USER_LANGUAGE = "language";
	
	private static DES des = DES.getInstance();
	
	public static void main(String[] args) throws Exception {
		TokenData data = new TokenData();
		data.setIsAdmin(true);
		data.setLanguage("zh_CN");
		data.setUserName("1413178537");
		data.setUserId(1413178537l);
		
		String token = getTokenString(data);
		System.out.println(token);
		
		data = parse("%2FGUi9rsqyjCKlrWPcZzItGsbpteF6MY%2Bo4g1OBK1D%2FINvbyTH6gx81gGGtNVyFlAbV2cMqF1Si54%0D%0AJgmDUnZTLZkpm0tVfhvnYlxy7Bo6lXo%3D", true);
		System.out.println(data.getUserId());
	}

	/**
	 * Parse token text into TokenData instance.
	 * @param token
	 * @return null if token is empty.
	 * @throws Exception 
	 */
	public static TokenData parse(String token, boolean urlDecode) throws Exception {
		TokenData tokenData = null;
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
	public static TokenData parse(String token, boolean replace, boolean urlDecode) throws Exception {
		TokenData tokenData = null;
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
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 * @throws ParseException
	 * @throws SDException
	 */
	public static TokenData parseDecryptedToken(String decryptedToken) throws JsonParseException, JsonMappingException, IOException {
		
		TokenData tokenData = null;
		if (decryptedToken != null && !decryptedToken.isEmpty()) {
			tokenData = mapper.readValue(decryptedToken, TokenData.class);
		}
		
		return tokenData;
	}
	
	/**
	 * Translate token object into token string, it's encrypted by DES.
	 * @param data
	 * @return empty string if data is null.
	 */
	public static String getTokenString(TokenData data) {
		String token = "";
		try {
			if (data != null) {
				token = mapper.writeValueAsString(data);
				token = DES.getInstance().encrypt(token, true);
			}
		} catch (JsonProcessingException e1) {
			logger.log(LogLevel.ERROR, "Unable to generate token string");
			e1.printStackTrace();
		} catch (Exception e) {
			logger.log(LogLevel.ERROR, "Unable to encrypt token string");
			e.printStackTrace();
		}
		
		return token;
	}
	
}