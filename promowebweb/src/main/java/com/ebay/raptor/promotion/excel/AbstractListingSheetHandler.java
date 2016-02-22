package com.ebay.raptor.promotion.excel;

import java.text.ParseException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;

import com.ebay.app.raptor.promocommon.CommonLogger;
import com.ebay.app.raptor.promocommon.excel.EmptyCellValueException;
import com.ebay.app.raptor.promocommon.excel.IExcelSheetHandler;
import com.ebay.app.raptor.promocommon.excel.InvalidCellValueException;
import com.ebay.app.raptor.promocommon.excel.InvalidDateCellValueException;
import com.ebay.app.raptor.promocommon.util.DateUtil;
import com.ebay.app.raptor.promocommon.util.StringUtil;

public abstract class AbstractListingSheetHandler implements IExcelSheetHandler{

	private static CommonLogger logger = CommonLogger.getInstance(AbstractListingSheetHandler.class);
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
	
	protected Object getCellValue (Cell cell, Class<?> fldType) throws UnsupportFieldDataTypeException, UnsupportExcelDataTypeException {
		if (cell == null) {
			return null;
		}
		
		int cellType = cell.getCellType();

		if (cellType == Cell.CELL_TYPE_BLANK) {
			return null;
		} else if (cellType == Cell.CELL_TYPE_STRING) {
			String cellValue = cell.getStringCellValue();
			
			if (cellValue == null || cellValue.isEmpty()) {
				return null;
			}

			if (String.class.isAssignableFrom(fldType)) {
				return cellValue;
			} else if (Integer.class.isAssignableFrom(fldType)) {
				return Integer.parseInt(cellValue);
			} else if (Long.class.isAssignableFrom(fldType)) {
				return Long.parseLong(cellValue);
			} else if (Float.class.isAssignableFrom(fldType)) {
				return Float.parseFloat(cellValue);
			} else if (Double.class.isAssignableFrom(fldType)) {
				return Double.parseDouble(cellValue);
			} else if (Boolean.class.isAssignableFrom(fldType)) {
				return Boolean.parseBoolean(cellValue);
			} else if (Date.class.isAssignableFrom(fldType)) {
				try {
					return DateUtil.parseSimpleDateWithDash(cellValue);
				} catch (ParseException e) {
					logger.error(String.format("The cell value [%s] does not match the format [%s].",
							cellValue, DateUtil.simple_date_format_dash), e);
				}
			} else {
				throw new UnsupportFieldDataTypeException(fldType.getSimpleName());
			}
		} else if (cellType == Cell.CELL_TYPE_NUMERIC) {
			Double cellValue = cell.getNumericCellValue();

			if (String.class.isAssignableFrom(fldType)) {
				return String.valueOf(cellValue);
			} else if (Integer.class.isAssignableFrom(fldType)) {
				return cellValue.intValue();
			} else if (Long.class.isAssignableFrom(fldType)) {
				return cellValue.longValue();
			} else if (Float.class.isAssignableFrom(fldType)) {
				return cellValue.floatValue();
			} else if (Double.class.isAssignableFrom(fldType)) {
				return cellValue;
			} else if (Boolean.class.isAssignableFrom(fldType)) {
				return cellValue == 1 ? true : false;
			} else if (Date.class.isAssignableFrom(fldType)) {
				return org.apache.poi.ss.usermodel.DateUtil.getJavaDate(cellValue);
			} else {
				throw new UnsupportFieldDataTypeException(fldType.getSimpleName());
			}
		} else {
			throw new UnsupportExcelDataTypeException(cellType + "");
		}
		
		return null;
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
