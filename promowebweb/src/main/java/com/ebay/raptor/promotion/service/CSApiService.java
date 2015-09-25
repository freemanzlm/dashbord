package com.ebay.raptor.promotion.service;

import java.io.ByteArrayInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.csApi.GetItemDetailResponse;
import com.ebay.app.raptor.promocommon.csApi.GetTokenResponse;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.app.raptor.promocommon.util.EnviromentUtil;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.app.raptor.promocommon.xml.XmlParseException;
import com.ebay.app.raptor.promocommon.xml.XmlParser;
import com.ebay.integ.dal.dao.FinderException;
import com.ebay.integ.user.User;
import com.ebay.integ.user.UserDAO;
import com.ebay.raptor.geo.utils.CountryEnum;

public class CSApiService {

	private static final CommonLogger _logger = CommonLogger.getInstance(CSApiService.class);

	private static final String API_URL = "https://apics.vip.ebay.com/ws/websvc/eBayCSAPI";
	private static final String API_USERNAME = "cn_cbt_sv_batch"; // TODO - persist in DB
	private static final String API_PASSWORD = "11aa!!AA";
	private static final String API_ID = "EBAYSLCTNSS2VCD9FGBWF7K53IIFIS";
	private static final String DEV_ID = "Z938W1651D97433L537737638H8289";
	private static final String AUTH_CERT = "P1HE1AE7LE1$2C31H57DH-EN943I68";

	private static final String GETTOKEN_API = "CSGetToken";
	private static final String GETUSER_API = "CSGetUserCore";
	private static final String GETITEMDETAIL_API = "CSGetItemDetail";
	
//	private String token;
//	private Date tokenExpiration;

	@Autowired private HttpRequestService httpRequestService;
	
	/**
	 * Get the token to access CS APIs.
	 *
	 * @return
	 */
	public String getToken () {
		// get token if no token exists or token has expired
		GetTokenResponse tokenInfor = initToken();

		if (tokenInfor != null && "Success".equalsIgnoreCase(tokenInfor.getAck())) {
			return tokenInfor.geteBayAuthToken();
		} else {
			_logger.error("Unable to get user token.");
		}

		return "";
	}

