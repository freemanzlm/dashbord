package com.ebay.raptor.promotion.list.controller;

import java.io.IOException;
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
import com.ebay.app.raptor.promocommon.excel.IExcelSheetHandler;
import com.ebay.app.raptor.promocommon.export.write.ExcelSheetWriter;
import com.ebay.app.raptor.promocommon.util.CommonConstant;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.kernel.context.IRaptorContext;
import com.ebay.raptor.promotion.excel.APACDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.FRESDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.GBHDealsListingSheetHandler;
import com.ebay.raptor.promotion.excel.InvalidCellDataException;
import com.ebay.raptor.promotion.excel.UploadListingSheetHandler;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.Listing;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.APACDealsListing;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.FRESDealsListing;
import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.PromotionSubType;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.ebay.raptor.promotion.util.PromotionUtil;

@Controller
@RequestMapping(ResourceProvider.ListingRes.dealsBase)
public class DealsListingController extends AbstractDealsListingController{

	private static CommonLogger logger = CommonLogger.getInstance(DealsListingController.class);
	
	@Inject
	IRaptorContext raptorCtx;
	
	@Autowired
	DealsListingService service;
	
	@Autowired SpringValidatorAdapter validator;
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.downloadSkuList)
    public void handleTempletRequest(HttpServletRequest req,
    		HttpServletResponse resp, @ModelAttribute RequestParameter param)
    				throws MissingArgumentException {
		PromotionSubType promoSubType = null;
		
		try {
			// if promotion sub type is empty, handle as general deals listings,
			// and don't throw the NullPointerException
			if (!StringUtil.isEmpty(param.getPromoSubType())) {
				promoSubType = PromotionSubType.valueOf(param.getPromoSubType());
			}
		} catch (Exception e) {
			logger.error("Invalid promotion Sub Type", e);
			throw new MissingArgumentException("PromoSubType");
		}

		UserData userData = CookieUtil.getUserDataFromCookie(req);
		XSSFWorkbook workBook = new XSSFWorkbook();

        try {
        	ExcelSheetWriter<?> writer = null;
        	String fileName = "";

        	if (promoSubType != null) {
        		switch (promoSubType) {
        			case GBH :
        				fileName = ResourceProvider.ListingRes.gbhSkuListFileName;
        				writer = new ExcelSheetWriter<GBHDealsListing>(GBHDealsListing.class,
        						workBook, fileName);
        				break;
        			case FRES :
        				fileName = ResourceProvider.ListingRes.fresSkuListFileName;
        				writer = new ExcelSheetWriter<FRESDealsListing>(FRESDealsListing.class,
        						workBook, fileName);
        				break;
        			case APAC :
        				fileName = ResourceProvider.ListingRes.apacSkuListFileName;
        				writer = new ExcelSheetWriter<APACDealsListing>(APACDealsListing.class,
        						workBook, fileName);
        				break;
        			default :
        				throw new MissingArgumentException("PromoSubType");
        		}
        	} else {
        		fileName = ResourceProvider.ListingRes.skuListFileName;
        		writer = new ExcelSheetWriter<DealsListing>(DealsListing.class,
    					workBook, fileName);
        	}

    		List<?> skuListings = service.getSkuListingsByPromotionId(
    				param.getPromoId(), userData.getUserId(), promoSubType);

    		writer.resetHeaders();
            writer.build(skuListings, 1, 3, 1, 3, 0, PromotionUtil.LISTING_TEMP_PASS);

            resp.setContentType("application/x-msdownload;");
    		resp.setHeader("Content-disposition", "attachment; filename="
    				+ fileName + ".xlsx");
    		workBook.write(resp.getOutputStream());
        } catch (IOException | PromoException e) {
        	logger.error("Unable to download deals listing.", e);
        } finally {
        	try {
				workBook.close();
			} catch (IOException e) {
				// ignore..
			}
        }
    }

	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadDealsListings)
	public ModelAndView uploadDealsListings(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile dealsListings, @RequestParam String promoId, @RequestParam String promoSubType) throws MissingArgumentException{
		ModelAndView mav = new ModelAndView(ViewResource.DU_UPLOAD_RESPONSE.getPath());
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		ResponseData <String> responseData = new ResponseData <String>();
		
		PromotionSubType pSubType = null;
		
		try {
			// if promotion sub type is empty, handle as general deals listings,
			// and don't throw the NullPointerException
			if (StringUtil.isEmpty(promoSubType)) {
				pSubType = PromotionSubType.valueOf(promoSubType);
			}
		} catch (Exception e) {
			logger.error("Invalid promotion Sub Type", e);
			throw new MissingArgumentException("PromoSubType");
		}

		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook(dealsListings.getInputStream());
			IExcelSheetHandler handler = null;
			
			if (pSubType != null) {
				switch (pSubType) {
					case GBH :
						handler = new GBHDealsListingSheetHandler(validator,
								service, promoId, userData.getUserId());
						break;
					case FRES :
						handler = new FRESDealsListingSheetHandler(validator,
								service, promoId, userData.getUserId());
						break;
					case APAC :
						handler = new APACDealsListingSheetHandler(validator,
								service, promoId, userData.getUserId());
						break;
					default :
						throw new MissingArgumentException("PromoSubType");
				}
			} else {
				handler = new UploadListingSheetHandler(service,
						promoId, userData.getUserId());
			}

			ExcelReader.readWorkbook(workbook, 0, handler);
			responseData.setStatus(true);
			this.acceptAgreement(promoId, userData.getUserId());
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
			
			Locale locale = Locale.SIMPLIFIED_CHINESE;
			
			if (!StringUtil.isEmpty(userData.getLang())
					&& CommonConstant.ZHHK_LANGUAGE.equalsIgnoreCase(userData.getLang())) {
				locale = new Locale("zh", "HK");
			}
			
			String errPrefix = messageSource.getMessage("listing.upload.rowPrefix", new Object []{e.getRowIndex()}, locale);
			
			if (errors.getFieldError() != null) {
				responseData.setMessage(errPrefix + errors.getFieldError().getDefaultMessage());
			} else if (errors.getGlobalError() != null) {
				responseData.setMessage(errPrefix + errors.getGlobalError().getDefaultMessage());
			}
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
	public ListDataWebResponse<?> getPromotionListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		return getListings(req, param, DealsListingType.Confirmed);
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getUploadedListings)
	@ResponseBody
	public ListDataWebResponse<?> getUploadedListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		return getListings(req, param, DealsListingType.Uploaded);
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getSubmittedListings)
	@ResponseBody
	public ListDataWebResponse<?> getSubmittedListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		return getListings(req, param, DealsListingType.Submitted);
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApplicableListings)
	@ResponseBody
	public ListDataWebResponse<DealsListing> getApplicableListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		ListDataWebResponse<DealsListing> resp = new ListDataWebResponse<DealsListing>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			List<DealsListing> listings = service.getApplicableListings(param.getPromoId(), userData.getUserId());
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
	@RequestMapping(ResourceProvider.ListingRes._getAppliedListings)
	@ResponseBody
	public ListDataWebResponse<?> getAppliedListings(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		return getListings(req, param, DealsListingType.Applied);
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getApprovedListings)
	@ResponseBody
	public ListDataWebResponse<?> getApprovedListings(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		return getListings(req, param, DealsListingType.Approved);
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getSKUsByPromotionId)
	@ResponseBody
	public ListDataWebResponse<Sku> getSKUsByPromotionId(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		ListDataWebResponse<Sku> resp = new ListDataWebResponse<Sku>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			resp.setData(service.getSkusByPromotionId(param.getPromoId(), userData.getUserId()));
		} catch (PromoException | MissingArgumentException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
//
//	private void writeGBHDealsTemplate (HttpServletResponse resp,
//			String promoId, Long userId)
//					throws PromoException, IOException {
//		resp.setContentType("application/x-msdownload;");
//		XSSFWorkbook workBook = new XSSFWorkbook();
//		
//		ExcelSheetWriter<GBHDealsListing> writer =
//				new ExcelSheetWriter<GBHDealsListing>(GBHDealsListing.class,
//					workBook, ResourceProvider.ListingRes.gbhSkuListFileName);
//
//		List<GBHDealsListing> skuListings = service
//				.getSkuListingsByPromotionIdAndType(promoId, userId,
//						PromotionSubType.GBH);
//
//		writer.resetHeaders();
//		writer.build(skuListings, 1, 2, 1, 3, 0,
//				PromotionUtil.LISTING_TEMP_PASS);
//
//		resp.setHeader("Content-disposition", "attachment; filename="
//				+ ResourceProvider.ListingRes.gbhSkuListFileName + ".xlsx");
//		workBook.write(resp.getOutputStream());
//	}
//	
//	private void writeFRESDealsTemplate (HttpServletResponse resp,
//			String promoId, Long userId)
//					throws PromoException, IOException {
//		resp.setContentType("application/x-msdownload;");
//		XSSFWorkbook workBook = new XSSFWorkbook();
//		
//		ExcelSheetWriter<FRESDealsListing> writer =
//				new ExcelSheetWriter<FRESDealsListing>(FRESDealsListing.class,
//					workBook, ResourceProvider.ListingRes.fresSkuListFileName);
//
//		List<FRESDealsListing> skuListings = service
//				.getSkuListingsByPromotionIdAndType(promoId, userId,
//						PromotionSubType.FRES);
//
//		writer.resetHeaders();
//		writer.build(skuListings, 1, 2, 1, 3, 0,
//				PromotionUtil.LISTING_TEMP_PASS);
//
//		resp.setHeader("Content-disposition", "attachment; filename="
//				+ ResourceProvider.ListingRes.fresSkuListFileName + ".xlsx");
//		workBook.write(resp.getOutputStream());
//	}
//	
//	private void writeAPACDealsTemplate (HttpServletResponse resp,
//			String promoId, Long userId)
//					throws PromoException, IOException {
//		resp.setContentType("application/x-msdownload;");
//		XSSFWorkbook workBook = new XSSFWorkbook();
//		
//		ExcelSheetWriter<APACDealsListing> writer =
//				new ExcelSheetWriter<APACDealsListing>(APACDealsListing.class,
//					workBook, ResourceProvider.ListingRes.apacSkuListFileName);
//
//		List<APACDealsListing> skuListings = service
//				.getSkuListingsByPromotionIdAndType(promoId, userId,
//						PromotionSubType.APAC);
//
//		writer.resetHeaders();
//		writer.build(skuListings, 1, 2, 1, 3, 0,
//				PromotionUtil.LISTING_TEMP_PASS);
//
//		resp.setHeader("Content-disposition", "attachment; filename="
//				+ ResourceProvider.ListingRes.apacSkuListFileName + ".xlsx");
//		workBook.write(resp.getOutputStream());
//	}
//	
//	private void writeGeneralDealsTemplate (HttpServletResponse resp,
//			String promoId, Long userId)
//					throws PromoException, IOException {
//		resp.setContentType("application/x-msdownload;");
//		XSSFWorkbook workBook = new XSSFWorkbook();
//
//		ExcelSheetWriter<DealsListing> writer =
//				new ExcelSheetWriter<DealsListing>(DealsListing.class,
//						workBook, ResourceProvider.ListingRes.skuListFileName);
//		List<DealsListing> skuListings = service.getSkuListingsByPromotionId(
//				promoId, userId);
//
//		writer.resetHeaders();
//        writer.build(skuListings, 1, 3, 1, 3, 0, PromotionUtil.LISTING_TEMP_PASS);
//
//		resp.setHeader("Content-disposition", "attachment; filename="
//				+ ResourceProvider.ListingRes.skuListFileName + ".xlsx");
//		workBook.write(resp.getOutputStream());
//	}
	
	private void writeDealsTemplate (HttpServletResponse resp,
			String promoId, Long userId, PromotionSubType promoSubType)
					throws PromoException, IOException {
		resp.setContentType("application/x-msdownload;");
		XSSFWorkbook workBook = new XSSFWorkbook();
		ExcelSheetWriter<?> writer = null;
		
		if (promoSubType != null) {
    		switch (promoSubType) {
    			case GBH :
    				writer = new ExcelSheetWriter<GBHDealsListing>(GBHDealsListing.class,
    						workBook, ResourceProvider.ListingRes.gbhSkuListFileName);
    				break;
    			case FRES :
    				writer = new ExcelSheetWriter<FRESDealsListing>(FRESDealsListing.class,
    						workBook, ResourceProvider.ListingRes.fresSkuListFileName);
    				break;
    			case APAC :
    				writer = new ExcelSheetWriter<APACDealsListing>(APACDealsListing.class,
    						workBook, ResourceProvider.ListingRes.apacSkuListFileName);
    				break;
    			default :
    				return;
    		}
    	} else {
    		writer = new ExcelSheetWriter<DealsListing>(DealsListing.class,
					workBook, ResourceProvider.ListingRes.skuListFileName);
    	}

		List<?> skuListings = service.getSkuListingsByPromotionId(
				promoId, userId, promoSubType);

		writer.resetHeaders();
        writer.build(skuListings, 1, 3, 1, 3, 0, PromotionUtil.LISTING_TEMP_PASS);

		resp.setHeader("Content-disposition", "attachment; filename="
				+ ResourceProvider.ListingRes.skuListFileName + ".xlsx");
		workBook.write(resp.getOutputStream());
	}

	private ListDataWebResponse<?> getListings (HttpServletRequest req,
			ListingWebParam param, DealsListingType listType) {
		PromotionSubType pSubType = null;
		UserData userData = null;

		try {
			userData = CookieUtil.getUserDataFromCookie(req);
		} catch (MissingArgumentException e) {
			logger.error("Missing required argument.", e);
			ListDataWebResponse<Void> resp = new ListDataWebResponse<Void>();
			resp.setStatus(Boolean.FALSE);
			return resp;
		}

		try {
			// if promotion sub type is empty, handle as general deals listings,
			// and don't throw the NullPointerException
			if (StringUtil.isEmpty(param.getPromoSubType())) {
				pSubType = PromotionSubType.valueOf(param.getPromoSubType());
			}
		} catch (Exception e) {
			logger.error("Invalid promotion Sub Type", e);
			ListDataWebResponse<Void> resp = new ListDataWebResponse<Void>();
			resp.setStatus(Boolean.FALSE);
			return resp;
		}
		
		if (pSubType != null) {
			switch (pSubType) {
				case GBH :
					ListDataWebResponse<GBHDealsListing> gbhResp = getListings(
							param.getPromoId(), userData.getUserId(), PromotionSubType.GBH, listType);
					return gbhResp;
				case FRES :
					ListDataWebResponse<FRESDealsListing> fresResp = getListings(
							param.getPromoId(), userData.getUserId(), PromotionSubType.FRES, listType);
					return fresResp;
				case APAC :
					ListDataWebResponse<APACDealsListing> apacResp = getListings(
							param.getPromoId(), userData.getUserId(), PromotionSubType.APAC, listType);
					return apacResp;
				default :
					ListDataWebResponse<Void> resp = new ListDataWebResponse<Void>();
					resp.setStatus(Boolean.FALSE);
					return resp;
			}
		} else {
			ListDataWebResponse<DealsListing> resp = getListings(
					param.getPromoId(), userData.getUserId(), null, listType);
			return resp;
		}
	}

}
