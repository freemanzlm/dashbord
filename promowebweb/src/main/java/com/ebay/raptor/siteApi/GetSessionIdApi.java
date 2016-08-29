package com.ebay.raptor.siteApi;

import java.io.InputStream;

import javax.xml.bind.JAXBException;

import org.springframework.stereotype.Component;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.GetSessionIDResponse;
import com.ebay.raptor.siteApi.util.XMLMarshallUtil;

/**
 * Get session id by RuName.
 * @author lyan2
 */
@Component
public class GetSessionIdApi extends AbstractSiteApi {
	private static final String GETSESSIONID_API = "GetSessionID";
	
	private static final Logger logger = Logger.getInstance(AbstractSiteApi.class);

	/**
	 * Get session id by RuName.
	 * @return
	 * @throws CallSiteApiException
	 */
	public String getSessionId() throws CallSiteApiException {
		GetSessionIDResponse response = (GetSessionIDResponse) callSiteAPI(
				GETSESSIONID_API, buildGetSessionIdContent(false), true);
		if (!"Failure".equalsIgnoreCase(response.getAck()) && response.getErrors() == null) {
			return response.getSessionID();
		} else {
			logger.log(LogLevel.ERROR, String.format(
					"Call API [%s] failed, with error message: %s",
					GETSESSIONID_API, response.getErrors().getLongMessage()));
		}
		return null;
	}
	
	public String getRuName () {
		return getSiteInfo().getRuName();
	}
	
	public String getSiteSignInUrl () {
		return getSiteInfo().getSiteSignInUrl();
	}
	
	private String buildGetSessionIdContent (boolean debug) {
		StringBuilder sb = new StringBuilder("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<GetSessionIDRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">")
			.append("<RuName>").append(getSiteInfo().getRuName()).append("</RuName>")
			.append("<ErrorLanguage>en_US</ErrorLanguage>")
			.append("<WarningLevel>").append(debug ? "High" : "Low").append("</WarningLevel>")
			.append("</GetSessionIDRequest>");

		return sb.toString();
	}

	@Override
	protected GetSessionIDResponse getResponseObjFromStream(
			InputStream inputStream) {
		GetSessionIDResponse obj = null;
		try {
			obj = XMLMarshallUtil.unmarshall(inputStream, GetSessionIDResponse.class);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

}
