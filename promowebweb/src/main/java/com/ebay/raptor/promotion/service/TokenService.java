package com.ebay.raptor.promotion.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ebay.globalenv.config.PooltypeEnum;
import com.ebay.globalenv.config.Site;
import com.ebay.iaf.client.IAFTokenUtility;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.util.IAFTokenProvider;
import com.ebay.raptor.promotion.util.StringUtil;

public class TokenService extends BaseService{

	private static Logger logger = Logger.getInstance(TokenService.class);
	
	private static String CONSUMER_ID = "urn:ebay-marketplace-consumerid:cae9973c-79c3-43d9-9cf7-3297668b17b9";
	
	static Map<String, String> secrets = null;
	static String iafToken = null;
//	static Map<String, String> cachedTokens = new ConcurrentHashMap<String, String>();
	
	static {
		secrets = new ConcurrentHashMap<String, String>();
		
		// Secrets are got from https://infohub.corp.ebay.com/consumer/662731495470142/credentials.do
		secrets.put(PooltypeEnum.DEVELOPER.getName(), "b6045daf-886d-4f8a-b90a-2e9c12bd40df");
		secrets.put(PooltypeEnum.FEATURE.getName(), "b6045daf-886d-4f8a-b90a-2e9c12bd40df");
		secrets.put(PooltypeEnum.STAGING.getName(), "b6045daf-886d-4f8a-b90a-2e9c12bd40df");
		
		secrets.put(PooltypeEnum.PRODUCTION.getName(), "28dd317b-831d-4bf7-8e3d-f63aed088411");
		secrets.put(PooltypeEnum.SANDBOX.getName(), "086a46f9-d2aa-4a55-994a-c70c8c2cc2d6");
		
		// when in development mode, we can use below fixed tokens. But we need to pay attention to their expire date (for example: 2017-02-15T04:03:42.3333).
		/*// staging IAFToken
		 cachedTokens.put("b6045daf-886d-4f8a-b90a-2e9c12bd40df", "v^1.1#i^1#f^0#p^1#d^2017-02-15T04:03:42.333Z#r^0#I^2#t^H4sIAAAAAAAAAKVUXWgcVRTOZDexsUb7IFXE6jqGRiwzc+/MTmbnmlndpi0uNE3ajbUN+HP37p1k2p0f5t5hE0HcBAm++FdQHw2FPlWhUPugiBJfCj6otFQqaKGCEhURfaiIFpydbLazUaPgvAz3nu+c853vnHtAs3/gwaVHl34dFG7qXW6CZq8gwK1goL9v162Z3rv6ekAKICw3h5rZxczqKMNuPUCHKAt8j9FceY8lUpuSmoEJxoV8Vc1jMVf21gFTviXqumFo1QLRIKA6rKqxnbGIlj3GscctUQVQl0BBgsYUMBDQUF6VNU2bFnOHacgc34shMhBzc27dYyghYIlR6CEfM4chD7uUIU5QpTS+H8VIFIQ+94lfF4sJXZSkC1P+m7tjxmjI47xisVzaN1YZVVJRim0FKhzziHWfxvwazR3G9YhunoAlaFSJCKGMiUpxLUN3UFRap5GIbOaJmie6bgOCDd22/7+I/1mELhH3+aGL+ea+rRunJtkJFFGPO3z+n7WMdageo4S3Twdi5/KeXOt3MMJ1x3ZoaIl7d5eOliYnxWIrL63iecnF4XHKgzomVCLxqEUuDZ0aIpiapqERyTCJJuW1mimZxDYkTTWNkZFCFRpVs01iLVNb/A0sxnyv5rTKZ7kDPt9N41pot8gjSE+JHIMmvImwZPMW2xhnSECVoD4F8ulmKOu9jfis1+o2dWN5csnx31vZ8eY8dKoRp50IGw2JfJaIg8CpiRuNyYS2B2GOWeIs5wFSlEajITc02Q9nFBVkF1YAVI6M76+QWepisQN3/h6fBktOUgmhsRdzEJ8PYipz8dDF+b0ZsWjqJjTaqneTKm68/ctFqmKl+5V0HtH66in2JB9cFAywKGjxegMqeADuBEP9mceymVt2MIdT2cG2TJjMnBkvfnshlY/T+QA7YW+/MK40Luuphbj8BLizsxIHMnBraj+Cu29Y+uBtdwxCHRSgAQyg5dVpcP8NaxZuz94+CR/ee9ZdeQP27Hr59e0foAtXRr8Agx2QIPT1ZBeaX351z80+PPVpfW7yrc+uIM1bXb087ZQXwmtDb56/9NIxtmX42i8fvv/zhPjk7759/fNHzpuXzrzwNT59YttTr8offXvx+afpe7+98mP0yb0Xrp7dceKnMw30zMmxleGHzj178B3he0v95vR3J68bby89vjM7fOi1wee2vfjxu1vO/WH+YN138UhlTcA/AUSUxTApBgAA");
		// sandbox IAFToken
		cachedTokens.put("086a46f9-d2aa-4a55-994a-c70c8c2cc2d6", "v^1.1#i^1#f^0#p^1#d^2017-02-15T04:02:59.388Z#r^0#I^3#t^H4sIAAAAAAAAAKVUXWzbVBSu07SoKgV1/BSNbTIuMMHm+NpO4thtwtK1gbD+sXZVNyjsxr5p3SZ25HvTPxWWRaLjZ33ggcH6QoXoy8TbYAJWHkCFdQi6sYcJtopp3TS6l71MqPxqOGmaJQV1SPjFuud853znfufcA1LlFU+OPzO+XEXd5ZhKgZSDovhKUFFetu2eUsfGshJQAKCmUo+mnOnSpXoM47GEshvhhGlgRIcb/QyKuqEkRaNewR2VVCQydNhYBXSafkbzaZIYAT5RU0UR2l6MkyhsYAIN4mcEwHtY4GN5qRNIChAUj+wSfb59DN2FLKybhg1xAYYejscMrGTp/UzSMhQTYh0rBowjrBBV6Qi2NCs2UklYJjFVM8YEssUqWTqrIH79cIgxsojNywTCwVBHWzDc2NTaWc8V5ArkVOggkCRx8WmnqSG6C8aSaH0anEUrHUlVRRgzXGCFoTipElwtJiu05pUj7qhHgkB0a94I//+l/M9SFEkZMq04JOvHZiy6xkazUAUZRCcjd1LUViPSj1SSO7XaKcKNdOb3XBLG9KiOLD/T1BDcG2xvZwIZdhSBI2wcWgOIJGJQRaxqD10yjixdU1SIZFkSVVaSVZF1i5rMympUYkVBlrxeX4SXInKuiBWmXAvWVLHTNDQ9IwKmW03SgOwboWKpvYqnQGob1Ga0WcEoyVRr4yQWCCzv6QTuwpZwqx1Okj4j03MUt0Wis8c7NzQfTYilR5IE5TOsdWTl8zMwkdA1Zq0zO6e5cRjGfqaPkITCcUNDQ86D37qGRJdp9XICADzX3dLcofahOGTycL0Q/+9gVs/eREV2FNYVMpKwSxm2R8/mN3qZgCDyMg9yshdXFVhr/Yeh4Mpc8WPJv6XVLRQoyX58mvocpKlP7U0HJMDy28AT5aV7nKV3M1gnyIWhoUXMYZcOoy6s9xr2O7SQawCNJKBuOcqpliuH9x4oWJBTPeCh/IqsKOUrC/Yl2HTbU8bfW1PFe4CPl2xawSPvA7W3vU7+Qef9P47OoJtvLG7/0/Pboe5DVOuG1NsaqMqDKKqsxJmmSsaenv1oZFK6wQlfbD64cXb/X4M3NtSeRicidE/9/OLol0cFxxG6afdg4zf4jGPyd7Lj2a3u+CMDtWd2tV8LhOJvCaOXdiTrfmoIbXls9sKuZprum3/JT784Vz0N3jsFfV+9rk5c7E+n93/d9YB836XYH1c/eXkpMs2G2P7qN+teOX7s+a6fPzt8uvz77svnxo5f0Baemtj0woFrXM3l+aYT71+d7D8rtG9ZqLnYPdd95fr0yYlfxsdI1dY9S6MzU/XNDyceN7WbJ484f5Wrl+duvfPh4LuV3ML1+u2h89+9tmjBo+PhYwPLP6jnPlj+uFarWTrlObv5/EwPoW5VuwNdsI56taF/pY1/A+TRYRC6BgAA");*/
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
		if (!StringUtil.isEmpty(iafToken)) {
			try{
				if(!IAFTokenUtility.isExpired(iafToken)){
					return iafToken;
				}
			} catch(Throwable e){
				logger.log(LogLevel.ERROR, "Failed to get the IAF Token.", e);
			}
		}

		iafToken = IAFTokenProvider.getConsumerIAFToken(CONSUMER_ID, secret());
		
		return null != iafToken ? iafToken : "";
	}
	
