package com.ebay.raptor.siteApi.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author lyan2
 */
@XmlRootElement(namespace="urn:ebay:apis:eBLBaseComponents", name="GetTokenStatusResponse")
public class GetTokenStatusResponse extends BaseAPIResponse {
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="TokenStatus")
	public TokenStatus getTokenStatus() {
		return tokenStatus;
	}

	public void setTokenStatus(TokenStatus tokenStatus) {
		this.tokenStatus = tokenStatus;
	}

	private TokenStatus tokenStatus;
	
	public static class TokenStatus {
		/**
		 * If token is not expired, its value will be "Active".
		 * @return
		 */
		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Status")
		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * Base64 encoded user oracle ID.
		 * We can get real user oracle ID by calling : EiasUserIdEncoder.decodeUserId(response.getTokenStatus().getEIASToken(), EiasUserIdEncoder.FILL_CHAR_EQUAL).
		 * 
		 * @return
		 */
		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="EIASToken")
		public String getEIASToken() {
			return EIASToken;
		}

		public void setEIASToken(String eIASToken) {
			EIASToken = eIASToken;
		}

		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="ExpirationTime")
		public String getExpirationTime() {
			return expirationTime;
		}

		public void setExpirationTime(String expirationTime) {
			this.expirationTime = expirationTime;
		}

		private String status;
		private String EIASToken;
		private String expirationTime;
	}
}

