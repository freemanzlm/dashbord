package com.ebay.raptor.promotion.excel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.ebay.app.raptor.promocommon.CommonException;
import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.error.ErrorType;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.raptor.promotion.excep.PromoException;
import com.ebay.raptor.promotion.list.service.DealsListingService;
import com.ebay.raptor.promotion.pojo.business.DealsListing;
import com.ebay.raptor.promotion.pojo.business.Sku;

public class UploadListingSheetHandler extends AbstractListingSheetHandler {
	private static CommonLogger logger =
            CommonLogger.getInstance(UploadListingSheetHandler.class);
	
	public UploadListingSheetHandler(DealsListingService dealsListingService,
			String promoId, Long userId) {
		this.dealsListingService = dealsListingService;
		this.promoId = promoId;
		this.userId = userId;
	}

	@Override
	public void handleSheet(XSSFSheet sheet) throws CommonException {
		readHeader(sheet);
		readContent(sheet);
	}
	
	private void readHeader (XSSFSheet sheet) throws InvalidCellValueException {
		Row row = sheet.getRow(0);
		int cellNum = row.getPhysicalNumberOfCells();
		
		if (cellNum != 8) {
			throw new InvalidCellValueException(ErrorType.InvalidHeaderCellValue,
					0, 0, "");
		}

		StringBuilder headerStr = new StringBuilder();
		
		for (int i = 0; i < cellNum; i++) {
			Cell cell = row.getCell(i);
			headerStr.append(cell.getStringCellValue());
		}
		
		logger.log("Sheet Headers:");
		logger.log(headerStr.toString());
	}
	
	private void readContent (XSSFSheet sheet)
			throws InvalidCellValueException, PromoException {
		List<Sku> skus = dealsListingService.getSkusByPromotionId(promoId, userId);
		List<DealsListing> uploadedListings = new ArrayList<DealsListing>();
		Set<Long> itemIds = new TreeSet<Long>();
		int rowNum = sheet.getPhysicalNumberOfRows();
		
		for (int i = 1; i < rowNum; i++) {
			Row row = sheet.getRow(i);

			int cellIndex = 0;
			Cell skuIdCell = row.getCell(cellIndex++);
			Cell skuNameCell = row.getCell(cellIndex++);
			Cell currencyCell = row.getCell(cellIndex++);
			Cell itemIdCell = row.getCell(cellIndex++);
//			Cell itemTitleCell = row.getCell(cellIndex++);
			Cell currPriceCell = row.getCell(cellIndex++);
			Cell dealsPriceCell = row.getCell(cellIndex++);
			Cell stockNumCell = row.getCell(cellIndex++);
			Cell stockReadyDateCell = row.getCell(cellIndex++);
			
	
			Object skuIdObj = getCellValue(skuIdCell, String.class);
			Object skuNameObj = getCellValue(skuNameCell, String.class);
			Object itemIdObj = getCellValue(itemIdCell, Double.class);
//			Object itemTitleObj = getCellValue(itemTitleCell);
			Object currPriceObj = getCellValue(currPriceCell, Double.class);
			Object dealsPriceObj = getCellValue(dealsPriceCell, Double.class);
			Object stockNumObj = getCellValue(stockNumCell, Double.class);
			Object stockReadyDateObj = getCellValue(stockReadyDateCell, Date.class);
			Object currencyObj = getCellValue(currencyCell, String.class);
			
			DealsListing listing = new DealsListing();
			
			// check if the sku is in the list
			String skuId = skuIdObj == null ? "" : (String)skuIdObj;
			String skuName = skuNameObj == null ? "" : (String)skuNameObj;
			String currency = currencyObj == null ? "" : (String)currencyObj;
			Long itemId = itemIdObj == null ? -1 : ((Double)itemIdObj).longValue();
			boolean foundSku = false;

			for (Sku sku : skus) {
				String storedSkuId = sku.getSkuId();
				String storedSkuName = sku.getName();
				if (skuId.equalsIgnoreCase(storedSkuId)
						&& skuName.equalsIgnoreCase(storedSkuName)) {
					foundSku = true;
					break;
				}
			}

			if (!foundSku) {
				// sku id is for internal using only, and user cares about sku name actually.
				throw new InvalidCellValueException(ErrorType.InvalidSkuCellValue,
						skuNameCell.getRowIndex() + 1,
						skuNameCell.getColumnIndex(),
						skuName);
			} else {
				listing.setSkuName(skuName);
				listing.setSkuId(skuId);
				listing.setCurrency(currency);
			}
			
			// duplicate item id was set, stop...
			if (itemId != -1 && !itemIds.add(itemId)) {
				throw new InvalidCellValueException(ErrorType.DuplicateItemFound,
						itemIdCell.getRowIndex() + 1, itemIdCell.getColumnIndex(), itemId + "");
			}
			
			// check if the list is set
			if (itemIdObj == null && currPriceObj == null && dealsPriceObj == null
					&& stockNumObj == null && stockReadyDateObj == null) {
				continue;
			} else {
				listing.setItemId(validateNumberData(itemIdObj, itemIdCell).longValue());
//				listing.setItemTitle(validateStringData(itemTitleObj, itemTitleCell));
				listing.setCurrPrice(validateNumberData(currPriceObj, currPriceCell).floatValue());
				listing.setDealsPrice(validateNumberData(dealsPriceObj, dealsPriceCell).floatValue());
				listing.setStockNum(validateNumberData(stockNumObj, stockNumCell).longValue());
				listing.setStockReadyDate(validateStringDateData(stockReadyDateObj, stockReadyDateCell));
				uploadedListings.add(listing);
			}
		}
		
		if (uploadedListings.size() > 0) {
			dealsListingService.uploadDealsListings(uploadedListings, promoId, userId);
		} else {
			throw new InvalidCellValueException(ErrorType.EmptyListingInExcel, 0, 0, "");
		}
	}

	private DealsListingService dealsListingService;
	private String promoId;
	private Long userId;
}
