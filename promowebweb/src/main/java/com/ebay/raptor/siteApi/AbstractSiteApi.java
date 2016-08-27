package com.ebay.raptor.siteApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.siteApi.exception.CallSiteApiException;
import com.ebay.raptor.siteApi.response.BaseAPIResponse;

/**
 * 
 * @author lyan2
 */
public abstract class AbstractSiteApi {
	private static final String API_COMPATIBILITY_LEVEL_KEY = "X-EBAY-API-COMPATIBILITY-LEVEL";
	// required for GetSessionID, FetchToken, GetTokenStatus, and RevokeToken
	private static final String API_DEV_NAME_KEY = "X-EBAY-API-DEV-NAME";
	// required for FetchToken
	private static final String API_APP_NAME_KEY = "X-EBAY-API-APP-NAME";
	// required for FetchToken
	private static final String API_CERT_NAME_KEY = "X-EBAY-API-CERT-NAME";
	private static final String API_CALL_NAME_KEY = "X-EBAY-API-CALL-NAME";
	private static final String API_SITEID_KEY = "X-EBAY-API-SITEID";

	private static final Logger logger = Logger.getInstance(AbstractSiteApi.class);
	private final static int PROXY_PORT = 80;
	private static String _proxyHost;

	private static SiteInfo siteInfor;

	static {
		if (EnvironmentUtil.isProduction()) {
			_proxyHost = "httpproxy.vip.ebay.com";
		} else {
			_proxyHost = "qa-proxy.qa.ebay.com";
		}
		
		siteInfor = getSiteInfo();
	}
	
	/**
	 * Get site API configurations from server. All this configurations are need
	 * to call site API.
	 * 
	 * @return
	 */
	public static SiteInfo getSiteInfo() {
		if (siteInfor == null) {
//			Properties props = new Properties();
//			try {
//				props.load(AbstractSiteApi.class.getResourceAsStream("config.properties"));
//				
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			String apiVersion = "865"; // The Site API version
			String siteId = "201"; // HongKong site id

			if (EnvironmentUtil.isProduction()) {
				String edpAppId = "ARVATOSYSTC8VD92984D27A4923Y51";
				String edpDevId = "A1M5L2XFZJBQE956616S6IO16EBY12";
				String edpCertId = "C5H14RA1JLV$E9DH4W31J-41216HHW";
				String ruName = "Arvato_systems-ARVATOSYSTC8VD9-dhgcm";
				String siteApiUrl = "https://api.ebay.com/ws/api.dll";
				String siteSignInUrl = "https://signin.ebay.com.hk/ws/eBayISAPI.dll";
				
//				String edpAppId = "ebay0f7ba-4b98-41bd-9416-6442ca2fa8b";
//				String edpDevId = "997e920c-dea8-46ff-8945-7c23d59b0f18";
//				String edpCertId = "6a79b148-88e2-4834-877c-b826efcd4dc2";
//				String ruName = "ebay-ebay0f7ba-4b98--ahuvaahzb";
				
				siteInfor = new SiteInfo(edpDevId, edpAppId, edpCertId, ruName,
						siteApiUrl, apiVersion, siteId, siteSignInUrl);
			} else {
				 String edpDevId = "d84ae3bf-b369-43af-a2ae-5e998fd64f30";
				 String edpAppId = "ebay87daa-ea38-4cd3-b4af-8703f00ec7d";
				 String edpCertId = "a0ce6911-41dc-4f16-95d1-a2409136fcc9";
				 String ruName = "ebay-ebay87daa-ea38--foqneviyn";
				 String siteApiUrl =
				 "https://api.sandbox.ebay.com/ws/api.dll";
				 String siteSignInUrl =
				 "https://signin.sandbox.ebay.com.hk/ws/eBayISAPI.dll";

				// TODO change to real site information
//				String edpAppId = "ebayaf9f4-bcdb-4e22-9195-bc48eb3add7";
//				String edpDevId = "888c1d20-aa70-40e7-906f-bcbe72f5c279";
//				String edpCertId = "71bae07d-9d55-438a-9552-9b8a362bac88";
//				String ruName = "ebay-ebayaf9f4-bcdb--psuiyifhk";
//				String siteApiUrl = "https://api.sandbox.ebay.com/ws/api.dll";
//				String siteSignInUrl = "https://signin.sandbox.ebay.com.hk/ws/eBayISAPI.dll";

				siteInfor = new SiteInfo(edpDevId, edpAppId, edpCertId, ruName,
						siteApiUrl, apiVersion, siteId, siteSignInUrl);
			}
		}
		return siteInfor;
	}

