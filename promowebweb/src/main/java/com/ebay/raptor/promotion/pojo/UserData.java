package com.ebay.raptor.promotion.pojo;

public class UserData {
    public UserData(){}
    
    public UserData(long userId, String userName, boolean admin) {
        this.userId = userId;
        this.userName = userName;
        this.admin = admin;
    }

    public long getUserId() {
        return userId;
    }
    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private long userId;
    private String userName;
    private boolean admin;
}
