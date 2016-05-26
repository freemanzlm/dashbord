package com.ebay.raptor.promotion.util;

import java.util.List;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.globalenv.config.Site;
import com.ebay.iaf.IAFUtilityException;
import com.ebay.iaf.client.IAFTokenUtility;
import com.ebay.idp.IAFException;
import com.ebay.marketplace.iaf.v1.services.Identifier;
import com.ebay.marketplace.iaf.v1.services.LoginRequest;
import com.ebay.marketplace.iaf.v1.services.LoginResponse;
import com.ebay.marketplace.iaf.v1.services.RequestSecurityTokenRequest;
import com.ebay.marketplace.iaf.v1.services.RequestSecurityTokenResponse;
import com.ebay.marketplace.iaf.v1.services.Secret;
import com.ebay.marketplace.iaf.v1.services.SecurityToken;
import com.ebay.marketplace.iaf.v1.services.Subject;
import com.ebay.marketplace.services.ErrorData;
import com.ebay.marketplace.services.iafservice.iafservice.gen.SharedIAFServiceConsumer;
import com.ebay.soaframework.common.exceptions.ServiceException;
import com.ebay.soaframework.common.types.SOAHeaders;
import com.ebay.soaframework.sif.service.Service;

/**
 * Application use project consumer id and secret to get a IAFToken.
 * 
 * @author lyan2
 */
public class IAFTokenProvider {
	private static CommonLogger logger = CommonLogger
			.getInstance(IAFTokenProvider.class);
	private static String PRE = "CBT IAF - ";

	/* *
	 * CLIENT_CONFIG_FOLDER_NAME: 
	 * 
	 * CLIENT_CONFIG_FOLDER_NAME has three kinds of values: 
	 * 1. "IAFServiceClient_soaident", if you want to consumer a service which
	 * deals with site users or site related activity or authenticate site users. 
	 * 2. "IAFServiceClient", if you want to consumer any services or
	 * authenticate users related to ASAC20/ASAC30/CS users. These are not site users. 
	 * 3. "IAFServiceClient_corp" For CORPUSER authentication.
	 * 
	 * "IAFServiceClient_soaident" is default in most of the cases if you use
	 * raptor helpers. If you directly instantiate consumer, then
	 * "IAFServiceClient" is default which should only be used if you need ASAC*
	 * authentication.
	 */
	static String CLIENT_CONFIG_FOLDER_NAME = "IAFServiceClient";
	static String DOMAIN = "EBAYAPP";
	
	/* *
	 * this scope is used to create a scoped token. But promoservcie doesn't
	 * have any method that uses this scope.
	 */
	// static String SCOPE = "https://api.ebay.com/oauth/scope/core@application";
	
	static String iafToken;

	private static String getEnv() {
		/* *
		 * sandbox (only availabe for IAFServiceClient_soaident client config folder)
		 * */
		return Site.getPooltype().getName();
	}

	/**
	 * Generate the token by passed in consumer id and secret.
	 * 
	 * @param cid
	 * @param secret
	 * @return
	 * @throws IAFException
	 */
	public static String getConsumerIAFToken(String cid, String secret) {
		if (!StringUtil.isEmpty(iafToken)) {
			try {
				if (!IAFTokenUtility.isExpired(iafToken)) {
					return iafToken;
				}
			} catch (IAFUtilityException e) {
				logger.warn(PRE + "Cached IAF token expired or invalid.", e);
			}
		}

		String loginToken = getLoginToken(cid, secret);
		iafToken = getIAFToken(cid, loginToken);
		return !StringUtil.isEmpty(iafToken) ? iafToken : "";
	}

	/**
	 * Generate the token by passed in consumer id and secret.
	 * 
	 * @param cid
	 * @param secret
	 * @return
	 * @throws IAFException
	 */
	public static String geteTRSToken(String cid, String secret) {
		if (StringUtil.isEmpty(cid) || StringUtil.isEmpty(secret)) {
			throw new IAFException(
					"Consumer id or Secret is not valid, failed to get Token.");
		}
		String loginToken = getLoginToken(cid, secret);
		String appToken = getgetIAFTokenWithoutScope(cid, loginToken);
		return !StringUtil.isEmpty(appToken) ? appToken : null;
	}

