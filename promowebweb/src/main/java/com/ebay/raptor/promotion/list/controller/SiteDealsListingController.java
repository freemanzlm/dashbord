package com.ebay.raptor.promotion.list.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
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
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.excel.ExcelReader;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.excel.APACDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.FRESDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.GBHDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.InvalidCellDataException;
import com.ebay.raptor.promotion.excel.SiteDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.PromotionSubType;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.ebay.raptor.promotion.util.PromotionUtil;

@Controller
@RequestMapping(ResourceProvider.ListingRes.siteDeals)
public class SiteDealsListingController extends AbstractListingController{
	private static CommonLogger logger = CommonLogger.getInstance(SiteDealsListingController.class);
	
	@Autowired
	DealsListingService service;
	
	@Autowired SpringValidatorAdapter validator;
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.downloadSkuList)
    public void handleTempletRequest(HttpServletRequest req, HttpServletResponse resp, @ModelAttribute RequestParameter param) {
        try {
        	resp.setContentType("application/x-msdownload;");
        	resp.setHeader("Content-disposition", "attachment; filename=" + ResourceProvider.ListingRes.skuListFileName + ".xlsx");
        	UserData userData = CookieUtil.getUserDataFromCookie(req);
        	
        	List<GBHDealsListing> gbhListings = initGBHList();

        	XSSFWorkbook workBook = new XSSFWorkbook();
        	ExcelSheetWriter<GBHDealsListing> writer = new ExcelSheetWriter<GBHDealsListing>(GBHDealsListing.class, workBook, ResourceProvider.ListingRes.skuListFileName);
            writer.resetHeaders();
            writer.build(gbhListings, 1, 3, 1, 3, 0, PromotionUtil.LISTING_TEMP_PASS);
            workBook.write(resp.getOutputStream());
        } catch (IOException | MissingArgumentException e) {
        	logger.error("Unable to download deals listing.", e);
        }
    }
	
	private static List<GBHDealsListing> initGBHList() {
		List<GBHDealsListing> gbhListings = new ArrayList<>();
		for(int i=0; i<10; i++) {
			GBHDealsListing listing = new GBHDealsListing();
			listing.setSkuName("testSKUNAME");
			listing.setSkuId("00000"+i);
			listing.setItemId((long)10000+i);
			listing.setListPrice((float)20.1+i);
			listing.setDealPrice((float)20.1-i);
			listing.setQty((long)17*i+i);
			gbhListings.add(listing);
		}
		return gbhListings;
	}
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadDealsListings)
	public ModelAndView uploadDealsListings(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile dealsListings, @RequestParam String promoId, @RequestParam String promoSubType) throws MissingArgumentException{
		ModelAndView mav = new ModelAndView(ViewResource.DU_UPLOAD_RESPONSE.getPath());
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		ResponseData <String> responseData = new ResponseData <String>();

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(dealsListings.getInputStream());
			SiteDealsListingSheetHandler<?> handler = null;
			
			if (promoSubType.equals(PromotionSubType.GBH.toString())) {
				handler = new GBHDealsListingSheetHandler(validator, service,
						promoId, userData.getUserId());
			} else if (promoSubType.equals(PromotionSubType.APAC.toString())) {
				handler = new APACDealsListingSheetHandler(validator, service,
						promoId, userData.getUserId());
			} else if (promoSubType.equals(PromotionSubType.FRES.toString())) {
				handler = new FRESDealsListingSheetHandler(validator, service,
						promoId, userData.getUserId());
			} else {
				logger.error("Unsupported promotion type " + promoSubType);
				responseData.setStatus(false);
			}
			
			if (handler != null) {
				ExcelReader.readWorkbook(workbook, 0, handler);
				responseData.setStatus(true);
				this.acceptAgreement(promoId, userData.getUserId());
			}
		} catch (IOException e) {
			// Got IO or PromoException exception -> means app level error -> show error page.
			logger.error("Upload listings got error.", e);
			responseData.setStatus(false);
		} catch (PromoException e) {
			// Got IO or PromoException exception -> means app level error -> show error page.
			logger.error("Upload listings got error.", e);
			responseData.setData(e.getErrorType().getCode() + "");
			responseData.setStatus(false);
		} catch (InvalidCellDataException e) {
			logger.error("The uploaded listings are invalid.", e);
			responseData.setStatus(false);
			Errors errors = e.getErrors();
			
			responseData.setMessage(errors.getFieldError().getDefaultMessage());
		} catch (CommonException e) {
			// Got logic exception -> check the error code and return the message to UI
			logger.error("The uploaded listings are invalid.", e);
			
			Locale locale = Locale.SIMPLIFIED_CHINESE;
			
			if (!StringUtil.isEmpty(userData.getLang())
					&& CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(userData.getLang())) {
				locale = new Locale("zh", "HK");
			}

			ErrorType errorType = e.getErrorType();
			responseData.setStatus(false);
			responseData.setMessage(messageSource.getMessage("err-"+ errorType.getCode(),
									e.getArgs(), locale));
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {}
			}
		}

		mav.addObject("response", PojoConvertor.convertToJson(responseData));
		return mav;
	}
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.submitDealsListings)
	public @ResponseBody ResponseData <String> submitDealsListings(HttpServletRequest req, HttpServletResponse resp) throws MissingArgumentException{
		ResponseData <String> responseData = new ResponseData <String>();
		String promoId = req.getParameter("promoId");

		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			boolean result = service.submitDealsListings(promoId, userData.getUserId());
			responseData.setStatus(result);
		} catch (PromoException | MissingArgumentException e) {
			// do not throw but set the error status.
			responseData.setStatus(false);
			responseData.setMessage("Internal Error happens.");
			responseData.setData(e.getErrorType().getCode() + "");
		}
		return responseData;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.reviewUploadedListings)
	public ModelAndView reviewUploadedListings(@RequestParam String promoId) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("formUrl", "/promotion/deals/submitDealsListings");
		mav.addObject(ViewContext.PromotionId.getAttr(), promoId);
		mav.setViewName(ViewResource.DU_LISTING_PREVIEW.getPath());
		return mav;
	}
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.confirmDealsListings)
	@ResponseBody
	public ResponseData <String> confirmDealsListings(HttpServletRequest req, @ModelAttribute("listings") UploadListingForm listings) {
		ResponseData <String> responseData = new ResponseData <String>();

		if(null != listings){
			Listing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), Listing[].class, false);
			try {
				UserData userData = CookieUtil.getUserDataFromCookie(req);
				boolean result = service.confirmDealsListings(listingAry, listings.getPromoId(), userData.getUserId());
				responseData.setStatus(result);
				this.acceptAgreement(listings.getPromoId(), userData.getUserId());
			} catch (PromoException | MissingArgumentException e) {
				// do not throw but set the error status.
				responseData.setStatus(false);
				responseData.setMessage("Internal Error happens.");
			}
		}
		return responseData;
	}

	@GET
	@RequestMapping(ResourceProvider.ListingRes._getPromotionListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getPromotionListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			List<DealsListing> listings = service.getPromotionListings(param.getPromoId(), userData.getUserId());
			if (listings != null && listings.size() > 0) {
				resp.setData(listings);
			} else {
				logger.error("No applicable listings found.");
			}
		} catch (PromoException | MissingArgumentException e) {
			logger.error("Unable to get applicable listings, with error", e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getUploadedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getUploadedListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			List<DealsListing> listings = service.getUploadedListings(param.getPromoId(), userData.getUserId());
			if (listings != null && listings.size() > 0) {
				resp.setData(listings);
			} else {
				logger.error("No uploaded listings found.");
			}
		} catch (PromoException | MissingArgumentException e) {
			logger.error("Unable to get uploaded listings, with error", e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getSubmittedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getSubmittedListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			List<DealsListing> listings = service.getSubmitedListings(param.getPromoId(), userData.getUserId());
			if (listings != null && listings.size() > 0) {
				resp.setData(listings);
			} else {
				logger.error("No submitted listings found.");
			}
		} catch (PromoException | MissingArgumentException e) {
			logger.error("Unable to get submitted listings, with error", e);
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApprovedListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApprovedListings(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			resp.setData(service.getApprovedListings(param.getPromoId(), userData.getUserId()));
		} catch (PromoException | MissingArgumentException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
}
