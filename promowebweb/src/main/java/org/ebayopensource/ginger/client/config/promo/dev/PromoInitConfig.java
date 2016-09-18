package org.ebayopensource.ginger.client.config.promo.dev;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

import com.sun.jersey.multipart.impl.FormDataMultiPartDispatchProvider;
import com.sun.jersey.multipart.impl.MultiPartConfigProvider;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
//		return "http://lm-shc-16501085:8080/promoservice/v1";
//		return "http://10.249.74.112:7080/promoservice/v1";
//		return "http://l-shc-15008105.corp.ebay.com:9090/promoservice/v1";
		return "http://monterey-3699.slc01.dev.ebayc3.com:8080/promoservice/v1";
	}
	
	/*@Override
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
