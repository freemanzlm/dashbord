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
	 * Generate the excel file which contains all listing of a seller in a promotion.
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
	public XSSFWorkbook getListingWorkbook(String promoId, Long uid, Locale locale, boolean isAdmin)
			throws PromoException, MissingArgumentException, JsonProcessingException, IOException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		
		List<Listing> listings = listingService.getSkuListingsByPromotionId(promoId, uid, false);
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
		Sheet sheet = workBook.createSheet(messageSource.getMessage("listing.template", null, LocaleUtil.getCurrentLocale()));
		
		Promotion promo = promoService.getPromotionById(promoId, uid, isAdmin);
		String fieldsDefinitions = null;
		if(promo != null) {
			fieldsDefinitions = promo.getListingFields();
		}
		
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
	 * Listing fields of promotion doesn't contain nomination id. But we need nomination id to differentiate listings. So we have to 
	 * prepend nomination id as the first column configuration.
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
