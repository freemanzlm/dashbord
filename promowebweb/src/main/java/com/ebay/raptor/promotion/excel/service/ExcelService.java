package com.ebay.raptor.promotion.excel.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.SheetWriter;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.pojo.business.Promotion;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

/**
 * 
 * @author lyan2
 */
@Component
public class ExcelService {
	public final static String LISTING_FILENAME_PREFIX = "Listing_Template";
	
	private static final SecureRandom random = new SecureRandom(); // not really random when cross multi-instance.
	private final ObjectMapper mapper = new ObjectMapper();
	private final String password = "111111";
	
	public ExcelService() {
	}

	@Autowired
	ListingService listingService;
	
	@Autowired
	PromotionService promoService;
	
	/**
	 * Get the excel file which contains all listing of a seller in a promotion.
	 * @param promoId
	 * @param uid
	 * @param locale
	 * @return
	 * @throws PromoException
	 * @throws MissingArgumentException
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public XSSFWorkbook getListingWorkbook(String promoId, Long uid, Locale locale)
			throws PromoException, MissingArgumentException, JsonProcessingException, IOException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		String sheetName = ResourceProvider.ListingRes.skuListFileName;
		
		List<Map<String, Object>> skuListings = listingService.getSkuListingsByPromotionId(promoId, uid);
		
		SheetWriter writer = new SheetWriter();		
		Sheet sheet = workBook.createSheet(sheetName);
		
		Promotion promo = promoService.getPromotionById(promoId, uid, false);
		String fieldsDefinitions = promo.getListingFields();
		// TODO remove this test string
		fieldsDefinitions =	"[{\"sample\":null,\"required\":false,\"labelName\":\"SKU_deal\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"TEXTAREA\",\"picklistEntry\":\"\",\"percision\":0,\"length\":32768,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"产品描述\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"產品描述\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"產品描述\",\"isDefault\":true}],\"display\":false,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"SKU_deal__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Stock ready Time\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DATETIME\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"库存到货时间\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"庫存到貨時間\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"庫存到貨時間\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Stock_ready_Time_deal__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Item ID\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":200,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"刊登编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Item_ID_base__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Category\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":200,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"品类\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"品類\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"品類\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Category_base__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Qty Available\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"INTEGER\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"总量\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"總量\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"總量\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Qty_Available_deal__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Site\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"活动站点\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活動網站\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活動網站\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Site_base__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Listing Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":10,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"货币单位\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Listing_Local_Currency_base__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Original Price_Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"原价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"原價\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"原價\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"Local Currency\",\"attachmentType\":null,\"api_Name\":\"Original_Price_Local_Currency_base__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Propose Price_Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"现价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"Local Currency\",\"attachmentType\":null,\"api_Name\":\"Propose_Price_Local_Currency_base__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"FVF take rate\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"PERCENT\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"成交费\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"成交費\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"成交費\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"FVF_take_rate_deal__c\"},{\"sample\":null,\"required\":false,\"labelName\":\"Final Subsidy %\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"PERCENT\",\"picklistEntry\":\"\",\"percision\":17,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"最终补贴金额\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"Final_Subsidy_deal__c\"},{\"api_Name\":\"Seller_Standard_base__c\",\"isUnique\":false,\"fieldtype\":{\"typeName\":\"PICKLIST\",\"picklistEntry\":\"eTRS;ASTD;BSTD;\",\"percision\":0,\"length\":255,\"digits\":0},\"currencyType\":\"\",\"display\":false,\"displayLabel\":[{\"isDefault\":true,\"labelName\":\"中国大陆\",\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\"},{\"isDefault\":false,\"labelName\":\"123\",\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\"},{\"isDefault\":false,\"labelName\":\"123\",\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\"}],\"required\":false,\"labelName\":\"Seller Standard\",\"attachmentType\":null,\"input\":true},{\"api_Name\":\"Seller_Standard_base__c\",\"isUnique\":false,\"fieldtype\":{\"typeName\":\"COMBOBOX\",\"picklistEntry\":\"eTRS;ASTD;BSTD;\",\"percision\":0,\"length\":255,\"digits\":0},\"currencyType\":\"\",\"display\":false,\"displayLabel\":[{\"isDefault\":true,\"labelName\":\"中国大陆\",\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\"},{\"isDefault\":false,\"labelName\":\"123\",\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\"},{\"isDefault\":false,\"labelName\":\"123\",\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\"}],\"required\":false,\"labelName\":\"Seller Standard\",\"attachmentType\":null,\"input\":true}]";
		
		if (fieldsDefinitions != null) {
			JsonNode tree = mapper.readTree(fieldsDefinitions);
			if (tree.isArray()) {
				List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, locale);
				writer.writeSheet(workBook, sheet, columnConfigs, skuListings, true);
			}
		}
		
		writer.freeze(sheet, 0, writer.getFirstDataRowNum());
		// lock not writable cells
		writer.setProtectionPassword(sheet, password);

		return workBook;
	}
	
	/**
	 * Get a random excel name for downloading.
	 * @param promoSubType
	 * @return
	 */
	public String getSKUListingTemplateFileName() {
		return ExcelService.LISTING_FILENAME_PREFIX + "_" + random.nextInt() + ".xlsx";
	}
}
