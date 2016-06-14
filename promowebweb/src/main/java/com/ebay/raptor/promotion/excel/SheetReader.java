package com.ebay.raptor.promotion.excel;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
import org.apache.poi.ss.usermodel.Sheet;

import com.ebay.raptor.promotion.enums.ICustomEnum;
import com.ebay.raptor.promotion.excel.util.ExcelUtil;

/**
 * SheetReader.readSheet() support localization messages, default message bundle is "ValidationMessages.properties".
 * 
 * @author lyan2
 */
public class SheetReader implements ISheetReader {

	private final Logger logger = Logger.getLogger(SheetReader.class.getName());
	protected SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm:ss");
	public static Map<Type, ArrayList<ColumnConfiguration>> sheetHeaders = new ConcurrentHashMap<Type, ArrayList<ColumnConfiguration>>();
	
	@Override
	public List<Object> readSheet(Sheet sheet, Class<?> clazz, int firstDataRow, Set<ConstraintViolation<Object>> constraintViolations) {
		// preparing validation
//		ValidatorFactory factory = Validation.byDefaultProvider().configure().messageInterpolator(new ResourceBundleMessageInterpolator(new PlatformResourceBundleLocator("ExcelValidationMessages"))).buildValidatorFactory();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();		
				
		ArrayList<ColumnConfiguration> headers = null;
		ArrayList<Object> list = new ArrayList<Object>();

		try {
			if (sheetHeaders.containsKey(clazz)) {
				 headers = sheetHeaders.get(clazz);
			} else {
				headers = ExcelUtil.getClassColumnConfiguration(clazz);
				sheetHeaders.put(clazz, headers);
			}
		} catch (IntrospectionException e) {
			logger.log(Level.SEVERE,
					"ExcelUtil can't get all the header configurations from type - "
							+ clazz.getName());
		}

		if (headers != null) {
			
			int lastRowNum = sheet.getLastRowNum();
			
			for (int i = firstDataRow; i < lastRowNum; i++) {
				logger.log(Level.INFO,	"Beginning to read row " + i);
				Row row = sheet.getRow(i);
				
				if (row != null) {
					Object obj = readRow(headers, row, clazz);
					if (obj != null) {
						Set<ConstraintViolation<Object>> violations = validator.validate(obj);
						
						if (violations == null || violations.isEmpty()) {
							list.add(obj);
						} else {
							if (constraintViolations != null) {
								constraintViolations.addAll(violations);
								break;
							}
						}
					}
				}
			}
			
			return list;

		}

		return null;
	}
	
	/**
	 * Read a single row and convert it into specified type instance. All cell values will be instance properties. 
	 */
	public Object readRow(List<ColumnConfiguration> headers, Row row,
			Class<?> clazz) {
		
		Object o = null;

		if (headers != null && !headers.isEmpty()) {
			try {
				o = clazz.newInstance();
			} catch (InstantiationException e) {
				logger.log(Level.WARNING, "Class " + clazz.getName()
						+ " can't be instantiated!");
				e.printStackTrace();
				return o;
			} catch (IllegalAccessException e) {
				logger.log(Level.WARNING, "Class " + clazz.getName()
						+ " can't be instantiated! Because it's not accssable.");
				e.printStackTrace();
				return o;
			}

			Iterator<ColumnConfiguration> iter = headers.iterator();
			ColumnConfiguration header = null;
			while (iter.hasNext()) {
				header = iter.next();
				
				Cell cell = row.getCell(header.getReadOrder(), Row.RETURN_NULL_AND_BLANK);
				Method setter = header.getPropertyDescriptor().getWriteMethod();
				Object value = readCell(cell, header.getPropertyDescriptor()
						.getReadMethod().getReturnType());

				logger.log(Level.INFO, "Property "
						+ header.getPropertyDescriptor().getName()
						+ " get value " + value);
				try {
					if (value != null ) {
						setter.invoke(o, value);
					}
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e) {
					logger.log(Level.WARNING, "Property "
							+ header.getPropertyDescriptor().getName()
							+ " can't be set on Class " + clazz.getName()
							+ " instance, value is " + value);
					e.printStackTrace();
				}
			}
		}
		return o;
	}
	
