package com.ebay.raptor.siteApi;

import com.ebay.kernel.context.AppBuildConfig;

public class EnvironmentUtil {

	private static AppBuildConfig bdCfg = AppBuildConfig.getInstance();
	
	/**
	 * Check if the service is production service.
	 *
	 * @return
	 */
	public static boolean isProduction () {
		return bdCfg.isProduction();
	}
}
