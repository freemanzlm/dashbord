package com.ebay.raptor.promotion.list.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
import com.ebay.app.raptor.promocommon.export.ColumnFormat;
import com.ebay.app.raptor.promocommon.export.HeaderConfiguration;
import com.ebay.app.raptor.promocommon.export.write.ExcelSheetWriter;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excel.UploadListingSheetHandler;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;

@Controller
@RequestMapping(ResourceProvider.ListingRes.dealsBase)
public class DealsListingController extends AbstractListingController{

	private static CommonLogger logger = CommonLogger.getInstance(DealsListingController.class);
	
	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	DealsListingService service;
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.downloadSkuList)
    public void handleTempletRequest(HttpServletRequest req, HttpServletResponse resp, @ModelAttribute RequestParameter param) {
        try {
        	resp.setContentType("application/x-msdownload;");
        	resp.setHeader("Content-disposition", "attachment; filename=" + ResourceProvider.ListingRes.skuListFileName + ".xlsx");
//        	UserData userData = CookieUtil.getUserDataFromCookie(req);
        	
        	List<DealsListing> skuListings = service.getSkuListingByPromotionId(param.getPromoId(), ListingWebParam.UID); // TODO userData.getUserId());


        	XSSFWorkbook workBook = new XSSFWorkbook();
        	ExcelSheetWriter<DealsListing> writer = new ExcelSheetWriter<DealsListing>(DealsListing.class, workBook, ResourceProvider.ListingRes.skuListFileName);
            writer.resetHeaders();
            writer.build(skuListings);
            workBook.write(resp.getOutputStream());
        } catch (IOException | PromoException e) {
        	logger.error("Unable to download deals listing.", e);
        }
    }
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadDealsListings)
	public ModelAndView uploadDealsListings(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile dealsListings, @RequestParam String promoId) throws MissingArgumentException{
		ModelAndView mav = new ModelAndView();
		UserData userData = CookieUtil.getUserDataFromCookie(req);

		XSSFWorkbook workbook = null;
		try {

			workbook = new XSSFWorkbook(dealsListings.getInputStream());
			ExcelReader.readWorkbook(workbook, 0, new UploadListingSheetHandler(service,
							promoId, userData.getUserId()));

			mav.addObject("formUrl", "submit"); // TODO - use constants and get the status
			mav.addObject(ViewContext.PromotionId.getAttr(), promoId);
			mav.setViewName(ViewResource.DU_LISTING_PREVIEW.getPath());
		} catch (IOException | PromoException e) {
			// Got IO or PromoException exception -> means app level error -> show error page.
			logger.error("Upload listings got error.", e);
			mav.setViewName(ViewResource.ERROR.getPath());
		} catch (CommonException e) {
			// Got logic exception -> check the error code and return the message to UI
			logger.error("The uploaded listings are invalid.", e);

			try {
				addPromotionContext(mav, promoId, ViewResource.DU_APPLICABLE.getPath());
				ErrorType errorType = e.getErrorType();
				mav.addObject(ViewContext.ErrorMsg.getAttr(),
									messageSource.getMessage("err-"+errorType.getCode(),
									e.getArgs(), Locale.SIMPLIFIED_CHINESE)); // TODO - use constants
				mav.setViewName(ViewResource.DU_APPLICABLE.getPath());

			} catch (PromoException ex) {
				logger.error("Unable to get promotion data.", e);
				mav.setViewName(ViewResource.ERROR.getPath());
			}
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {}
			}
		}
		return mav;
	}
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.confirmDealsListings)
	public ModelAndView confirmDealsListings(@ModelAttribute("listings") UploadListingForm listings) {
		ModelAndView model = new ModelAndView();
		if(null != listings){
			//TODO Add uid
			listings.setUid(ListingWebParam.UID);
			Listing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), Listing[].class, false);
			try {
				boolean result = service.confirmDealsListings(listingAry, listings.getPromoId(), listings.getUid());
				if(result){
					Promotion promotion = this.promoService.getPromotionById(listings.getPromoId());
					model.addObject(ViewContext.Promotion.getAttr(), promotion);
					switch(PMPromotionType.valueOfPMType(promotion.getType())){
						case DEALS_DASHBOARD_UPLOAD:
							model.setViewName(ViewResource.DU_APPLIED.getPath());
							break;
						case DEALS_AM_UPLOAD:
							model.setViewName(ViewResource.DP_APPLIED.getPath());
							break;
						default:
							model.setViewName(ViewResource.ERROR.getPath());
							break;
					}
				}
			} catch (PromoException e) {
				model.setViewName(ViewResource.ERROR.getPath());
			}
		}
		return model;
	}

	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApplicableListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param) throws MissingArgumentException {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		try {
			resp.setData(service.getApplicableListings(param.getPromoId(), userData.getUserId()));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getAppliedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getAppliedListings(@ModelAttribute ListingWebParam param) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			resp.setData(service.getAppliedListings(param.getPromoId(), param.getUid()));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApprovedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApprovedListings(@ModelAttribute ListingWebParam param) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			resp.setData(service.getApprovedListings(param.getPromoId(), param.getUid()));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getSKUsByPromotionId)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getSKUsByPromotionId(@ModelAttribute ListingWebParam param) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			resp.setData(service.getSkuListingByPromotionId(param.getPromoId(), param.getUid()));
		} catch (PromoException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}

}
