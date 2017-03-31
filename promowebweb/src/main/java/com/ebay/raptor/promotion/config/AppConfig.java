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
    
	private static String sellerDashboardServicePrefix = "";
	private static String bizReportServicePrefix = "";
	
	public static AppBuildConfig bdCfg = AppBuildConfig.getInstance();
	
	static {

		if (bdCfg.isPreProd()) { 
			// TODO 
		} else if (bdCfg.isProduction()) {
			sellerDashboardServicePrefix = "http://cbtsdws2.stratus.ebay.com";
			bizReportServicePrefix = "http://www.bizser.stratus.ebay.com/br/v1/";
		} else if ("staging".equals(bdCfg.getPoolType())) {
			sellerDashboardServicePrefix = "http://cbtsdws2.qa.ebay.com/";
			bizReportServicePrefix = "http://sdbrws-2.stratus.qa.ebay.com/br/v1/";
		} else if (bdCfg.isQATE()) {
			// feature pool or staging pool
			sellerDashboardServicePrefix = "http://cbtsdws2.qa.ebay.com/";
			bizReportServicePrefix = "http://sdbrws-2.stratus.qa.ebay.com/br/v1/";
		} else if (bdCfg.isDev()) {
			sellerDashboardServicePrefix = "http://l-shc-15008800.corp.ebay.com/:8180/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://sdbrws.stratus.qa.ebay.com/br/v1/";
		} else {
			sellerDashboardServicePrefix = "http://cbtsdws2.qa.ebay.com/sdt/secureResource/v1/";
			bizReportServicePrefix = "http://cbtsdws2.qa.ebay.com/";
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
	
}
