package com.ebay.raptor.promotion.excel.writer;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import com.ebay.app.raptor.promocommon.MissingArgumentException;
import com.ebay.app.raptor.promocommon.export.write.ExcelSheetWriter;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.APACDealsListing;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.FRESDealsListing;
import com.ebay.raptor.promotion.pojo.business.GBHDealsListing;
import com.ebay.raptor.promotion.pojo.business.PromotionSubType;
import com.ebay.raptor.promotion.service.ResourceProvider;
import com.ebay.raptor.promotion.util.PromotionUtil;

@Component
public class ExcelService {
	public final static String DEALS_LISTING_FILENAME_PREFIX = "Deals_listing_template";
	private final static int DOC_SHEET_ROW = 20;
	private final static int DOC_SHEET_COL = 8;
	private final static String DOC_SHEET_NAME_KEY = "Document.sheetName";
	
	private static final SecureRandom random = new SecureRandom(); // not really random when cross multi-instance.

	@Autowired ResourceBundleMessageSource messageSource;
	@Autowired DealsListingService service;

	public XSSFWorkbook getDealListingWorkbook (String promoId, Long uid,
			PromotionSubType promoSubType) throws PromoException, MissingArgumentException {
		XSSFWorkbook workBook = new XSSFWorkbook();
		String sheetName = "";

		if (promoSubType != null) {
    		switch (promoSubType) {
    			case GBH :
    				sheetName = ResourceProvider.ListingRes.gbhSkuListFileName;
				List<GBHDealsListing> gbhSkuListings =
						service.getSkuListingsByPromotionId(promoId,
								uid, PromotionSubType.GBH);

				com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter<GBHDealsListing> gbhWriter =
						new com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter<GBHDealsListing>(
								GBHDealsListing.class, workBook, sheetName,
								this.messageSource);

				gbhWriter.resetHeaders();
				gbhWriter.build(gbhSkuListings, 1, 3, 1, 3, 0,
						PromotionUtil.LISTING_TEMP_PASS);
    				break;

    			case FRES :
    				sheetName = ResourceProvider.ListingRes.fresSkuListFileName;
    				List<FRESDealsListing> fresSkuListings =
    						service.getSkuListingsByPromotionId(promoId,
    								uid, PromotionSubType.FRES);

    				com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter<FRESDealsListing> fresWriter =
    						new com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter<FRESDealsListing>(
    								FRESDealsListing.class,
    								workBook, sheetName, messageSource);
    				fresWriter.resetHeaders();
    				fresWriter.build(fresSkuListings, 1, 3, 1, 3, 0,
							PromotionUtil.LISTING_TEMP_PASS);
    				break;

    			case APAC :
    				sheetName = ResourceProvider.ListingRes.apacSkuListFileName;
    				List<APACDealsListing> apacSkuListings =
    						service.getSkuListingsByPromotionId(promoId,
    								uid, PromotionSubType.APAC);

    				com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter<APACDealsListing> apacWriter =
    						new com.ebay.raptor.promotion.excel.writer.ExcelSheetWriter<APACDealsListing>(
    								APACDealsListing.class,
    								workBook, sheetName, messageSource);

    				apacWriter.resetHeaders();
    				apacWriter.build(apacSkuListings, 1, 3, 1, 3, 0,
							PromotionUtil.LISTING_TEMP_PASS);
    				break;

    			default :
    				throw new MissingArgumentException("PromoSubType");
    		}
    	} else {
    		sheetName = ResourceProvider.ListingRes.skuListFileName;
    		List<DealsListing> skuListings = service
					.getSkuListingsByPromotionId(promoId, uid, null);
    		ExcelSheetWriter<DealsListing> writer = new ExcelSheetWriter<DealsListing>(DealsListing.class,
					workBook, sheetName);
    		writer.resetHeaders();
            writer.build(skuListings, 1, 3, 1, 3, 0, PromotionUtil.LISTING_TEMP_PASS);
    	}
		
		return workBook;
	}
	
	public void addDocSheet (XSSFWorkbook workBook, PromotionSubType promoSubType) {
		if (promoSubType != null) {

			Sheet docSheet = workBook.createSheet(messageSource.getMessage(DOC_SHEET_NAME_KEY, null, Locale.SIMPLIFIED_CHINESE));
			
			for (int i = 0; i < DOC_SHEET_ROW; i++) {
				Row row = docSheet.createRow(i);

				for (int j = 0; j < DOC_SHEET_COL; j++) {
					Cell cell = row.createCell(j);
					
					if (i == 0) {
						docSheet.setColumnWidth(j, 10 * 512);
					}
					
					if (i==0 && j==0) {
						String msgKey = "";
						switch (promoSubType) {
							case GBH: 
								msgKey = "Document.GBHDoc";
								break;
							case FRES:
								msgKey = "Document.FRESDoc";
								break;
							case APAC:
								msgKey = "Document.APACDoc";
								break;
						default:
							break;
						}

						XSSFCellStyle cellStyle = workBook.createCellStyle();
						cellStyle.setWrapText(true);
						cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
						cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
						cell.setCellStyle(cellStyle);
						
						cell.setCellValue(messageSource.getMessage(msgKey, null, Locale.SIMPLIFIED_CHINESE));
					}
				}
			}
			
			CellRangeAddress mergeRegion = new CellRangeAddress(0, DOC_SHEET_ROW - 1, 0, DOC_SHEET_COL - 1);
			docSheet.addMergedRegion(mergeRegion);
			
			docSheet.setFitToPage(true);
		}
	}
	
	public String getExcelName (PromotionSubType promoSubType) {
		return ExcelService.DEALS_LISTING_FILENAME_PREFIX
				+ (promoSubType == null ? "" : "_" + promoSubType)
				+ "_" + random.nextInt() + ".xlsx";
	}
}
