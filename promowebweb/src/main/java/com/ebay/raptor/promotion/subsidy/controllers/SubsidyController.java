package com.ebay.raptor.promotion.subsidy.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.po.WLTAccount;
import com.ebay.cbt.raptor.promotion.po.SubsidyCustomField;
import com.ebay.cbt.raptor.promotion.response.SubsidyLegalTermResponse;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.subsidy.service.SubsidyService;

/**
 * 
 * @author lyan2 2017-3-17
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
	public ModelAndView handleRequest(@RequestParam("promoId") String promoId, HttpServletRequest request,
			HttpServletResponse response) throws MissingArgumentException, IOException {
		ModelAndView model = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(request);
		Date now = new Date();
		Promotion promo = null;
		SubsidyLegalTermResponse term = null;

		try {
			promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), "CN");

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

	@RequestMapping(value = "/acknowledgment", method = RequestMethod.POST)
	@ResponseBody
	public ResponseData<String> saveSellerCustomFields(@RequestParam("promoId") String promoId,
			HttpServletRequest request, HttpServletResponse response) throws MissingArgumentException, IOException {
		UserData userData = loginService.getUserDataFromCookie(request);
		Promotion promo = null;
		SubsidyLegalTermResponse term = null;
		ResponseData<String> responseData = new ResponseData<String>();

		term = subsidyService.getSubsidyLegalTerm(promo.getRewardType(), "CN");
		
		ArrayList<SubsidyCustomField> fields = term.getSellerFillingFields();
		
		HashMap<String, String> map = new HashMap<String, String>();
		
		for (SubsidyCustomField field : fields) {
			map.put(field.getKey(), request.getParameter(field.getKey()));
		}
		
		// TODO Save map into database
		
		responseData.setStatus(true);
		responseData.setData(map.toString());
		
		return responseData;
	}

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView test(HttpServletRequest request, HttpServletResponse response) throws MissingArgumentException,
			IOException {
		ModelAndView model = new ModelAndView();
		WLTAccount account = null;
		UserData userData = loginService.getUserDataFromCookie(request);
		System.out.println("i am in now can u see me");
		try {
			account = subsidyService.getTest("11111", 1L);
			// account = new WLTAccount();
			// account.setId(2222);
			model.addObject("ha", account);
			model.setViewName(ViewResource.TEST_PAGE.getPath());
		} catch (Exception e) {
		}
		return model;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(MissingArgumentException exception, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView("error");
		CalEventHelper.writeException(exception.getErrorType().name(), exception);
		return mav;
	}
}
