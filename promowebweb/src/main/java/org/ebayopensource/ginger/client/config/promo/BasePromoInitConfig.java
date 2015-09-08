package org.ebayopensource.ginger.client.config.promo;

import org.ebayopensource.ginger.client.config.DefaultInitGingerClientConfig;

public abstract class BasePromoInitConfig extends DefaultInitGingerClientConfig{

	@Override
	public int getConnectTimeout() {
		return 50000;
	}
	
	@Override
	public String getEndPoint() {
		return "http://localhost:8080/";
	}

	@Override
	public int getReadTimeout() {
		return 50000;
	}

	@Override
	public int getThreadPoolSize() {
		return 20;
	}

	@Override
	public int getErrorCountThreshold() {
		return 10000;
	}

}