	/**
	 * Read cell value and convert them into specified type.
	 * @param cell
	 * @param type
	 * @return
	 */
	public Object readCell(Cell cell, Class<?> type) {
		if (cell == null) return null;
		
		logger.log(Level.INFO, "Cell type " + cell.getCellType()
				+ ", property type " + type);
		
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK: return null;
		case Cell.CELL_TYPE_ERROR: return null;
		case Cell.CELL_TYPE_NUMERIC:
			return readFromNumberCell(cell, type);
		case Cell.CELL_TYPE_STRING:
			return readFromStringCell(cell, type);
		case Cell.CELL_TYPE_BOOLEAN: 
			Boolean value = cell.getBooleanCellValue();
			if (Boolean.class.isAssignableFrom(type)) {
				return value;
			} else {
				return value.toString();
			}
		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
		default: 
			return null;
		}
	}
	
	/**
	 * If cell type is number, its value is Double type.
	 * @param cellVal
	 * @param type
	 * @return
	 */
	protected Object resolveDouble(Double cellVal, Class<?> type) {
		if (Integer.class.isAssignableFrom(type) || int.class.isAssignableFrom(type)) {
			return cellVal.intValue();
		} else if (Long.class.isAssignableFrom(type) || long.class.isAssignableFrom(type)) {
			return cellVal.longValue();
		} else if (Double.class.isAssignableFrom(type) || double.class.isAssignableFrom(type)) {
			return cellVal;
		} else if (Float.class.isAssignableFrom(type) || float.class.isAssignableFrom(type)) {
			return cellVal.floatValue();
		} else if (Short.class.isAssignableFrom(type) || short.class.isAssignableFrom(type)) {
			return cellVal.shortValue();
		} else if (Byte.class.isAssignableFrom(type) || byte.class.isAssignableFrom(type)) {
			return cellVal.byteValue();
		} else if (String.class.isAssignableFrom(type)) {
			return cellVal.toString();
		} else if (Boolean.class.isAssignableFrom(type) || boolean.class.isAssignableFrom(type)) {
			return cellVal.intValue() == 1;
		}
		
		return null;
	}

	/**
	 * Read Integer, Long, Double, Short, Byte, Float, Boolean and String from number cell. 
	 * @param cell
	 * @param type
	 * @return
	 */
	protected Object readFromNumberCell(Cell cell, Class<?> type) {
		Double cellVal = cell.getNumericCellValue();
		
		// CellType of cell which contains date is CELL_TYPE_NUMERIC
		if (Time.class.isAssignableFrom(type)) {
			
			Date date = cell.getDateCellValue();
			String time = timeformat.format(date);
			logger.log(Level.INFO, "Time cell value is " + cellVal);
			logger.log(Level.INFO, "Time cell format is " + time);
			return Time.valueOf(time);
		} else if (Date.class.isAssignableFrom(type)) {
			logger.log(Level.INFO, "Date cell value is " + cellVal);
			return cell.getDateCellValue();
		} 
		
		return resolveDouble(cellVal, type);
	}
	
	/**
	 * Read Integer, Long, Double, Short, Byte, Float, Boolean and String from string cell. 
	 * @param cell
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected Object readFromStringCell(Cell cell, Class<?> type) {
		String text = cell.getStringCellValue();
		
		if (String.class.isAssignableFrom(type)) {
			return text;
		} else if (Boolean.class.isAssignableFrom(type)) {
			return "yes".equalsIgnoreCase(text) || "true".equalsIgnoreCase(text);
		} else if (Date.class.isAssignableFrom(type)) {
			return cell.getDateCellValue();
		} else if (Integer.class.isAssignableFrom(type)
				|| Long.class.isAssignableFrom(type)
				|| Double.class.isAssignableFrom(type)
				|| Float.class.isAssignableFrom(type)
				|| Short.class.isAssignableFrom(type) 
				|| Byte.class.isAssignableFrom(type)) {
			Double cellVal = new Double(text);
			
			return resolveDouble(cellVal, type);
		} else if (type.isEnum()) {
			if (ICustomEnum.class.isAssignableFrom(type)) {
				return resolveExcelEnum(text, (Class<ICustomEnum>)type);
			} else {
				return resolveEnumValue(text, type);
			}
		}
		
		return null;
	}
	
	/**
	 * Resolve ExcelEnum values.
	 * @param value
	 * @param type
	 * @return
	 */
	protected ICustomEnum resolveExcelEnum(String value, Class<ICustomEnum> type) {
		for (ICustomEnum constant : type.getEnumConstants()) {
			if (constant.value().equalsIgnoreCase(value)) {
				return constant;
			}
		}
		
		return null;
	}
	
	/**
	 * Resolve enum values.
	 * @param value
	 * @param type
	 * @return
	 */
	protected <T> T resolveEnumValue(String value, Class<T> type) {
		for (T constant : type.getEnumConstants()) {
			if (constant.toString().equalsIgnoreCase(value)) {
				return constant;
			}
		}
		
		return null;
	}
	
}
