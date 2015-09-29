package com.ebay.raptor.promotion.util;

import com.ebay.app.raptor.promocommon.util.CommonUtil;

public class PromotionUtil {
	public static final String USD_CURRENCY = "USD";
	public static final String LOGIN_URL = "http://www.ebay.cn/maisha/sellercenter/performancetool/test.php";

	public static String _promoUrlPrefix = "";
	public static String _promoServicePrefix = "";
	public static String _sellerDashboardServicePrefix = "";

	static {
		if (CommonUtil.isProduction()) {
		    // TODO -
		    _promoUrlPrefix = "http://cbtpromo.ebay.com.hk";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://www.cbtsdws.stratus.ebay.com/sdt/secureResource/v1/";
		} else {
		    _promoUrlPrefix = "http://localhost:8080";
            _promoServicePrefix = "http://somehost";
            _sellerDashboardServicePrefix = "http://phx5qa01c-8a26.stratus.phx.qa.ebay.com:8080/sdt/secureResource/v1/";
		}
	}
}
