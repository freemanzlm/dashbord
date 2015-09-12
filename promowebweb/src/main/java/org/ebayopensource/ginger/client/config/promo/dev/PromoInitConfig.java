package org.ebayopensource.ginger.client.config.promo.dev;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
		return "http://localhost:8080/promoservice/v1";
	}

}
