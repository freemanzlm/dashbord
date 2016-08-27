package com.ebay.raptor.siteApi;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.FetchTokenResponse;
import com.ebay.raptor.siteApi.util.XMLMarshallUtil;

/**
 * 
 * @author lyan2
 */
@Component
public class FetchTokenApi extends AbstractSiteApi {
	private static final String FETCHTOKEN_API = "FetchToken";
	private static final Logger logger = Logger.getInstance(FetchTokenApi.class);

	/**
	 * Fetch the unique token by session id from eBay site API.
	 * @param sessionId
	 * @return
	 * @throws CallSiteApiException
	 * @throws XmlParseException
	 */
	public FetchTokenResponse fetchToken(String sessionId)
			throws CallSiteApiException {
		if (sessionId == null || sessionId.isEmpty()) return null;
		
		FetchTokenResponse response = null;
		try {
			response = (FetchTokenResponse) callSiteAPI(
					FETCHTOKEN_API, buildFetchTokenContent(sessionId, false), true);
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.WARN, "Failed to call FetchToken API, reason: " + e.getMessage());
		}
		return response;
	}

	@Override
	protected FetchTokenResponse getResponseObjFromStream(
			InputStream inputStream) {
		FetchTokenResponse obj = null;
		try {
			obj = XMLMarshallUtil.unmarshall(inputStream, FetchTokenResponse.class);
		} catch (JAXBException e) {
			logger.log(LogLevel.WARN, "Failed to unmarshall fetchToken response.");
		}
		return obj;
	}

	private String buildFetchTokenContent (String sessionId, boolean debug) {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<FetchTokenRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">")
			.append("<SessionID>").append(sessionId).append("</SessionID>")
			.append("<ErrorLanguage>en_US</ErrorLanguage>")
			.append("<WarningLevel>").append(debug ? "High" : "Low").append("</WarningLevel>")
			.append("</FetchTokenRequest>");

		return sb.toString();
	}

}
