package com.ebay.raptor.promotion.excel.writer;

import java.security.SecureRandom;
import java.util.List;
import java.util.Locale;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
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

//@Component
public class ExcelService {
	public final static String DEALS_LISTING_FILENAME_PREFIX = "Deals_listing_template";
	private final static String DOC_SHEET_NAME_KEY = "doc.sheetName";
	private final static String DOC_FIELD_HEADER_KEY = "doc.field";
	private final static String DOC_LOCKED_HEADER_KEY = "doc.locked";
	private final static String DOC_DESCRIPTION_HEADER_KEY = "doc.description";
	
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
		String docHeader = messageSource.getMessage(DOC_SHEET_NAME_KEY, null, Locale.SIMPLIFIED_CHINESE);

		// create sheet
		Sheet docSheet = workBook.createSheet(docHeader);
		
		// create header: create style and insert data
		CellStyle headerStyle = workBook.createCellStyle();
		headerStyle.setAlignment(CellStyle.ALIGN_LEFT);
		headerStyle.setFillForegroundColor(IndexedColors.LIME.getIndex());
		headerStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerStyle.setWrapText(true);
		Font ft = workBook.createFont();
		ft.setFontName("Arial");
		ft.setBoldweight(Font.BOLDWEIGHT_BOLD);
		headerStyle.setFont(ft);
		
		CellStyle bodyStyle = workBook.createCellStyle();
		bodyStyle.setAlignment(CellStyle.ALIGN_LEFT);
		bodyStyle.setWrapText(true);

		Row row0 = docSheet.createRow(0);
		Cell cell00 = row0.createCell(0);
		cell00.setCellStyle(bodyStyle);
		cell00.setCellValue(docHeader);
		
		Row row1 = docSheet.createRow(1);
		Cell cell10 = row1.createCell(0);
		Cell cell11 = row1.createCell(1);
		Cell cell12 = row1.createCell(2);
		cell10.setCellStyle(headerStyle);
		cell10.setCellValue(messageSource.getMessage(DOC_FIELD_HEADER_KEY, null, Locale.SIMPLIFIED_CHINESE));
		cell11.setCellStyle(headerStyle);
		cell11.setCellValue(messageSource.getMessage(DOC_LOCKED_HEADER_KEY, null, Locale.SIMPLIFIED_CHINESE));
		cell12.setCellStyle(headerStyle);
		cell12.setCellValue(messageSource.getMessage(DOC_DESCRIPTION_HEADER_KEY, null, Locale.SIMPLIFIED_CHINESE));

		CellRangeAddress mergeRegion = new CellRangeAddress(0, 0, 0, 2);
		docSheet.addMergedRegion(mergeRegion);
		docSheet.setFitToPage(true);
		
		// create body
		String [] rowKeys = null;
		String msgKeyPrefix = "";
		
		if (promoSubType != null) {
			switch (promoSubType) {
				case GBH:
					rowKeys = DocSheetKey.GBHKeys;
					msgKeyPrefix = "doc.GBH";
					break;
				case FRES:
					rowKeys = DocSheetKey.FRESKeys;
					msgKeyPrefix = "doc.FRES";
					break;
				case APAC:
					rowKeys = DocSheetKey.APACKeys;
					msgKeyPrefix = "doc.APAC";
					break;
				default:
					return;
			}
		} else {
			rowKeys = DocSheetKey.listingKeys;
			msgKeyPrefix = "doc.listing";
		}
		
		for (int i = 0; i < rowKeys.length; i++) {
			Row row = docSheet.createRow(i + 2);
			
			Cell cell0 = row.createCell(0);
			Cell cell1 = row.createCell(1);
			Cell cell2 = row.createCell(2);

			// set once only
			if (i == 1) {
				docSheet.setColumnWidth(0, 10 * 512);
				docSheet.setColumnWidth(1, 5 * 512);
				docSheet.setColumnWidth(2, 60 * 512);
			}

			cell0.setCellStyle(bodyStyle);
			cell1.setCellStyle(bodyStyle);
			cell2.setCellStyle(bodyStyle);
			cell0.setCellValue(messageSource.getMessage(msgKeyPrefix + '.' + rowKeys[i] + ".key", null, Locale.SIMPLIFIED_CHINESE));

			try {
				cell1.setCellValue(messageSource.getMessage(msgKeyPrefix + '.' + rowKeys[i] + ".locked", null, Locale.SIMPLIFIED_CHINESE));
			} catch (NoSuchMessageException e) {
				// ignore
			}
			
			cell2.setCellValue(messageSource.getMessage(msgKeyPrefix + '.' + rowKeys[i] + ".description", null, Locale.SIMPLIFIED_CHINESE));
		}
	}
	
	public String getExcelName (PromotionSubType promoSubType) {
		return ExcelService.DEALS_LISTING_FILENAME_PREFIX
				+ (promoSubType == null ? "" : "_" + promoSubType)
				+ "_" + random.nextInt() + ".xlsx";
	}
}
