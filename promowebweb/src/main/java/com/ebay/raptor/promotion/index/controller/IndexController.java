package com.ebay.raptor.promotion.index.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import org.apache.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.cbtcommon.pojo.db.AuditType;
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
import com.ebay.raptor.promotion.sd.service.SDDataService;
import com.ebay.raptor.promotion.service.CSApiService;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.service.TrackService;
import com.ebay.raptor.promotion.subsidy.service.SubsidyService;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.siteApi.util.SiteApiUtil;

@Controller
public class IndexController {
    
	private static CommonLogger logger =
            CommonLogger.getInstance(IndexController.class);
	
	@Autowired CSApiService csApiService;
    @Autowired LoginService loginService;
    @Autowired PromotionService service;
    @Autowired PromotionViewService view;
    @Autowired SubsidyService subsidyService;
    @Autowired TrackService trackService;
    @Autowired SDDataService sdDataService;
	
    @RequestMapping(value = "/backend", method = RequestMethod.GET)
    public void handleBackendRequest(HttpServletRequest request,
            HttpServletResponse response) throws MissingArgumentException, IOException {
        String hackId = request.getParameter("hack_id");   // name
        String userId = request.getParameter("user_id");   // id
        String admin = request.getParameter("admin");

        if ((userId == null || userId.isEmpty()) && (hackId == null || hackId.isEmpty())) {
        	response.sendRedirect("error");
        } else {
        	if (userId == null || userId.isEmpty()) {
        		userId = csApiService.getUserIdByName(hackId);
            } else if (hackId == null || hackId.isEmpty()) {
                hackId = userId;
            }
        	
            // Dashboard and Bizreport will give us hackId, userId, isAdmin and username by cookies. But we use token from backend.
            // You need to see how AppCookies.getUserDataFromCookie() to get user data.
            if (userId != null) {
            	// add hack mode in order to avoid login checking
            	// remove ebay token and hack mode can't exist at the same time.
            	CookieUtil.setCBTCookie(response, AppCookies.EBAY_TOKEN_COOKIE_NAME, "", CookieUtil.EXPIRED_COOKIE_LIFESPAN);
            	CookieUtil.setCBTCookie(response, AppCookies.HACK_MODE_COOKIE_NAME, "true", CookieUtil.SESSION_COOKIE_LIFESPAN);
            	
            	CookieUtil.setCBTCookie(response, AppCookies.EBAY_CBT_USER_ID_COOKIE_NAME, SiteApiUtil.encodeUserId(userId), CookieUtil.SESSION_COOKIE_LIFESPAN);
            	// hack_id is the user name.
            	CookieUtil.setCBTCookie(response, AppCookies.EBAY_CBT_USER_NAME_COOKIE_NAME, hackId, CookieUtil.ONE_DAY_COOKIE_LIFESPAN);
            	CookieUtil.setCBTCookie(response, AppCookies.EBAY_CBT_ADMIN_USER_COOKIE_NAME, admin, CookieUtil.SESSION_COOKIE_LIFESPAN);
                response.sendRedirect("index");
            } else {
            	response.sendRedirect("error");
            }
        }
    }

    @AuthNeed
	@RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView handleIndexRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();
        //Set unconfirmed status
        UserData userDt = loginService.getUserDataFromCookie(request);
        mav.addObject(ViewContext.IsAdmin.getAttr(), userDt.getAdmin());
        
       	mav.setViewName("index");
       	
       	//add track record to DB
       	trackService.logUserActivityAsync(userDt, AuditType.VisitToPromo, "");
        return mav;
    }
    
	@RequestMapping(value = "/maintain", method = RequestMethod.GET)
    public ModelAndView gotoMaintainPage(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();
       	mav.setViewName("maintain");
        return mav;
    }
	
    @AuthNeed
	@GET
	@RequestMapping("/{promoId}")
	public ModelAndView promotion(HttpServletRequest request,
			@PathVariable("promoId") String promoId) throws MissingArgumentException {
		ModelAndView model = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(request);
		Promotion promo = null;
		
		try {
			promo = service.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			
			if(null != promo && promo.getActiveFlag()){
				ContextViewRes res = handleViewBasedOnPromotion(promo, userData.getUserId());
				model.setViewName(res.getView().getPath());
				model.addAllObjects(res.getContext());
				if(promo.getCurrentStep()!=null) {
					promo.setCurrentStep(promo.getCurrentStep().toUpperCase());
				}
				if(promo.getDraftPreviewStep()!=null) {
					promo.setDraftPreviewStep(promo.getDraftPreviewStep().toUpperCase());
				}
				model.addObject(ViewContext.Promotion.getAttr(), promo);
				
			} else {
				model.setViewName(ViewResource.UNKNOW_CAMPAIGN.getPath());
			}
			
		} catch (PromoException e) {
			logger.error("Unable to get promotion for " + promoId, e);
			model.setViewName(ViewResource.ERROR.getPath());
		}
		
		/*try {
			
			// get subsidy details when promotion is validated.
			if (promo != null && PromotionStep.PROMOTION_VALIDATED.getName().equalsIgnoreCase(promo.getCurrentStep())) {
				Subsidy subsidy = subsidyService.getSubsidy(promoId, userData.getUserId());
				if (subsidy != null) {
					model.addObject(ViewContext.Subsidy.getAttr(), subsidy);
				}
			}
		} catch (PromoException e) {
			logger.error("Unable to get subsidy for " + promoId, e);
		}*/
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
	 /**
     * 
     * @param req
     * @param rsp
     * @param itemId
     */
    @RequestMapping(value = "/getNotification", method = RequestMethod.GET)
    public @ResponseBody Map <String, Object> getNotification(HttpServletRequest req
    		,HttpServletResponse rsp,@RequestParam("userId") String userid) {
    	
    	String resultJson = null;
		try {
			resultJson = sdDataService.getNotification(userid);
		} catch (HttpException e) {
			logger.error("Unable to get getNotification for " + userid, e);
		}
		Map <String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("status", true);
		jsonMap.put("data", resultJson);
		return jsonMap;
        
    }
    
    @RequestMapping(value = "/getNotiIgnoreSatus", method = RequestMethod.GET)
    public @ResponseBody Map <String, Object> getNotiIgnoreSatus(HttpServletRequest req
    		,HttpServletResponse rsp,@RequestParam("userId") String userid) {
    	
    	String resultJson = null;
    	try {
    		resultJson = sdDataService.getNotiIgnoreSatus(userid);
    	} catch (HttpException e) {
    		logger.error("Unable to get getNotiIgnoreSatus for " + userid, e);
    	}
    	Map <String, Object> jsonMap = new HashMap<String, Object>();
    	jsonMap.put("status", true);
    	jsonMap.put("data", resultJson);
    	return jsonMap;
    	
    }
    
    @RequestMapping(value = "/setSDNotifiStatus", method = RequestMethod.GET)
    public @ResponseBody Map <String, Object> setSDNotifiStatus(HttpServletRequest req
    		,HttpServletResponse rsp,@RequestParam("userId") String userid) {
    	
    	String resultJson = null;
		try {
			resultJson = sdDataService.setSDNotifiStatus(userid);
		} catch (HttpException e) {
			logger.error("Unable to get setSDNotifiStatus for " + userid, e);
		}
		Map <String, Object> jsonMap = new HashMap<String, Object>();
		jsonMap.put("status", true);
		jsonMap.put("data", resultJson);
		return jsonMap;
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
		return result;
	}
}