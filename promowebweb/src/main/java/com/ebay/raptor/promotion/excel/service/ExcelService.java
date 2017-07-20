package com.ebay.raptor.promotion.excel.service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.cbt.raptor.promotion.po.Listing;
import com.ebay.cbt.raptor.promotion.po.Promotion;
import com.ebay.raptor.promotion.excel.ColumnConfiguration;
import com.ebay.raptor.promotion.excel.SheetWriter;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;
import com.ebay.raptor.promotion.excel.validation.ColumnConstraint;
import com.ebay.raptor.promotion.excel.validation.NotNullColumnConstraint;
import com.ebay.raptor.promotion.excel.validation.RangeColumnConstraint;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.promo.service.PromotionService;
import com.ebay.raptor.promotion.util.LocaleUtil;
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
		
		List<Listing> listings = listingService.getListingsByPromotionId(promoId, uid, false);
		for(Listing l:listings){
			System.out.print("stickflag"+l.getStickFlag());
			System.out.print("state"+l.getState());
			System.out.print("lock"+l.getLocked());
			System.out.println("");
		}
		Collections.sort(listings,new Comparator<Listing>(){
			public int compare(Listing list1, Listing list2) {  
                if(list1.getLocked()){  
                    return 0;  
                }  
                if(list2.getLocked()){  
                    return 1;  
                }  
                return -1;  
            }  
		});
		for(Listing l:listings){
			System.out.print("stickflag"+l.getStickFlag());
			System.out.print("state"+l.getState());
			System.out.print("lock"+l.getLocked());
			System.out.println("");
		}
		List<Map<String, Object>> skuListings = new ArrayList<Map<String, Object>>();
		
		if (listings != null) {
			for (Listing listing : listings) {
				if(!listing.getLocked()){
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("skuId", listing.getSkuId());
					map.put("state", listing.getState());
					map.put("currency", listing.getCurrency());
					if(listing.getState().equalsIgnoreCase("CanEnroll") || listing.getState().equalsIgnoreCase("NotEnrolled")
							|| listing.getState().equalsIgnoreCase("UploadEnroll") || listing.getState().equalsIgnoreCase("ReEnroll")) {
						map.put("toUpload", "N");
					} else {
						map.put("toUpload", "Y");
					}
					
					String nominationValues = listing.getNominationValues();
					if (nominationValues != null) {
						map.putAll(mapper.readValue(nominationValues, Map.class));
					}
					map.put("lock", listing.getLocked());
					skuListings.add(map);
				}
			}
		}
		
		SheetWriter writer = new SheetWriter();
		writer.setMessageSource(messageSource);
		
		Font titleFont = workBook.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 9);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		writer.setTitleFont(titleFont);
		
		Font ft = workBook.createFont();
		ft.setFontName("Arial");
		ft.setFontHeightInPoints((short) 9);
		writer.setDefaultFont(ft);
		
		Sheet sheet = workBook.createSheet(messageSource.getMessage("listing.template", null, LocaleUtil.getCurrentLocale()));
		
		Promotion promo = promoService.getPromotionById(promoId, uid, isAdmin);
		String fieldsDefinitions = null;
		if(promo != null) {
			fieldsDefinitions = promo.getListingFields();
		}
		
		locale = LocaleUtil.getLocale(promo.getRegion());
		writer.setLocale(locale);
		if (fieldsDefinitions != null) {
			JsonNode tree = mapper.readTree(fieldsDefinitions);
			if (tree.isArray()) {
				List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, locale);
				adjustColumnConfigurations(columnConfigs, locale, promoId);
				preHandleData(columnConfigs, skuListings, locale);
				writer.writeSheet(workBook, sheet, columnConfigs, skuListings, true);
			}
		}
		
		writer.freeze(sheet, 0, writer.getFirstDataRowNum());
		// lock not writable cells
		writer.setProtectionPassword(sheet, password);

		return workBook;
	}
	
	
	@SuppressWarnings("unchecked")
	public XSSFWorkbook getDownLoadListingWorkbook(Promotion promo, Long uid, Locale locale, boolean isAdmin)
			throws PromoException, MissingArgumentException, JsonProcessingException, IOException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		List<Listing> listings = listingService.getListingsByPromotionId(promo.getPromoId(), uid, false);
		
		List<Map<String, Object>> ListingData = new ArrayList<Map<String, Object>>();
		if (!CollectionUtils.isEmpty(listings)) {
			for (Listing listing : listings) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("lockFlag", listing.getLocked()==true?"Y":"N");
					String nominationValues = listing.getNominationValues();
					if (nominationValues != null) {
						map.putAll(mapper.readValue(nominationValues, Map.class));
					}
					ListingData.add(map);
			}
		}
		
		SheetWriter writer = new SheetWriter();
		writer.setMessageSource(messageSource);
		
		Font titleFont = workBook.createFont();
		titleFont.setFontName("Arial");
		titleFont.setFontHeightInPoints((short) 9);
		titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
		writer.setTitleFont(titleFont);
		
		Font ft = workBook.createFont();
		ft.setFontName("Arial");
		ft.setFontHeightInPoints((short) 9);
		writer.setDefaultFont(ft);
		
		Sheet sheet = workBook.createSheet(messageSource.getMessage("listing.template", null, LocaleUtil.getCurrentLocale()));
		
		String fieldsDefinitions = null;
		if(promo != null) {
			fieldsDefinitions = promo.getListingFields();
		}
		
		locale = LocaleUtil.getLocale(promo.getRegion());
		writer.setLocale(locale);
		if (fieldsDefinitions != null) {
			JsonNode tree = mapper.readTree(fieldsDefinitions);
			if (tree.isArray()) {
				List<ColumnConfiguration> columnConfigs = ExcelUtil.getColumnConfigurations((ArrayNode)tree, locale);
				adjustColumnConfigurations2(columnConfigs, locale, promo.getPromoId());
				preHandleData(columnConfigs, ListingData, locale);
				writer.writeSheet(workBook, sheet, columnConfigs, ListingData, true);
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
	
	private void preHandleData(List<ColumnConfiguration> columnConfigs, List<Map<String, Object>> listings, Locale locale) {
		if (listings != null && columnConfigs != null) {
			for (ColumnConfiguration config : columnConfigs) {
				if ("attachment".equalsIgnoreCase(config.getRawType())) {
					for(Map<String, Object> map : listings) {
						Object value = map.get(config.getKey());
						if (value == null) {
							map.put(config.getKey(), messageSource.getMessage("listing.attachment.comment", null, locale));
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
	public List<ColumnConfiguration> adjustColumnConfigurations(List<ColumnConfiguration> columnConfigs, Locale locale, String promoId) {
		if (locale == null) locale = LocaleUtil.getCurrentLocale();
		
		// it's used to configure a hidden column, it will store nomination id.
		ColumnConfiguration nominationConfig = new ColumnConfiguration();
		nominationConfig.setKey("skuId");
		nominationConfig.setReadOrder(0);
		nominationConfig.setWriteOrder(0);
		nominationConfig.setWritable(false);
		nominationConfig.setDisplay(false);
		nominationConfig.setRawType("string");
		nominationConfig.setSample(promoId);
		
		ColumnConstraint constraint = new NotNullColumnConstraint();
		constraint.setMessage("excel.validation.template.message");
		nominationConfig.getConstraints().add(constraint);
		
		// upload config
		ColumnConfiguration uploadConfig = new ColumnConfiguration();
		uploadConfig.setKey("toUpload");
		uploadConfig.setTitle(messageSource.getMessage("excel.header.toUpload", null, locale));
		//uploadConfig.setLabel("Whether Upload");
		uploadConfig.setSample(messageSource.getMessage("excel.header.uploadSample", null, locale));
		uploadConfig.setReadOrder(1);
		uploadConfig.setWriteOrder(1);
		uploadConfig.setWritable(true);
		uploadConfig.setDisplay(true);
		uploadConfig.setRawType("picklist");
		
		RangeColumnConstraint rangeConstraint = new RangeColumnConstraint();
		String[] whetherToUpload = {"Y", "N"};
		rangeConstraint.setPickList(whetherToUpload);
		uploadConfig.getConstraints().add(rangeConstraint);
		
		if (columnConfigs != null) {
			
			// move colmuns to right by one column
			for (ColumnConfiguration config : columnConfigs) {
				config.setReadOrder(config.getReadOrder() + 2);
				config.setWriteOrder(config.getWriteOrder() + 2);
			}
			
			columnConfigs.add(nominationConfig);
			columnConfigs.add(uploadConfig);
		}
		
		return columnConfigs;
	}
	
	public List<ColumnConfiguration> adjustColumnConfigurations2(List<ColumnConfiguration> columnConfigs, Locale locale, String promoId) {
		if (locale == null) locale = LocaleUtil.getCurrentLocale();
		
		ColumnConfiguration lockConfig = new ColumnConfiguration();
		lockConfig.setKey("lockFlag");
		lockConfig.setTitle(messageSource.getMessage("excel.header.lockFlag", null, locale));
		lockConfig.setSample(messageSource.getMessage("excel.header.lockFlagSample", null, locale));
		lockConfig.setReadOrder(0);
		lockConfig.setWriteOrder(0);
		lockConfig.setWritable(false);
		lockConfig.setDisplay(true);
		lockConfig.setRawType("picklist");
		
		RangeColumnConstraint lockRangeConstraint = new RangeColumnConstraint();
		String[] whetherLock = {"Y", "N"};
		lockRangeConstraint.setPickList(whetherLock);
		lockConfig.getConstraints().add(lockRangeConstraint);
		
		
		if (columnConfigs != null) {
			
			// move colmuns to right by one column
			for (ColumnConfiguration config : columnConfigs) {
				config.setReadOrder(config.getReadOrder() + 1);
				config.setWriteOrder(config.getWriteOrder() + 1);
			}
			
			columnConfigs.add(lockConfig);
		}
		
		return columnConfigs;
	}
}
