package com.ebay.raptor.promotion.excel;

import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.ebay.app.raptor.promocommon.CommonException;
import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.excel.EmptyCellValueException;
import com.ebay.app.raptor.promocommon.excel.IExcelSheetHandler;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.app.raptor.promocommon.util.StringUtil;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;

public class UploadListingSheetHandler implements IExcelSheetHandler {
	private static CommonLogger logger =
            CommonLogger.getInstance(UploadListingSheetHandler.class);
	
	public UploadListingSheetHandler(DealsListingService dealsListingService, String promoId, Long userId) {
		this.dealsListingService = dealsListingService;
		this.promoId = promoId;
		this.userId = userId;
	}

	@Override
	public void handleSheet(XSSFSheet sheet) throws CommonException {
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
		List<Sku> skus = dealsListingService.getSkusByPromotionId(promoId, userId);
		List<DealsListing> uploadedListings = new ArrayList<DealsListing>();
		int rowNum = sheet.getPhysicalNumberOfRows();
		
		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);

			Cell skuCell = row.getCell(0);
			Cell itemIdCell = row.getCell(1);
			Cell itemNameCell = row.getCell(2);
			Cell priceCell = row.getCell(3);
			Cell actPriceCell = row.getCell(4);
			Cell inventoryCell = row.getCell(5);
	
			Object skuNameObj = getCellValue(skuCell);
			Object itemIdObj = getCellValue(itemIdCell);
			Object itemNameObj = getCellValue(itemNameCell);
			Object priceObj = getCellValue(priceCell);
			Object actPriceObj = getCellValue(actPriceCell);
			Object inventoryObj = getCellValue(inventoryCell);
			
			DealsListing listing = new DealsListing();
			
			// check if the sku is in the list
			String skuName = skuNameObj == null ? "" : skuNameObj.toString();
			if (!skus.contains(skuName)) {
				throw new InvalidCellValueException(ErrorType.InvalidSkuCellValue,
						skuCell.getRowIndex() + 1, skuCell.getColumnIndex() + 1, skuName);
			}
			listing.setSkuName(skuName);
			
			// check if the list is set
			if (itemIdObj == null && itemNameObj == null && priceObj == null && actPriceObj == null && inventoryObj == null) {
				continue;
			} else {
				listing.setItemId(validateNumberData(itemIdObj, itemIdCell).longValue());
				listing.setItemName(validateStringData(itemNameObj, itemNameCell));
				listing.setPrice(validateNumberData(priceObj, priceCell).floatValue());
				listing.setActPrice(validateNumberData(actPriceObj, actPriceCell).floatValue());
				listing.setInventory(validateNumberData(inventoryObj, inventoryCell).floatValue());
				uploadedListings.add(listing);
			}
		}
		
		if (uploadedListings.size() > 0) {
			dealsListingService.uploadDealsListings(uploadedListings, promoId, userId);
		}
	}

	private Object getCellValue (Cell cell) {
		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_BLANK) {
			return null;
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			String cellValue = cell.getStringCellValue();
			return StringUtil.isEmpty(cellValue) ? null : cellValue;
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			return cell.getNumericCellValue();
		} else {
			return null;
		}
	}
	
	private String validateStringData (Object cellValue, Cell cell) throws InvalidCellValueException {
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex() + 1;

		if (cellValue == null) {
			throw new EmptyCellValueException(rowIndex, colIndex);
		}

		String value = "";

		try {
			value = (String)cellValue;
		} catch (Exception e) {
			throw new InvalidCellValueException(rowIndex, colIndex, cellValue.toString(), e);
		}
		
		if (value.length() > 200 || value.length() <= 0) {
			throw new InvalidCellValueException(rowIndex, colIndex, value);
		}
		
		return value;
	}
	
	private Double validateNumberData (Object cellValue, Cell cell) throws InvalidCellValueException {
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex() + 1;

		if (cellValue == null) {
			throw new EmptyCellValueException(rowIndex, colIndex);
		}

		Double value = -1.0;

		try {
			value = (double)cellValue;
		} catch (Exception e) {
			throw new InvalidCellValueException(rowIndex, colIndex, cellValue.toString(), e);
		}
		
		if (value < 0) {
			throw new InvalidCellValueException(rowIndex, colIndex, value + "");
		}
		
		return value;
	}

	private DealsListingService dealsListingService;
	private String promoId;
	private Long userId;
}
