package com.ebay.raptor.promotion.subsidy.controllers;

import java.io.IOException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.business.SubsidyLegalTerm;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.subsidy.service.SubsidyService;

/**
 * 
 * @author lyan2
 * 2017-3-17
 */
@Controller
@RequestMapping("subsidy")
public class SubsidyController {
	private static final Logger logger = Logger.getInstance(SubsidyController.class);

	@Autowired LoginService loginService;
	@Autowired PromotionService promoService;
	@Autowired PromotionViewService view;
	@Autowired SubsidyService subsidyService;

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.GET)
	public ModelAndView handleRequest(@RequestParam("promoId") String promoId, HttpServletRequest request, HttpServletResponse response)
			throws MissingArgumentException, IOException {
		ModelAndView model = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(request);
		Date now = new Date();
		Promotion promo = null;
		SubsidyLegalTerm term = null;
		
		try {
			promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			term = getSubsidyLegalTerm(promo.getRewardType());
			
			if (promo != null) {
				view.calcualteCurentStep(promo);
				view.appendPromoEndCheck(model.getModel(), promo, now);
				view.appendPromoAwardEndCheck(model.getModel(), promo, now);
				
				model.addObject("subsidyTerm", term);
				model.addObject(ViewContext.Promotion.getAttr(), promo);
				model.addObject(ViewContext.IsAdmin.getAttr(), userData.getAdmin());
				model.setViewName("subsidy_acknowledgment");
			} else {
				model.setViewName(ViewResource.UNKNOW_CAMPAIGN.getPath());
			}
		} catch (PromoException e) {
			String message = "Failed to get promotion: promoId=" + promoId + ",userId:" + userData.getUserId();
			CalEventHelper.writeException("SubsidyError", e, message);
			logger.log(LogLevel.ERROR, message, e);
		}
		
		if (promo == null) {
			model.setViewName(ViewResource.ERROR.getPath());
		}

		return model;
	}
	
	/**
	 * 
	 * @param paymentType
	 * @return
	 */
	private SubsidyLegalTerm getSubsidyLegalTerm(Integer paymentType) {
		SubsidyLegalTerm term = new SubsidyLegalTerm();
		
		return term;
	}
	
	@ExceptionHandler(Exception.class)
    public ModelAndView handleException(MissingArgumentException exception,
            HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("error");
        CalEventHelper.writeException(exception.getErrorType().name(), exception);
        return mav;
    }
}
