package com.ebay.raptor.siteApi.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author lyan2
 */
@XmlRootElement(namespace="urn:ebay:apis:eBLBaseComponents", name="GetUserResponse")
public class GetUserResponse extends BaseAPIResponse {
	
	@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="User")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	private User user;

	public static class User {
		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="EIASToken")
		public String getEiasToken() {
			return eiasToken;
		}
		public void setEiasToken(String eiasToken) {
			this.eiasToken = eiasToken;
		}
		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Email")
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="UserID")
		public String getUserName() {
			return userName;
		}
		public void setUserName(String userName) {
			this.userName = userName;
		}
		@XmlElement(namespace="urn:ebay:apis:eBLBaseComponents", name="Site")
		public String getSite() {
			return site;
		}
		public void setSite(String site) {
			this.site = site;
		}
		// Encrypted user oracle id
		private String eiasToken;
		private String email;
		private String userName;
		private String site;
	}
}
