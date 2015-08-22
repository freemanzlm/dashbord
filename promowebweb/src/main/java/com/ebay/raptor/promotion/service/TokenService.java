package com.ebay.raptor.promotion.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.client.Entity;

import org.ebayopensource.ginger.client.GingerClient;
import org.ebayopensource.ginger.client.GingerClientResponse;
import org.ebayopensource.ginger.client.GingerWebTarget;

import com.ebay.globalenv.config.PooltypeEnum;
import com.ebay.globalenv.config.Site;
import com.ebay.iaf.client.IAFTokenUtility;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.pojo.service.req.TokenRequest;
import com.ebay.raptor.promotion.pojo.service.resp.BaseServiceResponse.AckValue;
import com.ebay.raptor.promotion.pojo.service.resp.TokenResponse;
import com.ebay.raptor.promotion.util.StringUtil;

public class TokenService extends BaseService{

	private static Logger logger = Logger.getInstance(TokenService.class);
	
	private static String CONSUMER_ID = "urn:ebay-marketplace-consumerid:cae9973c-79c3-43d9-9cf7-3297668b17b9";
	
	static Map<String, String> secrets = null;
	static Map<String, String> cachedTokens = null;
	
	static {
		secrets = new ConcurrentHashMap<String, String>();
		//With same value.
		secrets.put(PooltypeEnum.DEVELOPER.getName(), "b6045daf-886d-4f8a-b90a-2e9c12bd40df");
		secrets.put(PooltypeEnum.FEATURE.getName(), "b6045daf-886d-4f8a-b90a-2e9c12bd40df");
		secrets.put(PooltypeEnum.STAGING.getName(), "b6045daf-886d-4f8a-b90a-2e9c12bd40df");
		
		secrets.put(PooltypeEnum.PRODUCTION.getName(), "28dd317b-831d-4bf7-8e3d-f63aed088411");
		secrets.put(PooltypeEnum.SANDBOX.getName(), "086a46f9-d2aa-4a55-994a-c70c8c2cc2d6");
		cachedTokens = new ConcurrentHashMap<String, String>();
	}
	
	private static String secret(){
		PooltypeEnum pool = Site.getPooltype();
		if(null != pool && null != secrets){
			return !StringUtil.isEmpty(secrets.get(pool.getName()))?
					secrets.get(pool.getName()):
					secrets.get(PooltypeEnum.PRODUCTION.getName());
		}
		return secrets.get(PooltypeEnum.PRODUCTION.getName());
	}
	
	/**
	 * Get the IAF token
	 * @return
	 */
	public static String getIAFToken(){
		String appToken = cachedTokens.get(secret());
		try{
			if(!StringUtil.isEmpty(appToken) && !IAFTokenUtility.isExpired(appToken)){
				return appToken;
			}
			GingerClient client = PromoClient.getClient();
			GingerWebTarget target = client.target(ResourceProvider.Token.base + ResourceProvider.Token.token);
			TokenRequest req = new TokenRequest();
			req.setConsumerId(CONSUMER_ID);
			req.setSecret(secret());
			GingerClientResponse resp = (GingerClientResponse) target.request().headers(nonAuthHeaders()).post(Entity.json(req));
			TokenResponse token = resp.getEntity(TokenResponse.class);
			if(null != token && AckValue.SUCCESS == token.getAckValue()){
				cachedTokens.put(secret(), token.getIafToken());
				return token.getIafToken();
			}
		} catch(Throwable e){
			logger.log(LogLevel.ERROR, "Failed to get the IAF Token.", e);
		}
		return "";
	}
	
}
