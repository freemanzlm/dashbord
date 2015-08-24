package com.ebay.raptor.promotion.controller;

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
import com.ebay.raptor.promotion.pojo.business.PromotionStatus;
import com.ebay.raptor.promotion.pojo.business.PromotionType;
import com.ebay.raptor.promotion.pojo.business.UserPromotion;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.service.PromotionService;
import com.ebay.raptor.promotion.util.CookieUtil;

@Controller
public class PromotionController {
    private static CommonLogger logger =
            CommonLogger.getInstance(PromotionController.class);

    @Autowired CSApiService csApiService;
    @Autowired LoginService loginService;
    @Autowired PromotionService promotionService;

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
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();

//        UserData userData = CookieUtil.getUserDataFromCookie(request);

        // add page level data: userId, userName, admin
//        addPageLevelData(mav, userData, param);

        if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(param.getLang())) {
        	mav.setViewName("zh_HK/index");
        } else {
        	mav.setViewName("index"); // TODO - change to zh_CN/index
        }
        return mav;
    }
    
    @RequestMapping(value = "/promotion", method = RequestMethod.GET)
    public ModelAndView handlePromotionRequest(HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RequestParameter param) throws MissingArgumentException {
        ModelAndView mav = new ModelAndView();

        UserData userData = CookieUtil.getUserDataFromCookie(request);

        // add page level data: userId, userName, admin
        addPageLevelData(mav, userData, param);
        
        // TODO - get Promotion Description
        addPromotionPageData(mav, userData, param);

        // show page according to promotion status

        return mav;
    }

    @ExceptionHandler(MissingArgumentException.class)
    public ModelAndView handleException(MissingArgumentException exception,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        logger.error("Missing required arguments from cookie.", exception);
        return mav;
    }
    
    private void addPageLevelData (ModelAndView mav,
            UserData userData, RequestParameter param) {
        mav.addObject("userId", userData.getUserId());
        mav.addObject("userName", userData.getUserName());
        mav.addObject("lang", param.getLang());
    }

    private void addPromotionPageData (ModelAndView mav,
            UserData userData, RequestParameter param) {
    	UserPromotion promotionDetail =
        		promotionService.getPromotionDetail(userData.getUserId(),
        				param.getPromoId());
 
    	PromotionType promotionType = promotionDetail.getPromotionType();

        switch (promotionType) {
        	case HotSell:
        		handleHotSellPromotion(mav, userData, promotionDetail, param.getLang());
        		break;
        	case DealsPreset:
        		handleDealsPresetPromotion(mav, userData, promotionDetail, param.getLang());
        		break;
        	case DealsUpload:
        		handleDealsUploadPromotion(mav, userData, promotionDetail, param.getLang());
        		break;
        	case Other: break;
        	default:
        }
        
        mav.addObject("promoDetail", promotionDetail);
    }
    
    private void handleHotSellPromotion (ModelAndView mav,
            UserData userData, UserPromotion promotionDetail, String lang) {
    	PromotionStatus promoStatus  = promotionDetail.getPromoStatus();
    	Boolean result = promotionDetail.getPromoResult();
    	String viewName = "";

    	if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(lang)) {
    		viewName = "zh_HK/";
    	} else {
//    		viewName = "zh_CN/";
    	}

    	switch (promoStatus) {
    		case Created:
    			viewName += "hotsell/applicable";
    			break;
    		case OwnerConfirmed:
    			viewName += "hotsell/applicable";
    			break;
    		case Enrolled:
    			viewName += "hotsell/applied";
    			break;
    		case EnrollAudited:
    			if (result) {
    				viewName += "hotsell/state";
    				mav.addObject("state", "ongoing");
    			} else {
    				viewName += "hotsell/applyFail";
    			}
    			break;
    		case Start:
    			if (result) {
    				viewName += "hotsell/state";
    				mav.addObject("state", "ongoing");
    			} else {
    				viewName += "hotsell/applyFail";
    			}
    			break;
    		case ShutDown:
    			if (result) {
    				viewName += "hotsell/state";
    				mav.addObject("state", "rewarding");
    			} else {
    				viewName += "hotsell/end";
    			}
    			break;
    		case SubsidyAudited:
    			if (result) {
    				viewName += "hotsell/state";
    				mav.addObject("state", "claimable");
    			} else {
    				viewName += "hotsell/end";
    			}
    			break;
    		case SubsidyApplied:
    			if (result) {
    				viewName += "hotsell/state";
    				mav.addObject("state", "complete");
    			} else {
    				viewName += "hotsell/end";
    			}
    			break;
    		case End:
    			viewName += "hotsell/end";
    			break;
    		default:
    			viewName += "error";
    			break;
    	}

    	mav.setViewName(viewName);
    }
    
    private void handleDealsPresetPromotion (ModelAndView mav,
            UserData userData, UserPromotion promotionDetail, String lang) {
    	PromotionStatus promoStatus  = promotionDetail.getPromoStatus();
    	Boolean result = promotionDetail.getPromoResult();
    	String viewName = "";

    	if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(lang)) {
    		viewName = "zh_HK/";
    	} else {
//    		viewName = "zh_CN/";
    	}

    	switch (promoStatus) {
    		case Created:
    			viewName += "dealspreset/applicable";
    			break;
    		case OwnerConfirmed:
    			viewName += "dealspreset/applicable";
    			break;
    		case Enrolled:
    			viewName += "dealspreset/applied";
    			break;
    		case EnrollAudited:
    			if (result) {
    				viewName += "dealspreset/state";
    				mav.addObject("state", "ongoing");
    			} else {
    				viewName += "dealspreset/applyFail";
    			}
    			break;
    		case Start:
    			if (result) {
    				viewName += "dealspreset/state";
    				mav.addObject("state", "ongoing");
    			} else {
    				viewName += "dealspreset/applyFail";
    			}
    			break;
    		case ShutDown:
    			if (result) {
    				viewName += "dealspreset/state";
    				mav.addObject("state", "rewarding");
    			} else {
    				viewName += "dealspreset/end";
    			}
    			break;
    		case SubsidyAudited:
    			if (result) {
    				viewName += "dealspreset/state";
    				mav.addObject("state", "claimable");
    			} else {
    				viewName += "dealspreset/end";
    			}
    			break;
    		case SubsidyApplied:
    			if (result) {
    				viewName += "dealspreset/state";
    				mav.addObject("state", "complete");
    			} else {
    				viewName += "dealspreset/end";
    			}
    			break;
    		case End:
    			viewName += "dealspreset/end";
    			break;
    		default:
    			viewName += "error";
    			break;
    	}

    	mav.setViewName(viewName);
    }
    
    private void handleDealsUploadPromotion (ModelAndView mav,
            UserData userData, UserPromotion promotionDetail, String lang) {
    	PromotionStatus promoStatus  = promotionDetail.getPromoStatus();
    	Boolean result = promotionDetail.getPromoResult();
    	String viewName = "";

    	if (CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(lang)) {
    		viewName = "zh_HK/";
    	} else {
//    		viewName = "zh_CN/";
    	}

    	switch (promoStatus) {
    		case Created:
    			viewName += "deals/applicable";
    			break;
    		case OwnerConfirmed:
    			viewName += "deals/applicable";
    			break;
    		case Enrolled:
    			// enrolled and uploaded listings, waiting for auditing
    			viewName += "deals/applied";
    			break;
    		case EnrollFirstAudited:
    			// first audit complete, need to confirm and submit listings
    			if (result) {
    				viewName += "deals/listing";
    				mav.addObject("state", "inquiry");
    				mav.addObject("expired", false);
    			} else {
    				viewName += "deals/applyFail";
    			}
    			break;
    		case EnrollSubmitted:
    			// enroll confirmed, need to submit the final listings
    			if (result) {
    				viewName += "deals/listing";
    				mav.addObject("state", "confirm");
    				mav.addObject("expired", false);
    			} else {
    				viewName += "deals/applyFail";
    			}
    			break;
    		case EnrollAudited:
    			// final audit complete, waiting to start
    			if (result) {
    				viewName += "deals/listing";
    				mav.addObject("state", "confirm");
    				mav.addObject("expired", false);
    			} else {
    				viewName += "deals/applyFail";
    			}
    			break;
    		case Start:
    			if (result) {
    				viewName += "deals/state";
    				mav.addObject("state", "ongoing");
    			} else {
    				viewName += "deals/applyFail";
    			}
    			break;
    		case ShutDown:
    			if (result) {
    				viewName += "deals/state";
    				mav.addObject("state", "rewarding");
    			} else {
    				viewName += "deals/end";
    			}
    			break;
    		case SubsidyAudited:
    			if (result) {
    				viewName += "deals/state";
    				mav.addObject("state", "claimable");
    			} else {
    				viewName += "deals/end";
    			}
    			break;
    		case SubsidyApplied:
    			if (result) {
    				viewName += "deals/state";
    				mav.addObject("state", "complete");
    			} else {
    				viewName += "deals/end";
    			}
    			break;
    		case End:
    			viewName += "deals/end";
    			break;
    		default:
    			viewName += "error";
    			break;
    	}

    	mav.setViewName(viewName);
    }
}
