package com.ebay.raptor.promotion.excel;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 
 * @author lyan2
 */
public interface ISheetReader {

	public Object readRow(List<ColumnConfiguration> headers, Row row, Class<?> clazz);
	
	/**
	 * If a row is not valid, read operation will stop and argument violations will be filled with ConstraintViolations.
	 * 
	 * If argument violations is null, this method should read all rows and return all valid objects. But if argument violations is not null,
	 * reading operation should stops when it encounters an invalid object, and violations set will be filled with ConstraintViolations objects.
	 * However, those former valid objects should be returned.
	 * 
	 * @param sheet
	 * @param clazz
	 * @param firstDataRow
	 * @param violations
	 * @return
	 */
	public List<Object> readSheet(Sheet sheet, Class<?> clazz, int firstDataRow, Set<ConstraintViolation<Object>> violations);
}
