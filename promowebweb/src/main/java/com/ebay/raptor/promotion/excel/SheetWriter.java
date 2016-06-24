package com.ebay.raptor.promotion.excel;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.ebay.raptor.promotion.excel.validation.ColumnConstraint;
import com.ebay.raptor.promotion.excel.validation.RangeColumnConstraint;
import com.ebay.raptor.promotion.util.DateUtil;

/**
 * 
 * @author lyan2
 */
public class SheetWriter implements ISheetWriter {
	private final Logger logger = Logger.getLogger(SheetWriter.class.getName());
	private int firstRowNum = 0;
	private Map<Integer, Boolean> picklistMapDone;
	
	/*private Map<Integer, Integer> columnMaxSizes;*/
	

	@Override
	public void createCell(Workbook book, Sheet sheet, Row row, ColumnConfiguration config,
			Object value) {
		if (config == null) return;
		
		if (config.getRawType() == null) {
			// attachment doesn't have raw type.
			createCell(book, row, config, value);
			return;
		}
		
		switch(config.getRawType().toUpperCase()) {
			case "DOUBLE":
				createDoubleCell(book, row, config, value); break;
			case "DATE":
				createDateCell(book, row, config, value); break;
			case "DATETIME":
				createDateTimeCell(book, row, config, value); break;
			case "TIME":
				createTimeCell(book, row, config, value); break;
			case "PICKLIST":
				createPickListCell(book, sheet, row, config, value); break;
			case "STRING":
			case "TEXTAREA":
			default:
				createCell(book, row, config, value); break;
		}
		
	}
	
	private void createCell(Workbook book, Row row, ColumnConfiguration config, Object value) {
		Cell cell = row.createCell(config.getWriteOrder(), Cell.CELL_TYPE_STRING);
		if (value != null) {
			cell.setCellValue(value.toString());
		} else {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}
	
	private void createPickListCell(Workbook book, Sheet sheet, Row row, ColumnConfiguration config, Object value) {
		Cell cell = row.createCell(config.getWriteOrder());
		
		if (picklistMapDone == null || !picklistMapDone.get(config.getWriteOrder())) {
			List<ColumnConstraint> constraints = config.getConstraints();
			for (ColumnConstraint constraint : constraints) {
				if (constraint instanceof RangeColumnConstraint) {
					XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet)sheet);
					XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint)
					    dvHelper.createExplicitListConstraint(((RangeColumnConstraint) constraint).getPickList());
					CellRangeAddressList addressList = new CellRangeAddressList(firstRowNum,  sheet.getLastRowNum(), config.getWriteOrder(), config.getWriteOrder());
					XSSFDataValidation validation =(XSSFDataValidation)dvHelper.createValidation(
					    dvConstraint, addressList);
					
					// Here the boolean value false is passed to the setSuppressDropDownArrow()
					// method. In the hssf.usermodel examples above, the value passed to this
					// method is true.            
					validation.setSuppressDropDownArrow(true);
					
					// Note this extra method call. If this method call is omitted, or if the
					// boolean value false is passed, then Excel will not validate the value the
					// user enters into the cell.
					validation.setShowErrorBox(true);
					sheet.addValidationData(validation);
					break;
				}
			}
			
