package org.ebayopensource.ginger.client.config.promo.production;

import org.ebayopensource.ginger.client.config.promo.BasePromoInitConfig;

public class PromoInitConfig extends BasePromoInitConfig{


	@Override
	public String getEndPoint() {
//		return "http://phx5qa01c-ee86.stratus.phx.qa.ebay.com:8080/promocamp/sampleResource/v1";
		return "http://localhost:8080/promocamp/sampleResource/v1";
	}

}
