package com.ebay.raptor.promotion.index.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.ContextViewRes;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.TokenData;
import com.ebay.raptor.promotion.util.TokenUtil;

@Controller
public class IndexController {
    
	private static CommonLogger logger =
            CommonLogger.getInstance(IndexController.class);
	
	@Autowired CSApiService csApiService;
    @Autowired LoginService loginService;
    @Autowired PromotionService service;
    @Autowired PromotionViewService view;
	
    @RequestMapping(value = "/backend", method = RequestMethod.GET)
    public void handleBackendRequest(HttpServletRequest request,
            HttpServletResponse response) throws MissingArgumentException, IOException {
        String hackId = request.getParameter("hack_id");   // name
        String userId = request.getParameter("user_id");   // id
        String admin = request.getParameter("admin");

        //@TODO Disable IP validation for the moment.
//        String ip = request.getRemoteAddr();
//
//        if(!loginService.isLoginIPValid(ip)) {
//            throw new MissingArgumentException(ip);
//        }
        
        if ((userId == null || userId.isEmpty()) && (hackId == null || hackId.isEmpty())) {
            return;
        } else if (userId == null || userId.isEmpty()) {
            userId = csApiService.getUserIdByName(hackId);
        } else if (hackId == null || hackId.isEmpty()) {
            hackId = userId;
        }
        
        // Dashboard and Bizreport will give us hackId, userId, isAdmin and username by cookies. But we use token from backend.
        // You need to see how CookieUtil.getUserDataFromCookie() to get user data.
        if (userId != null) {
        	TokenData tokenData = new TokenData();
            tokenData.setIsAdmin(Boolean.parseBoolean(admin));
            tokenData.setUserId(Long.parseLong(userId));
            tokenData.setUserName(hackId);
            CookieUtil.setCBTPromotionCookie(response, AppCookies.EBAY_CBT_TOKEN_COOKIE_NAME, TokenUtil.getTokenString(tokenData));
        	// add hack_id in order to avoid login checking
        	CookieUtil.setCBTPromotionCookie(response, AppCookies.HACKID_COOKIE_KEY, hackId);
        	// hack_id is the user name.
        	CookieUtil.setCBTPromotionCookie(response, AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME, hackId);
            response.sendRedirect("index");
        } else {
        	response.sendRedirect("error");
        }
    }

    @AuthNeed
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView handleIndexRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();
        //Set unconfirmed status
        UserData userDt = CookieUtil.getUserDataFromCookie(request);
        mav.addObject(ViewContext.IsAdmin.getAttr(), userDt.getAdmin());
             
       	mav.setViewName("index");
        return mav;
    }
	
    @AuthNeed
	@GET
	@RequestMapping("/{promoId}")
	public ModelAndView promotion(HttpServletRequest request,
			@PathVariable("promoId") String promoId) throws MissingArgumentException {
		ModelAndView model = new ModelAndView();
		UserData userData = CookieUtil.getUserDataFromCookie(request);

		try {
			Promotion promo = service.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			
			if(null != promo){
				ContextViewRes res = handleViewBasedOnPromotion(promo, userData.getUserId());
				model.setViewName(res.getView().getPath());
				model.addAllObjects(res.getContext());
				model.addObject(ViewContext.Promotion.getAttr(), promo);
			} else {
				model.setViewName(ViewResource.ERROR.getPath());
			}
			
		} catch (PromoException e) {
			logger.error("Unable to get promotion for " + promoId, e);
			model.setViewName(ViewResource.ERROR.getPath());
		}
		return model;
	}
    
	@RequestMapping(value = "/error", method = RequestMethod.GET)
    public ModelAndView handleErrorRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();
       	mav.setViewName("error");
        return mav;
    }
	
	@ExceptionHandler(MissingArgumentException.class)
    public ModelAndView handleException(MissingArgumentException exception,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        logger.error("Missing required arguments from cookie.", exception);
        return mav;
    }
	
	private ContextViewRes handleViewBasedOnPromotion(Promotion promo, long uid) throws PromoException{
		ContextViewRes result = new ContextViewRes();
		result = view.handleView(promo, uid);
		/*switch(PMPromotionType.valueOfPMType(promo.getType())){
			case HIGH_VELOCITY:
				result = view.highVelocityView(promo, uid);
				break;
			case DEALS_DASHBOARD_UPLOAD:
				result = view.dealsUpload(promo, uid);
				break;
			case DEALS_AM_UPLOAD:
				result = view.dealsPresetView(promo, uid);
				break;
			case STANDARD:
				result = view.standard(promo);
				break;
			case PM_UNKNOWN_TYPE:
				result.setView(ViewResource.ERROR);
				break;
		}*/
		return result;
	}
}