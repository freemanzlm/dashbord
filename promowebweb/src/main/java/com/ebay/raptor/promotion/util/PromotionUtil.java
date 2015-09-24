package com.ebay.raptor.promotion.util;

import com.ebay.app.raptor.promocommon.util.CommonUtil;

public class PromotionUtil {
	public static final String USD_CURRENCY = "USD";
	public static final String LOGIN_URL = "http://www.ebay.cn/maisha/sellercenter/performancetool/test.php";

	public static String _promoUrlPrefix = "";
	public static String _promoServicePrefix = "";

	static {
		if (CommonUtil.isProduction()) {
		    // TODO -
		    _promoUrlPrefix = "http://somehost";
			_promoServicePrefix = "http://somehost";
		} else {
		    _promoUrlPrefix = "http://somehost";
            _promoServicePrefix = "http://somehost";
		}
	}
}
