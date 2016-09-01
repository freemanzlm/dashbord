package com.ebay.raptor.promotion.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.app.raptor.promocommon.httpRequest.HttpResponseData;
import com.ebay.raptor.promotion.config.AppConfig;
import com.google.gson.reflect.TypeToken;

/**
 * @author binkang
 * @date 2016-9-1
 */
@Service
public class BRDataService 
{
	protected static final String GET_METHOD = "GET";
	
	private static final CommonLogger logger = CommonLogger.getInstance(BRDataService.class);
	
	protected static String authorization = "";
    
	@Autowired
    protected HttpRequestService httpRequestService;
	
    private static String addInWhitelistUrl = "subscription/addInWhitelist/user/{userId}/whitelistType/{whitelistType}";
    private static String subscribeDialogClosedUrl = "subscription/subscribeDialogClosed/user/{userId}";
    private static String getBizSubscribeMsgUrl = "subscription/subscriptionMsg/user/{userId}";
    
    public Map<String, Boolean> getSubscriptionMsg(Long userId) throws HttpRequestException {
		String json = httpRequestService.doHttpRequest(buildServiceUrl(AppConfig.getBizReportServicePrefix(), getBizSubscribeMsgUrl, "{userId}", userId), GET_METHOD, null, prepareHeaders());
		TypeToken<HttpResponseData<Map<String, Boolean>>> type = new TypeToken<HttpResponseData<Map<String, Boolean>>>(){};
		HttpResponseData<Map<String, Boolean>> response = httpRequestService.getResponseData(json, type);
		
		if(response != null)
		{
			if(response.getIsSuccess())
			{
				return response.getData();
			}
			else
			{
				logger.error(String.format("Get subscription messages with [%d] failed, with error message: %s", userId, response.getErrorMmsg()));
			}
		}
		else
		{
			logger.error("Unable to call getSubscriptionMsg API.");
		}
		return null;
	}

	public boolean addWhitelist(long userId, int whitelistType) throws HttpRequestException {
		String json = httpRequestService.doHttpRequest(buildServiceUrl(AppConfig.getBizReportServicePrefix(), addInWhitelistUrl, "{userId}", userId, "{whitelistType}", whitelistType)
				, GET_METHOD, null, prepareHeaders());
		TypeToken<HttpResponseData<String>> type = new TypeToken<HttpResponseData<String>>() {};
		HttpResponseData<String> response = httpRequestService.getResponseData(json, type);

		boolean result = false;
		if (response != null) 
		{
			result = response.getIsSuccess();
			if (!result) 
			{
				logger.error(String.format("Add whitelist with [%d] failed, with error message: %s",
								userId, response.getErrorMmsg()));
			}
		} 
		else 
		{
			logger.error("Unable to call add whitelist API.");
		}
        return result;
	}

	public boolean subscribeDialogClosed(long userId) throws HttpRequestException {
		String json = httpRequestService.doHttpRequest(buildServiceUrl(AppConfig.getBizReportServicePrefix(), subscribeDialogClosedUrl, "{userId}", userId),
				GET_METHOD, null, prepareHeaders());
		TypeToken<HttpResponseData<Boolean>> type = new TypeToken<HttpResponseData<Boolean>>(){};
		HttpResponseData<Boolean> response = httpRequestService.getResponseData(json, type);
		
		boolean result = false;
		if(response != null)
		{
			result = response.getIsSuccess();
			if(!result)
			{
				logger.error(String.format("Persist subscription dialog closed to DB with [%d] failed, whith error message: s%" , userId, response.getErrorMmsg()));
			}
		}
		else
		{
			logger.error("Unable to call subscribeDialogClosed API.");
		}
		return result;
	}

	private Map<String, String> prepareHeaders(){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", authorization);
        return headers;
    }
	
	private String buildServiceUrl(String host, String baseUrl, Object... args){
        if(null != host && !"".equals(host)){
            for(int pos = 0; pos < args.length; pos = pos + 2){
                if(null == args[pos + 1]){
                    continue;
                }
                baseUrl = baseUrl.replace(args[pos].toString(), args[pos + 1].toString());
            }

            return host + baseUrl;
        } else {
            throw new RuntimeException("Failed to build the URL for " + baseUrl + ", host is null");
        }
    }
}
