package com.ebay.raptor.promotion.list.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.promotion.po.ListingAttachment;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.kernel.calwrapper.CalEvent;
import com.ebay.kernel.calwrapper.CalEventHelper;
import com.ebay.kernel.logger.LogLevel;
import com.ebay.kernel.logger.Logger;
import com.ebay.raptor.promotion.Router;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.UploadedListingFileHandler;
import com.ebay.raptor.promotion.excel.service.ExcelService;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.excep.UploadListingIsNullException;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.web.resp.ListingsUploadWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.util.LocaleUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.ebay.raptor.promotion.validation.AttachmentFileValidator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 
 * @author lyan2
 */
@Controller
@RequestMapping(Router.Listing.base)
public class ListingController extends AbstractListingController {
	private Logger logger = Logger.getInstance(ListingController.class);
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired LoginService loginService;
	@Autowired ListingService listingService;
	@Autowired PromotionViewService promoViewService;
	@Autowired ExcelService excelService;
	@Autowired PromotionService promotionService;

	/**
	 * Generate listing upload template.
	 * To use this method, you need to pass in promotion id and user oracle id.
	 * @param req
	 * @param resp
	 * @param param
	 * @throws MissingArgumentException
	 * @throws IOException 
	 * @throws JsonProcessingException 
	 * @throws PromoException 
	 */
	@GET
	@RequestMapping(Router.Listing.downloadTempldate)
    public void createListingUploadTemplet(HttpServletRequest req,
    		HttpServletResponse resp, @ModelAttribute RequestParameter param)
    				throws MissingArgumentException, PromoException, JsonProcessingException, IOException {
		
		UserData userData = loginService.getUserDataFromCookie(req);
		XSSFWorkbook workBook = null;
        try {
        	workBook = excelService.getListingWorkbook(param.getPromoId(),	userData.getUserId(), LocaleUtil.getCurrentLocale(), userData.getAdmin());
        	resp.setHeader("Content-disposition", "attachment; filename=" + excelService.getSKUListingTemplateFileName());
	        resp.setContentType("application/x-msdownload;");
	        workBook.write(resp.getOutputStream());
        } catch (IOException | PromoException e) {
        	logger.log(LogLevel.ERROR, "Failed to generate upload template.", e);
        } finally {
        	if (workBook != null) {
    			try {
		workBook.close();
				} catch (IOException e) {
					// ignore
				}
    		}
        }
    }
	
	@GET
	@RequestMapping(Router.Listing.downloadListings)
    public void downloadListings(HttpServletRequest req,
    		HttpServletResponse resp) throws MissingArgumentException, PromoException, JsonProcessingException, IOException {
		String promoId = req.getParameter("promoId");
		UserData userData = loginService.getUserDataFromCookie(req);
		XSSFWorkbook workBook = null;
        try {
        	Promotion promo = promotionService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
        	workBook = excelService.getDownLoadListingWorkbook(promo,userData.getUserId(), LocaleUtil.getCurrentLocale(), userData.getAdmin());
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH");
        	String attachmentName = URLEncoder.encode(promo.getName(),"utf-8");
        	resp.setHeader("Content-disposition", "attachment; filename=" + attachmentName + "_" +sdf.format(new Date(System.currentTimeMillis()+15*60*60*1000))+".xlsx");
	        resp.setContentType("application/x-msdownload;");
		workBook.write(resp.getOutputStream());
        } catch (IOException | PromoException e) {
        	logger.log(LogLevel.ERROR, "Failed to generate listings excel.", e);
        } finally {
        	if (workBook != null) {
    			try {
    				workBook.close();
				} catch (IOException e) {
					// ignore
				}
    		}
        }
    }
	