	/**
	 * Generate IAF token without scope.
	 * 
	 * @param token
	 * @return
	 */
	private static String getgetIAFTokenWithoutScope(String cid, String token) {
		String applicationToken = null;
		try {
			SharedIAFServiceConsumer defaultConsumer = new SharedIAFServiceConsumer(
					CLIENT_CONFIG_FOLDER_NAME, getEnv());
			RequestSecurityTokenRequest request = new RequestSecurityTokenRequest();

			Subject subject = prepareSubject(DOMAIN, cid);
			request.setSubject(subject);

			Service service = defaultConsumer.getService();
			service.setSessionTransportHeader(SOAHeaders.AUTH_IAFTOKEN, token);
			RequestSecurityTokenResponse rstResponse = defaultConsumer
					.requestSecurityToken(request);

			if (rstResponse.getErrorMessage() != null) {
				List<ErrorData> eds = rstResponse.getErrorMessage().getError();
				for (ErrorData e : eds) {
					logger.error(PRE
							+ "Failed to fetch the application based token, error: "
							+ e.getMessage());
				}
			} else {
				SecurityToken stoken = rstResponse.getSecurityToken();
				if (stoken != null) {
					applicationToken = stoken.getToken();
				}
			}
		} catch (ServiceException e) {
			logger.error(PRE
					+ "Error happens when fetch the app based token. Error: "
					+ e.getMessage(), e);
		}
		return applicationToken;
	}

	/**
	 * Return the scoped IAF token.
	 * 
	 * @param token
	 * @return
	 */
	private static String getIAFToken(String cid, String token) {
		String applicationToken = null;
		try {
			SharedIAFServiceConsumer defaultConsumer = new SharedIAFServiceConsumer(
					CLIENT_CONFIG_FOLDER_NAME, getEnv());
			RequestSecurityTokenRequest request = new RequestSecurityTokenRequest();

			Subject subject = prepareSubject(DOMAIN, cid);
			request.setSubject(subject);

			// request.getAttributeAssertion().add(prepareScopeAttribute(SCOPE));

			Service service = defaultConsumer.getService();
			service.setSessionTransportHeader(SOAHeaders.AUTH_IAFTOKEN, token);
			RequestSecurityTokenResponse rstResponse = defaultConsumer
					.requestSecurityToken(request);

			if (rstResponse.getErrorMessage() != null) {
				List<ErrorData> eds = rstResponse.getErrorMessage().getError();
				for (ErrorData e : eds) {
					logger.error(PRE
							+ "Failed to fetch the application based token, error: "
							+ e.getMessage());
				}
			} else {
				SecurityToken stoken = rstResponse.getSecurityToken();
				if (stoken != null) {
					applicationToken = stoken.getToken();
				}
			}
		} catch (ServiceException e) {
			logger.error(PRE
					+ "Error happens when fetch the app based token. Error: "
					+ e.getMessage(), e);
		}
		return applicationToken;
	}

	/**
	 * Return the app based login token.
	 * 
	 * @return
	 */
	private static String getLoginToken(String cid, String secret) {
		String logintoken = null;

		try {
			SharedIAFServiceConsumer iafServiceConsumer = new SharedIAFServiceConsumer(
					CLIENT_CONFIG_FOLDER_NAME, getEnv());
			LoginRequest request = new LoginRequest();
			Subject sub = prepareSubject(DOMAIN, cid);
			request.setSubject(sub);
			Secret sec = prepareSecret(secret);
			request.getSecret().add(sec);
			LoginResponse response = null;
			// Make sure only one thread tries to login at the same time.
			synchronized (IAFTokenProvider.class) {
				long start = System.currentTimeMillis();
				logger.log(String.format(
						"Start login IAF to get token for id %s. ", cid));
				response = iafServiceConsumer.login(request);
				logger.warn(String
						.format("Finish login IAF to get token for id %s time used %d.",
								cid, (System.currentTimeMillis() - start)));
			}
			if (null != response) {
				if (response.getErrorMessage() != null) {
					List<ErrorData> eds = response.getErrorMessage().getError();
					for (ErrorData e : eds) {
						logger.error(PRE
								+ "IAF login token fetch failed, error: "
								+ e.getMessage() + " - " + e.toString());
					}
				} else {
					SecurityToken stoken = response.getSecurityToken();
					if (stoken != null) {
						logintoken = stoken.getToken();
					} else {
						logger.error(PRE
								+ "IAF login token fetch returned empy value.");
					}
				}
			}
		} catch (ServiceException e) {
			logger.error(
					PRE + "IAF login token fetch failed. Error: "
							+ e.getMessage(), e);
		}
		return logintoken;
	}

	private static Subject prepareSubject(String domain, String id) {
		Identifier ident = new Identifier();
		ident.setFormat("USERNAME");
		ident.setValue(id);
		Subject sub = new Subject();
		sub.setIdentifier(ident);
		sub.setIdentityDomain(domain);
		return sub;

	}

	private static Secret prepareSecret(String password) {
		Secret secret = new Secret();
		secret.setEncoding("DEFAULT");
		secret.setType("PASSWORD");
		secret.setValue(password);
		return secret;
	}

/*	private static AttributeType prepareScopeAttribute(String scope) {
		AttributeType type = new AttributeType();
		type.setFriendlyName("Scope");
		type.setName("scope");
		type.setNameFormat("String");
		type.getAttributeValue().add(scope);
		return type;
	}*/
}
