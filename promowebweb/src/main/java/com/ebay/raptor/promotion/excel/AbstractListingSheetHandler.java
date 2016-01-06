package com.ebay.raptor.promotion.excel;

import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import com.ebay.app.raptor.promocommon.excel.EmptyCellValueException;
import com.ebay.app.raptor.promocommon.excel.IExcelSheetHandler;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.app.raptor.promocommon.excel.InvalidDateCellValueException;
import com.ebay.app.raptor.promocommon.util.DateUtil;
import com.ebay.app.raptor.promocommon.util.StringUtil;

public abstract class AbstractListingSheetHandler implements IExcelSheetHandler{

	private static final String DATE_FORMAT_STRING = "YYYY-MM-DD";
	
	protected Object getCellValue (Cell cell) {
		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_BLANK) {
			return null;
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			String cellValue = cell.getStringCellValue();
			return StringUtil.isEmpty(cellValue) ? null : cellValue;
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			if (org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
				 return cell.getDateCellValue();
			}

			return cell.getNumericCellValue();
		} else {
			return null;
		}
	}
	
	protected String validateStringData (Object cellValue, Cell cell) throws InvalidCellValueException {
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex();

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

	protected String validateStringDateData (Object cellValue, Cell cell) throws InvalidCellValueException {
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex();

		if (cellValue == null) {
			throw new EmptyCellValueException(rowIndex, colIndex);
		}

		Date value = null;

		try {
			value = (Date)cellValue;
		} catch (Exception e) {
			throw new InvalidDateCellValueException(rowIndex, colIndex, cellValue.toString(), DATE_FORMAT_STRING, e);
		}
		
		return value == null ? "" : DateUtil.formatSimpleDateWithDash(value);
	}
	
	protected Double validateNumberData (Object cellValue, Cell cell) throws InvalidCellValueException {
		int rowIndex = cell.getRowIndex() + 1;
		int colIndex = cell.getColumnIndex();

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
}
