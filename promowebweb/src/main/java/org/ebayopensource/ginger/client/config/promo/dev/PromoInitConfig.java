package org.ebayopensource.ginger.client.config.promo.dev;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

import com.ebay.raptor.promotion.service.ObjectMapperProvider;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
//		return "http://lm-shc-16501085:8080/promoservice/v1";
		return "http://10.249.75.170:8080/promoservice/v1";
//		return "http://phx5qa01c-ee86.stratus.phx.qa.ebay.com:8080/promoservice/v1";
	}
	
	/*@Override
	public Object[] getProviders() {
	    return new Object[] {new ContextPropagator(),new RaptorContextInjector()};
	}
	
	@Override
	public Map<String, Object> getProperties() {
	    Map<String, Object> m=new HashMap<String, Object>();
	    m.put(COSConstants.COS_Enabled, Boolean.TRUE);
	    m.put(COSConstants.COS_MODE, CosRunningMode.COS2);
	    return m;
	}*/
//	
//	@Override
//	public Set<String> getScopes(){
//	    if(scopes.isEmpty()) {
//	       scopes.add("https://api.ebay.com/oauth/scope/buy@user");
//	       scopes.add("https://api.ebay.com/oauth/scope/sell@user");
//	    }
//	    return scopes;
//	}

}
