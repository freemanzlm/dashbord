package com.ebay.raptor.promotion.util;

import com.ebay.app.raptor.promocommon.util.CommonUtil;

public class PromotionUtil {
	public static String _promoUrlPrefix = "";
	public static String _promoServicePrefix = "";

	static {
		if (CommonUtil.isProduction()) {
		    // TODO -
		    _promoUrlPrefix = "";
			_promoServicePrefix = "";
		} else {
		    _promoUrlPrefix = "";
            _promoServicePrefix = "test";
		}
	}
}