	/**
	 * Call site API without using proxy.
	 * 
	 * @param apiCallName
	 * @param apiContent
	 * @param needAuth
	 * @return
	 * @throws CallSiteApiException
	 * @throws XmlParseException
	 */
	public BaseAPIResponse callSiteAPI(String apiCallName,
			String apiContent, boolean needAuth) throws CallSiteApiException {
		return callSiteAPI(apiCallName, apiContent, needAuth, false);
	}

	/**
	 * Call site API, and get the response.
	 * 
	 * @param apiCallName
	 *            The API name
	 * @param apiContent
	 *            The requested content
	 * @return The object parsed from the response
	 * @throws CallSiteApiException
	 * @throws XmlParseException
	 */
	protected BaseAPIResponse callSiteAPI(String apiCallName,
			String apiContent, boolean needAuth, boolean useProxy)
			throws CallSiteApiException {
		BaseAPIResponse response = null;

		URL url = null;
		HttpURLConnection conn = null;
		try {
			url = new URL(siteInfor.getSiteApiUrl());

			if (useProxy) {
				Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(
						_proxyHost, PROXY_PORT));
				conn = (HttpURLConnection) url.openConnection(proxy);
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}

			conn.setRequestMethod("POST"); // Only support "POST" request
		} catch (MalformedURLException e1) {
			throw new CallSiteApiException(
					String.format(
							"Unable to call site API [%s], and the API URL [%s] is incorrect.",
							apiCallName, siteInfor.getSiteApiUrl()), e1);
		} catch (IOException e2) {
			throw new CallSiteApiException(
					String.format(
							"Unable to call site API [%s], and can't open the connection to site URL [%s].",
							apiCallName, siteInfor.getSiteApiUrl()), e2);
		}

		conn.setDoInput(true);
		conn.setDoOutput(true);
		// conn.setReadTimeout(5000);

		conn.addRequestProperty("Content-Type", "text/xml");
		conn.addRequestProperty(API_COMPATIBILITY_LEVEL_KEY,
				siteInfor.getApiVersion());
		conn.addRequestProperty(API_SITEID_KEY, siteInfor.getSiteId());
		conn.addRequestProperty(API_CALL_NAME_KEY, apiCallName);

		if (needAuth) {
			conn.addRequestProperty(API_DEV_NAME_KEY, siteInfor.getEdpDevId());
			conn.addRequestProperty(API_APP_NAME_KEY, siteInfor.getEdpAppId());
			conn.addRequestProperty(API_CERT_NAME_KEY, siteInfor.getEdpCertId());
		}

		if (apiContent != null && !apiContent.equals("")) {
			OutputStream outputStream = null;
			try {
				outputStream = conn.getOutputStream();
				outputStream.write(apiContent.getBytes("UTF-8"));
				outputStream.flush();
			} catch (IOException e) {
				throw new CallSiteApiException(
						String.format(
								"Unable to call site API [%s], and can't send the request.",
								apiCallName), e);
			} finally {
				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					// ignore
					logger.log(LogLevel.WARN,
							"Unable to close the connection.", e);
				}
			}
		}

		InputStream inputStream = null;
		try {
			inputStream = conn.getInputStream();
			response = getResponseObjFromStream(inputStream);
		} catch (IOException e) {
			throw new CallSiteApiException(
					String.format(
							"Unable to call site API [%s], and can't get the response.",
							apiCallName), e);
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				// ignore
				logger.log(LogLevel.WARN, "Unable to close the connection.", e);
			}
		}

		return response;
	}

	/**
	 * Parse the xml from the input stream, and put the related information into
	 * an instance.
	 * 
	 * @param inputStream
	 *            The input stream
	 * @return The object contains information from the stream
	 */
	protected abstract BaseAPIResponse getResponseObjFromStream(
			InputStream inputStream);

}
