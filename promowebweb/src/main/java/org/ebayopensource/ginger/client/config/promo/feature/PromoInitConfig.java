package org.ebayopensource.ginger.client.config.promo.feature;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
		return "http://promocamp.stratus.qa.ebay.com/promoservice/v1";
//		return "http://localhost:9080/promoservice/v1";
	}

}