	/**
	 * Get the IAF token
	 * @return
	 */
	/*public static String getIAFToken(){
		if (!StringUtil.isEmpty(iafToken)) {
			try{
				if(!IAFTokenUtility.isExpired(iafToken)){
					return iafToken;
				}
			} catch(Throwable e){
				logger.log(LogLevel.ERROR, "Failed to get the IAF Token.", e);
			}
		}
		
		try {
			ISecureTokenManager tokenGen = SecureTokenFactory.getInstance();
			iafToken = tokenGen.getAppToken().getAccessToken();
			System.out.println(iafToken);
		} catch (TokenCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null != iafToken ? iafToken : "";
	}*/
	
	/**
	 * Get the IAF token
	 * @return
	 */
//	public static String getIAFToken(){
//		String appToken = cachedTokens.get(secret());
//		try{
//			if(!StringUtil.isEmpty(appToken) && !IAFTokenUtility.isExpired(appToken)){
//				return appToken;
//			}
//			GingerClient client = PromoClient.getClient();
//			GingerWebTarget target = client.target(ResourceProvider.Token.base + ResourceProvider.Token.token);
//			TokenRequest req = new TokenRequest();
//			req.setConsumerId(CONSUMER_ID);
//			req.setSecret(secret());
//			GingerClientResponse resp = (GingerClientResponse) target.request().headers(nonAuthHeaders()).post(Entity.json(req));
//			TokenResponse token = resp.getEntity(TokenResponse.class);
//			if(null != token && AckValue.SUCCESS == token.getAckValue()){
//				cachedTokens.put(secret(), token.getIafToken());
//				return token.getIafToken();
//			}
//		} catch(Throwable e){
//			logger.log(LogLevel.ERROR, "Failed to get the IAF Token.", e);
//		}
//		return "";
//	}
	
}
