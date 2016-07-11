package com.ebay.raptor.promotion.list.controller;

import java.io.IOException;
import java.util.List;
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
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.req.ListingWebParam;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.locale.LocaleUtil;
import com.ebay.raptor.promotion.pojo.RequestParameter;
import com.ebay.raptor.promotion.pojo.ResponseData;
import com.ebay.raptor.promotion.pojo.UserData;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.pojo.business.Sku;
import com.ebay.raptor.promotion.pojo.web.resp.ListDataWebResponse;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.promo.service.ViewResource;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.CookieUtil;
import com.ebay.raptor.promotion.util.PojoConvertor;
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
	ListingService service;
	
	@Autowired
	PromotionService promoService;
	
	@Autowired ExcelService excelService;

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
	
	@GET
	@RequestMapping(ResourceProvider.ListingRes.downloadSkuList)
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
        	logger.error("Unable to download sku listings.", e);
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
			UploadedListingFileHandler handler = new UploadedListingFileHandler(promoId, userData.getUserId());
			
			if (fieldsDefinitions != null) {
				JsonNode tree = mapper.readTree(fieldsDefinitions);
				if (tree.isArray()) {
					List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, LocaleUtil.getCurrentLocale());
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
					errorMessage.append((first ? "" : "</br>") + violation.getMessage());
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
}
