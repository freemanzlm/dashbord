package com.ebay.raptor.promotion.config;

import com.ebay.kernel.context.AppBuildConfig;
/**
 * 
 * @author lyan2
 *
 */
public class AppConfig {

	public  static String SELLER_DASHBOARD_URL = "http://cbtreport.ebay.com.hk/";
    public  static String BIZ_REPORT_URL = "http://biz.ebay.com.hk/bizreportweb/index";
    
    /*
     * LOGIN_URL and REFER_PARAM are very important for login.
     * REFER_PARAM indicates where to when when user login system.
     */
    public final static String LOGIN_URL = "http://cbtreport.ebay.com.hk/dashboard/login";
    public final static String REFER_PARAM = "referUrl";
    
	private static String sellerDashboardServicePrefix = "";
	private static String bizReportServicePrefix = "";
	
	public static AppBuildConfig bdCfg = AppBuildConfig.getInstance();
	
	static {

		if (bdCfg.isPreProd()) { 
			// TODO 
		} else if (bdCfg.isProduction()) {
			sellerDashboardServicePrefix = "http://cbtsdws2.stratus.ebay.com";
			bizReportServicePrefix = "http://www.bizser.stratus.ebay.com";
		} else if ("staging".equals(bdCfg.getPoolType())) {
			sellerDashboardServicePrefix = "http://cbtsdws2-2.stratus.qa.ebay.com";
			SELLER_DASHBOARD_URL="http://dashboard-4.stratus.qa.ebay.com/";
			BIZ_REPORT_URL="http://anonymous-1-bizreport-envf7jokk59vh.vip.stratus.qa.ebay.com/bizreportweb/index";
			bizReportServicePrefix = "http://anonymous-1-sdbrws-envf7jokk59vh.vip.stratus.qa.ebay.com";
		} else if (bdCfg.isQATE()) {
			// feature pool or staging pool
			sellerDashboardServicePrefix = "http://cbtsdws2-2.stratus.qa.ebay.com";
			bizReportServicePrefix = "http://sdbrws-2.stratus.qa.ebay.com";
		} else if (bdCfg.isDev()) {
			sellerDashboardServicePrefix = "http://cbtsdws2-2.stratus.qa.ebay.com";
			bizReportServicePrefix = "http://anonymous-1-sdbrws-envf7jokk59vh.vip.stratus.qa.ebay.com";
			BIZ_REPORT_URL="http://L-SHC-15008822.corp.ebay.com:8030/bizreportweb/index";
			SELLER_DASHBOARD_URL="http://L-SHC-15008822.corp.ebay.com:8090/";
		} else {
			sellerDashboardServicePrefix = "http://cbtsdws2-2.stratus.qa.ebay.com";
			bizReportServicePrefix = "anonymous-1-sdbrws-envf7jokk59vh.vip.stratus.qa.ebay.com";
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
	
	public static String getSellerDashboardServicePrefix() {
		return sellerDashboardServicePrefix;
	}
	public static void setSellerDashboardServicePrefix(
			String sellerDashboardServicePrefix) {
		AppConfig.sellerDashboardServicePrefix = sellerDashboardServicePrefix;
	}

	public static String getSELLER_DASHBOARD_URL() {
		return SELLER_DASHBOARD_URL;
	}

	public static void setSELLER_DASHBOARD_URL(String sELLER_DASHBOARD_URL) {
		SELLER_DASHBOARD_URL = sELLER_DASHBOARD_URL;
	}

	public static String getBIZ_REPORT_URL() {
		return BIZ_REPORT_URL;
	}

	public static void setBIZ_REPORT_URL(String bIZ_REPORT_URL) {
		BIZ_REPORT_URL = bIZ_REPORT_URL;
	}
	
	
}
