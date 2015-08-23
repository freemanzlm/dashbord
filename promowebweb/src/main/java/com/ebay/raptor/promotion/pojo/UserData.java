package com.ebay.raptor.promotion.pojo;

import com.ebay.app.raptor.promocommon.util.CommonConstant;

public class UserData {
    public UserData(){}

    public UserData(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
        this.admin = false;
    }

    public UserData(Long userId, String userName, Boolean admin) {
        this.userId = userId;
        this.userName = userName;
        this.admin = admin;
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

	private Long userId;
    private String userName;
    private Boolean admin;
}
