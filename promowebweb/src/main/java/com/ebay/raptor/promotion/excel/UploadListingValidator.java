package com.ebay.raptor.promotion.excel;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.ss.usermodel.Cell;

import com.ebay.app.raptor.promocommon.excel.EmptyCellValueException;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.app.raptor.promocommon.util.StringUtil;

public class UploadListingValidator {

	// validate item Id: number only, 
	public static Long validateItemId (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex() + 1;

		if (cell.getCellType() == Cell.CELL_TYPE_BLANK
				|| (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtil
						.isEmpty(cell.getStringCellValue()))) {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(rowIndex, colIndex));
			}
			
			return null;
		}
		
		if (ex != null) {
			throw ex;
		}

		double itemId = -1;

		try {
			itemId = cell.getNumericCellValue();
		} catch (Exception e) {
			throw new InvalidCellValueException(rowIndex, colIndex, e);
		}
		
		if (itemId < 0) {
			throw new InvalidCellValueException(rowIndex, colIndex);
		}
		
		return (long) itemId;
	}
	
	public static String validateItemTitleAndSku (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex() + 1;

		if (cell.getCellType() == Cell.CELL_TYPE_BLANK
				|| (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtil
						.isEmpty(cell.getStringCellValue()))) {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(rowIndex, colIndex));
			}
			
			return null;
		}
		
		if (ex != null) {
			throw ex;
		}

		String itemTitle = "";

		try {
			itemTitle = cell.getStringCellValue();
		} catch (Exception e) {
			throw new InvalidCellValueException(rowIndex, colIndex, e);
		}
		
		if (itemTitle == null || itemTitle.length() > 200 || itemTitle.length() <= 0) {
			throw new InvalidCellValueException(rowIndex, colIndex);
		}
		
		return itemTitle;
	}
	
	public static Float validatePrice (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex() + 1;

		if (cell.getCellType() == Cell.CELL_TYPE_BLANK
				|| (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtil
						.isEmpty(cell.getStringCellValue()))) {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(rowIndex, colIndex));
			}
			
			return null;
		}
		
		if (ex != null) {
			throw ex;
		}

		double price = -1;

		try {
			price = cell.getNumericCellValue();
		} catch (Exception e) {
			throw new InvalidCellValueException(rowIndex, colIndex, e);
		}
		
		if (price < 0) {
			throw new InvalidCellValueException(rowIndex, colIndex);
		}
		
		return (float) price;
	}
	
	public static Long validateStockNumber (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex() + 1;

		if (cell.getCellType() == Cell.CELL_TYPE_BLANK
				|| (cell.getCellType() == Cell.CELL_TYPE_STRING && StringUtil
						.isEmpty(cell.getStringCellValue()))) {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(rowIndex, colIndex));
			}
			
			return null;
		}
		
		if (ex != null) {
			throw ex;
		}

		double price = -1;

		try {
			price = cell.getNumericCellValue();
		} catch (Exception e) {
			throw new InvalidCellValueException(rowIndex, colIndex, e);
		}
		
		if (price < 0) {
			throw new InvalidCellValueException(rowIndex, colIndex);
		}
		
		return (long) price;
	}
}
