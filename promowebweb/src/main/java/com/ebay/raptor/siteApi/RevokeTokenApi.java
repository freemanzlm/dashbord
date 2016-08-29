package com.ebay.raptor.siteApi;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.RevokeTokenResponse;
import com.ebay.raptor.siteApi.util.XMLMarshallUtil;

/**
 * 
 * @author lyan2
 */
@Component
public class RevokeTokenApi extends AbstractSiteApi{
	private static final String CALLNAME = "RevokeToken";
	private static final Logger logger = Logger.getInstance(RevokeTokenApi.class);

	/**
	 * Fetch the unique token by session id from eBay site API.
	 * @param sessionId
	 * @return
	 * @throws CallSiteApiException
	 * @throws XmlParseException
	 */
	public RevokeTokenResponse fetchToken(String token)
			throws CallSiteApiException {
		if (token == null || token.isEmpty()) return null;
		
		RevokeTokenResponse response = null;
		try {
			response = (RevokeTokenResponse) callSiteAPI(
					CALLNAME, buildRequest(token, false), true);
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.WARN, "Failed to get fetchToken response.");
		}
		return response;
	}

	@Override
	protected RevokeTokenResponse getResponseObjFromStream(
			InputStream inputStream) {
		RevokeTokenResponse obj = null;
		try {
			obj = XMLMarshallUtil.unmarshall(inputStream, RevokeTokenResponse.class);
		} catch (JAXBException e) {
			logger.log(LogLevel.WARN, "Failed to unmarshall fetchToken response.");
		}
		return obj;
	}

	private String buildRequest (String token, boolean debug) {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<RevokeTokenRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">")
			.append("<RequesterCredentials>")
			.append("<eBayAuthToken>").append(token).append("</eBayAuthToken>")
			.append("</RequesterCredentials>")
			.append("</RevokeTokenRequest>");

		return sb.toString();
	}
}
