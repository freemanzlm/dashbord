package com.ebay.raptor.promotion.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.util.CookieUtil;



@Controller
public class DataController {

    @RequestMapping("/getOngoingEvents")
    @ResponseBody
    public Map<String, Object>  getOngoingEvents(HttpServletRequest request,
            HttpServletResponse response) throws MissingArgumentException {
        UserData userData = CookieUtil.getUserDataFromCookie(request);
        return null;
    }
}