	public String getUserIdByNameDAL(String userName){
		try {
			User usr = UserDAO.getInstance().findCompactUserByName(userName, true, true);
			_logger.error("Load user ID by user name via DAL, uname: " + userName);
			return usr.getUserId() + "";
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public String getUserCountryByNameDAL(String userName){
		try {
			User usr = UserDAO.getInstance().findCompactUserByName(userName, true, true);
			_logger.error("Load user COUNTRY by user name via DAL, uname: " + userName);
			return CountryEnum.get(usr.getCountryId()).getName();
		} catch (FinderException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * Get user id by the user name.
	 *
	 * @param userName    The user name
	 * @return
	 */
	public String getUserIdByName (String userName) {
		//For production env, use dal instead of service
		if(EnviromentUtil.isProduction()){
			return getUserIdByNameDAL(userName);
		}
		
		String token = getToken();

		String url = API_URL + "?callname=" + GETUSER_API;
		String soapMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
				+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<soapenv:Header>\n"
				+ "<RequesterCredentials soapenv:mustUnderstand=\"0\" xmlns=\"urn:ebay:apis:eBLBaseComponents\"><eBayAuthToken>"
				+ token
				+ "</eBayAuthToken></RequesterCredentials>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<CSGetUserCoreRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
				+ "<Version>999</Version>"
				+ "<OutputSelector>IpGeoLocation</OutputSelector>" + "<UserID>"
				+ userName + "</UserID>" + "</CSGetUserCoreRequest>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";


		Map <String, String> headers = new HashMap <String, String> ();
		headers.put("Content-Type","text/xml; charset=utf-8");
		headers.put("SOAPAction","");

		try {
			String xmlStr = httpRequestService.doHttpRequest(url,
					HttpRequestService.POST_METHOD, soapMessage, headers);

			if (!StringUtil.isEmpty(xmlStr)) {
				int start = xmlStr.indexOf("<UserId>");
				int end = xmlStr.indexOf("</UserId>");
				if (start != -1 && end != -1) {
					String userId = xmlStr.substring(start + 8, end);
					return userId.trim();
				}
			}
		} catch (HttpRequestException e) {
			_logger.error("Unable to call CSGetUserCore API.", e);
		}

		return null;
	}
	
	
	
	/**
	 * Get user region by the user name.
	 *
	 * @param userName    The user name
	 * @return
	 */
	public String getUserCountryByName (String name) {
		if(EnviromentUtil.isProduction()){
			return getUserCountryByNameDAL(name);
		}
		String token = getToken();

		String url = API_URL + "?callname=" + GETUSER_API;
		String soapMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
				+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
				+ "<soapenv:Header>\n"
				+ "<RequesterCredentials soapenv:mustUnderstand=\"0\" xmlns=\"urn:ebay:apis:eBLBaseComponents\"><eBayAuthToken>"
				+ token
				+ "</eBayAuthToken></RequesterCredentials>"
				+ "</soapenv:Header>"
				+ "<soapenv:Body>"
				+ "<CSGetUserCoreRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
				+ "<Version>999</Version>"
				+ "<OutputSelector>IpGeoLocation</OutputSelector>" + "<UserID>"
				+ name + "</UserID>" + "</CSGetUserCoreRequest>"
				+ "</soapenv:Body>" + "</soapenv:Envelope>";


		Map <String, String> headers = new HashMap <String, String> ();
		headers.put("Content-Type","text/xml; charset=utf-8");
		headers.put("SOAPAction","");

		try {
			String xmlStr = httpRequestService.doHttpRequest(url,
					HttpRequestService.POST_METHOD, soapMessage, headers);

			if (!StringUtil.isEmpty(xmlStr)) {
				int start = xmlStr.indexOf("<Country>");
				int end = xmlStr.indexOf("</Country>");
				if (start != -1 && end != -1) {
					String country = xmlStr.substring(start + 9, end);
					return country.trim();
				}
			}
		} catch (HttpRequestException e) {
			_logger.error("Unable to call CSGetUserCore API.", e);
		}

		return null;
	}
	
	/**
     * Get Item detail data by the item id.
     *
     * @param itemId    The Item Id
     * @return
     */
    public GetItemDetailResponse getItemDetail (long itemId) {
        String token = getToken();

        String url = API_URL + "?callname=" + GETITEMDETAIL_API;
        String soapMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
                + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\""
                + " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\""
                + " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">"
                + "<soapenv:Header>\n"
                + "<RequesterCredentials soapenv:mustUnderstand=\"0\" xmlns=\"urn:ebay:apis:eBLBaseComponents\"><eBayAuthToken>"
                + token
                + "</eBayAuthToken></RequesterCredentials>"
                + "</soapenv:Header>"
                + "<soapenv:Body>"
                + "<CSGetItemDetailRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">"
                + "<Version>999</Version>"
                + "<OutputSelector>ItemHeader.ItemID</OutputSelector>"
                + "<OutputSelector>ItemHeader.ItemTitle</OutputSelector>"
                + "<OutputSelector>ItemHeader.ItemStatus</OutputSelector>"
                + "<OutputSelector>ItemHeader.Seller.LongId</OutputSelector>"
                + "<OutputSelector>ItemHeader.Seller.DisplayLoginName</OutputSelector>"
                + "<ItemID>" + itemId + "</ItemID>"
                + "</CSGetItemDetailRequest>"
                + "</soapenv:Body>"
                + "</soapenv:Envelope>";


        Map <String, String> headers = new HashMap <String, String> ();
        headers.put("Content-Type","text/xml; charset=utf-8");
        headers.put("SOAPAction","");

        try {
            String xmlStr = httpRequestService.doHttpRequest(url,
                    HttpRequestService.POST_METHOD, soapMessage, headers);

            if (!StringUtil.isEmpty(xmlStr)) {
                return XmlParser.parseXmlToObject(
                        new ByteArrayInputStream(xmlStr.getBytes()),
                        GetItemDetailResponse.class, "ItemHeader");
            }
        } catch (HttpRequestException e) {
            _logger.error("Unable to call CSGetToken API.", e);
        } catch (XmlParseException e) {
            _logger.error("Unable to parse the response from CSGetToken API.", e);
        }

        return null;
    }

	/**
	 * Get the initialized token information.
	 *
	 * @return
	 */
	private GetTokenResponse initToken () {
		String url = API_URL + "?callname=" + GETTOKEN_API;
		String soapMessage = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"
				+ "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\""
				+ " xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\""
				+ " xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n"
				+ "<soap:Header>\n"
				+ "<RequesterCredentials xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n"
				+ "<Credentials>\n"
				+ "<AppId>" + API_ID + "</AppId>\n"
				+ "<DevId>" + DEV_ID + "</DevId>\n"
				+ "<AuthCert>" + AUTH_CERT + "</AuthCert>\n"
				+ "<Username>" + API_USERNAME + "</Username>\n"
				+ "<Password>" + API_PASSWORD +"</Password>\n"
				+ "</Credentials>\n"
				+ "</RequesterCredentials>\n"
				+ "</soap:Header>\n"
				+ "<soap:Body>\n"
				+ "<CSGetTokenRequest xmlns=\"urn:ebay:apis:eBLBaseComponents\">\n"
				+ "<Version>999</Version>\n" + "</CSGetTokenRequest>\n"
				+ "</soap:Body>\n" + "</soap:Envelope>\n";

		Map <String, String> headers = new HashMap <String, String> ();
		headers.put("Content-Type","text/xml; charset=utf-8");
		headers.put("SOAPAction","");

		try {
			String xmlStr = httpRequestService.doHttpRequest(url,
					HttpRequestService.POST_METHOD, soapMessage, headers);

			if (!StringUtil.isEmpty(xmlStr)) {
				return XmlParser.parseXmlToObject(
						new ByteArrayInputStream(xmlStr.getBytes()),
						GetTokenResponse.class, "CSGetTokenResponse");
			}
		} catch (HttpRequestException e) {
			_logger.error("Unable to call CSGetToken API.", e);
		} catch (XmlParseException e) {
			_logger.error("Unable to parse the response from CSGetToken API.", e);
		}

		return null;
	}

	private boolean isDateExpired (Date dt) {
		// date is empty or current date is not earlier than the date minus 1h. 
		if (dt == null || dt.getTime() - new Date().getTime() < 3600000) {
			return true;
		}
		return false;
	}
}
