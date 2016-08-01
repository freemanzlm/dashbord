package com.ebay.raptor.promotion.list.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.UploadedListingFileHandler;
import com.ebay.raptor.promotion.excel.service.ExcelService;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excep.AttachmentUploadException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.req.SelectableListing;
import com.ebay.raptor.promotion.list.req.UploadListingForm;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.locale.LocaleUtil;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Listing;
import com.ebay.raptor.promotion.pojo.business.ListingAttachment;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.PromotionViewService;
import com.ebay.raptor.promotion.promo.service.ViewContext;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;
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
@RequestMapping(ResourceProvider.ListingRes.base)
public class ListingController extends AbstractListingController {
	private static CommonLogger logger = CommonLogger.getInstance(ListingController.class);
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	@Autowired
	ListingService listingService;
	
	@Autowired
	PromotionService promoService;

	@Autowired
	PromotionViewService promoViewService;
	
	@Autowired ExcelService excelService;

	@Deprecated
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getSKUsByPromotionId)
	@ResponseBody
	public ListDataWebResponse<Sku> getSKUsByPromotionId(HttpServletRequest req, @ModelAttribute ListingWebParam param) {
		ListDataWebResponse<Sku> resp = new ListDataWebResponse<Sku>();
		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			resp.setData(listingService.getSkusByPromotionId(param.getPromoId(), userData.getUserId()));
		} catch (PromoException | MissingArgumentException e) {
			resp.setStatus(Boolean.FALSE);
		}
		return resp;
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.downloadTempldate)
    public void createListingUploadTemplet(HttpServletRequest req,
    		HttpServletResponse resp, @ModelAttribute RequestParameter param)
    				throws MissingArgumentException {
		
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		XSSFWorkbook workBook = null;

        try {
        	workBook = excelService.getListingWorkbook(param.getPromoId(),
        			userData.getUserId(), LocaleUtil.getCurrentLocale());
        	
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
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadListings)
	public ModelAndView uploadDealsListings(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile uploadFile, @RequestParam String promoId) throws MissingArgumentException{
		ModelAndView mav = new ModelAndView(ViewResource.DU_UPLOAD_RESPONSE.getPath());
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		ResponseData <String> responseData = new ResponseData <String>();
		
		XSSFWorkbook workbook = null;
		try {
			// get listing fields definitions
			Promotion promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			String fieldsDefinitions = promo.getListingFields();
			// TODO remove this test string
			fieldsDefinitions =	"[{\"sample\":null,\"required\":false,\"labelName\":\"SKU_deal\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":32768,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"產SKU编号\",\"isDefault\":true}],\"display\":false,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuId\"},{\"sample\":\"2015-12-24 24:38:56\",\"required\":false,\"labelName\":\"Stock ready Time\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuName\"},{\"sample\":null,\"required\":false,\"labelName\":\"Item ID\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":200,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"刊登编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"itemId\"},{\"sample\":null,\"required\":false,\"labelName\":\"Qty Available\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"總量\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currPrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Site\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活動網站\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Listing Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":10,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"货币单位\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currency\"},{\"sample\":null,\"required\":false,\"labelName\":\"Propose Price_Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"现价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"Local Currency\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"FVF take rate\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"INTEGER\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"成交費\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockNum\"},{\"sample\":null,\"required\":false,\"labelName\":\"Final Subsidy %\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DATE\",\"picklistEntry\":\"\",\"percision\":17,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"备货日期\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockReadyDate\"}]";

			Set<ConstraintViolation<Object>> violations = null;
			
			// read uploaded excel file.
			workbook = new XSSFWorkbook(uploadFile.getInputStream());			
			Sheet sheet = workbook.getSheetAt(0);
			UploadedListingFileHandler handler = new UploadedListingFileHandler(listingService, promoId, userData.getUserId());
			
			if (fieldsDefinitions != null) {
				JsonNode tree = mapper.readTree(fieldsDefinitions);
				if (tree.isArray()) {
					List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, LocaleUtil.getCurrentLocale());
					excelService.adjustColumnConfigurations(columnConfigs);
					violations = handler.handleSheet(sheet, columnConfigs);
				}
			}
			
			if (violations == null || violations.size() == 0) {
				responseData.setStatus(true);
				this.acceptAgreement(promoId, userData.getUserId());
			} else {
				responseData.setStatus(false);
				StringBuffer errorMessage = new StringBuffer();
				
				boolean first = true;
				for (ConstraintViolation<Object> violation : violations) {
					errorMessage.append((first ? "" : "<br/>") + violation.getMessage());
					first = false;
				}
				
				responseData.setMessage(errorMessage.toString());
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
	@RequestMapping(ResourceProvider.ListingRes.confirmListings)
	@ResponseBody
	public ResponseData <String> confirmListings(HttpServletRequest req, @ModelAttribute("listings") UploadListingForm listings) {
		ResponseData <String> responseData = new ResponseData <String>();

		if(null != listings){
			SelectableListing[] listingAry = PojoConvertor.convertToObject(listings.getListings(), SelectableListing[].class);
			try {
				UserData userData = CookieUtil.getUserDataFromCookie(req);
				boolean result = listingService.confirmListings(listingAry, listings.getPromoId(), userData.getUserId());
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
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.uploadListingAttachment)
	public ModelAndView uploadListingAttachment(HttpServletRequest req, HttpServletResponse resp, 
			@RequestPart MultipartFile uploadFile, @RequestParam String skuId, @RequestParam String promoId) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView(ViewResource.DU_UPLOAD_RESPONSE.getPath());
		ResponseData <String> responseData = new ResponseData <String>();
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		AttachmentFileValidator attachmentFileValidator = AttachmentFileValidator.getInstance();
		try {
			if(attachmentFileValidator.isValidate(uploadFile)) {
				try {
					String fileType = attachmentFileValidator.getType(uploadFile).toString();
					String downloadUrl = listingService.uploadListingAttachment(skuId, promoId, userData.getUserId(), uploadFile, fileType);
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
	@RequestMapping(ResourceProvider.ListingRes.downloadListingAttachment)
	public void downloadListingAttachment(HttpServletRequest req, HttpServletResponse resp,
			@PathVariable("promoId") String promoId, @PathVariable("userId") Long userId, 
			@PathVariable("skuId") String skuId) throws MissingArgumentException, IOException {
		resp.setContentType("application/x-msdownload;");
		UserData userData = CookieUtil.getUserDataFromCookie(req);
		if(userData.getUserId()!=userId) {
			//TODO
		}
		InputStream inputStream = null;
		OutputStream outStream = null;
		ListingAttachment attachment = null;
		String attachmentName = "";
		String attachmentType = "";
		try {
			attachment = listingService.downloadListingAttachment(promoId, userId, skuId);
			if(attachment!=null) {
				inputStream = attachment.getContent();
				attachmentName = attachment.getAttachmentName();
				attachmentType = attachment.getAttachmentType();
			}
			resp.setHeader("Content-disposition", "attachment; filename="+attachmentName+"."+attachmentType);
			outStream = resp.getOutputStream();
			int len = 0;
			byte[] buffer = new byte[4096];
			while((len = inputStream.read(buffer)) != -1) {
	            outStream.write(buffer, 0, len);
	        }
		} catch (PromoException e) {
			e.printStackTrace();
		} finally {
			inputStream.close();
			outStream.flush();
			outStream.close();
		}
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes._getPromotionListings)
	@ResponseBody
	public ListDataWebResponse<?> getPromotionListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		
		return getListings(req, param);
	}
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.reviewUploadedListings)
	public ModelAndView reviewUploadedListings(HttpServletRequest req, @RequestParam String promoId) throws MissingArgumentException {
		ModelAndView mav = new ModelAndView();
		Map<String, Object> context = new HashMap<String, Object>();
		
		UserData userData = CookieUtil.getUserDataFromCookie(req);

		Promotion promo;
		try {
			promo = promoService.getPromotionById(promoId, userData.getUserId(), userData.getAdmin());
			promoViewService.handleListingFields(promo, context);
		} catch (PromoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		mav.addAllObjects(context);
		mav.addObject("formUrl", "/promotion/listings/submitDealsListings");
		mav.addObject(ViewContext.PromotionId.getAttr(), promoId);
		mav.setViewName(ViewResource.LISTING_PREVIEW.getPath());
		return mav;
	}
	
	@POST
	@RequestMapping(ResourceProvider.ListingRes.submitDealsListings)
	public @ResponseBody ResponseData <String> submitDealsListings(HttpServletRequest req, HttpServletResponse resp) throws MissingArgumentException{
		ResponseData <String> responseData = new ResponseData <String>();
		String promoId = req.getParameter("promoId");

		try {
			UserData userData = CookieUtil.getUserDataFromCookie(req);
			boolean result = listingService.submitListings(promoId, userData.getUserId());
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
	@RequestMapping(ResourceProvider.ListingRes._getUploadedListings)
	@ResponseBody
	public ListDataWebResponse<?> getUploadedListings(HttpServletRequest req,
			@ModelAttribute ListingWebParam param)  {
		return getListings(req, param);
	}
	
	protected ListDataWebResponse<?> getListings (HttpServletRequest req,
			ListingWebParam param) {
		UserData userData = null;

		try {
			userData = CookieUtil.getUserDataFromCookie(req);
		} catch (MissingArgumentException e) {
			logger.error("Missing required argument.", e);
			ListDataWebResponse<Void> resp = new ListDataWebResponse<Void>();
			resp.setStatus(Boolean.FALSE);
			return resp;
		}

		ListDataWebResponse<Listing> resp = getListings(param.getPromoId(), userData.getUserId());
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		ListDataWebResponse<Map<String, Object>> mergedResp = new ListDataWebResponse<Map<String, Object>>();
		
		if (resp != null && resp.getData() != null) {
			for (Listing listing : resp.getData()) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("skuId", listing.getSkuId());
				map.put("state", listing.getState());
				map.put("currency", listing.getCurrency());
				
				if (listing.getNominationValues() != null) {
					try {
						@SuppressWarnings("unchecked")
						Map<String, Object> fields = mapper.readValue(listing.getNominationValues(), HashMap.class);
						map.putAll(fields);
					} catch (IOException e) {
						logger.error("Can't read listing normination values for listing with sku ID: " + listing.getSkuId());
					}
				}
				
				data.add(map);
			}
			
			mergedResp.setStatus(resp.isStatus());
			mergedResp.setData(data);
		} else {
			mergedResp.setStatus(resp.isStatus());
		}
		
		
		return mergedResp;
	}
	
	// TODO - If there is no particular business logic on the meta data,
	// it's no need to convert to an object, and add the json string in response.
	protected <T> ListDataWebResponse<T> getListings (String promoId, Long userId) {
		ListDataWebResponse<T> resp = new ListDataWebResponse<T>();
		try {
			List<T> listings = listingService.getSkuListingsByPromotionId(promoId, userId);
			
			if (listings != null && listings.size() > 0) {
				resp.setData(listings);
			} else {
				logger.error("No listings found for promotion: " + promoId + ", user:" + userId);
			}
		} catch (PromoException e) {
			logger.error("No listings found for promotion: " + promoId + ", user:" + userId + ", with error", e);
			resp.setStatus(Boolean.FALSE);
		}

		return resp;
	} 

}
