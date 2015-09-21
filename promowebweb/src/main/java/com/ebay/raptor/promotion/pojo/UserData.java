package com.ebay.raptor.promotion.pojo;


public class UserData {
    public UserData(){}

    public UserData(Long userId, String userName, String lang) {
        this.userId = userId;
        this.userName = userName;
        this.admin = false;
        this.lang = lang;
    }

    public UserData(Long userId, String userName, Boolean admin, String lang) {
        this.userId = userId;
        this.userName = userName;
        this.admin = admin;
        this.lang = lang;
    }

    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public Boolean getAdmin() {
        return admin;
    }
    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	private Long userId;
    private String userName;
    private Boolean admin;
    private String lang;
}
