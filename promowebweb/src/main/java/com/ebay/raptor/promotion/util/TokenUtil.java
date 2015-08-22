package com.ebay.raptor.promotion.util;

import java.util.Date;

import com.ebay.app.raptor.promocommon.security.DES;
import com.ebay.app.raptor.promocommon.util.DateUtil;

public class TokenUtil {
    public static final String TOKEN_SELECTED_ID = "selected_id";
    public static final String TOKEN_ORACLE_ID = "oracle_id";
    public static final String TOKEN_VISIT_IP = "visit_ip";
    public static final String TOKEN_VISIT_DT = "visit_dt";
    public static final String TOKEN_LANGUAGE = "lang";
    public static final String TOKEN_REPORT_DT = "report_dt";
    public static final String TOKEN_ADMIN = "admin";
    public static final String TOKEN_ADMIN_USER = "admin_user";
    public static final String TOKEN_LIST_TYPE = "list_type";
    public static final String TOKEN_SITE_AUTH = "auth";
    public static final String TOKEN_CURRENT_SITE = "site";
    public static final String TOKEN_USER_VISIT_IP = "user_visit_ip";
    
    public static final String BIZREPORT_IP = "192.168.0.1";

    public static String generateSDToken (String userName, long userId, String userIp, String lang, boolean admin) {
        String token = null;
        StringBuilder tokenSb = new StringBuilder();
        tokenSb.append(TOKEN_SELECTED_ID).append('=').append(userName).append(';')
            .append(TOKEN_ORACLE_ID).append('=').append(userId).append(';')
            .append(TOKEN_LANGUAGE).append('=').append(lang).append(';')
            .append(TOKEN_ADMIN).append('=').append(admin).append(';')
            .append(TOKEN_VISIT_IP).append('=').append(BIZREPORT_IP).append(';')
            .append(TOKEN_VISIT_DT).append('=').append(DateUtil.formatSimpleDateWithDash(new Date())).append(';')
            .append(TOKEN_USER_VISIT_IP).append('=').append(userIp);

        try {
            token = DES.getInstance().encrypt(tokenSb.toString(), true);
        } catch (Exception e) {
            // ignore...
        }

        return token;
    }
}
