package com.ebay.raptor.promotion.excel.service;

import java.security.SecureRandom;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.export.write.ExcelSheetWriter;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.ListingService;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.PromotionUtil;

@Component
public class ExcelService {
	public final static String LISTING_FILENAME_PREFIX = "Listing_Template";
	
	private static final SecureRandom random = new SecureRandom(); // not really random when cross multi-instance.
	
	@Autowired
	ListingService service;
	
	public XSSFWorkbook getDealListingWorkbook(String promoId, Long uid)
			throws PromoException, MissingArgumentException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		String sheetName = "";

		sheetName = ResourceProvider.ListingRes.skuListFileName;
		List<DealsListing> skuListings = service.getSkuListingsByPromotionId(promoId, uid);
		ExcelSheetWriter<DealsListing> writer = new ExcelSheetWriter<DealsListing>(
				DealsListing.class, workBook, sheetName);
		writer.resetHeaders();
		writer.build(skuListings, 1, 3, 1, 3, 0,
				PromotionUtil.LISTING_TEMP_PASS);

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
