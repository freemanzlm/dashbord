package org.ebayopensource.ginger.client.config.promo.staging;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
		return "http://promocamp-phx-1-web-envf7jokk59vh.stratus.phx.qa.ebay.com/promoservice/v1";
	}

	
}
