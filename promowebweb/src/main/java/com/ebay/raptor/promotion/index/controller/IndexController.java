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
import com.ebay.cbt.common.constant.pm.PMPromotionType;
import com.ebay.raptor.promotion.AuthNeed;
import com.ebay.raptor.promotion.config.AppCookies;
import com.ebay.raptor.promotion.enums.PromotionStep;
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
        
        //Add admin cookie
        Cookie adminCookie = new Cookie(AppCookies.ADMIN_COOKIE_NAME, Boolean.parseBoolean(admin) + "");
	   	adminCookie.setMaxAge(CookieUtil.ONE_DAY_COOKIE_LIFESPAN);
	   	adminCookie.setPath(AppCookies.COOKIE_PATH_ROOT);
        response.addCookie(adminCookie);

        if (userId != null) {
        	// add hack_id in order to avoid login checking
        	CookieUtil.setCBTPromotionCookie(response, AppCookies.HACKID_COOKIE_KEY, hackId);
        	CookieUtil.setCBTPromotionCookie(response, AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME, userId);
        	// hack_id is the user name.
        	CookieUtil.setCBTPromotionCookie(response, AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME, hackId);
        	
        	response.sendRedirect("index");// TODO - index
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