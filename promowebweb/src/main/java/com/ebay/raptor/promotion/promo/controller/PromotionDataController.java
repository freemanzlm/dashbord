package com.ebay.raptor.promotion.promo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.CommonException;
import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.businesstype.PMPromotionType;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.excel.ExcelReader;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excel.UploadListingSheetHandler;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.web.resp.DataWebResponse;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.ContextViewRes;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;

@Controller
@RequestMapping(ResourceProvider.PromotionRes.base)
public class PromotionDataController{
	private static CommonLogger logger =
            CommonLogger.getInstance(PromotionDataController.class);

	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	PromotionService service;
	
	@Autowired
	PromotionViewService view;
	
	@Autowired DealsListingService dealsListingService;
	
	@Autowired ResourceBundleMessageSource messageSource;
	
	@GET
	@RequestMapping("/{promoId}")
	public ModelAndView promotion(@PathVariable("promoId") String promoId) {
		ModelAndView model = new ModelAndView();
		//TODO Get the uid from cookie
		Long uid = -1L;
		try {
			Promotion promo = service.getPromotionById(promoId);

			if(null != promo){
				ContextViewRes res = handleViewBasedOnPromotion(promo);
				model.setViewName(res.getView().getPath());
				model.addAllObjects(res.getContext());
				model.addObject(ViewContext.Promotion.getAttr(), promo);
			}
		} catch (PromoException e) {
			e.printStackTrace();
			model.setViewName(ViewResource.ERROR.getPath());
		}
		return model;
	}
	
	private ContextViewRes handleViewBasedOnPromotion(Promotion promo) throws PromoException{
		ContextViewRes result = new ContextViewRes();
		switch(PMPromotionType.valueOfPMType(promo.getType())){
			case HIGH_VELOCITY:
				result = view.highVelocityView(promo);
				break;
			case DEALS_DASHBOARD_UPLOAD:
				result = view.dealsUpload(promo);
				break;
			case DEALS_AM_UPLOAD:
				result = view.dealsPresetView(promo);
				break;
			case STANDARD:
				result = view.standard(promo);
				break;
			case PM_UNKNOWN_TYPE:
				result.setView(ViewResource.ERROR);
				break;
		}
		return result;
	}
	

	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getIngPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getIngPromotion() {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			//TODO Change to cookie.
			Long uid = ListingWebParam.UID; 
			resp.setData(service.getIngPromotion(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getSubsidyPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getSubsidyPromotions() {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			//TODO Change to cookie.
			Long uid = ListingWebParam.UID; 
			resp.setData(service.getSubsidyPromotions(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getEndPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getEndPromotions() {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			//TODO Change to cookie.
			Long uid = ListingWebParam.UID; 
			resp.setData(service.getEndPromotions(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getPromotions)
	@ResponseBody
	public ListDataWebResponse<Promotion> getPromotions(@RequestParam("uid") Long uid) {
		ListDataWebResponse<Promotion> resp = new ListDataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotions(uid));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.PromotionRes._getPromotionById)
	@ResponseBody
	public DataWebResponse<Promotion> getPromotionById(@RequestParam("promoId")String promoId, @RequestParam("uid") Long uid) {
		DataWebResponse<Promotion> resp = new DataWebResponse<Promotion>();
		try {
			resp.setData(service.getPromotionById(promoId));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@RequestMapping(value = ResourceProvider.ListingRes.uploadDealsListings, method = RequestMethod.POST)
	public ModelAndView handleUploadRequest(HttpServletRequest request,
            HttpServletResponse response, @RequestPart("UploadListing") MultipartFile xmlFile,
            @RequestParam String promoId) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView();
		
		UserData userData = CookieUtil.getUserDataFromCookie(request);
		
		XSSFWorkbook workbook = null;

		try {
			workbook = new XSSFWorkbook(xmlFile.getInputStream());
			ExcelReader.readWorkbook(workbook, 0,
					new UploadListingSheetHandler(dealsListingService,
							promoId, userData.getUserId()));

			mav.addObject("formUrl", "submit"); // TODO - use constants
			mav.setViewName(ViewResource.DU_LISTING_PREVIEW.getPath());
		} catch (IOException | PromoException e) {
			// Got IO or PromoException exception -> means app level error -> show error page.
			logger.error("Upload listings got error.", e);
			mav.setViewName(ViewResource.ERROR.getPath());
		} catch (CommonException e) {
			// Got logic exception -> check the error code and return the message to UI
			logger.error("The uploaded listings are invalid.", e);

			try {
				Promotion promo = service.getPromotionById(promoId);

				if(null != promo){
					ContextViewRes res = handleViewBasedOnPromotion(promo);
					mav.setViewName(res.getView().getPath());
					mav.addAllObjects(res.getContext());
					mav.addObject(ViewContext.Promotion.getAttr(), promo);
					ErrorType errorType = e.getErrorType();
					mav.addObject("errorMsg",
							messageSource.getMessage("err-"+errorType.getCode(),
									e.getArgs(), Locale.SIMPLIFIED_CHINESE)); // TODO - use constants
				}
			} catch (PromoException ex) {
				mav.setViewName(ViewResource.ERROR.getPath());
			}
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					// ignore...
				}
			}
		}

		return mav;
	}

	public static void main(String[] args){
		System.out.println(new Date());
	}
	

}
