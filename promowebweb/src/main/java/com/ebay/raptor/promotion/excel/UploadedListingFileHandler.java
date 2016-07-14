package com.ebay.raptor.promotion.excel;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Sheet;

import com.ebay.cbt.sf.service.ServiceExecutor;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Read listings from excel and upload them to server.
 * @author lyan2
 */
public class UploadedListingFileHandler {
	private final ObjectMapper mapper = new ObjectMapper();
	private Logger logger = Logger.getLogger(UploadedListingFileHandler.class);
	
	public UploadedListingFileHandler(
			String promoId, Long userId) {
		this.promoId = promoId;
		this.userId = userId;
		
		mapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
	}
	
	public Set<ConstraintViolation<Object>> handleSheet(Sheet sheet, List<ColumnConfiguration> configs) {
		ISheetReader reader = new SheetReader();
		Set<ConstraintViolation<Object>> violations = new HashSet<ConstraintViolation<Object>>();
		List<Map<String, Object>> list = reader.readSheet(sheet, configs, 3, violations);
		
		// TODO add sales force logic.
		/*ServiceExecutor serviceInstance = ServiceExecutor.getInstance();
		serviceInstance.submitListing(list);*/
		try {
			logger.warn(mapper.writeValueAsString(list));
		} catch (Exception e) {
			logger.error(e);
		}
		
		return violations;
	}
	
	private ListingService listingService;
	private String promoId;
	private Long userId;
}
