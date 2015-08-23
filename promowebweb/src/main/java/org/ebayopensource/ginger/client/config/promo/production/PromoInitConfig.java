package org.ebayopensource.ginger.client.config.promo.production;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
		return "http://www.promocamp.stratus.ebay.com/promocamp/sampleResource/v1";
	}

}
