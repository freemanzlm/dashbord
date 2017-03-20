package com.ebay.raptor.promotion.service;

import org.apache.commons.httpclient.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.app.raptor.promocommon.httpRequest.HttpResponseData;
import com.ebay.kernel.context.AppBuildConfig;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.pojo.PGCSeller;
import com.google.gson.reflect.TypeToken;

/**
 * Service about public greate china.
 * 
 * @author lyan2
 *
 */
@Service
public class PGCService {
	private static Logger logger = Logger.getInstance(PGCService.class);
	
	@Autowired HttpRequestService httpRequestService;
	
	public static String serviceUrlPrefix = null;
	private static String pgcAccountUrl;
	//private static String authorization;
	
	private static AppBuildConfig bdCfg = AppBuildConfig.getInstance();
	
	static {
		if (bdCfg.isProduction()) {
			serviceUrlPrefix = "http://pgcservice.ebay.com";
		} else if ("feature".equalsIgnoreCase(bdCfg.getPoolType())) {
			serviceUrlPrefix = "http://pgc-phx-1-web-envf7jokk59vh.stratus.phx.qa.ebay.com";
		} else if (bdCfg.isDev()) {
			serviceUrlPrefix = "http://pgc-phx-1-web-envf7jokk59vh.stratus.phx.qa.ebay.com";
		} else {
			// default is staging pool
			serviceUrlPrefix = "http://pgc-phx-1-web-envf7jokk59vh.stratus.phx.qa.ebay.com";
		}
		
		pgcAccountUrl = serviceUrlPrefix + "/pgc/v1/resource/getquota/userid/";
	}
	
	/**
	 * Get PGC account information.
	 * @param userId
	 * @return
	 * @throws HttpException
	 * @throws HttpRequestException 
	 */
	public PGCSeller getPGCAccount(String userId) throws HttpRequestException {
		//Map<String, String> headers = new HashMap<String, String>();
		//headers.put("Authorization", authorization);
		
		String json = httpRequestService.doHttpRequest(pgcAccountUrl+ userId, "GET", null, null);

		TypeToken<HttpResponseData<PGCSeller>> type = new TypeToken<HttpResponseData<PGCSeller>>() {};
		HttpResponseData<PGCSeller> response = httpRequestService.getResponseData(json, type);

		if (response != null && response.getData() !=null) {
				return response.getData();
		}else {
			logger.log(LogLevel.WARN, "Failed to get PGC account, with error msg: " + response.getErrorMmsg());
		} 

		if (json == null || json.isEmpty()) {
			logger.log(LogLevel.ERROR, "No response is got when to get PGC account.");
		}
		
		return null;
	}
}
