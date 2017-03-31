package com.ebay.raptor.promotion.excel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;

import com.ebay.cbt.raptor.po.Listing;
import com.ebay.raptor.promotion.enums.ListingState;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.excep.UploadListingIsNullException;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Read listings from excel and upload them to server.
 * @author lyan2
 */
public class UploadedListingFileHandler {
	private final ObjectMapper mapper = new ObjectMapper();
	private Logger logger = Logger.getLogger(UploadedListingFileHandler.class);
	private String bundleBaseName = "ExcelValidationMessages";
	private ResourceBundle bundle;
	
	public UploadedListingFileHandler(ListingService listingService,
			String promoId, Long userId) {
		this.listingService = listingService;
		this.promoId = promoId;
		this.userId = userId;
		
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
	}
	
	public Set<ConstraintViolation<Object>> handleSheet(Sheet sheet, List<ColumnConfiguration> configs) throws PromoException, UploadListingIsNullException {
		ISheetReader reader = new SheetReader();
		Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();
		List<Map<String, Object>> list = reader.readSheet(sheet, configs, 2, violations);
		List<Listing> listings = new ArrayList<Listing>();
		
		Map<String, Object> sample = reader.readRow(configs, sheet.getRow(1));
		if (sample != null) {
			if (sample.get("skuId") == null) {
				violations.add(new InvalidCellValueError(1, 1, null, getBundle().getString("excel.validation.template.wrong.message")));
			} else if (!this.promoId.equalsIgnoreCase(sample.get("skuId").toString())) {
				violations.add(new InvalidCellValueError(1, 1, null, getBundle().getString("excel.validation.template.wrong.message")));
			}
		}
		
		if (violations != null && violations.size() > 0) return violations;
		
		if (list != null && list.size()!=0) {
			for (Map<String, Object> row : list) {
				Listing listing = new Listing();
				if(row.get("skuId")!=null) {
					listing.setSkuId(row.remove("skuId").toString());
				}
				if(row.get("currency")!=null) {
					listing.setCurrency(row.remove("currency").toString());
				}
				listing.setState(ListingState.Uploaded.getName());
				try {
					listing.setNominationValues(mapper.writeValueAsString(row));
				} catch (JsonProcessingException e) {
					logger.error("Listing nomination value is unresolvable.");
				}
				listings.add(listing);
			}			
			
			listingService.uploadListings(listings, promoId, userId);
		} else {
			throw new UploadListingIsNullException("Uploaded Listing is null");
		}
		
		return violations;
	}
	
	/**
	 * Default return "ExcelValidationMessages" resource bundle.
	 * @return
	 */
	public ResourceBundle getBundle() {
		return bundle == null ? bundle = ResourceBundle.getBundle(bundleBaseName) : bundle;
	}
	
	private ListingService listingService;
	private String promoId;
	private Long userId;
}
