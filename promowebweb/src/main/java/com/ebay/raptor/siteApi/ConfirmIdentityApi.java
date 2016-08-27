package com.ebay.raptor.siteApi;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.ConfirmIdentityResponse;
import com.ebay.raptor.siteApi.util.XMLMarshallUtil;

/**
 * 
 * @author lyan2
 */
@Component
public class ConfirmIdentityApi extends AbstractSiteApi {
	private static final String CALLNAME = "ConfirmIdentity";
	private static final Logger logger = Logger.getInstance(ConfirmIdentityApi.class);
	
	/**
	 * Get user name (not oracle id, just user name) from sessionId by calling site service. 
	 * It's used to confirm request parameter "username" corresponding to this sessionId. 
	 * @param sessionId
	 * @return
	 * @throws CallSiteApiException
	 * @throws XmlParseException
	 */
	public ConfirmIdentityResponse getUserID(String sessionId)
			throws CallSiteApiException {
		if (sessionId == null || sessionId.isEmpty()) return null;
		
		ConfirmIdentityResponse response = null;
		try {
			response = (ConfirmIdentityResponse) callSiteAPI(
					CALLNAME, buildRequest(sessionId), true);
		} catch (com.ebay.raptor.siteApi.exception.CallSiteApiException e) {
			logger.log(LogLevel.WARN, "Failed to call ConfirmIdentity API, reason: " + e.getMessage());
		}
		return response;
	}
	
	private String buildRequest (String sessionId) {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
		.append("<ConfirmIdentityRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">")
		.append("<SessionID>").append(sessionId).append("</SessionID>")
		.append("<ErrorLanguage>en_US</ErrorLanguage>")
		.append("</ConfirmIdentityRequest>");

		return sb.toString();
	}

	@Override
	protected ConfirmIdentityResponse getResponseObjFromStream(InputStream inputStream) {
		ConfirmIdentityResponse obj = null;
		try {
			obj = XMLMarshallUtil.unmarshall(inputStream, ConfirmIdentityResponse.class);
		} catch (JAXBException e) {
			logger.log(LogLevel.WARN, "Failed to unmarshall ConfirmIdentify response.");
		}
		return obj;
	}

}
