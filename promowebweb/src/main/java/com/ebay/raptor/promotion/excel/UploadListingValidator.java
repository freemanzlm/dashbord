package com.ebay.raptor.promotion.excel;

import java.util.concurrent.atomic.AtomicReference;

import org.apache.poi.ss.usermodel.Cell;

import com.ebay.app.raptor.promocommon.excel.EmptyCellValueException;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;

public class UploadListingValidator {

	// validate item Id: number only, 
	public static Long validateItemId (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();

		if (cell.getStringCellValue() == "") {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(cell.getRowIndex(), cell.getColumnIndex()));
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
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
		}
		
		if (itemId < 0) {
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex());
		}
		
		return (long) itemId;
	}
	
	public static String validateItemTitleAndSku (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();

		if (cell.getStringCellValue() == "") {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(cell.getRowIndex(), cell.getColumnIndex()));
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
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
		}
		
		if (itemTitle == null || itemTitle.length() > 200 || itemTitle.length() <= 0) {
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex());
		}
		
		return itemTitle;
	}
	
	public static Float validatePrice (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();

		if (cell.getStringCellValue() == "") {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(cell.getRowIndex(), cell.getColumnIndex()));
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
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
		}
		
		if (price < 0) {
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex());
		}
		
		return (float) price;
	}
	
	public static Long validateStockNumber (Cell cell, AtomicReference<EmptyCellValueException> emptyException) throws InvalidCellValueException {
		EmptyCellValueException ex = emptyException.get();

		if (cell.getStringCellValue() == "") {
			if (ex == null) {
				emptyException.set(new EmptyCellValueException(cell.getRowIndex(), cell.getColumnIndex()));
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
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex(), e);
		}
		
		if (price < 0) {
			throw new InvalidCellValueException(cell.getRowIndex(), cell.getColumnIndex());
		}
		
		return (long) price;
	}
}
