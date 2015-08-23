package com.ebay.raptor.promotion.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.service.CSApiService;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.util.CookieUtil;

@Controller
public class PromotionController {
    private static CommonLogger logger =
            CommonLogger.getInstance(PromotionController.class);

    private static final String DEFAULT_LANGUAGE  = "zh_CN";

    @Autowired CSApiService csApiService;
    @Autowired LoginService loginService;

    @RequestMapping(value = "/backend", method = RequestMethod.GET)
    public void handleBackendRequest(HttpServletRequest request,
            HttpServletResponse response) throws MissingArgumentException {
        String hackId = request.getParameter("hack_id");   // name
        String userId = request.getParameter("user_id");   // id

        String ip = request.getRemoteAddr();

        if(!loginService.isLoginIPValid(ip)) {
            throw new MissingArgumentException(ip);
        }

        if (StringUtil.isEmpty(userId)) {
            userId = csApiService.getUserIdByName(hackId);
        }

        if (StringUtil.isEmpty(hackId)) {
            hackId = userId;
        }

        if (!StringUtil.isEmpty(userId)) {

            Cookie hackIdCookie = new Cookie (CookieUtil.HACKID_COOKIE_KEY,
                    hackId);
            hackIdCookie.setMaxAge(CookieUtil.COOKIE_LIFESPAN);
            hackIdCookie.setPath(CookieUtil.COOKIE_PATH_ROOT);

            Cookie userIdCookie = new Cookie (CookieUtil.USERID_COOKIE_KEY,
                    userId);
            userIdCookie.setMaxAge(CookieUtil.COOKIE_LIFESPAN);
            userIdCookie.setPath(CookieUtil.COOKIE_PATH_ROOT);

            Cookie userNameCookie = new Cookie (CookieUtil.USERNAME_COOKIE_KEY,
                    hackId);
            userNameCookie.setMaxAge(CookieUtil.COOKIE_LIFESPAN);
            userNameCookie.setPath(CookieUtil.COOKIE_PATH_ROOT);

            if (CookieUtil.COOKIE_DOMAIN != null) {
                hackIdCookie.setDomain(CookieUtil.COOKIE_DOMAIN);
                userIdCookie.setDomain(CookieUtil.COOKIE_DOMAIN);
                userNameCookie.setDomain(CookieUtil.COOKIE_DOMAIN);
            }

            response.addCookie(hackIdCookie);
            response.addCookie(userIdCookie);
            response.addCookie(userNameCookie);
        }

        try {
            response.sendRedirect("index");
        } catch (IOException e) {
            // ignore
        }
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView handleIndexRequest(HttpServletRequest request,
            HttpServletResponse response) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();

//        UserData userData = CookieUtil.getUserDataFromCookie(request);
//
//        addPageLevelData(mav, request, userData);

        mav.setViewName("index");
        return mav;
    }

    @ExceptionHandler(MissingArgumentException.class)
    public ModelAndView handleException(MissingArgumentException exception,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        logger.error("Missing required arguments from cookie.", exception);
        return mav;
    }
    
    private void addPageLevelData (ModelAndView mav, HttpServletRequest request,
            UserData userData) {
        mav.addObject("userId", userData.getUserId());
        mav.addObject("userName", userData.getUserName());
    }
}
