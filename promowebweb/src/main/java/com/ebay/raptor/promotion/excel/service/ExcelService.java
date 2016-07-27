package com.ebay.raptor.promotion.excel.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.SheetWriter;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excel.validation.ColumnConstraint;
import com.ebay.raptor.promotion.excel.validation.NotNullColumnConstraint;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.locale.LocaleUtil;
import com.ebay.raptor.promotion.pojo.business.Listing;
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
	
	@Autowired ResourceBundleMessageSource messageSource;
	
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
	@SuppressWarnings("unchecked")
	public XSSFWorkbook getListingWorkbook(String promoId, Long uid, Locale locale)
			throws PromoException, MissingArgumentException, JsonProcessingException, IOException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		String sheetName = ResourceProvider.ListingRes.skuListFileName;
		
		List<Listing> listings = listingService.getSkuListingsByPromotionId(promoId, uid);
		List<Map<String, Object>> skuListings = new ArrayList<Map<String, Object>>();
		
		if (listings != null) {
			for (Listing listing : listings) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("skuId", listing.getSkuId());
				map.put("state", listing.getState());
				map.put("currency", listing.getCurrency());
				
				String nominationValues = listing.getNominationValues();
				if (nominationValues != null) {
					map.putAll(mapper.readValue(nominationValues, Map.class));
				}
				
				skuListings.add(map);
			}
		}
		
		SheetWriter writer = new SheetWriter();
		Sheet sheet = workBook.createSheet(sheetName);
		
		Promotion promo = promoService.getPromotionById(promoId, uid, false);
		String fieldsDefinitions = promo.getListingFields();
		// TODO remove this test string
		fieldsDefinitions =	"[{\"sample\":null,\"required\":true,\"labelName\":\"SKU_deal\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":32768,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU编号\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"產SKU编号\",\"isDefault\":true}],\"display\":false,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuId\"},{\"sample\":\"2015-12-24 24:38:56\",\"required\":true,\"labelName\":\"Stock ready Time\",\"isUnique\":true,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"SKU名称\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"skuName\"},{\"sample\":null,\"required\":true,\"labelName\":\"Item ID\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":200,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"刊登编号\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"刊登編號\",\"isDefault\":true}],\"display\":false,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"itemId\"},{\"sample\":null,\"required\":false,\"labelName\":\"Qty Available\",\"isUnique\":false,\"input\":true,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"当前价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"總量\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currPrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Site\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":0,\"length\":100,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活动价\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"活動網站\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"Listing Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"STRING\",\"picklistEntry\":\"\",\"percision\":0,\"length\":10,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"货币单位\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"貨幣單位\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"currency\"},{\"sample\":null,\"required\":false,\"labelName\":\"Propose Price_Local Currency\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DOUBLE\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"现价\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"現價\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"Local Currency\",\"attachmentType\":null,\"api_Name\":\"proposePrice\"},{\"sample\":null,\"required\":false,\"labelName\":\"FVF take rate\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"INTEGER\",\"picklistEntry\":\"\",\"percision\":10,\"length\":0,\"digits\":0},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"库存量\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"成交費\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockNum\"},{\"sample\":null,\"required\":false,\"labelName\":\"Final Subsidy %\",\"isUnique\":false,\"input\":false,\"fieldtype\":{\"typeName\":\"DATE\",\"picklistEntry\":\"\",\"percision\":17,\"length\":0,\"digits\":2},\"displayLabel\":[{\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\",\"labelName\":\"备货日期\",\"isDefault\":true},{\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true},{\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\",\"labelName\":\"最終補貼金額\",\"isDefault\":true}],\"display\":true,\"currencyType\":\"\",\"attachmentType\":null,\"api_Name\":\"stockReadyDate\"},{\"api_Name\":\"account_item_attachment_1__c\",\"fieldtype\":null,\"isUnique\":false,\"currencyType\":null,\"display\":false,\"displayLabel\":[{\"isDefault\":true,\"labelName\":\"123\",\"locale\":\"CN\",\"localDisplayName\":\"中国大陆\"},{\"isDefault\":false,\"labelName\":\"123\",\"locale\":\"HK\",\"localDisplayName\":\"香港/台湾\"},{\"isDefault\":false,\"labelName\":\"123\",\"locale\":\"TW\",\"localDisplayName\":\"香港/台湾\"}],\"required\":false,\"labelName\":\"Account Item Attachment 1\",\"attachmentType\":\"account_item\",\"input\":true}]";
		
		if (fieldsDefinitions != null) {
			JsonNode tree = mapper.readTree(fieldsDefinitions);
			if (tree.isArray()) {
				List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, locale);
				adjustColumnConfigurations(columnConfigs);
				preHandleData(columnConfigs, skuListings);
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
	
	private void preHandleData(List<ColumnConfiguration> columnConfigs, List<Map<String, Object>> listings) {
		if (listings != null && columnConfigs != null) {
			for (ColumnConfiguration config : columnConfigs) {
				if ("attachment".equalsIgnoreCase(config.getRawType())) {
					for(Map<String, Object> map : listings) {
						Object value = map.get(config.getKey());
						if (value == null) {
							map.put(config.getKey(), messageSource.getMessage("listing.attachment.comment", null, LocaleUtil.getCurrentLocale()));
						}
					}
				}
			}
		}
	}
	
	/**
	 * Nomination id is used to differentiate listings.
	 * @param columnConfigs
	 */
	public List<ColumnConfiguration> adjustColumnConfigurations(List<ColumnConfiguration> columnConfigs) {
		// it's used to configure a hidden column, it will store nomination id.
		ColumnConfiguration nominationConfig = new ColumnConfiguration();
		nominationConfig.setKey("skuId");
		nominationConfig.setReadOrder(0);
		nominationConfig.setWriteOrder(0);
		nominationConfig.setWritable(false);
		nominationConfig.setDisplay(false);
		nominationConfig.setRawType("string");
		
		ColumnConstraint constraint = new NotNullColumnConstraint();
		constraint.setMessage("excel.validation.template.message");
		
		if (columnConfigs != null) {
			// move colmuns to right by one column
			for (ColumnConfiguration config : columnConfigs) {
				config.setReadOrder(config.getReadOrder() + 1);
				config.setWriteOrder(config.getWriteOrder() + 1);
			}
			
			columnConfigs.add(nominationConfig);
		}
		
		return columnConfigs;
	}
}
