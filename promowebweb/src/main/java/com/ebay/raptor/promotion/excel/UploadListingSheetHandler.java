package com.ebay.raptor.promotion.excel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.excel.EmptyCellValueException;
import com.ebay.app.raptor.promocommon.excel.IExcelSheetHandler;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.DealsListing;

public class UploadListingSheetHandler implements IExcelSheetHandler {
	private static CommonLogger logger =
            CommonLogger.getInstance(UploadListingSheetHandler.class);
	
	public UploadListingSheetHandler(DealsListingService dealsListingService, String promoId, Long userId) {
		this.dealsListingService = dealsListingService;
		this.promoId = promoId;
		this.userId = userId;
	}

	@Override
	public void handleSheet(XSSFSheet sheet) throws InvalidCellValueException, PromoException {
		readHeader(sheet);
		readContent(sheet);
	}
	
	private void readHeader (XSSFSheet sheet) {
		Row row = sheet.getRow(0);
		int cellNum = row.getPhysicalNumberOfCells();

		StringBuilder headerStr = new StringBuilder();
		
		for (int i = 0; i < cellNum; i++) {
			Cell cell = row.getCell(i);
			headerStr.append(cell.getStringCellValue());
		}
		
		logger.log("Sheet Headers:");
		logger.log(headerStr.toString());
	}
	
	private void readContent (XSSFSheet sheet) throws InvalidCellValueException, PromoException {
		int rowNum = sheet.getPhysicalNumberOfRows();
		
		List<DealsListing> uploadedListings = new ArrayList<DealsListing>();
		
		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);
			AtomicReference<EmptyCellValueException> emptyException = new AtomicReference<EmptyCellValueException>();

			// TODO - optimize the parsing process
			DealsListing listing = new DealsListing();
			listing.setSkuId(UploadListingValidator.validateItemTitleAndSku(row.getCell(0), emptyException));
			listing.setItemId(UploadListingValidator.validateItemId(row.getCell(1), emptyException));
			listing.setName(UploadListingValidator.validateItemTitleAndSku(row.getCell(2), emptyException));
			listing.setPrice(UploadListingValidator.validatePrice(row.getCell(3), emptyException));
			listing.setActPrice(UploadListingValidator.validatePrice(row.getCell(4), emptyException));
			listing.setInventory(UploadListingValidator.validateStockNumber(row.getCell(5), emptyException));
			
			EmptyCellValueException ex = emptyException.get();
			if (ex != null && ex.getColIndex() == 6) {
				throw ex;
			}
			
			uploadedListings.add(listing);
		}
		
		// TODO - persist listings
		dealsListingService.uploadDealsListings(uploadedListings, promoId, userId);
	}
	
	 private DealsListingService dealsListingService;
	 private String promoId;
	 private Long userId;
}
