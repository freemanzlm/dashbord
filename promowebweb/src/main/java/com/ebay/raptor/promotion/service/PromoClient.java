package com.ebay.raptor.promotion.service;

import org.ebayopensource.ginger.client.GingerClient;
import org.ebayopensource.ginger.client.GingerClientState;
import org.ebayopensource.ginger.client.internal.GingerClientFactory.ClientCreationException;
import org.ebayopensource.ginger.client.internal.GingerClientManager;
import org.ebayopensource.ginger.client.internal.GingerClientManager.ClientAlreadyRegisteredException;
import org.ebayopensource.ginger.client.internal.InitGingerClientConfigFactory.ConfigCreationException;

import com.ebay.globalenv.config.Site;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;

public class PromoClient {

	private static Logger logger = Logger.getInstance(PromoClient.class);
	
	private static GingerClient CLIENT = null;
	
	private static String CLIENT_NAME = "Promo";
	
	private static String CLIENT_NAME_PACKAGE = "promo";
	
	private static String getPoolEnv(){
		String pool = Site.getPooltype().getName();
		String[][] envs = new String[][]{
			{"dev", "dev"},
			{"feature", "feature"},
			{"sandbox", "feature"},
			{"staging", "feature"},
			{"production", "production"},
		};
		for(String[] env : envs){
			if(env[0].equalsIgnoreCase(pool)){
				return env[1];
			}
		}
		return "production";
	}
	
	private static void init(){
		try {
			CLIENT = GingerClientManager.get().getOrRegisterClient(CLIENT_NAME, CLIENT_NAME_PACKAGE, getPoolEnv());
		} catch (ConfigCreationException | ClientCreationException
				| ClientAlreadyRegisteredException e) {
			e.printStackTrace();
			logger.log(LogLevel.ERROR, "Failed to init the Promo Client.", e);
		}
	}

	public synchronized static GingerClient getClient(){
		if(null == CLIENT || (null != CLIENT && GingerClientState.DOWN == CLIENT.getState())){
			init();
			if(GingerClientState.DOWN == CLIENT.getState()){
				CLIENT.markUp();
			}
		}
		return CLIENT;
	}
	
}
