package com.ebay.raptor.promotion.config;

import com.ebay.kernel.context.AppBuildConfig;
/**
 * 
 * @author lyan2
 *
 */
public class AppConfig {

	public final static String SELLER_DASHBOARD_URL = "http://cbtreport.ebay.com.hk/";
    public final static String BIZ_REPORT_URL = "http://biz.ebay.com.hk/bizreportweb/index";
    
    /*
     * LOGIN_URL and REFER_PARAM are very important for login.
     * REFER_PARAM indicates where to when when user login system.
     */
    public final static String LOGIN_URL = "http://cbtreport.ebay.com.hk/dashboard/login";
    public final static String REFER_PARAM = "referUrl";
    
    private static String promoUrlPrefix = "";
	private static String sellerDashboardServicePrefix = "";
	private static String bizReportServicePrefix = "";
	
	private static AppBuildConfig bdCfg = AppBuildConfig.getInstance();
	
	static {

		if (bdCfg.isPreProd()) {
			// TODO
		} else if (bdCfg.isProduction()) {
			promoUrlPrefix = "http://cbtpromo.ebay.com.hk";
			sellerDashboardServicePrefix = "http://www.cbtsdws.stratus.ebay.com/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://www.bizser.stratus.ebay.com/br/v1/";
		} else if ("staging".equals(bdCfg.getPoolType())) {
			promoUrlPrefix = "http://promoweb-2.stratus.qa.ebay.com";
			sellerDashboardServicePrefix = "http://cbtsdws2.qa.ebay.com/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://sdbrws-2.stratus.qa.ebay.com/br/v1/";
		} else if (bdCfg.isQATE()) {
			// feature pool or staging pool
			promoUrlPrefix = "http://promoweb-2.stratus.qa.ebay.com";
			sellerDashboardServicePrefix = "http://cbtsdws2.qa.ebay.com/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://sdbrws-2.stratus.qa.ebay.com/br/v1/";
		} else if (bdCfg.isDev()) {
			promoUrlPrefix = "http://L-SHC-15008575.corp.ebay.com:7080";
			sellerDashboardServicePrefix = "http://L-SHC-15008575.corp.ebay.com:8080/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://sdbrws.stratus.qa.ebay.com/br/v1/";
		} else {
			promoUrlPrefix = "http://promoweb-2.stratus.qa.ebay.com";
			sellerDashboardServicePrefix = "http://cbtsdws2.qa.ebay.com/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://sdbrws-2.stratus.qa.ebay.com/br/v1/";
		}
	}
	
	public static String getBizReportServicePrefix() 
	{
		return bizReportServicePrefix;
	}
	
	public static void setBizReportServicePrefix(String bizReportServicePrefix) 
	{
		AppConfig.bizReportServicePrefix = bizReportServicePrefix;
	}
	
	public static String getPromoUrlPrefix() {
		return promoUrlPrefix;
	}
	public static void setPromoUrlPrefix(String promoUrlPrefix) {
		AppConfig.promoUrlPrefix = promoUrlPrefix;
	}
	public static String getSellerDashboardServicePrefix() {
		return sellerDashboardServicePrefix;
	}
	public static void setSellerDashboardServicePrefix(
			String sellerDashboardServicePrefix) {
		AppConfig.sellerDashboardServicePrefix = sellerDashboardServicePrefix;
	}
	
}
