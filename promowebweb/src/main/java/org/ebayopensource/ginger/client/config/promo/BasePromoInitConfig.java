package org.ebayopensource.ginger.client.config.promo;

import org.ebayopensource.ginger.client.config.DefaultInitGingerClientConfig;

import com.ebay.raptor.promotion.service.ObjectMapperProvider;

public abstract class BasePromoInitConfig extends DefaultInitGingerClientConfig{
	
	@Override
	public Object[] getProviders() {
		// set custom jackson ObjectMapper
		return new Object[] {new ObjectMapperProvider()};
//	    return new Object[] {new ContextPropagator(),new RaptorContextInjector()};
//		return new Object[] {new MultiPartConfigProvider(), new FormDataMultiPartDispatchProvider()};
	}

	@Override
	public int getConnectTimeout() {
		return 500000;
	}
	
	@Override
	public String getEndPoint() {
		return "http://localhost:8080/";
	}

	@Override
	public int getReadTimeout() {
		return 500000;
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
