package com.ebay.raptor.siteApi;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.GetTokenStatusResponse;
import com.ebay.raptor.siteApi.util.XMLMarshallUtil;

/**
 * 
 * @author lyan2
 */
@Component
public class GetTokenStatusApi extends AbstractSiteApi {
	private static final Logger logger = Logger.getInstance(GetTokenStatusApi.class);
	private static final String CALLNAME = "GetTokenStatus";
	
	public GetTokenStatusResponse getTokenStatus(String token)
			throws CallSiteApiException {
		if (token == null || token.isEmpty()) return null;
		
		GetTokenStatusResponse response = null;
		try {
			response = (GetTokenStatusResponse) callSiteAPI(
					CALLNAME, buildRequestContent(token), true);
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.WARN, "Failed to call GetTokenStatus API, reason: " + e.getMessage());
		}
		return response;
	}

	@Override
	protected GetTokenStatusResponse getResponseObjFromStream(InputStream inputStream) {
		GetTokenStatusResponse obj = null;
		try {
			obj = XMLMarshallUtil.unmarshall(inputStream, GetTokenStatusResponse.class);
		} catch (JAXBException e) {
			logger.log(LogLevel.WARN, "Failed to unmarshall GetTokenStatus response.");
		}
		return obj;
	}

	private String buildRequestContent(String token) {
		StringBuilder sb = new StringBuilder(
				"<?xml version=\"1.0\" encoding=\"utf-8\"?>")
				.append("<GetTokenStatusRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">")
				.append("<RequesterCredentials>")
				.append("<eBayAuthToken>").append(token).append("</eBayAuthToken>")
				.append("</RequesterCredentials>")
				.append("</GetTokenStatusRequest>");

		return sb.toString();
	}
}
