package com.ebay.raptor.siteApi;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.GetUserResponse;
import com.ebay.raptor.siteApi.util.XMLMarshallUtil;

/**
 * With this api, you can get user name, user oracle id and user email from token (Got from FetchToken api).
 * 
 * @author lyan2
 */
@Component
public class GetUserApi extends AbstractSiteApi {
	private static final Logger logger = Logger.getInstance(GetUserApi.class);
	private static final String CALLNAME = "GetUser";

	public GetUserResponse getUser(String token)
			throws CallSiteApiException {
		if (token == null || token.isEmpty()) return null;
		
		GetUserResponse response = null;
		try {
			response = (GetUserResponse) callSiteAPI(
					CALLNAME, buildRequestContent(token), false);
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.WARN, "Failed to call GetUser API, reason: " + e.getMessage());
		}
		return response;
	}

	@Override
	protected GetUserResponse getResponseObjFromStream(InputStream inputStream) {
		GetUserResponse obj = null;
		try {
			obj = XMLMarshallUtil.unmarshall(inputStream, GetUserResponse.class);
		} catch (JAXBException e) {
			logger.log(LogLevel.WARN, "Failed to unmarshall GetUser response.");
		}
		return obj;
	}

	private String buildRequestContent(String token) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>")
				.append("<GetUserRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">")
				.append("<RequesterCredentials>")
				.append("<eBayAuthToken>").append(token).append("</eBayAuthToken>")
				.append("</RequesterCredentials>")
				.append("</GetUserRequest>");

		return sb.toString();
	}

}
