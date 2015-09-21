package com.ebay.raptor.promotion.index.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.service.CSApiService;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.util.CookieUtil;


@Controller
public class IndexController {
    
	private static CommonLogger logger =
            CommonLogger.getInstance(IndexController.class);
	
	@Autowired CSApiService csApiService;
    @Autowired LoginService loginService;
	
    @RequestMapping(value = "/backend", method = RequestMethod.GET)
    public void handleBackendRequest(HttpServletRequest request,
            HttpServletResponse response) throws MissingArgumentException {
        String hackId = request.getParameter("hack_id");   // name
        String userId = request.getParameter("user_id");   // id
        String admin = request.getParameter("admin");

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
        
        //Add admin cookie
        Cookie adminCookie = new Cookie(CookieUtil.ADMIN_COOKIE_NAME, !StringUtil.isEmpty(admin) + "");
	   	adminCookie.setMaxAge(CookieUtil.COOKIE_LIFESPAN);
	   	adminCookie.setPath(CookieUtil.COOKIE_PATH_ROOT);
        response.addCookie(adminCookie);

        if (!StringUtil.isEmpty(userId)) {
        	// add hack_id in order to avoid login checking
        	CookieUtil.setCBTPromotionCookie(response, CookieUtil.HACKID_COOKIE_KEY, hackId);
        	CookieUtil.setCBTPromotionCookie(response, CookieUtil.USERID_COOKIE_KEY, userId);
        	// hack_id is the user name.
        	CookieUtil.setCBTPromotionCookie(response, CookieUtil.USERNAME_COOKIE_KEY, hackId);
        }

        try {
            response.sendRedirect("index");// TODO - index
        } catch (IOException e) {
            // ignore
        }
    }

	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView handleIndexRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();
        //Set unconfirmed status
        UserData userDt = CookieUtil.getUserDataFromCookie(request);
        mav.addObject(ViewContext.IsUnconfirmedVisable.getAttr(), userDt.getAdmin());
        
        if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(param.getLang())) {
        	mav.setViewName("zh_HK/index");
        } else {
        	mav.setViewName("index");
        }
        return mav;
    }
	
	@RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView handleErrorRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();
        if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(param.getLang())) {
        	mav.setViewName("zh_HK/error");
        } else {
        	mav.setViewName("error");
        }
        return mav;
    }
    
	@ExceptionHandler(MissingArgumentException.class)
    public ModelAndView handleException(MissingArgumentException exception,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        logger.error("Missing required arguments from cookie.", exception);
        return mav;
    }
}