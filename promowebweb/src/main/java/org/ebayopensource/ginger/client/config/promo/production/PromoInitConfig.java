package org.ebayopensource.ginger.client.config.promo.production;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
		// TODO Change to production pool
//		return "http://10.249.74.206:7080/promoservice/v1";
		return "http://www.promocamp.stratus.ebay.com/promoservice/v1";
	}

}
