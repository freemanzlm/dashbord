package com.ebay.raptor.promotion.service;

import java.beans.IntrospectionException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.csApi.GetItemDetailResponse;
import com.ebay.app.raptor.promocommon.csApi.GetTokenResponse;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestException;
import com.ebay.app.raptor.promocommon.httpRequest.HttpRequestService;
import com.ebay.app.raptor.promocommon.xml.XmlParseException;
import com.ebay.app.raptor.promocommon.xml.XmlParser;
import com.ebay.raptor.promotion.soap.CSSOAPMessageFactory;
import com.ebay.raptor.promotion.util.DateUtil;
import com.ebay.raptor.promotion.util.StringUtil;
import com.ebay.raptor.promotion.xml.DOMResolver;

public class CSApiService {

	private static final CommonLogger _logger = CommonLogger.getInstance(CSApiService.class);

	private static final String API_URL = "https://apics.vip.ebay.com/ws/websvc/eBayCSAPI";

	private static final String GETTOKEN_API = "CSGetToken";
	private static final String GETUSER_API = "CSGetUserCore";
	private static final String GETITEMDETAIL_API = "CSGetItemDetail";
	
	private static Date expiredTime;
	private static boolean tokenExpired = true;
	private String token;
	
	@Autowired private HttpRequestService httpRequestService;
	
	/**
	 * Get the token to access CS APIs.
	 *
	 * @return
	 */
	public String getToken () {
		// get token if no token exists or token has expired
		if (!isTokenExpired() && token != null) {
			return token;
		}
		
		GetTokenResponse tokenInfor = initToken();
		
		if (tokenInfor != null && "Success".equalsIgnoreCase(tokenInfor.getAck())) {
			
			/* Each time call CSGetToken API, response will include the HardExpirationTime. But we don't know which time zone it is. 
			 * So, we only use the time lag to infer the expiration time. */
			Date responseTime;
			try {
				responseTime = DateUtil.parseUTCDateTime(tokenInfor.getTimeStamp(), null);
				Date expectedExpiredTime = DateUtil.parseUTCDateTime(tokenInfor.getHardExpirationTime(), null);
				long lag = DateUtil.timeLag(responseTime, expectedExpiredTime);
				expiredTime = new Date();
				// reduce token life 5 minutes because there may have delay between CS server and client.
				expiredTime.setTime(expiredTime.getTime() + lag - 300);
				tokenExpired = false;
			} catch (ParseException e) {
				tokenExpired = true;
				e.printStackTrace();
			}

			token = tokenInfor.geteBayAuthToken();
			return token;
		} else {
			_logger.error("Unable to get user token.");
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
		String token = getToken();
		
		SOAPMessage soapMessage = null;
		String message = "";
		try {
			soapMessage = CSSOAPMessageFactory.createGetUserCoreByNameMessage(token, userName);
			message = CSSOAPMessageFactory.parseToString(soapMessage);
		} catch (SOAPException | IOException e1) {
			_logger.error("Unable to create CSGetUserCoreRequest SOAP message.", e1);
			return null;
		}

		String url = API_URL + "?callname=" + GETUSER_API;

		Map <String, String> headers = new HashMap <String, String> ();
		headers.put("Content-Type","text/xml; charset=utf-8");
		headers.put("SOAPAction","");

		try {
			String xmlStr = httpRequestService.doHttpRequest(url,
					HttpRequestService.POST_METHOD, message, headers);
			
			/*if (xmlStr != null) {
				// if it's token expired, try again.
				if (xmlStr.indexOf("<ErrorCode>931</ErrorCode>") != -1) {
					tokenExpired = true;
					return getUserIdByName(userName);
				}
			}*/

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
     * Get Item detail data by the item id.
     *
     * @param itemId    The Item Id
     * @return
     */
    public GetItemDetailResponse getItemDetail (long itemId) {
        String token = getToken();

        String url = API_URL + "?callname=" + GETITEMDETAIL_API;
        
        SOAPMessage soapMessage = null;
		String message = "";
		try {
			soapMessage = CSSOAPMessageFactory.createGetItemDetailMessage(token, itemId);
			message = CSSOAPMessageFactory.parseToString(soapMessage);
		} catch (SOAPException | IOException e1) {
			_logger.error("Unable to create CSGetItemDetailRequest SOAP message.", e1);
			return null;
		}
		
        Map <String, String> headers = new HashMap <String, String> ();
        headers.put("Content-Type","text/xml; charset=utf-8");
        headers.put("SOAPAction","");

        try {
            String xmlStr = httpRequestService.doHttpRequest(url,
                    HttpRequestService.POST_METHOD, message, headers);
            
            /*if (xmlStr != null) {
				// if it's token expired, try again.
				if (xmlStr.indexOf("<ErrorCode>931</ErrorCode>") != -1) {
					tokenExpired = true;
					return getItemDetail(itemId);
				}
			}*/

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
		GetTokenResponse response = null;
		String url = API_URL + "?callname=" + GETTOKEN_API;
		
		DOMResolver resolver = new DOMResolver();
		try {
			SOAPConnectionFactory factory = SOAPConnectionFactory.newInstance();
			SOAPMessage requestMessage = CSSOAPMessageFactory.createGetTokenMessage();
			SOAPConnection con = factory.createConnection();
			
			SOAPMessage responseMessage = con.call(requestMessage, url);
			
			Node responseNode = CSSOAPMessageFactory.getResponse(responseMessage, "CSGetTokenResponse");
			
			if (responseNode != null) {
				response = resolver.parseElementToObject(responseNode, GetTokenResponse.class);
			}
		} catch (UnsupportedOperationException | SOAPException e1) {
			_logger.error("Unable to call CSGetToken API.", e1);
		} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException | InvocationTargetException
						| DOMException | IntrospectionException e2) {
			_logger.error("Unable to parse the response from CSGetToken API.", e2);
		}
		
		return response;
	}
	
	/**
	 * Check if the CS API token is expired. It's based on the time lag between response Timestamp and HardExpirationTime.
	 * @return
	 */
	public boolean isTokenExpired() {
		Date now = new Date();
		if (expiredTime != null && now.before(expiredTime) && !tokenExpired) {
			return false;
		}
		return true;
	}

}
