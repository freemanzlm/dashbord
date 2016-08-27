package com.ebay.raptor.promotion.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.kernel.util.FastURLEncoder;
import com.ebay.raptor.siteApi.AbstractSiteApi;
import com.ebay.raptor.siteApi.FetchTokenApi;
import com.ebay.raptor.siteApi.GetSessionIdApi;
import com.ebay.raptor.siteApi.GetTokenStatusApi;
import com.ebay.raptor.siteApi.GetUserApi;
import com.ebay.raptor.siteApi.response.FetchTokenResponse;
import com.ebay.raptor.siteApi.response.GetTokenStatusResponse;
import com.ebay.raptor.siteApi.response.GetTokenStatusResponse.TokenStatus;
import com.ebay.raptor.siteApi.response.GetUserResponse;

/**
 * 
 * @author lyan2
 */
@Service
public class SiteAPIService {
	private static final Logger logger = Logger.getInstance(SiteAPIService.class);
	
	@Autowired GetSessionIdApi getSessionIdApi;
	@Autowired FetchTokenApi fetchTokenApi;
	@Autowired GetTokenStatusApi getTokenStatusApi;
	@Autowired GetUserApi getUserApi;
	
	/**
	 * Get a session id from eBay developer site API.
	 * @return
	 */
	public String getSessionId () {
		try {
			return getSessionIdApi.getSessionId();
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.ERROR, "Unable to get the session ID", e);
		}
		return null;
	}
	
	/**
	 * Generate a site sign in URL by sessionID (Got from GetSessionID API).
	 * @param sessionId
	 * @return sign in URL
	 */
	public String getSiteSignInUrl (String sessionId) {
		
		String url = String.format("%s?SignIn&RuName=%s&SessID=%s",
				AbstractSiteApi.getSiteInfo().getSiteSignInUrl(), getSessionIdApi.getRuName(), FastURLEncoder.encode(sessionId));

		return url;
	}
	
	/**
	 * Check if passed url is eBay signin page.
	 * @param url
	 * @return
	 */
	public boolean isSiteSignInUrl (String url) {
		if (url == null || url.isEmpty()) {
			return false;
		}

		return url.contains(AbstractSiteApi.getSiteInfo().getSiteSignInUrl());
	}
	
	/**
	 * Get token by sessionID
	 * @param sessionId
	 * @return
	 */
	public String getToken (String sessionId) {
		try {
			FetchTokenResponse response = fetchTokenApi.fetchToken(sessionId);
			if (response != null) {
				if (!"Failure".equalsIgnoreCase(response.getAck()) && response.getErrors() == null) {
					return response.geteBayAuthToken();
				} else {
					logger.log(LogLevel.ERROR, String.format(
							"Getting user token response error, with error message: %s",
							response.getErrors().getLongMessage()));
				}
			}
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.ERROR, "Unable to get token.", e);
		}

		return null;
	}
	
	/**
	 * Get token status by calling site api: GetTokenStatus.
	 * @param token
	 * @return
	 */
	public TokenStatus getTokenStatus(String token) {
		try {
			GetTokenStatusResponse response = getTokenStatusApi.getTokenStatus(token);
			if (response != null) {
				if (!"Failure".equalsIgnoreCase(response.getAck()) && response.getErrors() == null) {
					return response.getTokenStatus();
				} else {
					logger.log(LogLevel.ERROR, String.format(
							"Unable to get token status from GetTokenStatus response, with error message: %s",
							response.getErrors().getLongMessage()));
				}
			}
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.ERROR, "Failed to get token status by calling GetTokenStatus, detail : " + e.getMessage());
		}

		return null;
	}
		
	/**
	 * Get user, this user contains user name, user id and user email.
	 * @param token
	 * @return
	 */
	public GetUserResponse.User getUser(String token) {
		GetUserResponse.User user = null;

		try {
			GetUserResponse response = getUserApi.getUser(token);
			if (response != null && "Success".equalsIgnoreCase(response.getAck())) {
				user = response.getUser();
			}
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.ERROR, "Failed to get User.", e);
		}
		
		return user;
	}	
}
