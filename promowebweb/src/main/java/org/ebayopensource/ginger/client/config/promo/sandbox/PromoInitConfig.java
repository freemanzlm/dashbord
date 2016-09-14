package org.ebayopensource.ginger.client.config.promo.sandbox;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
//		return "http://localhost:9080/promoservice/v1";
		return "http://monterey-3699.slc01.dev.ebayc3.com:8080/promoservice/v1";
	}

}