	/**
	 * Read user uploaded template file.
	 * Use this method, you need to pass in promotion id and user oracle id.
	 * Response is a HTML page, but its body is a JSON string.
	 * @param req
	 * @param resp
	 * @param uploadFile
	 * @param promoId
	 */
	@POST
	@RequestMapping(Router.Listing.uploadListings)
	public ModelAndView uploadListings(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile uploadFile, @RequestParam String promoId) {
		ModelAndView mav = new ModelAndView(ViewResource.UPLOAD_RESPONSE.getPath());
		ListingsUploadWebResponse responseData = new ListingsUploadWebResponse();
		Set<ConstraintViolation<Object>> violations = null;
		UserData userData = null;
		
		try {
			userData = loginService.getUserDataFromCookie(req);
			
			// get listing fields definitions
			Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			String fieldsDefinitions = promo.getListingFields();

			Locale locale = LocaleUtil.getLocale(promo.getRegion());
			
			// read uploaded excel file.
			XSSFWorkbook workbook = new XSSFWorkbook(uploadFile.getInputStream());			
			Sheet sheet = workbook.getSheetAt(0);
			UploadedListingFileHandler handler = new UploadedListingFileHandler(listingService, promoId, userData.getUserId());
			
			if (fieldsDefinitions != null) {
				JsonNode tree = mapper.readTree(fieldsDefinitions);
				if (tree.isArray()) {
					List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, locale);
					excelService.adjustColumnConfigurations(columnConfigs, locale, promoId);
					
					violations = handler.handleSheet(sheet, columnConfigs);
				}
			}
			
			workbook.close();
		} catch (UploadListingIsNullException e) {
			responseData.setStatus(false);
			responseData.setMessage(messageSource.getMessage("excel.validation.listing.notnull.message", null, LocaleUtil.getCurrentLocale()));
			mav.addObject("response", PojoConvertor.convertToJson(responseData));
			return mav;
		} catch (Exception e) {
			String message = String.format("Failed to upload listings for user(ID is %s)", userData != null ? userData.getUserId() : null);
			logger.log(LogLevel.ERROR, message, e);
			CalEventHelper.writeLog(CalEvent.CAL_ERROR, "ListingController", message, e, "Error");
			responseData.setStatus(false);
			if (e instanceof PromoException) {
				responseData.setStatusCode(((PromoException)e).getErrorType().getCode());
			}
			responseData.setMessage(e.getMessage());
			mav.addObject("response", PojoConvertor.convertToJson(responseData));
			return mav;
		} 
		
		// if there is listing that not comply with validation rules
		if (violations == null || violations.size() == 0) {
			responseData.setStatus(true);
		} else {
			responseData.setStatus(false);
			responseData.setErrors(violations);
		}
			
		mav.addObject("response", PojoConvertor.convertToJson(responseData));
		return mav;
	}
	
	@POST
	@RequestMapping(Router.Listing.uploadListingAttachment)
	public ModelAndView uploadListingAttachment(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile uploadFile, @RequestParam String skuId, @RequestParam String promoId, @RequestParam String key) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView(ViewResource.UPLOAD_RESPONSE.getPath());
		ResponseData <String> responseData = new ResponseData <String>();
		UserData userData = loginService.getUserDataFromCookie(req);
		AttachmentFileValidator attachmentFileValidator = AttachmentFileValidator.getInstance();
		attachmentFileValidator.setLocale(LocaleUtil.getCurrentLocale());
		try {
			if(attachmentFileValidator.isValidate(uploadFile)) {
				try {
					String fileType = attachmentFileValidator.getType(uploadFile).toString();
					String downloadUrl = listingService.uploadListingAttachment(skuId, promoId, userData.getUserId(), key, uploadFile, fileType);
					responseData.setStatus(true);
					responseData.setMessage(downloadUrl);
				} catch (Exception e) {
					responseData.setStatus(false);
					responseData.setMessage(e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (AttachmentUploadException e) {
			responseData.setStatus(false);
			responseData.setMessage(e.getMessage());
		}
		mav.addObject("response", PojoConvertor.convertToJson(responseData));
		return mav;
	}
	
	@GET
	@RequestMapping(Router.Listing.listingAttachment)
	public void downloadListingAttachment(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("promoId") String promoId, @PathVariable("userId") Long userId, 
			@PathVariable("skuId") String skuId, @PathVariable("key") String key) throws MissingArgumentException, IOException, PromoException {
		resp.setContentType("application/x-msdownload;");
		InputStream inputStream = null;
		String attachmentName = "";
		String attachmentType = "";
		
		ListingAttachment attachment = listingService.downloadListingAttachment(promoId, userId, skuId, key);
		if(attachment!=null) {
			inputStream = new  ByteArrayInputStream(attachment.getContent());
			attachmentName = attachment.getAttachmentName();
			attachmentType = attachment.getAttachmentType();
		}
		resp.setHeader("Content-disposition", "attachment; filename=\""+attachmentName+"."+attachmentType+"\"");
		
		OutputStream outStream = resp.getOutputStream();
		int len = 0;
		byte[] buffer = new byte[4096];
		while((len = inputStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }

		inputStream.close();
		outStream.flush();
		outStream.close();
	}
	
	@GET
	@RequestMapping(Router.Listing.reviewUploadedListings)
	public ModelAndView reviewUploadedListings(HttpServletRequest req, @RequestParam String promoId) throws MissingArgumentException, PromoException {
		ModelAndView mav = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(req);

		Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
		String fieldsDefinitions = promo.getListingFields();
						
		promoViewService.handleListingFields(fieldsDefinitions, mav, promo.getRegion());
		
		mav.addObject(ViewContext.Promotion.getAttr(), promo);
		mav.setViewName(ViewResource.LISTING_PREVIEW.getPath());
		return mav;
	}
	
}