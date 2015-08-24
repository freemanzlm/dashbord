package org.ebayopensource.ginger.client.config.promo.feature;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
		return "http://phx5qa01c-ee86.stratus.phx.qa.ebay.com:8080/promoservice/v1";
	}

}