			picklistMapDone.put(config.getWriteOrder(), true);
		}
		
		
		if (value != null) {
			cell.setCellValue(value.toString());
		} else {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}
	
	private void createDoubleCell(Workbook book, Row row, ColumnConfiguration config, Object value) {
		Cell cell = row.createCell(config.getWriteOrder(), Cell.CELL_TYPE_NUMERIC);
		CellStyle style = book.createCellStyle();
		style.setAlignment(CellStyle.ALIGN_RIGHT);
		
		if (value != null) {
			if (value instanceof Number) {
				cell.setCellValue(((Number)value).doubleValue());
			} else {
				try {
					cell.setCellValue(Double.parseDouble(value.toString()));
				} catch (Exception e) {
					logger.log(Level.WARNING, "Double cell value is not a valid double number.");
				}
			}
		} else {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}
	
	private void createDateCell(Workbook book, Row row, ColumnConfiguration config, Object value) {
		Cell cell = row.createCell(config.getWriteOrder(), Cell.CELL_TYPE_STRING);
		if (value instanceof Date && value != null) {
			cell.setCellValue(DateUtil.formatISODate((Date)value, null));
		} else {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}		
	}
	
	private void createDateTimeCell(Workbook book, Row row, ColumnConfiguration config, Object value) {
		Cell cell = row.createCell(config.getWriteOrder(), Cell.CELL_TYPE_STRING);
		if (value instanceof Date && value != null) {
			cell.setCellValue(DateUtil.formatISODateTime((Date)value, null));
		} else {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}
	
	private void createTimeCell(Workbook book, Row row, ColumnConfiguration config, Object value) {
		Cell cell = row.createCell(config.getWriteOrder(), Cell.CELL_TYPE_STRING);
		if (value instanceof Date && value != null) {
			cell.setCellValue(DateUtil.formatTime((Date)value));
		} else {
			cell.setCellType(Cell.CELL_TYPE_BLANK);
		}
	}
	
	@Override
	public void createCell(Workbook book, Sheet sheet,  Row row, int column, Object value) {
		Cell cell = null;
		
		if (value instanceof Number) {
			cell = row.createCell(column, Cell.CELL_TYPE_NUMERIC);
			CellStyle style = book.createCellStyle();
			style.setAlignment(CellStyle.ALIGN_RIGHT);
			cell.setCellValue(((Number) value).doubleValue());
			cell.setCellStyle(style);
		} else if (value instanceof Date) {
			cell = row.createCell(column, Cell.CELL_TYPE_NUMERIC);
			CellStyle style = book.createCellStyle();
			DataFormat df = book.createDataFormat();
			style.setDataFormat(df.getFormat("yyyy-MM-dd"));
			cell.setCellValue((Date)value);
			cell.setCellStyle(style);
		} else if (value instanceof Boolean) {
			cell = row.createCell(column, Cell.CELL_TYPE_BOOLEAN);
			cell.setCellValue((Boolean)value);
		} else if (value == null) {
			cell = row.createCell(column, Cell.CELL_TYPE_BLANK);
		} else {
			cell = row.createCell(column, Cell.CELL_TYPE_STRING);
			if (value != null) {
				cell.setCellValue(value.toString());
			}
		}
	}

	@Override
	public void writeRow(Workbook book, Sheet sheet, Row row, List<Object> list) {
		int column = 0;
		for (Object value : list) {
			createCell(book, sheet, row, column++, value);
		}
	}

	@Override
	public void writeRow(Workbook book, Sheet sheet, Row row, List<ColumnConfiguration> configs, Map<String, Object> map) {
		for (ColumnConfiguration config : configs) {
			if (config != null) {
				createCell(book, sheet, row, config, map.get(config.getKey()));
			}
		}
	}

	@Override
	public void writeSheet(Workbook book, Sheet sheet, List<ColumnConfiguration> configs, List<Map<String, Object>> list, boolean hasTitle) {
		initPicklistMapDone(configs.size());
		if (hasTitle) createTitle(book, sheet, configs);
		int rowNum = firstRowNum;
		for (Map<String, Object> map : list) {
			Row row = sheet.createRow(rowNum);
			writeRow(book, sheet, row, configs, map);
		}
		
//		adjustColumnsWidth(sheet);
	}

	@Override
	public void writeSheet2(Workbook book, Sheet sheet, List<ColumnConfiguration> configs, List<List<Object>> list, boolean hasTitle) {
		initPicklistMapDone(configs.size());
		if (hasTitle) createTitle(book, sheet, configs);
		
		int rowNum = firstRowNum;
		for (List<Object> obj : list) {
			Row row = sheet.createRow(rowNum);
			writeRow(book, sheet, row, obj);
		}
		
//		adjustColumnsWidth(sheet);
	}

	@Override
	public void createTitle(Workbook book, Sheet sheet, List<ColumnConfiguration> configs) {
		Row row = sheet.createRow(0);
		setFirstRowNum(1);
		for (ColumnConfiguration config : configs) {
			if (config != null) {
				createCell(book, sheet, row, config.getWriteOrder(), config.getTitle());
			}
		}
	}
	
	private void initPicklistMapDone(int columns) {
		picklistMapDone = new HashMap<Integer, Boolean>();
		for (int i = 0; i < columns; i++) {
			picklistMapDone.put(i, false);
		}
	}
	
/*	private void initColumnMaxSizes(int columns) {
		columnMaxSizes = new HashMap<Integer, Integer>();
		for (int i = 0; i < columns; i++) {
			columnMaxSizes.put(i, 0);
		}
	}
	
	private void calculateMaxSize(int column, Object value) {
		Integer maxSize = columnMaxSizes.get(column);
		int size = value != null ? value.toString().length() : 0;
		if (maxSize < size) {
			columnMaxSizes.put(column, size);
		}
	}
	
	private void adjustColumnsWidth(Sheet sheet) {
		Iterator<Integer> iter = columnMaxSizes.keySet().iterator();
		while (iter.hasNext()) {
			int column = iter.next();
			sheet.setColumnWidth(column, columnMaxSizes.get(column));
			logger.log(Level.INFO, "Width of Column " + column + " is: " + columnMaxSizes.get(column));
		}
	}
*/	
	public int getFirstRowNum() {
		return firstRowNum;
	}

	public void setFirstRowNum(int firstRowNum) {
		this.firstRowNum = firstRowNum;
	}


}
