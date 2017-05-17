package com.ebay.raptor.promotion.list.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.promotion.po.ListingAttachment;
import com.ebay.cbt.raptor.promotion.route.ResourceProvider;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.UploadedListingFileHandler;
import com.ebay.raptor.promotion.excel.service.ExcelService;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.excep.UploadListingIsNullException;
import com.ebay.raptor.promotion.list.req.SelectableListing;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.LoginService;
import com.ebay.raptor.promotion.util.LocaleUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;
import com.ebay.raptor.promotion.validation.AttachmentFileValidator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 
 * @author lyan2
 */
@Controller
@RequestMapping(ResourceProvider.ListingRes.listingBase)
public class ListingController extends AbstractListingController {
	private static CommonLogger logger = CommonLogger.getInstance(ListingController.class);
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired LoginService loginService;
	@Autowired ListingService listingService;
	@Autowired PromotionViewService promoViewService;
	@Autowired ExcelService excelService;

	/**
	 * Generate listing upload template.
	 * To use this method, you need to pass in promotion id and user oracle id.
	 * @param req
	 * @param resp
	 * @param param
	 * @throws MissingArgumentException
	 */
	@GET
	@RequestMapping(ResourceProvider.ListingRes.downloadTempldate)
    public void createListingUploadTemplet(HttpServletRequest req,
    		HttpServletResponse resp, @ModelAttribute RequestParameter param)
    				throws MissingArgumentException {
		
		UserData userData = loginService.getUserDataFromCookie(req);
		XSSFWorkbook workBook = null;

        try {
        	workBook = excelService.getListingWorkbook(param.getPromoId(),
        			userData.getUserId(), LocaleUtil.getCurrentLocale(), userData.getAdmin());
        	
            resp.setContentType("application/x-msdownload;");
    		resp.setHeader("Content-disposition", "attachment; filename=" + excelService.getSKUListingTemplateFileName());
    		workBook.write(resp.getOutputStream());
        } catch (IOException | PromoException e) {
        	logger.error("Failed to generate upload template.", e);
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
	 * @param req
	 * @param resp
	 * @param uploadFile
	 * @param promoId
	 * @throws MissingArgumentException
	 */
	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadListings)
	public ModelAndView uploadListings(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile uploadFile, @RequestParam String promoId) throws MissingArgumentException{
		ModelAndView mav = new ModelAndView(ViewResource.UPLOAD_RESPONSE.getPath());
		UserData userData = loginService.getUserDataFromCookie(req);
		ResponseData <String> responseData = new ResponseData <String>();
		
		XSSFWorkbook workbook = null;
		try {
			// get listing fields definitions
			Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			String fieldsDefinitions = promo.getListingFields();

			Locale locale = LocaleUtil.getLocale(promo.getRegion());
			Set<ConstraintViolation<Object>> violations = null;
			
			// read uploaded excel file.
			workbook = new XSSFWorkbook(uploadFile.getInputStream());			
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
			
			// if there is listing that not comply with validation rules
			if (violations == null || violations.size() == 0) {
				responseData.setStatus(true);
				this.acceptAgreement(promoId, userData.getUserId());
			} else {
				responseData.setStatus(false);
				StringBuffer errorMessage = new StringBuffer();
				
				boolean first = true;
				for (ConstraintViolation<Object> violation : violations) {
					errorMessage.append((first ? "" : "&lt;br/&gt;") + violation.getMessage());
					first = false;
				}
				
				responseData.setMessage(errorMessage.toString());
			}
			
		} catch (IOException e) {
			// Got IO or PromoException exception -> means app level error -> show error page.
			logger.error("Unable to read upload file", e);
			responseData.setStatus(false);
		} catch (PromoException e) {
			// Got IO or PromoException exception -> means app level error -> show error page.
			logger.error("Got error when to read uploaded listings.", e);
			responseData.setData(e.getErrorType().getCode() + "");
			responseData.setStatus(false);
		} catch(UploadListingIsNullException e) {
			responseData.setStatus(false);
			responseData.setMessage(messageSource.getMessage("excel.validation.listing.notnull.message", null, LocaleUtil.getCurrentLocale()));
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
	@RequestMapping(ResourceProvider.ListingRes.uploadListingAttachment)
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
	@RequestMapping(ResourceProvider.ListingRes.listingAttachment)
	public void downloadListingAttachment(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("promoId") String promoId, @PathVariable("userId") Long userId, 
			@PathVariable("skuId") String skuId, @PathVariable("key") String key) throws MissingArgumentException, IOException, PromoException {
		resp.setContentType("application/x-msdownload;");
		UserData userData = loginService.getUserDataFromCookie(req);
		if(userData.getUserId()!=userId) {
			//throw new PromoException();
		}
		InputStream inputStream = null;
		OutputStream outStream = null;
		ListingAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";
		try {
			attachment = listingService.downloadListingAttachment(promoId, userId, skuId, key);
			if(attachment!=null) {
				inputStream = new  ByteArrayInputStream(attachment.getContent());
				attachmentName = attachment.getAttachmentName();
				attachmentType = attachment.getAttachmentType();
			}
			resp.setHeader("Content-disposition", "attachment; filename=\""+attachmentName+"."+attachmentType+"\"");
			System.out.println(resp.getHeaders("Content-disposition"));
			outStream = resp.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[4096];
			while((len = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, len);
	        }
		} catch (PromoException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				inputStream.close();
				outStream.flush();
				outStream.close();
			}
		}
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.reviewUploadedListings)
	public ModelAndView reviewUploadedListings(HttpServletRequest req, @RequestParam String promoId) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView();
		UserData userData = loginService.getUserDataFromCookie(req);

		Promotion promo;
		try {
			promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			String fieldsDefinitions = promo.getListingFields();
						
			promoViewService.handleListingFields(fieldsDefinitions, mav, promo.getRegion());
		} catch (PromoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//mav.addObject("formUrl", "/promotion/listings/submitDealsListings");
		mav.addObject(ViewContext.PromotionId.getAttr(), promoId);
		mav.setViewName(ViewResource.LISTING_PREVIEW.getPath());
		return mav;
	}
	
}