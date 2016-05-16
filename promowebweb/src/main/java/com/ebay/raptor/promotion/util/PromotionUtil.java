package com.ebay.raptor.promotion.util;

import com.ebay.app.raptor.promocommon.util.CommonUtil;
import com.ebay.kernel.context.AppBuildConfig;

public class PromotionUtil {
	public static final String USD_CURRENCY = "USD";
	public static final String LOGIN_URL = "http://cbtreport.ebay.com.hk/dashboard/login";
	public static final String LISTING_TEMP_PASS = "password";
	public static final String LANG_REQUEST_PARAMETER_KEY = "lang";
	public final static String REFER_PARAM = "referUrl";

	public static String _promoUrlPrefix = "";
	public static String _promoServicePrefix = "";
	public static String _sellerDashboardServicePrefix = "";

	private static AppBuildConfig bdCfg = AppBuildConfig.getInstance();

	static {
		if (CommonUtil.isProduction()) {
			// TODO -
			_promoUrlPrefix = "http://cbtpromo.ebay.com.hk";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://www.cbtsdws.stratus.ebay.com/sdt/secureResource/v1/";
		} else {
			_promoUrlPrefix = "http://L-SHC-00437469.corp.ebay.com:9080";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://phx5qa01c-8a26.stratus.phx.qa.ebay.com:8080/sdt/secureResource/v1/";
		}

		if (bdCfg.isPreProd()) {
			// TODO
		} else if (bdCfg.isProduction()) {
			_promoUrlPrefix = "http://cbtpromo.ebay.com.hk";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://www.cbtsdws.stratus.ebay.com/sdt/secureResource/v1/";
		} else if ("staging".equals(bdCfg.getPoolType())) {
			_promoUrlPrefix = "http://L-SHC-00437469.corp.ebay.com:9080";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://phx5qa01c-8a26.stratus.phx.qa.ebay.com:8080/sdt/secureResource/v1/";
		} else if (bdCfg.isQATE()) {
			// feature pool or staging pool
			_promoUrlPrefix = "http://L-SHC-00437469.corp.ebay.com:9080";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://phx5qa01c-8a26.stratus.phx.qa.ebay.com:8080/sdt/secureResource/v1/";
		} else if (bdCfg.isDev()) {
			_promoUrlPrefix = "http://L-SHC-00437469.corp.ebay.com:9080";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://phx5qa01c-8a26.stratus.phx.qa.ebay.com:8080/sdt/secureResource/v1/";
		} else {
			_promoUrlPrefix = "http://L-SHC-00437469.corp.ebay.com:9080";
			_promoServicePrefix = "http://somehost";
			_sellerDashboardServicePrefix = "http://phx5qa01c-8a26.stratus.phx.qa.ebay.com:8080/sdt/secureResource/v1/";
		}
	}
}
