package com.ebay.raptor.promotion.excel;

import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author lyan2
 *
 */
public interface ISheetWriter {
	
	public void createCell(Workbook book, Sheet sheet, Row row, int column, Object value);
	
	public void createCell(Workbook book, Sheet sheet, Row row, ColumnConfiguration config, Object value);

	public void writeRow(Workbook book, Sheet sheet, Row row, List<Object> list);
	
	public void writeRow(Workbook book, Sheet sheet, Row row, List<ColumnConfiguration> configs, Map<String, Object> map);
	
	public void createTitle(Workbook book, Sheet sheet, List<ColumnConfiguration> configs);
	
	public void writeSheet(Workbook book, Sheet sheet, List<ColumnConfiguration> configs, List<Map<String, Object>> list, boolean hasTitle);
	
	public void writeSheet2(Workbook book, Sheet sheet, List<ColumnConfiguration> configs, List<List<Object>> list, boolean hasTitle);
	
}
